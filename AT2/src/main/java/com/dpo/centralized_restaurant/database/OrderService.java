package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Request.RequestDish;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class OrderService {

    private Connection conn;
    private Statement s;

    public OrderService(Connection conn) {
        this.conn = conn;
    }

    /**
     * Sets the number of units of a dish to 0, given its name
     * @param name nombre (identificador) del plato a agotar
     * @return Ha salido bien o no la operación
     */
    public synchronized boolean agotarPlato(String name){
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE dish SET units = 0 WHERE name = '" + name + "';");
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Adds a determined number of requests into the request_order table
     * @param listaRequests ArrayList de Requests par ainsertar comandas
     * @return Ha salido bien o no la operación
     */
    public synchronized boolean insertComanda(ArrayList<RequestDish> listaRequests){
        boolean done = true;
        for (RequestDish requestDish : listaRequests){
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            try {
                ps = conn.prepareStatement("INSERT INTO request_order(request_id, dish_id, quantity, actual_service, activation_date, timecost) " +
                        "VALUES(" + requestDish.getRequest_id() + ", " + requestDish.getDish_id() + ", " + requestDish.getUnits() + ", " + requestDish.getActualService() +
                        ", '" + requestDish.getActivation_date() + "', " + requestDish.getTimecost() + ");");
                ps.executeUpdate();

                ps2 = conn.prepareStatement("UPDATE dish SET units = units - " + requestDish.getUnits() + " WHERE id = " + requestDish.getDish_id() + ";");
                ps2.executeUpdate();

            } catch (SQLException e) {
                done = false;
                e.printStackTrace();
                break;
            }
        }

        return done;
    }

    /**
     * Removes a request given its id and unlinks if from its dishes
     * @param requestDish Plato de la comanda a eliminar
     * @param rId Id de la request
     * @return Ha salido bien o no la operación
     */
    public synchronized boolean deleteComanda(RequestDish requestDish, int rId){
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            if (requestDish.getActualService() < 2) {
                ps = conn.prepareStatement("UPDATE dish SET units = units + " + requestDish.getUnits() +
                        " WHERE id = " + requestDish.getDish_id() + " LIMIT 1;");
                ps.executeUpdate();

                ps2 = conn.prepareStatement("DELETE FROM request_order WHERE dish_id = " + requestDish.getDish_id() +
                        " AND request_id = " + rId + " LIMIT 1;");
                ps2.executeUpdate();
                return true;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates a request with the activation date set to the current time
     * @param requestDish Plato de la comanda a actualizar
     * @param rId Id de la request
     * @return Ha salido bien o no la operación
     */
    public synchronized boolean updateComanda(RequestDish requestDish, int rId) {
        PreparedStatement ps = null;
        try {
            if (requestDish.getActualService() < 2) {
                ps = conn.prepareStatement("UPDATE request_order SET actual_service = actual_service + 1, activation_date = NOW()" +
                        "WHERE actual_service < 2 AND request_id = " + rId + " AND dish_id = " + requestDish.getDish_id() + ";");
                ps.executeUpdate();
                return true;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets a request as paid, therefore updating the table as available
     * @param requestPagado Reguest a pagar
     * @return Request Resultado del pago (en función de la request qeu retorne)
     */
    public synchronized Request payBill(Request requestPagado){
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps2 = conn.prepareStatement("UPDATE request SET in_service = 2 WHERE id = " + requestPagado.getId() + ";");
            ps2.executeUpdate();

            String query = "SELECT id, name FROM request WHERE mesa_name >= '" + requestPagado.getMesa_name() + "' ORDER BY id ASC LIMIT 1;";
            ResultSet rs = null;

            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            if(rs.next()){
                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();

                PreparedStatement ps3 = conn.prepareStatement("UPDATE request SET in_service = 1, password = '" + randomUUIDString
                        + "' WHERE id = " + rs.getInt("id") + ";");
                ps3.executeUpdate();

                Request requestAux = new Request(0,rs.getString("name"), randomUUIDString);
                requestAux.setId(rs.getInt("id"));

                return requestAux;
            }
            else {
                PreparedStatement ps3 = conn.prepareStatement("UPDATE mesa SET in_use = false " +
                        "WHERE name = '" + requestPagado.getMesa_name() + "';");
                ps3.executeUpdate();

                return new Request(-1, "NO HAY REQUEST EN COLA PARA ESTA MESA");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Gets the total cost of a whole request, given itself
     * @param requestACalcular Request de la que s quiere calcular el precio
     * @return Precio total calculado
     */
    public synchronized float calcularPrecioTotal(Request requestACalcular){
        try {
            String query = "SELECT SUM(ro.cost) AS coste_total FROM request AS r," +
                    " request_order AS ro WHERE r.id = " + requestACalcular.getId() + " AND r.id = ro.request_id AND ro.activation_date <> null;";
            ResultSet rs = null;

            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            if(rs.next()){
                return rs.getFloat("coste_total");
            }
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Gets into an ArrayList all the requests that are currently in service
     *
     */
    public void comprobarServidos(){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE request_order SET actual_service = 2 WHERE NOW() <= " +
                    "addtime(activation_date, concat(timecost / 60, ':', MOD(timecost, 60), ':00')) AND actual_service = 1;");
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades 32--> " + ex.getSQLState());
        }

    }

    /**
     * Gets the group of orders of a request, given its id
     * @param requestid id de las orders a encontrar
     * @return  Orders encontradas en relación con la request de la mesa
     */
    public synchronized ArrayList<RequestDish> getMyOrders(int requestid){
        try {
            String query = "SELECT ro.id AS id, ro.dish_id AS dish_id, r.id AS request_id, d.name AS name, d.cost AS cost, ro.quantity AS units, " +
                    "d.timecost AS timecost, ro.activation_date AS activation_date, ro.actual_service AS actual_service " +
                    "FROM request AS r, request_order AS ro, dish AS d WHERE r.id = ro.request_id AND r.id = " + requestid +" AND d.id = ro.dish_id;";
            ResultSet rs = null;

            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            ArrayList<RequestDish> result = new ArrayList<>();

            while(rs.next()){
                result.add(new RequestDish(rs.getInt("id"), rs.getInt("dish_id"), rs.getInt("request_id"), rs.getString("name"),
                        rs.getFloat("cost"), rs.getInt("units"), rs.getInt("timecost"), rs.getString("activation_date"), rs.getInt("actual_service")));
            }

            return result;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades 33--> " + ex.getSQLState());
            return null;
        }
    }


    public synchronized ArrayList<RequestDish> getTableOrders(String tableName) {
        try {
            String query = "SELECT ro.id AS id, ro.dish_id AS dish_id, r.id AS request_id, d.name AS name, d.cost AS cost, ro.quantity AS units, " +
                    "d.timecost AS timecost, ro.activation_date AS activation_date, ro.actual_service AS actual_service " +
                    "FROM request AS r, request_order AS ro, dish AS d WHERE r.in_service = 1 AND r.id = ro.request_id AND r.mesa_name= '" + tableName +"' AND d.id = ro.dish_id;";
            ResultSet rs = null;

            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            ArrayList<RequestDish> result = new ArrayList<>();

            while(rs.next()){
                result.add(new RequestDish(rs.getInt("id"), rs.getInt("dish_id"), rs.getInt("request_id"), rs.getString("name"),
                        rs.getFloat("cost"), rs.getInt("units"), rs.getInt("timecost"), rs.getString("activation_date"), rs.getInt("actual_service")));
            }

            return result;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades 34--> " + ex.getSQLState());
            return null;
        }
    }
}

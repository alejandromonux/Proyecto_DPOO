package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Request.RequestOrder;
import com.dpo.centralized_restaurant.Model.Service.Comanda;
import com.dpo.centralized_restaurant.Repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

public class TableService {

    private Connection conn;
    private Statement s;

    public TableService(Connection conn){
        this.conn = conn;
    }

    /**
     * Returns a table, given its ID
     *
     * @param id nombre de la mesa a encontrar
     * @return Mesa encontrada
     */
    public synchronized Mesa findTableById(String id) {
        String query = "SELECT * FROM Mesa AS t WHERE t.id = " + id + " LIMIT 1;";
        ResultSet rs = null;
        Mesa aux = null;
        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                aux = new Mesa(rs.getString("id"), rs.getInt("chairs"));
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return aux;
    }

    /**
     * Returns an arrayList of all the tables that are currently active
     *
     * @return Mesas activas actualmente
     */
    public synchronized ArrayList<Mesa> findActiveTables() {
        String query = "SELECT * FROM mesa AS t WHERE t.active = true;";
        ResultSet rs = null;
        ArrayList<Mesa> aux = new ArrayList<>();
        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                aux.add(new Mesa(rs.getString("name"), rs.getInt("chairs")));
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return aux;
    }

    /**
     * Returns an arrayList of all the tables that are currently active
     *
     * @return Comandas activas
     */
    public synchronized ArrayList<Comanda> findActiveTablesWithInfo() {
        String query = "SELECT * FROM mesa AS t WHERE t.active = true;";
        ResultSet rs = null;
        ResultSet rs2 = null;
        ArrayList<Mesa> aux = new ArrayList<>();
        ArrayList<RequestOrder> orderMesa = new ArrayList<>();
        ArrayList<Comanda> result = new ArrayList<>();
        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                aux.add(new Mesa(rs.getString("name"), rs.getInt("chairs")));
                result.add(new Comanda(rs.getString("name"), 0, 0, 0, "0000-01-01 00:01"));
            }

            int i = 0;
            if (aux.size() > 0) {
                for (Mesa m : aux) {
                    String query2 = "SELECT * FROM request_order AS ro JOIN request AS r ON r.id = ro.request_id " +
                            "JOIN mesa AS m ON m.name = r.mesa_name WHERE r.in_service = 1 AND m.active = true AND m.name = + '" + m.getId() + "';";
                    s = (Statement) conn.createStatement();
                    rs2 = s.executeQuery(query2);
                    while (rs.next()) {
                        orderMesa.add(new RequestOrder(rs2.getInt("request_id"), rs.getInt("dish_id"),
                                rs2.getInt("actual_service"), rs2.getInt("quantity"),
                                rs2.getDate("activation_date").toString()));
                    }
                    int pendingDishes = 0, cookingDishes = 0;
                    for (int j = 0; j < orderMesa.size(); j++) {
                        if (orderMesa.get(j).getActual_service() == 0) {
                            pendingDishes++;
                        }
                        if (orderMesa.get(j).getActual_service() == 1) {
                            cookingDishes++;
                        }
                    }
                    result.get(i).setAllDishes(orderMesa.size());
                    result.get(i).setPendingDishes(pendingDishes);
                    result.get(i).setCookingDishes(cookingDishes);
                    i++;
                }
            }


        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return result;
    }


    /**
     * Get an array with the dishes with most historic orders
     *
     * @param today servicio actual (true) o desde que se creó la base de datos (false)
     * @return top 5 platos del servicio de hoy o del servicio total
     */
    public synchronized ArrayList<OrderedDish> getTopDishes(boolean today) {
        String query;
        ResultSet rs = null;
        ArrayList<OrderedDish> aux = new ArrayList<>();

        if (today) {
            query = "SELECT name, historics_orders FROM dish AS d WHERE d.active = 1 ORDER BY historics_orders DESC LIMIT 5;";
        } else {
            query = "SELECT name, historics_orders FROM dish AS d ORDER BY historics_orders DESC LIMIT 5;";
        }

        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                aux.add(new OrderedDish(rs.getString("name"), rs.getInt("historics_orders")));
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }

        return aux;
    }

    /**
     * Get the economic balance of the dishes given today
     *
     * @param today servicio actual (true) o desde que se creó la base de datos (false)
     * @return ganancia total de hoy/total
     */
    public synchronized float getGain(boolean today) {
        String query;
        ResultSet rs = null;
        float aux = 0;

        if (today) {
            query = "SELECT sum(d.cost*ro.quantity) AS gain FROM (dish AS d JOIN request_order AS ro ON ro.dish_id = d.id) JOIN request AS r ON r.id = ro.request_id WHERE ro.actual_service >= 1 AND r.in_service < 3;";
        } else {
            query = "SELECT sum(d.cost*ro.quantity) AS gain FROM dish AS d JOIN request_order AS ro ON ro.dish_id = d.id WHERE ro.actual_service >= 1;";
        }

        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                aux = rs.getInt("gain");
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }

        return aux;
    }

    /**
     * Get the average price of a table
     * @return precio por mesa medio
     */
    public synchronized float getAvgPrice() {
        String query = "SELECT avg(aux.pricePerTable) AS priceTable FROM (SELECT sum(cost*ro.quantity)/(SELECT count(*) AS n_mesas FROM mesa AS m) AS pricePerTable\n" +
                "        FROM Dish as d JOIN request_order AS ro ON d.name = ro.dish_id JOIN request AS r ON ro.request_id = r.id JOIN mesa AS m ON m.name = r.mesa_name GROUP BY m.name) AS aux;";
        ResultSet rs = null;
        float aux = 0;
        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                aux = rs.getFloat("priceTable");
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return aux;

    }

    /**
     * Get the average number of dishes there are per table
     * @return cantidad de  platos pedidos por mesa de media
     */
    public synchronized float getAvgDishes() {
        String query = "SELECT avg(aux.dishPerTable) AS dishPerTable FROM (SELECT sum(ro.quantity)/(SELECT count(*) AS n_mesas FROM mesa AS m) AS dishPerTable \n" +
                "FROM Dish as d JOIN request_order AS ro ON d.id = ro.dish_id JOIN request AS r ON ro.request_id = r.id JOIN mesa AS m ON m.name = r.mesa_name GROUP BY m.name) AS aux;";
        ResultSet rs = null;
        float aux = 0;
        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                aux = rs.getFloat("dishPerTable");
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return aux;
    }

    /**
     * Deletes a table, given its name
     *
     * @param name nombre de la mesa a eliminar (Es identificador)
     * @return Ha salido bien o no la operación
     */
    public boolean deleteTable(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE mesa SET active = false " +
                    "WHERE name = '" + name + "';");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a new table, given its name and number of chairs
     *
     * @param name nombre de la mesa a crear (identificador)
     * @param chairs sillas de las mesa a crear
     * @return Ha salido bien o no la operación
     */
    public boolean createTable(String name, int chairs) {
        try {
            String query = "SELECT * FROM mesa AS t WHERE t.name = '" + name + "'";
            ResultSet rs = null;
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            if (!rs.next()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO mesa(name, chairs, in_use, active) VALUES('" + name + "', '"
                        + chairs + "', false, true);");
                ps.executeUpdate();
            } else {
                PreparedStatement ps = conn.prepareStatement("UPDATE mesa SET chairs = " + chairs + ", in_use = false, active = true " +
                        "WHERE name = '" + name + "';");
                ps.executeUpdate();
            }

            return true;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            return false;
        }

    }
}

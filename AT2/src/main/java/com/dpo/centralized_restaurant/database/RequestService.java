package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Request.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class RequestService {

    private Connection conn;
    private Statement s;

    public RequestService(Connection conn){
        this.conn = conn;
    }

    /**
     * Gets a request, given its name and the password to access
     * @param requestName
     * @param password
     * @return
     */
    public Request loginRequest(String requestName, String password) {

        String query = "SELECT * FROM request WHERE name = '"+ requestName +
                "' AND password = '" + password + "' LIMIT 1;";
        ResultSet rs = null;
        Request result = null;

        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);
            if (rs.next()) {
                result = new Request(rs.getInt("id") ,rs.getString("name"), rs.getString("password"));
            }
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return result;
    }

    /**
     * Get unactive requests to be processed within an ArrayList
     *
     * @return
     */
    public ArrayList<Request> getRequests() {
        // Busca requests que esten pendientes de entrar o que tengan mesa asignada pero que aun no se hayan ido y pagado
        String query = "SELECT * FROM request WHERE in_service <= 1 ORDER BY id ASC;";
        ResultSet rs = null;
        ArrayList<Request> result = new ArrayList<>();

        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                Request requestAux = new Request(rs.getInt("id"), rs.getString("name"), rs.getString("password"), rs.getString("mesa_name"));
                result.add(requestAux);
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return result;

    }

    /**
     * Get active requests to be processed within an ArrayList
     *
     * @return
     */
    public ArrayList<Request> getRequestsPendientes() {
        // Busca requests que esten pendientes de entrar o que tengan mesa asignada pero que aun no se hayan ido y pagado
        String query = "SELECT id, name FROM request WHERE in_service = 0 ORDER BY id;";
        ResultSet rs = null;
        ArrayList<Request> result = new ArrayList<>();

        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                result.add(new Request(rs.getInt("id"), rs.getString("name")));
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return result;

    }

    /**
     * Gets a single request given its ID
     *
     * @param id
     * @return
     */
    public Request findRequest(int id) {
        // Busca requests que esten pendientes de entrar o que tengan mesa asignada pero que aun no se hayan ido y pagado
        String query = "SELECT * FROM request WHERE id = " + id + ";";
        ResultSet rs = null;

        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            if (rs.next()) {
                return new Request(rs.getInt("id"), rs.getString("name"), rs.getInt("quantity"),
                        rs.getInt("in_service"), rs.getString("mesa_name"), rs.getString("password"));
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return null;

    }


    /**
     * Delete a single request given its id
     *
     * @param id
     * @return
     */
    public Boolean deleteRequest(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM request WHERE request.id = " + id + ";");
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            return false;
        }

    }

    public Boolean deleteRequest(String id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM request WHERE request.name = '" + id + "';");
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            return false;
        }

    }


    /**
     * Creates a new request
     *
     * @param name
     * @param cantidad
     * @return
     */
    public boolean insertRequest(String name, int cantidad) {
        try {
            String query = "SELECT * FROM request WHERE name = '" + name + "' AND in_service < 2;";
            ResultSet rs = null;

            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            if (!(rs.next())) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO request(name, quantity, in_service) VALUES('" + name + "', " + cantidad + ", 0);");
                ps.executeUpdate();
                return true;
            } else {
                return false;
            }



        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return false;


    }

    public int findRequestByTableName(String tableName) {
        String query = "SELECT id FROM request WHERE mesa_name = '" + tableName + "' AND in_service < 2;";
        ResultSet rs = null;
        int idReturn = 0;

        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            if (rs.next()) {
                idReturn = rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return idReturn;
    }

    /**
     * Sets a request as inactive given its password
     * @param password
     * @return
     */
    public Boolean updateRequest(String password) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE request SET request(in_service, pass) VALUES(0, '" + password + "') WHERE ;");
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return false;
    }

    /**
     * Links a new request with an available table that can handle the request's needs
     *
     * @param nuevoRequest
     * @return
     */
    public boolean asignarMesa(Request nuevoRequest) {
        String query = "SELECT name, in_use, chairs FROM mesa AS m WHERE m.active = true AND m.chairs >= " + nuevoRequest.getQuantity() + " ORDER BY chairs ASC;";
        ResultSet rs = null;

        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            boolean recibido = false;

            while (rs.next()) {
                recibido = true;
                // Buscamos si hay una mesa  libre que cumpla con la tolerancia de comensales establecida
                // (Es viable que haya una tolerancia de dos comensales mas por mesa, pero no mas de dos)
                boolean a = rs.getBoolean("in_use");
                int num = rs.getInt("chairs");
                if (!a && num <= nuevoRequest.getQuantity() + 2 && num >= nuevoRequest.getQuantity()) {
                    UUID uuid = UUID.randomUUID();
                    String randomUUIDString_aux = uuid.toString();
                    String randomUUIDString = randomUUIDString_aux.substring(0,8);

                    PreparedStatement ps = conn.prepareStatement("UPDATE mesa SET in_use = true " +
                            "WHERE name = '" + rs.getString("name") + "';");
                    ps.executeUpdate();

                    PreparedStatement ps2 = conn.prepareStatement("UPDATE request SET mesa_name = '" + rs.getString("name") + "', " +
                            "in_service = 1, password = '" + randomUUIDString + "' WHERE id = " + nuevoRequest.getId() + ";");
                    ps2.executeUpdate();

                    nuevoRequest.setMesa_name(rs.getString("name"));
                    nuevoRequest.setPassword(randomUUIDString);
                    return true;
                }
            }

            // No ha habido exito pero si existe una mesa que pueda contener al numero de comensales del pedido
            if (recibido) {
                rs.first();

                // Buscamos mesas ocupadas que entren dentro de la tolerancia para poder poner el pedido de mesa en su cola de espera
                while (rs.next()) {
                    if (rs.getInt("chairs") <= nuevoRequest.getQuantity() + 2 && rs.getInt("chairs") >= nuevoRequest.getQuantity()) {
                        PreparedStatement ps = conn.prepareStatement("UPDATE request SET mesa_name = '" + rs.getString("name") + "', " +
                                "in_service = 0 WHERE id = " + nuevoRequest.getId() + ";");
                        ps.executeUpdate();

                        nuevoRequest.setMesa_name(rs.getString("name"));
                        nuevoRequest.setPassword(null);
                        return true;
                    }
                }

                // Llegado este paso no existen mesas que entren dentro de la tolerancia de comensales
                // Hay que buscar mesas mas grandes
                rs.first();

                //Buscamos una mesa que no este usada, ya sin tener en cuenta la tolerancia
                while (rs.next()) {
                    if (!(rs.getBoolean("in_use"))) {
                        UUID uuid = UUID.randomUUID();
                        String randomUUIDString_aux = uuid.toString();
                        String randomUUIDString = randomUUIDString_aux.substring(0,8);

                        PreparedStatement ps = conn.prepareStatement("UPDATE mesa SET in_use = true " +
                                "WHERE name = '" + rs.getString("name") + "';");
                        ps.executeUpdate();

                        PreparedStatement ps2 = conn.prepareStatement("UPDATE request SET mesa_name = '" + rs.getString("name") + "', " +
                                "in_service = 1, password = '" + randomUUIDString + "' WHERE id = " + nuevoRequest.getId() + ";");
                        ps2.executeUpdate();

                        nuevoRequest.setMesa_name(rs.getString("name"));
                        nuevoRequest.setPassword(randomUUIDString);
                        return true;
                    }
                }

                // Como ultima opcion, ponemos en la cola de espera de la mesa que menos sillas tenga de las opciones disponibles
                rs.first();
                rs.next();

                PreparedStatement ps = conn.prepareStatement("UPDATE request SET mesa_name = '" + rs.getString("name") + "', " +
                        "in_service = 0 WHERE id = " + nuevoRequest.getId() + ";");
                ps.executeUpdate();

                nuevoRequest.setMesa_name(rs.getString("name"));
                nuevoRequest.setPassword(null);
                return true;

            }
            // No hay mesa que pueda contener a ese numero de comensales (se envia mensaje de error a Entrada)
            else {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM request WHERE id = " + nuevoRequest.getId() + ";");
                ps.executeUpdate();

                nuevoRequest.setMesa_name(rs.getString("NO SE HA ENCONTRADO MESA"));
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return false;
    }
}

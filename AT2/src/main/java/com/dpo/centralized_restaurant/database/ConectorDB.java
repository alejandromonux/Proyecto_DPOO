package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;
import com.dpo.centralized_restaurant.Model.Model;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;


/**
 *Links the system with the Database and executes the required queries
 */
    public class ConectorDB {
        private String userName;
        private String password;
        private String db;
        private int port;
        private String url = "jdbc:mysql://localhost";
        private Connection conn = null;
        private Statement s;

        public ConectorDB(String usr, String pass, String db, int port) {
            userName = usr;
            password = pass;
            this.db = db;
            this.port = port;
            url += ":"+port+"/";
            url += db;
            url += "?verifyServerCertificate=false&useSSL=true";
        }

        /**
         * Connects the system to a mysql database
         */
        public void connect() {
            try {
                Class.forName("com.mysql.jdbc.Connection");
                conn = (Connection) DriverManager.getConnection(url, userName, password);
                if (conn != null) {
                    System.out.println("Conexió a base de dades "+url+" ... Ok");
                }

            }
            catch(SQLException ex) {
                System.out.println("Problema al connecta-nos a la BBDD --> "+url);
            }
            catch(ClassNotFoundException ex) {
                System.out.println(ex);
            }

        }

        /* ***********************************************************************************
         ***********************************************************************************
         *
         *  [   CONFIGURATION   ]
         *
         * ***********************************************************************************
         *********************************************************************************** */
        public boolean createConfiguration(String name, Worker worker){
            try {
                // Miramos si existe la configuracion en la base de datos
                String query = "SELECT c.id FROM configuration AS c WHERE c.name = '" + name + "' AND c.worker_name = '" + worker.getUsername() + "';";
                ResultSet rs = null;
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                if(!rs.next()){
                    // Insertamos vinculacion de la configuracion con el worker
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO configuration(name, worker_name) VALUES('" + name + "', '"
                            + worker.getUsername() + "');");
                    ps.executeUpdate();

                    insertarConfiguraciones(name, worker);
                }
                else {
                    // Si existe, borramos datos existentes antes de volver a insertar los datos nuevos
                    PreparedStatement ps = conn.prepareStatement("DELETE FROM configuration_dish WHERE configuration_id = " + rs.getInt("id") + ";");
                    ps.executeUpdate();

                    PreparedStatement ps2 = conn.prepareStatement("DELETE FROM configuration_table WHERE configuration_id = " + rs.getInt("id") + ";");
                    ps2.executeUpdate();

                    insertarConfiguraciones(name, worker);
                }

                return true;

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
                return false;
            }
        }

        public void insertarConfiguraciones(String name, Worker worker) throws SQLException {
            // Buscamos mesas activas
            String query4 = "SELECT m.name FROM mesa AS m WHERE m.active = true;";
            ResultSet rs4 = null;
            s =(Statement) conn.createStatement();
            rs4 = s.executeQuery(query4);

            // Miramos que id se le ha asignado a la nueva configuracion
            String query3 = "SELECT co.id FROM configuration AS co WHERE co.name = '" + name + "' AND co.worker_name = '" + worker.getUsername() + "';";
            ResultSet rs3 = null;
            s =(Statement) conn.createStatement();
            rs3 = s.executeQuery(query3);
            rs3.next();

            // Buscamos dishes activos
            String query2 = "SELECT d.id FROM dish AS d WHERE d.active = true;";
            ResultSet rs2 = null;
            s =(Statement) conn.createStatement();
            rs2 = s.executeQuery(query2);

            while(rs2.next()){
                // Insertamos vinculacion de la configuracion con los dishes activos
                PreparedStatement ps2 = conn.prepareStatement("INSERT INTO configuration_dish(configuration_id, dish_id) VALUES(" + rs3.getInt("id") + ", "
                        + rs2.getInt("id") + ");");
                ps2.executeUpdate();
            }

            while(rs4.next()){
                // Insertamos vinculacion de la configuracion con las mesas activas
                PreparedStatement ps2 = conn.prepareStatement("INSERT INTO configuration_table(configuration_id, mesa_name) VALUES(" + rs3.getInt("id") + ", '"
                        + rs4.getString("name") + "');");
                ps2.executeUpdate();
            }
        }

        public boolean removeConfiguration(String name, Worker worker){
            try {
                String query = "SELECT c.id FROM configuration AS c WHERE c.name = '" + name + "' AND c.worker_name = '" + worker.getUsername() + "';";
                ResultSet rs = null;
                s = (Statement) conn.createStatement();
                rs = s.executeQuery(query);

                rs.next();

                PreparedStatement ps = conn.prepareStatement("DELETE FROM configuration_dish WHERE configuration_id = " + rs.getInt("id") + ";");
                ps.executeUpdate();

                PreparedStatement ps2 = conn.prepareStatement("DELETE FROM configuration_table WHERE configuration_id = " + rs.getInt("id") + ";");
                ps2.executeUpdate();

                PreparedStatement ps3 = conn.prepareStatement("DELETE FROM configuration WHERE id = " + rs.getInt("id") + ";");
                ps3.executeUpdate();

                return true;

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
                return false;
            }

        }

        public boolean pickConfiguration(String name, Worker worker){
            try {
                // Ponemos todos los actives de mesaa y dishes a falso para actualizarlos desde el principio
                PreparedStatement ps = conn.prepareStatement("UPDATE dish SET active = false;");
                ps.executeUpdate();

                PreparedStatement ps4 = conn.prepareStatement("UPDATE mesa SET active = false;");
                ps4.executeUpdate();

                // Buscamos el id de la configuracion elegida
                String query = "SELECT c.id FROM configuration AS c WHERE c.name = '" + name + "' AND c.worker_name = '" + worker.getUsername() + "';";
                ResultSet rs = null;
                s = (Statement) conn.createStatement();
                rs = s.executeQuery(query);
                rs.next();

                // Buscamos aquellos platos que en la configuracion elegida estan activos
                String query2 = "SELECT cd.dish_id FROM configuration_dish AS cd WHERE cd.configuration_id = " + rs.getInt("id") + ";";
                ResultSet rs2 = null;
                s = (Statement) conn.createStatement();
                rs2 = s.executeQuery(query2);

                // Aquellos platos activos en la configuracion son activados
                while(rs2.next()){
                    PreparedStatement ps2 = conn.prepareStatement("UPDATE dish SET active = true WHERE id = " + rs2.getInt("dish_id") + ";");
                    ps2.executeUpdate();
                }

                //Mismo procesoo con las mesas
                String query3 = "SELECT cm.mesa_name FROM configuration_table AS cm WHERE cm.configuration_id = " + rs.getInt("id") + ";";
                ResultSet rs3 = null;
                s = (Statement) conn.createStatement();
                rs3 = s.executeQuery(query3);

                while(rs3.next()){
                    PreparedStatement ps3 = conn.prepareStatement("UPDATE mesa SET active = true WHERE name = '" + rs3.getString("mesa_name") + "';");
                    ps3.executeUpdate();
                }

                return true;

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
                return false;
            }

        }

        /**
         * Get the configuration of a worker, given his name
         * @param name
         * @return
         */
        public ArrayList<Configuration> findConfigurationByWorker(String name) {
            String query = "SELECT * FROM Configuration AS c WHERE c.worker_name = '" + name + "';";
            ResultSet rs = null;
            ArrayList<Configuration> result = new ArrayList<>();

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    Configuration aux = new Configuration(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                    result.add(aux);
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return result;
        }

        /**
         * Get the configuration of a worker, given his name and the own worker
         * @param name
         * @param worker
         * @return
         */
        public ResultSet findConfigurationByNameAndWorker(String name, String worker) {
            String query = "SELECT * FROM Configuration AS c WHERE c.worker_name = '" + worker + "' AND c.name = '" + name + "';";
            ResultSet rs = null;
            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return rs;
        }


        /* ***********************************************************************************
         ***********************************************************************************
         *
         *  [   DISH   ]
         *
         * ***********************************************************************************
         *********************************************************************************** */

        /**
         * Creates a new dish, given its new parameters
         * @param name
         * @param units
         * @param cost
         * @param timeCost
         * @return
         */
        public boolean createDish(String name, int units, double cost, double timeCost){
            try {
                String query = "SELECT * FROM dish AS d WHERE d.name = '" + name + "'";
                ResultSet rs = null;
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                if(!rs.next()){
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO dish(name, cost, units, timecost, active) VALUES('" + name + "', "
                            + cost + ", " + units + ", "+ timeCost + ", true);");
                    ps.executeUpdate();
                }
                else {
                    PreparedStatement ps = conn.prepareStatement("UPDATE mesa SET units = " + units + ", cost = " + cost + ", timecost = "+ timeCost +", active = true " +
                            "WHERE name = '" + name + "';");
                    ps.executeUpdate();
                }

                return true;

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
                return false;
            }

        }

        /**
         * Returns an arrayList of dishes, given their name
         * @param name
         * @return
         */
        public synchronized ArrayList<Dish> findDishByName(String name) {
            String query = "SELECT * FROM Dish AS d WHERE d.name = '" + name + "';";
            ResultSet rs = null;
            ArrayList<Dish> result = new ArrayList<>();
            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    Dish aux = new Dish(
                            rs.getString(0),
                            rs.getDouble(1),
                            rs.getInt(2),
                            rs.getInt(3)
                    );
                    result.add(aux);
                }


            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return result;
        }

        /**
         * Returns an arrayList of dishes that are currently active
         * @return
         */
        public synchronized ArrayList<Dish> findActiveDishes() {
            String query = "SELECT * FROM Dish AS d WHERE d.active = true;";
            ResultSet rs = null;
            ArrayList<Dish> result = new ArrayList<>();

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    Dish aux = new Dish(
                            rs.getString("name"),
                            rs.getDouble("cost"),
                            rs.getInt("units"),
                            rs.getInt("timecost")
                    );
                    result.add(aux);
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return result;
        }

        /**
         * Deletes a dish, given its name
         * @param name
         * @return
         */
        public boolean deleteDish(String name){
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE dish SET active = false " +
                        "WHERE name = '" + name + "';");
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Returns an arrayList of all the dishes that cost more than a given number
         * @param cost
         * @return
         */
        public synchronized ArrayList<Dish> findAllDishesByCostGreaterThan(double cost) {
            String query = "SELECT * FROM Dish AS d WHERE d.cost >= " + cost + ";";
            ResultSet rs = null;
            ArrayList<Dish> result = new ArrayList<>();

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                while (rs.next()) {
                    Dish aux = new Dish(
                            rs.getString(0),
                            rs.getDouble(1),
                            rs.getInt(2),
                            rs.getInt(3)
                    );
                    result.add(aux);
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return result;
        }


        /* ***********************************************************************************
         ***********************************************************************************
         *
         *  [   REQUEST   ]
         *
         * ***********************************************************************************
         *********************************************************************************** */
        public ArrayList<String> getRequests(){
            // Busca requests que esten pendientes de entrar o que tengan mesa asignada pero que aun no se hayan ido y pagado
            String query = "SELECT name FROM request WHERE in_service <= 1";
            ResultSet rs = null;
            ArrayList<String> result = new ArrayList<>();

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                while (rs.next()) {
                    result.add(rs.getString("name"));
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return result;

        }

    public ArrayList<String> getRequestsPendientes(){
        // Busca requests que esten pendientes de entrar o que tengan mesa asignada pero que aun no se hayan ido y pagado
        String query = "SELECT name FROM request WHERE in_service <= 0;";
        ResultSet rs = null;
        ArrayList<String> result = new ArrayList<>();

        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString("name"));
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return result;

    }


    public Boolean deleteRequest(String delete){

            String query = "UPDATE request SET in_service = false WHERE request.name = '" + delete + "';";
            ResultSet rs = null;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                if (rs.next()) {
                    return rs.next();
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }

            return false;
        }


        public boolean insertRequest(String name, int cantidad){
            String query = "INSERT INTO request(name, quantity, in_service) VALUES('" + name + "', " + cantidad + ", 0);";
            ResultSet rs = null;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                if (rs.next()) {
                    return rs.next();
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return false;


        }

        public Boolean updateRequest(String password){
            String query = "UPDATE request SET request(in_service, pass) VALUES(0, '" + password + "') WHERE ;";
            ResultSet rs = null;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                if (rs.next()) {
                    return rs.next();
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return false;
        }

        public boolean asignarMesa(Request nuevoRequest){
            String query = "SELECT name, in_use, chairs FROM mesa WHERE chairs >= " + nuevoRequest.getQuantity() + " ORDER BY chairs ASC;";
            ResultSet rs = null;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                boolean recibido = false;

                while (rs.next()) {
                    recibido = true;
                    // Buscamos si hay una mesa  libre que cumpla con la tolerancia de comensales establecida
                    // (Es viable que haya una tolerancia de dos comensales mas por mesa, pero no mas de dos)
                    if(!(rs.getBoolean("inUse")) && rs.getInt("chairs") <= nuevoRequest.getQuantity() + 2){
                        UUID uuid = UUID.randomUUID();
                        String randomUUIDString = uuid.toString();

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

                // No ha habido exito pero si existe una mesa que pueda contener al umero de comensales del pedido
                if (recibido){
                    rs.first();

                    // Buscamos mesas ocupadas que entren dentro de la tolerancia para poder poner el pedido de mesa en su cola de espera
                    while (rs.next()) {
                        if(rs.getInt("chairs") <= nuevoRequest.getQuantity() + 2){
                            PreparedStatement ps = conn.prepareStatement("UPDATE request SET mesa_name = '" + rs.getString("name") + "', " +
                                    "in_service = 0 WHERE id = " + nuevoRequest.getId() + ";");
                            ps.executeUpdate();

                            nuevoRequest.setMesa_name(rs.getString("name"));
                            return true;
                        }
                    }

                    // Llegado este paso no existen mesas que entren dentro de la tolerancia de comensales
                    // Hay que buscar mesas mas grandes
                    rs.first();

                    //Buscamos una mesa que no este usada, ya sin tener en cuenta la tolerancia
                    while (rs.next()) {
                        if(!(rs.getBoolean("inUse"))){
                            UUID uuid = UUID.randomUUID();
                            String randomUUIDString = uuid.toString();

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



        /* ***********************************************************************************
         ***********************************************************************************
         *
         *  [   TABLE   ]
         *
         * ***********************************************************************************
         *********************************************************************************** */

        /**
         * Returns a table, given its ID
         * @param id
         * @return
         */
        public synchronized Mesa findTableById(String id) {
            String query = "SELECT * FROM Mesa AS t WHERE t.id = "+ id + " LIMIT 1;";
            ResultSet rs = null;
            Mesa aux = null;
            try {
                s =(Statement) conn.createStatement();
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
         * @return
         */
        public synchronized ArrayList<Mesa> findActiveTables() {
            String query = "SELECT * FROM mesa AS t WHERE t.active = true;";
            ResultSet rs = null;
            ArrayList<Mesa> aux = new ArrayList<>();
            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    aux.add(new Mesa(rs.getString("name"), rs.getInt("chairs")));
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return aux;
        }

        public synchronized ArrayList<OrderedDish> getTopDishes(boolean today){
            String query;
            ResultSet rs = null;
            ArrayList<OrderedDish> aux = new ArrayList<>();

            if(today){
                query  = "SELECT name, historicOrders FROM dish AS d JOIN request_order AS ro ON ro.dish_id = d.dish_id WHERE ro.actual_service = true ORDER BY historics_orders DESC LIMIT 5;";
            }else{
                query  = "SELECT name, historics_orders FROM dish AS d ORDER BY historics_orders DESC LIMIT 5;";
            }

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    aux.add(new OrderedDish(    rs.getString("name"), rs.getInt("historicOrders")));
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }

            return aux;
        }

        public synchronized float getGain(boolean today){
            String query;
            ResultSet rs = null;
            float aux = 0;

            if(today){
                query  = "SELECT sum(d.cost) AS gain FROM dish AS d JOIN request_order AS ro ON ro.dish_id = d.dish_id WHERE ro.actual_service = true;";
            }else{
                query  = "SELECT sum(d.cost) AS gain FROM dish AS d JOIN request_order AS ro ON ro.dish_id = d.dish_id;";
            }

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    aux = rs.getInt("gains");
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }

            return aux;
        }

        
        /**
         * Deletes a table, given its name
         * @param name
         * @return
         */
        public boolean deleteTable(String name){
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
         * @param name
         * @param chairs
         * @return
         */
        public boolean createTable(String name, int chairs){
            try {
                String query = "SELECT * FROM mesa AS t WHERE t.name = '" + name + "'";
                ResultSet rs = null;
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                if(!rs.next()){
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO mesa(name, chairs, in_use, active) VALUES('" + name + "', '"
                            + chairs + "', false, true);");
                    ps.executeUpdate();
                }
                else {
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

        /* ***********************************************************************************
         ***********************************************************************************
         *
         *  [   WORKER   ]
         *
         * ***********************************************************************************
         *********************************************************************************** */

        /**
         * Creates a new worker, given his full data
         * @param worker
         * @return
         */
        public boolean createWorker(Worker worker){
            //ResultSet rs = null;
            //Worker aux = null;
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO worker(username, email, password, connected) VALUES('" + worker.getUsername() + "', '"
                        + worker.getEmail() + "', '" + worker.getPassword() + "', " + worker.isConnected() + ");");
                ps.executeUpdate();
                //s =(Statement) conn.createStatement();
                //rs = s.executeQuery(query);
                return true;

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
                return false;
            }

        }

        /**
         * Returns a worker instance, given his name
         * @param name
         * @return
         */
        public Worker findWorkerByName(String name) {
            String query = "SELECT * FROM worker AS w WHERE w.username = '" + name + "';";
            ResultSet rs = null;
            Worker aux = null;
            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                while (rs.next()) {
                    aux = new Worker(rs.getString("username"), rs.getString("email"), null);
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return aux;
        }

        /**
         * Returns a worker instance, given his mail
         * @param email
         * @return
         */
        public Worker findWorkerByEmail(String email) {
            String query = "SELECT * FROM worker AS w WHERE w.email = '" + email + "';";
            ResultSet rs = null;
            Worker aux = null;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    aux = new Worker(rs.getString("username"), rs.getString("email"), null);
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return aux;
        }

        /**
         * Returns a worker instance, given its name and password
         * @param name
         * @param password
         * @return
         */
        public Worker findWorkerByNameAndPassword(String name, String password) {
            String query = "SELECT * FROM worker AS w WHERE w.username = '" + name + "' AND w.password = '" + password + "';";
            ResultSet rs = null;
            Worker aux = null;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                while (rs.next()) {
                    aux = new Worker(rs.getString("username"), rs.getString("email"), null);
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return aux;
        }

        /**
         * Returns a worker instance, given its email and password
         * @param email
         * @param password
         * @return
         */
        public Worker findWorkerByEmailAndPassword(String email, String password) {
            String query = "SELECT * FROM worker AS w WHERE w.email = '" + email + "' AND w.password = '" + password + "';";
            ResultSet rs = null;
            Worker aux = null;
            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                while (rs.next()) {
                    aux = new Worker(rs.getString("username"), rs.getString("email"), null);
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return aux;
        }

        /* ***********************************************************************************
                ***********************************************************************************
                *
                *  [   ESTADO DEL SERVICIO   ]
            *
            * ***********************************************************************************
            *********************************************************************************** */
    /**
     * Returns the  state at which the program was closed before the change to the login panel.
     * State 0: Pre-service
     * State 1: Service
     * State 2: Post-service
     * @return
     */
        public int estadoServicio(){
            try {
                String query = "SELECT estado_servicio FROM variables_importantes;";
                ResultSet rs = null;
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);
                rs.next();


                return rs.getInt("estado_servicio");

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
                return -1;
            }
        }

    public boolean actualizarEstadoServicio(int estado){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE variables_importantes SET estado_servicio = " + estado + ";");
            ps.executeUpdate();

            return true;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            return false;
        }
    }

    public boolean setHistoricos(){
        try {
            String query = "SELECT ro.dish_id AS dish_id, SUM(ro.quantity) AS cantidad_total FROM request AS r, request_order AS ro " +
                    "WHERE r.id = ro.request_id AND r.in_service <= 2 GROUP BY dish_id;";
            ResultSet rs = null;
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while(rs.next()){
                PreparedStatement ps = conn.prepareStatement("UPDATE dish SET historics_orders = historics_orders + " + rs.getInt("cantidad_total") + " " +
                        "WHERE id = " + rs.getInt("dish_id")+ ";");
                ps.executeUpdate();
            }

            PreparedStatement ps2 = conn.prepareStatement("UPDATE request SET in_service = 3;");
            ps2.executeUpdate();

            return true;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            return false;
        }
    }

        /* *********************************************************************************** */

        /**
         * Disconnect the system from the database
         */
        public void disconnect(){
            try {
                conn.close();
                System.out.println("Desconnectat!");
            } catch (SQLException e) {
                System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
            }
        }
    }

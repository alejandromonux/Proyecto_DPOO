package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
import com.dpo.centralized_restaurant.Model.Model;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Worker;

import java.sql.*;
import java.util.ArrayList;


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
        public boolean createConfiguration(String name, Model model){
            try {
                String query = "SELECT * FROM configuration AS c WHERE c.name = '" + name + "'";
                ResultSet rs = null;
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                if(!rs.next()){
                    PreparedStatement ps = conn.prepareStatement("INSERT INTO configuration(name, worker) VALUES('" + name + "', "
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
         * Get the configuration of a worker, given his name
         * @param name
         * @return
         */
        public ResultSet findConfigurationByWorker(String name) {
            String query = "SELECT * FROM Configuration AS c WHERE c.worker = '" + name + "';";
            ResultSet rs = null;
            Configuration configurationAux;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return rs;
        }

        /**
         * Get the configuration of a worker, given his name and the own worker
         * @param name
         * @param worker
         * @return
         */
        public ResultSet findConfigurationByNameAndWorker(String name, String worker) {
            String query = "SELECT * FROM Configuration AS c WHERE c.worker = " + worker + " AND c.name = '" + name + "';";
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
                            rs.getDouble(3)
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
                            rs.getString(0),
                            rs.getDouble(1),
                            rs.getInt(2),
                            rs.getDouble(3)
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
                            rs.getDouble(3)
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
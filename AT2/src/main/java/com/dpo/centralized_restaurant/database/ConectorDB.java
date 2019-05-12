package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
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

        public synchronized ResultSet findConfigurationByWorker(String name) {
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

        public synchronized ResultSet findConfigurationByNameAndWorker(String name, String worker) {
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

        public synchronized ArrayList<Mesa> findActiveTables() {
            String query = "SELECT * FROM mesa AS t WHERE t.active = true;";
            ResultSet rs = null;
            ArrayList<Mesa> aux = new ArrayList<>();
            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);

                while (rs.next()) {
                    aux.add(new Mesa(rs.getString("id"), rs.getInt("chairs")));
                }

            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return aux;
        }

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
        public void disconnect(){
            try {
                conn.close();
                System.out.println("Desconnectat!");
            } catch (SQLException e) {
                System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
            }
        }
    }
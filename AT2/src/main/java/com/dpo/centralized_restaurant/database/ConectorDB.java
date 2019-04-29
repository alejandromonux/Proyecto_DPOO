package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Worker;

import java.sql.*;
import java.util.ArrayList;


    public class ConectorDB {
        static String userName;
        static String password;
        static String db;
        static int port;
        static String url = "jdbc:mysql://localhost";
        static Connection conn = null;
        static Statement s;

        public ConectorDB(String usr, String pass, String db, int port) {
            com.dpo.centralized_restaurant.database.ConectorDB.userName = usr;
            com.dpo.centralized_restaurant.database.ConectorDB.password = pass;
            com.dpo.centralized_restaurant.database.ConectorDB.db = db;
            com.dpo.centralized_restaurant.database.ConectorDB.port = port;
            com.dpo.centralized_restaurant.database.ConectorDB.url += ":"+port+"/";
            com.dpo.centralized_restaurant.database.ConectorDB.url += db;
            com.dpo.centralized_restaurant.database.ConectorDB.url += "?verifyServerCertificate=false&useSSL=true";
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

        public ResultSet findConfigurationByWorker(String name) {
            String query = "SELECT * FROM Configuration AS c WHERE c.worker = " + name + ";";
            ResultSet rs = null;

            try {
                s =(Statement) conn.createStatement();
                rs = s.executeQuery(query);


            } catch (SQLException ex) {
                System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            }
            return rs;
        }

        public ResultSet findConfigurationByNameAndWorker(String name, String worker) {
            String query = "SELECT * FROM Configuration AS c WHERE c.worker = " + worker + " AND c.name = " + name + ";";
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

        public ArrayList<Dish> findDishByName(String name) {
            String query = "SELECT * FROM Dish AS d WHERE d.name = " + name + ";";
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

        public ArrayList<Dish> findActiveDishes() {
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

        public ArrayList<Dish> findAllDishesByCostGreaterThan(double cost) {
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

        public Mesa findTableById(String id) {
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

        public ArrayList<Mesa> findActiveTables() {
            String query = "SELECT * FROM Mesa AS t WHERE t.active = true;";
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

        /* ***********************************************************************************
         ***********************************************************************************
         *
         *  [   WORKER   ]
         *
         * ***********************************************************************************
         *********************************************************************************** */
        public Worker findWorkerByName(String name) {
            String query = "SELECT * FROM Worker AS w WHERE w.username = " + name + ";";
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
            String query = "SELECT * FROM Worker AS w WHERE w.email = " + email + ";";
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
            String query = "SELECT * FROM Worker AS w WHERE w.username = " + name + "AND w.password = " + password + ";";
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
            String query = "SELECT * FROM Worker AS w WHERE w.email = " + email + "AND w.password = " + password + ";";
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
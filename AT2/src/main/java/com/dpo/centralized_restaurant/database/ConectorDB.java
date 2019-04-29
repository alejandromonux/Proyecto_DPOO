package com.dpo.centralized_restaurant.database;

import java.sql.*;

public class ConectorDB {
    static String userName;
    static String password;
    static String db;
    static int port;
    static String url = "jdbc:mysql://localhost";   //canviar esta hardcored a localhost i pot tenir una ip
    static Connection conn = null;
    static Statement s;

    public ConectorDB(String usr, String pass, String db, int port) {
        ConectorDB.userName = usr;
        ConectorDB.password = pass;
        ConectorDB.db = db;
        ConectorDB.port = port;
        ConectorDB.url += ":"+port+"/";
        ConectorDB.url += db;
        ConectorDB.url += "?verifyServerCertificate=false&useSSL=true";
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

    public ResultSet findDishByName(String name) {
        String query = "SELECT * FROM Dish AS d WHERE d.name = " + name + ";";
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public ResultSet findActiveDishes() {
        String query = "SELECT * FROM Dish AS d WHERE d.active = true;";
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public ResultSet findAllDishesByCostGreaterThan(double cost) {
        String query = "SELECT * FROM Dish AS d WHERE d.cost >= " + cost + ";";
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

    public ResultSet findTableById(String id) {
        String query = "SELECT * FROM Mesa AS t WHERE t.id = "+ id + ";";
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public ResultSet findActiveTables() {
        String query = "SELECT * FROM Mesa AS t WHERE t.active = true;";
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
     *  [   WORKER   ]
     *
     * ***********************************************************************************
     *********************************************************************************** */
    public ResultSet findWorkerByName(String name) {
        String query = "SELECT * FROM Worker AS w WHERE w.username = " + name + ";";
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public ResultSet findWorkerByEmail(String email) {
        String query = "SELECT * FROM Worker AS w WHERE w.email = " + email + ";";
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public ResultSet findWorkerByNameAndPassword(String name, String password) {
        String query = "SELECT * FROM Worker AS w WHERE w.username = " + name + "AND w.password = " + password + ";";
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public ResultSet findWorkerByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM Worker AS w WHERE w.email = " + email + "AND w.password = " + password + ";";
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
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
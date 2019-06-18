package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
import com.dpo.centralized_restaurant.Model.Graphics.OrderedDish;
import com.dpo.centralized_restaurant.Model.Preservice.Dish;
import com.dpo.centralized_restaurant.Model.Preservice.Mesa;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Request.RequestDish;
import com.dpo.centralized_restaurant.Model.Request.RequestOrder;
import com.dpo.centralized_restaurant.Model.Service.Comanda;
import com.dpo.centralized_restaurant.Model.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;


/**
 * Links the system with the Database and executes the required queries
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
        url += ":" + port + "/";
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
                System.out.println("Conexió a base de dades " + url + " ... Ok");
            }

        } catch (SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> " + url);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public Connection getConn() {
        return conn;
    }

    /* ***********************************************************************************
     ***********************************************************************************
     *
     *  [   ESTADO DEL SERVICIO   ]
     *
     * ***********************************************************************************
     *********************************************************************************** */

    /**
     * @return the state at which the program was closed before the change to the login panel.
     *      * State 0: Pre-service
     *      * State 1: Service
     *      * State 2: Post-service
     */
    public int estadoServicio() {
        try {
            String query = "SELECT estado_servicio FROM variables_importantes;";
            ResultSet rs = null;
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            rs.next();


            return rs.getInt("estado_servicio");

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            return -1;
        }
    }

    /**
     * Updates the Service Status in the database
     *
     * @param estado estado actual del servicio
     * @return Ha salido bien o no la operación
     */
    public boolean actualizarEstadoServicio(int estado) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE variables_importantes SET estado_servicio = " + estado + ";");
            ps.executeUpdate();

            return true;

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            return false;
        }
    }

    /**
     * Sets the number of historic orders by dish
     *
     * @return Ha salido bien o no la operación
     */
    public boolean setHistoricos() {
        try {
            String query = "SELECT ro.dish_id AS dish_id, SUM(ro.quantity) AS cantidad_total FROM request AS r, request_order AS ro " +
                    "WHERE r.id = ro.request_id AND r.in_service <= 2 GROUP BY ro.dish_id;";
            ResultSet rs = null;
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                PreparedStatement ps = conn.prepareStatement("UPDATE dish SET historics_orders = historics_orders + " + rs.getInt("cantidad_total") + " " +
                        "WHERE id = " + rs.getString("dish_id") + ";");
                ps.executeUpdate();
            }

            PreparedStatement ps2 = conn.prepareStatement("UPDATE request SET in_service = 3;");
            ps2.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement("UPDATE mesa SET in_use = false;");
            ps3.executeUpdate();

            PreparedStatement ps4 = conn.prepareStatement("UPDATE dish SET units = units_backup;");
            ps4.executeUpdate();

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
    public void disconnect() {
        try {
            conn.close();
            System.out.println("Desconnectat!");
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }
}

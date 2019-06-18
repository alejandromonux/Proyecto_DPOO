package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Preservice.Dish;

import java.sql.*;
import java.util.ArrayList;

/**
 * Creates the general configuration of the dishes
 */
public class DishServiceDB {

    private Connection conn;
    private Statement s;

    public DishServiceDB(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new dish, given its new parameters
     *
     * @param name
     * @param units
     * @param cost
     * @param timeCost
     * @return
     */
    public boolean createDish(String name, int units, double cost, int timeCost) {
        try {
            String query = "SELECT * FROM dish AS d WHERE d.name = '" + name + "'";
            ResultSet rs = null;
            s = (java.sql.Statement) conn.createStatement();
            rs = s.executeQuery(query);

            if (!rs.next()) {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO dish(name, cost, units, units_backup, timecost, active) VALUES('" + name + "', "
                        + cost + ", " + units + ", " + units + ", " + timeCost + ", true);");
                ps.executeUpdate();
            } else {
                PreparedStatement ps = conn.prepareStatement("UPDATE mesa SET units = " + units + ", units_backup = " + units + ", cost = " + cost + ", timecost = " + timeCost + ", active = true " +
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
     *
     * @param name
     * @return
     */
    public synchronized ArrayList<Dish> findDishByName(String name) {
        String query = "SELECT * FROM Dish AS d WHERE d.name = '" + name + "';";
        ResultSet rs = null;
        ArrayList<Dish> result = new ArrayList<>();
        try {
            s = (java.sql.Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                Dish aux = new Dish(
                        rs.getInt("id"),
                        rs.getString(1),
                        rs.getDouble(2),
                        rs.getInt(3),
                        rs.getInt(4)
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
     *
     * @return
     */
    public synchronized ArrayList<Dish> findActiveDishes() {
        String query = "SELECT * FROM Dish AS d WHERE d.active = 1 AND d.units > 0;";
        ResultSet rs = null;
        ArrayList<Dish> result = new ArrayList<>();

        try {
            s = (java.sql.Statement) conn.createStatement();
            rs = s.executeQuery(query);

            while (rs.next()) {
                Dish aux = new Dish(
                        rs.getInt("id"),
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
     * Sets the num of available dishes of that name to 0
     * @param name
     * @return
     */
    public synchronized boolean deactivateDish(String name){
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE dish SET units = 0 " +
                    "WHERE name = '" + name + "';");
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a dish, given its name
     *
     * @param name
     * @return
     */
    public boolean deleteDish(String name) {
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
     *
     * @param cost
     * @return
     */
    public synchronized ArrayList<Dish> findAllDishesByCostGreaterThan(double cost) {
        String query = "SELECT * FROM Dish AS d WHERE d.cost >= " + cost + ";";
        ResultSet rs = null;
        ArrayList<Dish> result = new ArrayList<>();

        try {
            s = (java.sql.Statement) conn.createStatement();
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
}
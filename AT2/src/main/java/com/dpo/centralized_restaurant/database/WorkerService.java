package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Worker;

import java.sql.*;

public class WorkerService {

    private Connection conn;
    private Statement s;

    public WorkerService(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new worker, given his full data
     *
     * @param worker Usuario a crear
     * @return Ha salido bien o no la operación
     */
    public boolean createWorker(Worker worker) {
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
     *
     * @param name nombre del usuario
     * @return Usuario encontrado
     */
    public Worker findWorkerByName(String name) {
        String query = "SELECT * FROM worker AS w WHERE w.username = '" + name + "';";
        ResultSet rs = null;
        Worker aux = null;
        try {
            s = (Statement) conn.createStatement();
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
     *
     * @param email mail del usuario
     * @return Usuario encontreado
     */
    public Worker findWorkerByEmail(String email) {
        String query = "SELECT * FROM worker AS w WHERE w.email = '" + email + "';";
        ResultSet rs = null;
        Worker aux = null;

        try {
            s = (Statement) conn.createStatement();
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
     *
     * @param name nombre de usuario
     * @param password contraseña del usuario
     * @return Usuario encontrado
     */
    public Worker findWorkerByNameAndPassword(String name, String password) {
        String query = "SELECT * FROM worker AS w WHERE w.username = '" + name + "' AND w.password = '" + password + "';";
        ResultSet rs = null;
        Worker aux = null;

        try {
            s = (Statement) conn.createStatement();
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
     *
     * @param email mail del usuario
     * @param password contraseña del usuario
     * @return Usuario correspondiente
     */
    public Worker findWorkerByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM worker AS w WHERE w.email = '" + email + "' AND w.password = '" + password + "';";
        ResultSet rs = null;
        Worker aux = null;
        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                aux = new Worker(rs.getString("username"), rs.getString("email"), null);
            }

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return aux;
    }
}

package com.dpo.centralized_restaurant.database;

import com.dpo.centralized_restaurant.Model.Configuration.Configuration;
import com.dpo.centralized_restaurant.Model.Worker;
import com.dpo.centralized_restaurant.Repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;
import java.util.ArrayList;

/**
 * Creates the general configuration of the service
 */
public class ConfigurationService {

    private Connection conn;
    private Statement s;

    public ConfigurationService(Connection conn){
        this.conn = conn;
    }

    /**
     * Creates the configuration of a worker with the name he wishes,
     * if that configuration has not been previusly created
     *
     * @param name
     * @param worker
     * @return
     */
    public boolean createConfiguration(String name, Worker worker) {
        try {
            // Miramos si existe la configuracion en la base de datos
            String query = "SELECT c.id FROM configuration AS c WHERE c.name = '" + name + "' AND c.worker_name = '" + worker.getUsername() + "';";
            ResultSet rs = null;
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

            if (!rs.next()) {
                // Insertamos vinculacion de la configuracion con el worker
                PreparedStatement ps = conn.prepareStatement("INSERT INTO configuration(name, worker_name) VALUES('" + name + "', '"
                        + worker.getUsername() + "');");
                ps.executeUpdate();

                insertarConfiguraciones(name, worker);
            } else {
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

    /**
     * Links the configuration of a given worker with group of dishes and tables
     *
     * @param name
     * @param worker
     * @throws SQLException
     */
    public void insertarConfiguraciones(String name, Worker worker) throws SQLException {
        // Buscamos mesas activas
        String query4 = "SELECT m.name FROM mesa AS m WHERE m.active = true;";
        ResultSet rs4 = null;
        s = (Statement) conn.createStatement();
        rs4 = s.executeQuery(query4);

        // Miramos que id se le ha asignado a la nueva configuracion
        String query3 = "SELECT co.id FROM configuration AS co WHERE co.name = '" + name + "' AND co.worker_name = '" + worker.getUsername() + "';";
        ResultSet rs3 = null;
        s = (Statement) conn.createStatement();
        rs3 = s.executeQuery(query3);
        rs3.next();

        // Buscamos dishes activos
        String query2 = "SELECT d.id FROM dish AS d WHERE d.active = true;";
        ResultSet rs2 = null;
        s = (Statement) conn.createStatement();
        rs2 = s.executeQuery(query2);

        while (rs2.next()) {
            // Insertamos vinculacion de la configuracion con los dishes activos
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO configuration_dish(configuration_id, dish_id) VALUES(" + rs3.getInt("id") + ", "
                    + rs2.getInt("id") + ");");
            ps2.executeUpdate();
        }

        while (rs4.next()) {
            // Insertamos vinculacion de la configuracion con las mesas activas
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO configuration_table(configuration_id, mesa_name) VALUES(" + rs3.getInt("id") + ", '"
                    + rs4.getString("name") + "');");
            ps2.executeUpdate();
        }
    }

    /**
     * Deletes the configuration unlinking its existing attributes
     *
     * @param name
     * @param worker
     * @return
     */
    public boolean removeConfiguration(String name, Worker worker) {
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

    /**
     * Gets a configuration given its name and its worker
     *
     * @param name
     * @param worker
     * @return
     */
    public boolean pickConfiguration(String name, Worker worker) {
        try {
            // Ponemos todos los actives de mesa y dishes a falso para actualizarlos desde el principio
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
            while (rs2.next()) {
                PreparedStatement ps2 = conn.prepareStatement("UPDATE dish SET active = true WHERE id = " + rs2.getInt("dish_id") + ";");
                ps2.executeUpdate();
            }

            //Mismo procesoo con las mesas
            String query3 = "SELECT cm.mesa_name FROM configuration_table AS cm WHERE cm.configuration_id = " + rs.getInt("id") + ";";
            ResultSet rs3 = null;
            s = (Statement) conn.createStatement();
            rs3 = s.executeQuery(query3);

            while (rs3.next()) {
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
     *
     * @param name
     * @return
     */
    public ArrayList<Configuration> findConfigurationByWorker(String name) {
        String query = "SELECT * FROM Configuration AS c WHERE c.worker_name = '" + name + "';";
        ResultSet rs = null;
        ArrayList<Configuration> result = new ArrayList<>();

        try {
            s = (Statement) conn.createStatement();
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
     *
     * @param name
     * @param worker
     * @return
     */
    public ResultSet findConfigurationByNameAndWorker(String name, String worker) {
        String query = "SELECT * FROM Configuration AS c WHERE c.worker_name = '" + worker + "' AND c.name = '" + name + "';";
        ResultSet rs = null;
        try {
            s = (Statement) conn.createStatement();
            rs = s.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }
}

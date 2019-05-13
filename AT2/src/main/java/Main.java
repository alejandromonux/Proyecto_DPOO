import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.View.MainView;
import Initialization.modifyProperties;
import com.dpo.centralized_restaurant.Model.*;
import com.dpo.centralized_restaurant.database.ConectorDB;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
//public static Model Model = new Model();
    public static void main(String[] args){
        Model model = new Model();
        Gson gson = new Gson();
        configJson configInicial;
        try {
            configInicial = gson.fromJson(new FileReader("config.json"), configJson.class);
            modifyProperties aiudame = new modifyProperties(configInicial);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ConectorDB conectorDB =  new ConectorDB(configInicial.getUser(), configInicial.getPassword(), configInicial.getNomBBDD(), configInicial.getPort_BBDD());
                    //ConectorDB conectorDB =  new ConectorDB("root", "mysql1234", "oltpdb_p2", 3306);

                    conectorDB.connect();
                    Controller controlador = new Controller(model, configInicial, conectorDB);
                    MainView vista = new MainView(controlador);
                    controlador.setVista(vista);
                    vista.registerController(controlador);
                    vista.setVisible(true);
                }
            });

        }catch (FileNotFoundException e){
            System.out.println("FileNotFound config.json");

        }

    }
}

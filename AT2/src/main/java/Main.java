import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.Network.ServerEntrada;
import com.dpo.centralized_restaurant.Network.ServerTaula;
import com.dpo.centralized_restaurant.View.MainView;
import Initialization.modifyProperties;
import com.dpo.centralized_restaurant.database.ConectorDB;
import com.google.gson.Gson;
import org.springframework.ui.Model;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
//public static Model model = new Model();
    public static void main(String[] args){

        Gson gson = new Gson();
        configJson configInicial;
        try {
            configInicial = gson.fromJson(new FileReader("config.json"), configJson.class);
            modifyProperties aiudame = new modifyProperties(configInicial);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //ConectorDB conectorDB =  new ConectorDB("root", "mysql1234", "centralized_restaurant", 3306);
                    ConectorDB conectorDB =  new ConectorDB("root", "mysql1234", "oltpdb_p2", 3306);

                    conectorDB.connect();
                    /*Controller controlador = new Controller(model, configInicial, conectorDB);
                    MainView vista = new MainView(controlador);
                    controlador.setVista(vista);
                    vista.registerController(controlador);
                    vista.setVisible(true);*/
                }
            });

        }catch (FileNotFoundException e){
            System.out.println("FileNotFound config.json");

        }

    }
}

import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.Network.ServerEntrada;
import com.dpo.centralized_restaurant.Network.ServerTaula;
import com.dpo.centralized_restaurant.View.MainView;
import Initialization.modifyProperties;
import com.dpo.centralized_restaurant.Model.Model;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
public static  Model model = new Model();
    public static void main(String[] args){

        Gson gson = new Gson();
        configJson configInicial;
        try {
            configInicial = gson.fromJson(new FileReader("config.json"), configJson.class);
            modifyProperties aiudame = new modifyProperties(configInicial);

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    Controller controlador = new Controller(model);
                    MainView vista = new MainView(controlador);
                    controlador.setVista(vista);
                    vista.registerController(controlador);
                    vista.setVisible(true);
                    //de momento creo los servidores en el main, se puede hacer tambien en el controller
                    ServerEntrada entrada = new ServerEntrada(configInicial);
                    entrada.start();

                    ServerTaula taula = new ServerTaula(configInicial);
                    taula.start();
                }
            });

        }catch (FileNotFoundException e){
            System.out.println("FileNotFound config.json");

        }

    }
}

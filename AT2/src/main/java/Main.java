import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.View.MainView;
import Initialization.modifyProperties;
import com.dpo.centralized_restaurant.controller.Controller;
import com.dpo.centralized_restaurant.model.Model;
import com.dpo.centralized_restaurant.view.LogInPanel;
import com.dpo.centralized_restaurant.view.MainView;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
public static  Model model = new Model();
    public static void main(String[] args){

        Gson gson = new Gson();
        try {
            configJson configInicial = gson.fromJson(new FileReader("config.json"), configJson.class);
            modifyProperties aiudame = new modifyProperties(configInicial);
        }catch (FileNotFoundException e){
            System.out.println("FileNotFound config.json");
        }



        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                 MainView vista = new MainView();
                 Controller controlador = new Controller(vista, model);
                 vista.registerController(controlador);
                 vista.setVisible(true);

            }
        });
    }
}

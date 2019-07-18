import com.dpo.centralized_restaurant.Controller.Controller;
import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.View.MainView;
import Initialization.modifyProperties;
import com.dpo.centralized_restaurant.Model.*;
import com.dpo.centralized_restaurant.database.*;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Main void that starts the execution of the program
 */
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
                    conectorDB.connect();

                    ConfigurationService configS = new ConfigurationService(conectorDB.getConn());
                    DishServiceDB dishS = new DishServiceDB(conectorDB.getConn());
                    OrderService orderS = new OrderService(conectorDB.getConn());
                    RequestService requestS = new RequestService(conectorDB.getConn());
                    TableService tableS = new TableService(conectorDB.getConn());
                    WorkerService workerS = new WorkerService(conectorDB.getConn());

                    model.setDishes(dishS.findActiveDishes());
                    model.setMesas(tableS.findActiveTables());

                    Controller controlador = new Controller(model, configInicial, conectorDB, configS, dishS, orderS, requestS, tableS, workerS);
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

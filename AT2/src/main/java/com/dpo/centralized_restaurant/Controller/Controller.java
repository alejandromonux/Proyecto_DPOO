package com.dpo.centralized_restaurant.Controller;

import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.Model.Model;
import com.dpo.centralized_restaurant.Model.Request.Request;
import com.dpo.centralized_restaurant.Model.Service.Comanda;
import com.dpo.centralized_restaurant.Model.Worker;
import com.dpo.centralized_restaurant.Network.ServerEntrada;
import com.dpo.centralized_restaurant.Network.ServerTaula;
import com.dpo.centralized_restaurant.View.ConfigurationPanels.ConfigurationListPanel;
import com.dpo.centralized_restaurant.View.DishPanels.DishListPanel;
import com.dpo.centralized_restaurant.View.MainView;
import com.dpo.centralized_restaurant.View.Service.OrdersService;
import com.dpo.centralized_restaurant.View.TablePanels.TablesListPanel;
import com.dpo.centralized_restaurant.View.Service.RequestsService;
import com.dpo.centralized_restaurant.database.ConectorDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles all the operations between the data and the views, and thus the interaction with the user
 */
public class Controller implements ActionListener {
    private MainView vista;
    private Model model;
    private ServerEntrada serverEntrada;
    private ServerTaula serverTaula;
    private configJson configJson;
    private Worker workerActual;
    private ConectorDB conectorDB;

    public Controller(){

    }

    public Controller(Model model){
        this.model = model;
    }

    public Controller(MainView vista) {
        this.vista = vista;
    }

    public Controller(Model model, configJson configJson, ConectorDB conectorDB) {
        this.model = model;
        this.configJson = configJson;
        this.conectorDB = conectorDB;
    }


    /**
     * It chooses the action to do once the user triggers an event listener
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        JButton aux = (JButton)e.getSource();

        String aux2 = aux.getActionCommand();
        switch (aux.getActionCommand()) {
            // Pre-servicio:
            //---------------------------------------------
            case "MAIN":
                boolean done19 = conectorDB.setHistoricos();

                if(done19){
                    done19 = conectorDB.actualizarEstadoServicio(0);

                    if(done19){
                        vista.changePanel(aux.getActionCommand());
                    }
                    else {
                        JOptionPane.showMessageDialog(vista,
                                "Error al cargar el programa!",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "Error al cargar el programa!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "TABLES":
                vista.changePanel(aux.getActionCommand());
                model.setMesas(conectorDB.findActiveTables());
                vista.getJpTables().setTableList(new TablesListPanel(model.getMesas(), this));
                break;
            case "DISHES":
                vista.changePanel(aux.getActionCommand());
//                model.setDishes(conectorDB.findActiveDishes());
                vista.getJpDish().setJpList(new DishListPanel(model.getDishes(), this));
                break;

            // Starts service
            case "START":
                boolean done = conectorDB.actualizarEstadoServicio(1);

                if(done){
                    vista.hideConfiguration();
                    vista.changePanel(aux.getActionCommand());
                    serverEntrada = new ServerEntrada(configJson, conectorDB, this);
                    serverEntrada.start();
                    serverTaula = new ServerTaula(configJson, conectorDB, this);
                    serverTaula.start();
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "Error al cargar el programa!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                vista.changePanel(aux.getActionCommand());
                break;

            case "TABLE-CREATE":
                vista.changeTablePanel("TABLE-CREATE");
                break;
            case "TABLE-LIST":
                vista.changeTablePanel("TABLE-LIST");
                break;
            case "TABLE-BACK":
                vista.changePanel("MAIN");
                break;
            case "DISH-CREATE":
                vista.getJpDish().changePanel("DISH-CREATE");
                break;
            case "CONFIGURATIONS":
                model.setConfigurations(conectorDB.findConfigurationByWorker(workerActual.getUsername()));
                vista.getJpConfig().setConfigurationList(new ConfigurationListPanel(model.getConfigurations(), this));
                vista.changePanel("CONFIGURATIONS");
                break;
            case "SAVE-CONFIGURATION":
                boolean done6 = conectorDB.createConfiguration(vista.getConfigName(), workerActual);

                if(!done6){
                    JOptionPane.showMessageDialog(vista,
                            "Create configuration not successfull!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    model.setConfigurations(conectorDB.findConfigurationByWorker(workerActual.getUsername()));
                    vista.getJpConfig().setConfigurationList(new ConfigurationListPanel(model.getConfigurations(), this));

                    JOptionPane.showMessageDialog(vista,
                            "Configuration created successfully",
                            "Completed!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                break;

            case "REMOVE-CONFIGURATION":
                String nameToRemove = vista.getJpConfig().getConfigListName();
                boolean done7 = conectorDB.removeConfiguration(nameToRemove, workerActual);

                if(done7){
                    model.setConfigurations(conectorDB.findConfigurationByWorker(workerActual.getUsername()));
                    vista.getJpConfig().setConfigurationList(new ConfigurationListPanel(model.getConfigurations(), this));

                    JOptionPane.showMessageDialog(vista,
                            "Configuration deleted successfully",
                            "Completed!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "Delete configuration not successfull!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;
            case "PICK-THIS-CONFIGURATION":
                String nameToPick = vista.getJpConfig().getConfigListName();
                boolean done8 = conectorDB.pickConfiguration(nameToPick, workerActual);

                if(done8){
                    JOptionPane.showMessageDialog(vista,
                            "Configuration set successfully",
                            "Completed!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "There was an error downloading configuration settings!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;
            case "CONFIGURATION-CREATE":
                vista.changeConfigurationPanel("CONFIGURATION-CREATE");
                break;
            case "CONFIGURATION-LIST":
                vista.changeConfigurationPanel("CONFIGURATION-LIST");
                break;
            case "CONFIGURATION-BACK":
                vista.changePanel("MAIN");
                break;
            case "DISH-LIST":
                vista.getJpDish().changePanel("DISH-LIST");
                break;
            case "DISH-BACK":
                vista.changePanel("MAIN");
                break;

            // Log out button
            case "FORMS":
                workerActual = null;
                vista.changePanel("FORMS");
                vista.changeHeader(false);
                break;
            case "TABLE-ORDERS":
                vista.changePanel("ORDERS");
                ArrayList<Comanda> auxC = new ArrayList<>();
                auxC.add(new Comanda(1,5,3,2,"23445"));
                auxC.add(new Comanda(2,12,7,5,"e45g"));
                auxC.add(new Comanda(3,9,3,6,"4f5g"));
                auxC.add(new Comanda(4,7,3,4,"f45g"));
                vista.setTableDishOrder(auxC, this);

                break;
            case "TABLE-CREATE-ACTION":
                boolean done18 = conectorDB.createTable(vista.getJpTables().getJpCreator().getJtfId().getText(),
                        (int) vista.getJpTables().getJpCreator().getJcbQuantity().getSelectedItem());

                if(!done18){
                    JOptionPane.showMessageDialog(vista,
                            "Insert table not successfull!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    model.setMesas(conectorDB.findActiveTables());
                    vista.getJpTables().setTableList(new TablesListPanel(model.getMesas(), this));

                    /*//Vista del servei
                    vista.setJpReq(new RequestsService(model.getMesas(), this));
                    vista.getJpReq().registerControllers(this);
                    */
                }

                break;
            case "REMOVE-TABLE":
                String tableName = vista.getJpTables().getTableList().getTableName();
                boolean done3 = conectorDB.deleteTable(tableName);

                if(done3){
                    model.setMesas(conectorDB.findActiveTables());
                    vista.getJpTables().setTableList(new TablesListPanel(model.getMesas(), this));
                    /*vista.setJpReq(new RequestsService(model.getMesas(), this));
                    vista.getJpReq().registerControllers(this);*/
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "Delete table not successfull!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "DISH-CREATE-ACTION":
                boolean done4 = conectorDB.createDish(vista.getJpDish().getJpCreator().getJtfName().getText(),
                        (int) vista.getJpDish().getJpCreator().getJcbQuantity().getSelectedItem(),
                        Double.parseDouble(vista.getJpDish().getJpCreator().getJtCost().getText()),
                        Double.parseDouble(vista.getJpDish().getJpCreator().getJtTime().getText()));

                if(!done4){
                    JOptionPane.showMessageDialog(vista,
                            "Insert dish not successfull!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    model.addDish(
                            vista.getJpDish().getJpCreator().getJtfName().getText(),
                            vista.getJpDish().getJpCreator().getJtCost().getText(),
                            vista.getJpDish().getJpCreator().getJcbQuantity().getSelectedItem().toString(),
                            vista.getJpDish().getJpCreator().getJtTime().getText());
                    //Update a la vista
                    vista.getJpDish().setJpList(new DishListPanel(model.getDishes(), this));
                }
                break;

            case "REMOVE-DISH":
                String dishName = vista.getJpDish().getJpList().getDishName();
                boolean done5 = conectorDB.deleteTable(dishName);

                if(done5){
                    model.setDishes(conectorDB.findActiveDishes());
                    vista.getJpDish().setJpList(new DishListPanel(model.getDishes(), this));
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "Delete dish not successfull!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;

            case "BACK-TO-MAIN":
                vista.changePanel("MAIN");
                vista.changeHeader(true);
                break;

                //Pantalla Login:
                //----------------------------------------------------

            case "CREATING USER":
                String registerName = vista.getJpLogIn().getJtfRegisterName().getText();
                String registerEmail = vista.getJpLogIn().getJtfRegisterEmail().getText();
                char[] passwordArray = vista.getJpLogIn().getJpfRegisterPassword().getPassword();
                String registerPassword = new String(vista.getJpLogIn().getJpfRegisterPassword().getPassword());
                String confirmPassword = new String (vista.getJpLogIn().getJpfConfirmPassword().getPassword());

                // Campos estan vacios
                if (registerName.isEmpty() || registerEmail.isEmpty() || registerPassword.isEmpty() || confirmPassword.isEmpty()){
                    JOptionPane.showMessageDialog(vista,
                            "Empty fields must be completed",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                // Contraseña confirmada no coincide
                else if (!(registerPassword.equals(confirmPassword))) {
                    JOptionPane.showMessageDialog(vista,
                            "Passwords don't match",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                // Menos de 6 caracteres
                else if (passwordArray.length < 6){
                    JOptionPane.showMessageDialog(vista,
                            "Password must be at least 6 characters long",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    //Debe seguir formato email
                    String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
                    Pattern pattern = Pattern.compile(emailPattern);
                    Matcher matcher = pattern.matcher(registerEmail);

                    // Debe contener minusculas, mayusculas y un valor numerico
                    boolean upperCase = false;
                    boolean lowerCase = false;
                    boolean number = false;

                    for(int i = 0; i < passwordArray.length; i++){
                        if (passwordArray[i] >= '0' && passwordArray[i] <= '9'){
                            number = true;
                        }
                        if (passwordArray[i] >= 'A' && passwordArray[i] <= 'Z'){
                            upperCase = true;
                        }
                        if (passwordArray[i] >= 'a' && passwordArray[i] <= 'z'){
                            lowerCase = true;
                        }
                    }

                    if (!(upperCase && lowerCase && number)){
                        JOptionPane.showMessageDialog(vista,
                                "Password must contain at least a lower-case letter, upper-case letter and a number",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else if (!(matcher.matches())){
                        JOptionPane.showMessageDialog(vista,
                                "Email format is not valid",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    // Comprobamos que no existe ningun worker con mismo nombre o email
                    else if (conectorDB.findWorkerByName(registerName) != null) {
                        JOptionPane.showMessageDialog(vista,
                                "Username is already taken",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else if (conectorDB.findWorkerByEmail(registerEmail) != null){
                        JOptionPane.showMessageDialog(vista,
                                "Email is already registered",
                                "Error!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        // Registramos usuario en sistema y, posteriormente, lo logueamos automaticamente
                        workerActual = new Worker(registerName, registerEmail, registerPassword);
                        boolean done2 = conectorDB.createWorker(workerActual);

                        if(done2){
                            vista.changeUserView(workerActual.getUsername());
                            vista.changePanel("MAIN");
                            vista.changeHeader(true);
                        }
                        else {
                            JOptionPane.showMessageDialog(vista,
                                    "Register not successfull!",
                                    "Error!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }

                break;

            case "LOGGING USER":
                String loginUsername = vista.getJpLogIn().getJtfLoginUsername().getText();
                String loginPassword = new String(vista.getJpLogIn().getJpfLoginPassword().getPassword());

                if (loginUsername.isEmpty() || loginPassword.isEmpty()){
                    JOptionPane.showMessageDialog(vista,
                            "Empty fields must be completed",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    Worker workerName = conectorDB.findWorkerByNameAndPassword(loginUsername, loginPassword);
                    Worker workerEmail = conectorDB.findWorkerByEmailAndPassword(loginUsername, loginPassword);

                    if (workerName != null){
                        workerActual = workerName;
                        vista.changeUserView(workerActual.getUsername());
                        buscarEstado();

                    }

                    else if (workerEmail != null){
                        workerActual = workerEmail;
                        vista.changeUserView(workerActual.getUsername());
                        buscarEstado();


                    }

                    else {
                        JOptionPane.showMessageDialog(vista,
                            "Incorrect user or password",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }

                break;


                // Servicio:
                //---------------------------------------------
            case "SERVICE-DISHES":
                vista.changePanel("SERVICE-DISHES");
                break;
            case "REQUESTS":
                actualizarVistaRequests(conectorDB.getRequestsPendientes());
                vista.changePanel("REQUESTS");
            break;
            case "SEE-TABLE-ORDERS":
                vista.changePanel("SPECIFIC-ORDERS");
                break;
            case "BACKSERVICE" :
                vista.changePanel("START");
            break;

            case "POSTSERVICE" :
                boolean done20 = conectorDB.actualizarEstadoServicio(2);

                if(done20){
                    vista.changePanel("POSTSERVICE");
                    try {
                        serverEntrada.closeServer();
                    }catch (IndexOutOfBoundsException b){

                    }
                    serverEntrada = null;
                    try {
                        serverTaula.closeServer();
                    }catch (NullPointerException s){

                    }
                    serverTaula = null;
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "Error al cargar el programa!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

            break;
            case "GRAPHICS":
                vista.changePanel("STADISTICS");
            break;
            case "TODAYGRAPH":
                vista.getJpStats().changePanel("TODAYGRAPH");
            break;
            case "TOTALGRAPH":
                vista.getJpStats().changePanel("TOTALGRAPH");
            break;
            case "STATSPANEL" :
                vista.getJpStats().changePanel("STATS");
            break;
            case "ACCEPT-REQUEST":
                int id = vista.getJpReq().getSelectedRequestName();
                Request requestAceptado = conectorDB.findRequest(id);

                conectorDB.asignarMesa(requestAceptado);
                serverEntrada.updateAssignment(requestAceptado);
                actualizarVistaRequests(conectorDB.getRequestsPendientes());

                break;
            case "DECLINE-REQUEST" :
                int idAEliminar = vista.getJpReq().getSelectedRequestName();

                boolean finished = conectorDB.deleteRequest(idAEliminar);

                if(finished){
                    serverEntrada.updateAll(conectorDB.getRequests());
                    actualizarVistaRequests(conectorDB.getRequestsPendientes());
                }
                else {
                    JOptionPane.showMessageDialog(vista,
                            "Error al eliminar la peticion de mesa!",
                            "Error!",
                            JOptionPane.ERROR_MESSAGE);
                }

                break;

        }

    }

    /**
     * Status Machine, checks the current status of the service and runs the right choice
     * (pre-service, service, post-service)
     */
    private void buscarEstado(){
        int estadoServicio = conectorDB.estadoServicio();

        if(estadoServicio == -1){
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar el programa!",
                    "Error!",
                    JOptionPane.ERROR_MESSAGE);
        }
        else if(estadoServicio == 0){
            vista.changeHeader(true);
            vista.changePanel("MAIN");
            vista.showConfiguration();
        }
        else if(estadoServicio == 1){
            serverEntrada = new ServerEntrada(configJson, conectorDB, this);
            serverEntrada.start();
            serverTaula = new ServerTaula(configJson, conectorDB, this);
            serverTaula.start();
            vista.changeHeader(true);
            vista.changePanel("START");
            vista.hideConfiguration();
        }
        else if(estadoServicio == 2){
            vista.changeHeader(true);
            vista.changePanel("POSTSERVICE");
            vista.hideConfiguration();
        }
    }

    public void actualizarVistaRequests(ArrayList<Request> listaRequests){
        vista.updateRequests(listaRequests, this);
    }

    public MainView getVista() {
        return vista;
    }

    public void setVista(MainView vista) {
        this.vista = vista;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}

package com.dpo.centralized_restaurant.Controller;

import com.dpo.centralized_restaurant.Model.Configuration.configJson;
import com.dpo.centralized_restaurant.Model.Worker;
import com.dpo.centralized_restaurant.Network.ServerEntrada;
import com.dpo.centralized_restaurant.Network.ServerTaula;
import com.dpo.centralized_restaurant.View.DishPanels.DishListPanel;
import com.dpo.centralized_restaurant.View.MainView;
import com.dpo.centralized_restaurant.View.TablePanels.TablesListPanel;
import com.dpo.centralized_restaurant.View.Service.RequestsService;
import com.dpo.centralized_restaurant.Model.Model;
import com.dpo.centralized_restaurant.database.ConectorDB;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton aux = (JButton)e.getSource();

        String aux2 = aux.getActionCommand();
        switch (aux.getActionCommand()) {
            // Pre-servicio:
            //---------------------------------------------
            case "MAIN":
                vista.changePanel(aux.getActionCommand());
                break;
            case "TABLES":
                vista.changePanel(aux.getActionCommand());
                break;
            case "DISHES":
                vista.changePanel(aux.getActionCommand());
                break;

            // Starts service
            case "START":
                vista.changePanel(aux.getActionCommand());
                serverEntrada = new ServerEntrada(configJson, conectorDB, this);
                serverEntrada.start();
                serverTaula = new ServerTaula(configJson, conectorDB, this);
                serverTaula.start();
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
                break;
            case "TABLE-CREATE-ACTION":
                model.addTable(
                        vista.getJpTables().getJpCreator().getJtfId().getText(),
                        vista.getJpTables().getJpCreator().getJcbQuantity().getSelectedItem().toString()
                );
                //Update a la vista
                vista.getJpTables().setTableList(new TablesListPanel(model.getMesas(), this));
                //Vista del servei
                vista.setJpReq(new RequestsService(model.getMesas(), this));
                vista.getJpReq().registerControllers(this);
                break;
            case "REMOVE-TABLE":

                String dishname = vista.getJpDish().getJpList().getDishName();

                System.out.println(dishname);
                break;
            case "DISH-CREATE-ACTION":
                model.addDish(
                        vista.getJpDish().getJpCreator().getJtfName().getText(),
                        vista.getJpDish().getJpCreator().getJtCost().getText(),
                        vista.getJpDish().getJpCreator().getJcbQuantity().getSelectedItem().toString(),
                        vista.getJpDish().getJpCreator().getJtTime().getText());
                //Update a la vista
                vista.getJpDish().setJpList(new DishListPanel(model.getDishes(), this));
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
                        boolean done = conectorDB.createWorker(workerActual);

                        if(done){
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

            case "LOGGING-USER":
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
                        vista.changePanel("MAIN");
                        vista.changeHeader(true);
                        vista.changeUserView(workerActual.getUsername());
                    }

                    else if (workerEmail != null){
                        workerActual = workerEmail;
                        vista.changePanel("MAIN");
                        vista.changeHeader(true);
                        vista.changeUserView(workerActual.getUsername());
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
                vista.changePanel("REQUESTS");
            break;
            case "BACKSERVICE" :
                vista.changePanel("START");
            break;
            case "POSTSERVICE" :
                vista.changePanel("POSTSERVICE");
            break;
        }

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

package com.dpo.centralized_restaurant.Controller;

import com.dpo.centralized_restaurant.View.DishPanels.DishListPanel;
import com.dpo.centralized_restaurant.View.MainView;
import com.dpo.centralized_restaurant.View.TablePanels.TablesListPanel;
import com.dpo.centralized_restaurant.View.Service.RequestsService;
import com.dpo.centralized_restaurant.Model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private MainView vista;
    private Model model;

    public Controller(MainView vista) {
        this.vista = vista;
    }

    public Controller(MainView vista, Model model) {
        this.vista = vista;
        this.model = model;
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
            case "START":
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
            case "DISH-LIST":
                vista.getJpDish().changePanel("DISH-LIST");
                break;
            case "DISH-BACK":
                vista.changePanel("MAIN");
                break;
            case "FORMS":
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
                vista.getJpTables().setTableList(new TablesListPanel(model.getMesas()));
                //Vista del servei
                vista.setJpReq(new RequestsService(model.getMesas()));
                vista.getJpReq().registerControllers(this);
                break;
            case "DISH-CREATE-ACTION":
                model.addDish(
                        vista.getJpDish().getJpCreator().getJtfName().getText(),
                        vista.getJpDish().getJpCreator().getJtCost().getText(),
                        vista.getJpDish().getJpCreator().getJcbQuantity().getSelectedItem().toString(),
                        vista.getJpDish().getJpCreator().getJtTime().getText());
                //Update a la vista
                vista.getJpDish().setJpList(new DishListPanel(model.getDishes()));
                break;
            case "CREATING USER":
                vista.changePanel("MAIN");
                vista.changeHeader(true);
                if (vista.getJpLogIn().getJtfRegisterName().getText().isEmpty()){
                    JDialog error = new JDialog(vista, "tu mudare en vinager");
                }
                System.out.println("GOOD");
                JDialog error = new JDialog(vista, "perf");
                //dfVerifier.verify(vista.getJpLogIn().getJtfRegisterName().getText());
                break;
            case "BACK-TO-MAIN":
                vista.changePanel("MAIN");
                vista.changeHeader(true);
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
}

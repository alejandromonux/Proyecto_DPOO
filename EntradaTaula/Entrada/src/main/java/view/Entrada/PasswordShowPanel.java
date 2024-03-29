package view.Entrada;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

public class PasswordShowPanel extends JPanel {

    private String sPass;
    private String requestName;

    private JButton jbtnAccept;
    private JLabel jname;
    private JLabel jpassWord;
    private JPanel jpNameForm;
    private JPanel jpPass;
    private JPanel jpGeneralForm;

    public PasswordShowPanel() {
        //creacio del panell sense informacio
        this.setLayout(new GridLayout(2,1,0,10));

        sPass = "default_password";
        requestName = "default_name";

        jpGeneralForm = new JPanel(new GridLayout(2,1));

        jpNameForm = new JPanel(new GridLayout(1,2,10,0));
        JLabel jRequest = new JLabel("Name of Request:");
        jRequest.setForeground(Color.white);
        jname = new JLabel(requestName);
        jname.setForeground(Color.white);
        jpNameForm.add(jRequest);
        jpNameForm.add(jname);

        jpPass = new JPanel(new GridLayout(1,2,10,0));
        JLabel jPass = new JLabel("Password assigned:");
        jPass.setForeground(Color.white);
        jpassWord = new JLabel(sPass);
        jpassWord.setEnabled(true);
        Font font = jpassWord.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        jpassWord.setFont(font.deriveFont(attributes));
        jpassWord.setForeground(Color.white);
        jpPass.add(jPass);
        jpPass.add(jpassWord);

        jpGeneralForm.add(jpNameForm);
        jpGeneralForm.add(jpPass);

        JPanel jpBtn = new JPanel();
        jbtnAccept = new JButton("Accept");
        jbtnAccept.setPreferredSize(new Dimension(750,30));
        jpBtn.add(jbtnAccept);

        Color cAux = new Color(0x1A0D08);
        jpBtn.setBackground(cAux);
        jpGeneralForm.setBackground(cAux);
        jpNameForm.setBackground(cAux);
        jpPass.setBackground(cAux);

        this.add(jpGeneralForm);
        this.add(jpBtn);
    }

    public PasswordShowPanel(String name, String pass){
        //creacio del panell amb la inforacio inserida
        this.setLayout(new GridLayout(2,1,0,10));

        sPass = pass;
        requestName = name;

        jpGeneralForm = new JPanel(new GridLayout(2,1));

        jpNameForm = new JPanel(new GridLayout(1,2,10,0));
        JLabel jRequest = new JLabel("Name of Request:");
        jRequest.setForeground(Color.white);
        jname = new JLabel(requestName);
        jname.setForeground(Color.white);
        jpNameForm.add(jRequest);
        jpNameForm.add(jname);

        jpPass = new JPanel(new GridLayout(1,2,10,0));
        JLabel jPass = new JLabel("Password assigned:");
        jPass.setForeground(Color.white);
        jpassWord = new JLabel(sPass);
        jpassWord.setForeground(Color.white);
        jpPass.add(jPass);
        jpPass.add(jpassWord);

        jpGeneralForm.add(jpNameForm);
        jpGeneralForm.add(jpPass);

        JPanel jpBtn = new JPanel();
        jbtnAccept = new JButton("Accept");
        jbtnAccept.setPreferredSize(new Dimension(750,30));
        jpBtn.add(jbtnAccept);

        Color cAux = new Color(0x1A0D08);
        jpBtn.setBackground(cAux);
        jpGeneralForm.setBackground(cAux);
        jpNameForm.setBackground(cAux);
        jpPass.setBackground(cAux);

        this.add(jpGeneralForm);
        this.add(jpBtn);

    }

    public void registerController(ActionListener c) {
        jbtnAccept.setActionCommand("SEEN-PASSWORD");
        jbtnAccept.addActionListener(c);
    }

    public void showPasswordTo(String name, String password) {
        sPass = password;
        requestName = name;
        jname.revalidate();
        jname.repaint();
        jpassWord.repaint();
        jpassWord.revalidate();
        jpNameForm.revalidate();
        jpNameForm.repaint();
        jpPass.revalidate();
        jpPass.repaint();
        jpGeneralForm.revalidate();
        jpGeneralForm.repaint();
        this.revalidate();
        this.repaint();
    }

    /**
     * Sets values to empty
     */
    public void resetValues() {
        sPass = "";
        requestName = "";
    }
}

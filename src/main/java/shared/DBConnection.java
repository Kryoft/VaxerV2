package shared;

import centrivaccinali.Registrazioni;
import centrivaccinali.SwingAwt;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection extends Registrazioni {
    //Border bordo = new Border() ;
    private final JLabel user_label = new JLabel("USERNAME:");
    private final int width_user_label = 250;

    private final JLabel password_label = new JLabel("PASSWORD:");
    private final int width_password_label = 250;

    private final JLabel conferma_password_label = new JLabel("CONFERMA PASSWORD:");
    private final int width_conferma_password_label = 250;


    private final JTextField user = new JTextField();
    private final int width_nome_centro = 310;

    private final JPasswordField password = new JPasswordField();
    private final int width_via = 310;

    private final JPasswordField conferma_password = new JPasswordField();
    private final int width_conferma_password = 310;

    //Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536 @Marceca
    private final int margin = display_width * 10 / 1536,
            margin_y = display_height / 8,
            first_row_x = (display_width / 2) - (width_nome_centro + margin * 4),
            first_row_y = (int) (0.2 * display_height),
            second_column = first_row_x + width_user_label + margin,
            second_row_x = (display_width / 2) - (width_via + margin * 4),
            second_row_y = (int) (0.2 * display_height) + margin_y,

    third_row_x = (display_width / 2) - (width_via + margin * 4),
            third_row_y = (int) (0.2 * display_height) + margin_y * 2,
            fourth_row_x = second_column - 200,
            fourth_row_y = (int) (0.2 * display_height) + margin_y * 3;


    public DBConnection() {
        initWindow();
    }

    public static void main(String[] args) {
        new DBConnection();
    }

    /**
     * Permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione del centro vaccinale
     *
     * @author Daniele Caspani, Manuel Marceca
     */
    private void initWindow() {
        settings("Accesso al database");

        layered_pane.add(user_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,              //nome_label
                width_user_label, base_height), 16, 1, false);

        layered_pane.add(user, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, first_row_y,                   //nome_centro
                width_nome_centro, base_height), 15, 1, false);

        layered_pane.add(password_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //indirizzo_label
                width_password_label, base_height), 16, 1, false);

        layered_pane.add(password, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, second_row_y,                   //via
                width_via, base_height), 20, 1, false);

        layered_pane.add(conferma_password_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(third_row_x, third_row_y,            //indirizzo_label
                width_conferma_password_label, base_height), 16, 1, false);

        layered_pane.add(conferma_password, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, third_row_y,            //indirizzo_label
                width_conferma_password, base_height), 20, 1, false);

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x + width_buttons, fourth_row_y,      //conferma
                250, 60), 18, 1, false);
        Color c = new Color(51, 153, 255);
        conferma.setText("Connetti");
        conferma.setBackground(c);
        conferma.setForeground(Color.white);
        conferma.setFont(new Font("Courier", Font.BOLD, 20));

        conferma.addActionListener(this);
        border = user.getBorder();
        background_panel.setBorder(new LineBorder(c, 60, false));
        setVisible(true);

    }
    /**
     * Metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == conferma) {

            Connection conn = null;

            try {
                conn = DBInterface.connected("");
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            char[] controlla = password.getPassword();
            char[] controlla2 = conferma_password.getPassword();


            if(CredenzialiDB.isValid(user.getText(),new String(controlla),new String(controlla2))) {
                JOptionPane.showMessageDialog(this, "Credenziali corrette");
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException ex) {
                        new DBException("",ex.getSQLState(),ex.getMessage());
                    }

                    JOptionPane.showMessageDialog(this, "Connessione verificata");
                    new RemoteManager();
                    this.dispose();
                }
            }
            else{
                SwingAwt.modificaBordo("", password, border);
                if(CredenzialiDB.getIndiceMessage(0).contains("User")){
                    SwingAwt.modificaBordo("",user,border);
                    }
                else
                    user.setBorder(border);

                if(CredenzialiDB.getIndiceMessage(1).contains("password")) {
                    SwingAwt.modificaBordo("", password, null);
                }
                else
                    password.setBorder(border);

                if(CredenzialiDB.getIndiceMessage(2).contains("coincidono")) {
                    SwingAwt.modificaBordo("", conferma_password, border);
                    SwingAwt.modificaBordo("", password, border);
                }
                else{
                        conferma_password.setBorder(border);
                    }
                JOptionPane.showMessageDialog(this, CredenzialiDB.getMessage());

            }
        }
    }
}



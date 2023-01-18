package server;

import centrivaccinali.SwingAwt;
import shared.DBException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * classe che estende <code> ServerGraphics</code> e viene utilizzata dal server per verificare il corretto accesso al database.
 */

public class DBConnection extends ServerGraphics{

    /**
     * <code> JLabel</code> utilizzate per definire lo username la password e il campo conferma password.
     */
    private final JLabel user_label = new JLabel("USERNAME:"),
            password_label = new JLabel("PASSWORD:"),
            conferma_password_label = new JLabel("CONFERMA PASSWORD:");


    /**
     * <code> JTextField</code> utilizzate per definire la casella di testo dello username.
     */
    private final JTextField user = new JTextField();

    /**
     * <code> JPasswordField</code> utilizzate per definire la casella di testo dei campi password e conferma password
     */
    private final JPasswordField password = new JPasswordField(),
            conferma_password = new JPasswordField();

    /**
     * Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536
     * @author Marceca Manuel
     */

    private final int margin = display_width * 10 / 1536,
            margin_y = display_height / 8,
            first_row_x = (display_width / 2) - (width_text + margin * 4),
            first_row_y = (int) (0.2 * display_height),
            second_column = first_row_x + width_label + margin,
            second_row_x = (display_width / 2) - (width_text + margin * 4),
            second_row_y = (int) (0.2 * display_height) + margin_y,

    third_row_x = (display_width / 2) - (width_text + margin * 4),
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
     * Permette la definizione dell' interfaccia grafica tramite la classe <code> JFrame</code>.
     *
     * @author Daniele Caspani
     */
    private void initWindow() {
        settings("Accesso al database");

        layered_pane.add(user_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,              //user_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(user, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, first_row_y,                   //user
                width_text, base_height), 15, 1, false);

        layered_pane.add(password_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //pass_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(password, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, second_row_y,                   //password
                width_text, base_height), 20, 1, false);

        layered_pane.add(conferma_password_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(third_row_x, third_row_y,            //conf_pass_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(conferma_password, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, third_row_y,            //conf_pass
                width_text, base_height), 20, 1, false);

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x + width_buttons, fourth_row_y,      //connetti
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
        border = user.getBorder();
    }
    /**
     * Metodo ereditato dall'interfaccia <code>ActionListener</code>
     * @author Daniele Caspani, Marceca Manuel
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == conferma) {

            Connection conn = null;
            CredenzialiDB.setUser(user.getText());
            CredenzialiDB.setPassword(String.valueOf(password.getPassword()));
            boolean password_equal = String.valueOf(password.getPassword()).
                    equals(String.valueOf(conferma_password.getPassword())),
                    user_vuoto=CredenzialiDB.getUser().isBlank(),
                    password_vuota = CredenzialiDB.getPassword().isBlank();

            if(user_vuoto) {
                SwingAwt.modificaBordo("", user, null);
                JOptionPane.showMessageDialog(this, "Errore:Campo username vuoto");
            }
            else
                SwingAwt.modificaBordo("border",user,border);
            if(password_vuota){
                SwingAwt.modificaBordo("", password, null);
                SwingAwt.modificaBordo("", conferma_password, null);
                JOptionPane.showMessageDialog(this, "Errore: Password non inserita");
            }
            else{
                SwingAwt.modificaBordo("",password,border);
                SwingAwt.modificaBordo("",conferma_password,border);
            }
            if(password_equal && !user_vuoto && !password_vuota) {
                try {
                    conn = DBManager.connected("", CredenzialiDB.getUser(), CredenzialiDB.getPassword());
                    if(conn!=null)
                        JOptionPane.showMessageDialog(this, "Connessione verificata");
                    else
                        JOptionPane.showMessageDialog(this, "Connessione non riuscita: Credenziali non corrette");
                    conn.close();
                    new RemoteManager();
                    this.dispose();
                } catch (SQLException ex) {
                    new DBException("", ex.getSQLState(), ex.getMessage());
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(this, "Errore di connessione remota","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            if(!password_equal){

                SwingAwt.modificaBordo("", password, null);
                SwingAwt.modificaBordo("", conferma_password, null);
                JOptionPane.showMessageDialog(this, "Errore: Le password non coincidono");

            }
            else if(!password_vuota){
                SwingAwt.modificaBordo("border", password, border);
                SwingAwt.modificaBordo("border", conferma_password, border);
            }
        }
    }

        }



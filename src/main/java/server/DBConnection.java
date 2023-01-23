package server;

import client.centrivaccinali.SwingAwt;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import static server.CredenzialiDB.message;

/**
 * Classe che estende <code>ServerGraphics</code> e viene utilizzata dal server per accedere al DBMS.
 */
public class DBConnection extends ServerGraphics {

    /**
     * <code>JLabel</code> utilizzate per definire lo username la password e il campo conferma password.
     */
    private final JLabel user_label = new JLabel("USERNAME:"),
            password_label = new JLabel("PASSWORD:"),
            conferma_password_label = new JLabel("CONFERMA PASSWORD:");

    /**
     * <code>JTextField</code> utilizzato per definire la casella di testo dello username.
     */
    private final JTextField user_text = new JTextField();

    /**
     * <code>JPasswordField</code> utilizzate per definire la casella di testo dei campi password e conferma password
     */
    private final JPasswordField password_text = new JPasswordField(),
            conferma_password_text = new JPasswordField();

    /**
     * Bottone utilizzato per il tentativo di accesso al database
     */
    protected JButton accedi_button = new JButton("ACCEDI");

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
     * Inserisce i componenti necessari nella finestra e la rende visibile.
     *
     * @author Daniele Caspani
     */
    private void initWindow() {
        settings("Accesso al database");

        layered_pane.add(user_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,              //user_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(user_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, first_row_y,            //user_text
                width_text, base_height), 15, 1, false);

        layered_pane.add(password_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //password_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(password_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, second_row_y,           //password_text
                width_text, base_height), 20, 1, false);

        layered_pane.add(conferma_password_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(third_row_x, third_row_y,              //conferma_password_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(conferma_password_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, third_row_y,            //conferma_password_text
                width_text, base_height), 20, 1, false);

        layered_pane.add(accedi_button, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x + width_buttons,       //connetti
                fourth_row_y, 250, 60), 18, 1, false);

        Color c = new Color(51, 153, 255);
        accedi_button.setText("Connetti");
        accedi_button.setBackground(c);
        accedi_button.setForeground(Color.white);
        accedi_button.setFont(new Font("Courier", Font.BOLD, 20));

        accedi_button.addActionListener(this);
        background_panel.setBorder(new LineBorder(c, 60, false));
        setVisible(true);
        border = user_text.getBorder();
    }
    /**
     * Metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     * @author Daniele Caspani, Marceca Manuel, Cristian Corti
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accedi_button) {
            Connection conn;
            String user = user_text.getText();
            String password = String.valueOf(password_text.getPassword());
            String conferma_password = String.valueOf(conferma_password_text.getPassword());

            CredenzialiDB.setUser(user);
            CredenzialiDB.setPassword(password);

            if (CredenzialiDB.isValid(password, conferma_password)) {
                try {
                    conn = DBManager.connected("", user, password);

                    if (conn != null) {
                        SwingAwt.modificaBordo(user_text);
                        SwingAwt.modificaBordo(password_text);
                        SwingAwt.modificaBordo(conferma_password_text);
                        JOptionPane.showMessageDialog(this, "Connessione verificata");
                        conn.close();
                        new RemoteManager();
                        this.dispose();
                    } else
                        JOptionPane.showMessageDialog(this, "Connessione non riuscita: Credenziali non corrette");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Errore nella connessione con il database", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (RemoteException ex) {
                    JOptionPane.showMessageDialog(this, "Errore di connessione remota", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (!message[0].isBlank()) {
                    user_text.setBorder(new LineBorder(Color.RED, 3, true));
                } else
                    SwingAwt.modificaBordo(user_text);
                if (!message[1].isBlank()) {
                    password_text.setBorder(new LineBorder(Color.RED, 3, true));
                    conferma_password_text.setBorder(new LineBorder(Color.RED, 3, true));
                } else {
                    SwingAwt.modificaBordo(password_text);
                    SwingAwt.modificaBordo(conferma_password_text);
                }
                JOptionPane.showMessageDialog(this, "Errore: " + Arrays.toString(message), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}



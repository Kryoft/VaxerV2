package server;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Classe che estende <code>ServerGraphics</code> (e quindi <code>JFrame</code>)
 * utilizzata pe stabilire i parametri di comunicazione con il client (Indirizzo IP e porta)
 */

public class RemoteManager extends ServerGraphics implements Remote {

    /**
     * Stringhe utilizzate per i messaggi di errore
     */
    private final String already_bound_exception = "Nome già utilizzato per un altro bind",
                            unknown_host_exception = "Server Host Irraggiungibile";

    /**
     * <code>JLabel</code> per indirizzo IP e porta del server
     */
    private final JLabel ip_label = new JLabel("INDIRIZZO IP:"),
                            porta_label = new JLabel("PORTA:");

    /**
     * Caselle di Testo di tipo <code>JTextField</code> per indirizzo ip e porta del server
     */
    private JTextField ip_text = new JTextField(""),
                        porta_text = new JTextField(RemoteInformation.getPORT());

    /**
     * Bottoni che corrispondono ad attivazione e disattivazione del server
     */
    protected JButton attiva = new JButton("Attiva"),
                        disattiva = new JButton("Disattiva");

    /**
     * Dimensione width degli oggetti <code>JButton</code>
     */
    private final int width_button = 150;

    /** Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536
     *  @author Marceca Manuel
     */
    private final int margin = display_width * 10 / 1536,
            margin_y = display_height/8,
            first_row_x = (display_width/2) - (width_text + margin*4),
            first_row_y = (int)(0.3 * display_height),
            second_column = first_row_x + width_label + margin,
            second_row_x = (display_width /2) - (width_text + margin*4),
            second_row_y = (int)(0.3 * display_height) + margin_y,
            third_row_x = second_column - 200,
            third_row_y = (int)(0.3 * display_height) + margin_y*2,
            x_disable= second_column - width_button + width_text;

    public RemoteManager() {
        try {
            initWindow();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (AlreadyBoundException e) {
            JOptionPane.showMessageDialog(this, already_bound_exception, "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Inserisce i componenti necessari nella finestra e la rende visibile.
     *
     * @author Daniele Caspani, Manuel Marceca
     */
    private void initWindow() throws RemoteException, AlreadyBoundException {

        settings("SERVER");

        layered_pane.add(ip_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,                      //ip_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(ip_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, first_row_y,                    //ip_text
                width_text, base_height), 15, 1, false);

//        ip.setEnabled(false);

        layered_pane.add(porta_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,                    //porta_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(porta_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, second_row_y,                   //porta_text
                width_text, base_height), 15, 1, false);

        layered_pane.add(attiva, 2, 0);
        layeredPaneSettings(0, new Rectangle(third_row_x + width_buttons, third_row_y,   //attiva
                width_button, 60), 18, 1, false);

        layered_pane.add(disattiva, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_disable,third_row_y,                         //disattiva
                width_button, 60), 18, 1, false);

        Color c = new Color(51, 153, 255);
        attiva.setBackground(c);
        attiva.setForeground(Color.white);
        attiva.setFont(new Font("Courier", Font.BOLD, 20));

        disattiva.setBackground(c);
        disattiva.setForeground(Color.white);
        disattiva.setFont(new Font("Courier", Font.BOLD, 20));

        disattiva.setEnabled(false);

        attiva.addActionListener(this);
        disattiva.addActionListener(this);
        background_panel.setBorder(new LineBorder(c, 60, false));
        setVisible(true);
        try {
            RemoteInformation.setIpToLocalHost();
            ip_text.setText(RemoteInformation.getIpAddress());
        } catch (UnknownHostException | NullPointerException un) {
            JOptionPane.showMessageDialog(this, unknown_host_exception,"Errore",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     * @param e evento che si è verificato
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == attiva) {
            disattiva.setEnabled(true);
            attiva.setEnabled(false);
            try {
                RemoteInformation.setIpAddress(ip_text.getText());
                RemoteInformation.setPORT(porta_text.getText());
                AcceptServer.attiva();
            } catch (AlreadyBoundException ex) {
                JOptionPane.showMessageDialog(this,already_bound_exception,"Errore",JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(this, unknown_host_exception,"Errore",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this,"Il server è attivo");
        }

        if (e.getSource() == disattiva) {
            attiva.setEnabled(true);
            disattiva.setEnabled(false);
            try {
                RemoteInformation.setIpAddress(ip_text.getText());
                RemoteInformation.setPORT(porta_text.getText());
                AcceptServer.disattiva();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this,"Il server è stato disabilitato");
        }
    }
}

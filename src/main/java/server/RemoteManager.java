package server;

import centrivaccinali.PlaceholderTextField;
import interfaccia.DBInterface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * classe che estende <code>ServerGraphics(e quindi JFrame)</code>
 * che serve a stabilire i parametri di comunicazione con il client(porta e IndirizzoIp)
 */

public class RemoteManager extends ServerGraphics implements Remote {

    /**
     * Stringhe utilizzate per i messaggi di errore
     */
    private final String already_bound_exception ="Nome già utilizzato per un altro bind",
            unknown_host="Server Host Irraggiungibile";

    /**
     * oggetto <code>DBInterface</code> condiviso che verra condiviso sul server
     */
    private DBInterface stub=null;

    /**
     * <code>JLabel</code> per indirizzo ip e porta del server
     */
    private final JLabel ip_label = new JLabel("INDIRIZZO IP"),
            porta_label = new JLabel("PORTA:");

    /**
     * Caselle di Testo di tipo <code>PTextField</code> per indirizzo ip e porta del server
     */
    private PlaceholderTextField ip=new PlaceholderTextField(""),
            porta = new PlaceholderTextField(RemoteInformation.getPORT());
    /**
     * dimensione width degli oggetti <code>JButton</code>
     */
    private final int width_button = 150;

    /** Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536
     *   @author Marceca Manuel
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
            JOptionPane.showMessageDialog(this,e.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
        } catch (AlreadyBoundException e) {
            JOptionPane.showMessageDialog(this,already_bound_exception,"Errore",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione del centro vaccinale
     *
     * @author Daniele Caspani, Manuel Marceca
     */
    private void initWindow() throws RemoteException, AlreadyBoundException {

        settings("SERVER");

        layered_pane.add(ip_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,              //nome_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(ip, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, first_row_y,                   //nome_centro
                width_text, base_height), 15, 1, false);

//        ip.setEnabled(false);

        layered_pane.add(porta_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //indirizzo_label
                width_label, base_height), 16, 1, false);

        layered_pane.add(porta, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, second_row_y,                   //via
                width_text, base_height), 15, 1, true);

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0, new Rectangle(third_row_x + width_buttons, third_row_y,      //conferma
                width_button, 60), 18, 1, false);

        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_disable,third_row_y,      //conferma
                width_button, 60), 18, 1, false);

        Color c = new Color(51, 153, 255);
        conferma.setText("Activate");
        conferma.setBackground(c);
        conferma.setForeground(Color.white);
        conferma.setFont(new Font("Courier", Font.BOLD, 20));

        annulla.setText("Disable");
        annulla.setBackground(c);
        annulla.setForeground(Color.white);
        annulla.setFont(new Font("Courier", Font.BOLD, 20));

        annulla.setEnabled(false);

        conferma.addActionListener(this);
        annulla.addActionListener(this);
        stub = AcceptServer.create();
        background_panel.setBorder(new LineBorder(c, 60, false));
        setVisible(true);
        try {
            ip.setText(RemoteInformation.getIp_host());
        } catch (UnknownHostException | NullPointerException un) {
            JOptionPane.showMessageDialog(this,unknown_host,"Errore",JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == conferma) {
            RemoteInformation.setPORT(porta.getText());
            annulla.setEnabled(true);
            conferma.setEnabled(false);
            try {
                AcceptServer.attiva(stub);
            } catch (AlreadyBoundException ex) {
                JOptionPane.showMessageDialog(this,already_bound_exception,"Errore",JOptionPane.ERROR_MESSAGE);
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
            } catch (UnknownHostException ex) {
                JOptionPane.showMessageDialog(this,unknown_host,"Errore",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this,"Il server è attivo");
        }

        if (e.getSource() == annulla) {
            conferma.setEnabled(true);
            annulla.setEnabled(false);
            try {
                AcceptServer.disattiva(stub);
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(this,ex.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
            } catch (NotBoundException ex) {
                JOptionPane.showMessageDialog(this,already_bound_exception,"Errore",JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(this,"Il server è stato momentaneamente disabilitato");
        }
    }
}

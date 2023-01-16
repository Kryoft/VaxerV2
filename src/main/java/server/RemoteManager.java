package server;

import centrivaccinali.PlaceholderTextField;
import centrivaccinali.Registrazioni;
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

public class RemoteManager extends Registrazioni implements Remote {
    String already_bound_exception ="Nome già utilizzato per un altro bind";
    String unknown_host="Server Host Irraggiungibile";

    private DBInterface stub=null;
    private final JLabel ip_label = new JLabel("INDIRIZZO IP");
    private final int width_ip_label = 250;

    private final JLabel porta_label = new JLabel("PORTA:");
    private final int width_porta_label = 250;

    private PlaceholderTextField ip=new PlaceholderTextField("");;
    private final int width_ip = 310;

    private final int width_button = 150;

    private final PlaceholderTextField porta = new PlaceholderTextField(RemoteInformation.getPORT());
    private final int width_porta = 310;
    //Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536 @Marceca
    private final int margin = display_width * 10 / 1536,
            margin_y = display_height/8,
            first_row_x = (display_width/2) - (width_ip + margin*4),
            first_row_y = (int)(0.3 * display_height),
            second_column = first_row_x + width_ip_label + margin,
            second_row_x = (display_width /2) - (width_porta + margin*4),
            second_row_y = (int)(0.3 * display_height) + margin_y,
            third_row_x = second_column - 200,
            third_row_y = (int)(0.3 * display_height) + margin_y*2,

            x_disable= second_column - width_button + width_porta;
    public RemoteManager() {
        try {
            initWindow();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Errore",JOptionPane.ERROR_MESSAGE);
        } catch (AlreadyBoundException e) {
            JOptionPane.showMessageDialog(this,already_bound_exception,"Errore",JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new RemoteManager();
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
                width_ip_label, base_height), 16, 1, false);

        layered_pane.add(ip, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, first_row_y,                   //nome_centro
                width_porta, base_height), 15, 1, false);

//        ip.setEnabled(false);

        layered_pane.add(porta_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //indirizzo_label
                width_porta_label, base_height), 16, 1, false);

        layered_pane.add(porta, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_column, second_row_y,                   //via
                width_porta, base_height), 15, 1, true);

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



        //TODO: gestire esistecentro() (fare nome centro unique?)
        //TODO:statistiche per visualizza eventi moda mediana dev. standard
        //TODO: fare uml

    /*@Override
    public Connection connected() throws SQLException {
        Connection conn = null;
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/ProgettoB", "postgres", "Antananarivo01");
        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.err.println("Failed to make connection!");
        }
        return conn;
    }


    public void selectData(String query) throws SQLException {
        // crea il java statement
        Statement st = connected().createStatement();

        // esegue la query e mette i risultati in rs
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        while (rs.next()) {
            String nome = rs.getString("nome");
            String comune = rs.getString("comune");
            String sigla = rs.getString("sigla");
            String nome_via= rs.getString("nome_via");
            String id = rs.getString("codice");
            String qualificatore=rs.getString("qualificatore");
            // print the results
            System.out.format("%s, %s, %s, %s, %s, %s\n", id,nome,comune,sigla,qualificatore,nome_via);
        }
        st.close();
    }

     */
    /*
    public void executeQuery(String query) throws SQLException {
        Statement st = connected().createStatement();
        st.executeUpdate(query);
        st.close();
    public void upData(String query) throws SQLException {
        Statement st = null;
            st = connected().createStatement();
            st.executeUpdate(query);
            st.close();
            connected().close();
    }

     */
}

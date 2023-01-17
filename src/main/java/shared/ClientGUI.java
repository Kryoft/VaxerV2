package shared;

import centrivaccinali.CentriVaccinaliGUI;
import interfaccia.DBInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * La classe <strong>ClientGUI</strong> estende la classe <code>JFrame</code> e implementa
 * l'interfaccia <code>ActionListener</code>;
 * Il suo compito è quello d'istanziare la finestra principale in cui l'utente dovrà
 * inserire l'ip e la porta del server a cui connettersi.
 *
 * @author Cristian Corti
 */
public class ClientGUI extends JFrame implements ActionListener {

    public static DBInterface dbobj=null;

    private int display_width = Utility.getDisplayWidth(),
                display_height = Utility.getDisplayHeight(),
                minimum_width = 800,
                minimum_height = 450,
                window_width = Math.max((int) (display_width / 1.5), minimum_width),
                window_height = Math.max((int) (display_height/1.5), minimum_height);
    private JPanel background_panel = new JPanel();
    private final JLabel instructions_label = new JLabel("Inserisci l'IP e la porta del Server", SwingConstants.CENTER),
                            ip_label = new JLabel("IP:", SwingConstants.CENTER),
                            port_label = new JLabel("Porta:", SwingConstants.CENTER);
    private final JTextField txt_ip = new JTextField(),
                                txt_port = new JTextField();
    private final int txt_width = 400,
                        txt_height = 40;
    private final int first_row_y = (int) (window_height/4.1),
                        second_row_y = window_height/3,
                        third_row_y = (int) (window_height/2.3);
    private final JButton connetti = new JButton("CONNETTI");
    private final int button_width = 200, button_height = 100;

    public ClientGUI() {
        initWindow();
    }

    private void settings() {
        setMinimumSize(new Dimension(minimum_width, minimum_height));
        setSize(window_width, window_height);

        setLocationRelativeTo(null);
        setTitle("Connettiti al Server");
        setFocusable(true);
        requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Utility.setWindowLogo(this, "logo.png");
    }

    private void initWindow() {
        settings();

        background_panel.setLayout(null);
        background_panel.setOpaque(true);
        add(background_panel, BorderLayout.CENTER);

        instructions_label.setSize(350, 30);
        background_panel.add(instructions_label, 2, 0);
        panelSettings(0, new Rectangle((window_width/2) - (instructions_label.getWidth()/2),
                                                first_row_y,
                                                instructions_label.getWidth(),
                                                instructions_label.getHeight()),
                                                20, 1);

        ip_label.setSize(50, 20);
        background_panel.add(ip_label, 2, 0);
        panelSettings(0, new Rectangle((window_width/2) - (ip_label.getWidth()/2) - window_width/5,
                                                second_row_y,
                                                ip_label.getWidth(),
                                                ip_label.getHeight()),
                                                18, 0);

        txt_ip.setSize(txt_width, txt_height);
        txt_ip.setHorizontalAlignment(JTextField.CENTER);
        background_panel.add(txt_ip, 2, 0);
        panelSettings(0, new Rectangle((window_width/2) - txt_width/2,
                                                (second_row_y - ((Math.abs(ip_label.getHeight() - txt_height)/2))),
                                                txt_width, txt_height),
                                                18, 0);

        port_label.setSize(100, 20);
        background_panel.add(port_label, 2, 0);
        panelSettings(0, new Rectangle((window_width/2) - (port_label.getWidth()/2) - window_width/5,
                                                third_row_y,
                                                port_label.getWidth(),
                                                port_label.getHeight()),
                                                18, 0);

        txt_port.setSize(txt_width, txt_height);
        txt_port.setHorizontalAlignment(JTextField.CENTER);
        background_panel.add(txt_port, 2, 0);
        panelSettings(0, new Rectangle((window_width/2) - txt_width/2,
                                                (third_row_y - ((Math.abs(port_label.getHeight() - txt_height)/2))),
                                                txt_width, txt_height),
                                                18, 0);

        connetti.setSize(button_width, button_height);
        background_panel.add(connetti, 2, 0);
        panelSettings(0, new Rectangle((window_width/2) - button_width/2,
                                                (int) (window_height/1.9),
                                                button_width, button_height),
                                                20, 1);

        connetti.addActionListener(this);

        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientGUI();
    }

    /**
     * Permette la definizione di alcune caratteristiche dei vari componenti situati nel Panel
     *
     * @param index           definisce l'indice del componente a cui si fa riferimento
     * @param rect            definisce la misura
     * @param size            definisce la dimensione della scritta
     * @param font            definisce il tipo di scritta (BOLD o PLAIN)
     */
    private void panelSettings(int index, Rectangle rect, int size, int font) {
        background_panel.getComponent(index).setBounds(rect);
        background_panel.getComponent(index).setFont(new Font("Arial", font, size));
    }

    /**
     * Metodo appartenente all'interfaccia ActionListener
     *
     * @param e evento che si è verificato
     * @author Cristian Corti
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == connetti) {
            Registry registry = null;
            try {
                //registry = LocateRegistry.getRegistry(ip,PORT);
                registry = LocateRegistry.getRegistry(txt_ip.getText(),Integer.parseInt(txt_port.getText()));
                dbobj = (DBInterface) registry.lookup("DBInterface");

                JOptionPane.showMessageDialog(this,"il client si è connesso");
                new CentriVaccinaliGUI();
                this.dispose();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } catch (NotBoundException ex) {
                JOptionPane.showMessageDialog(this,"ERRORE: Connessione al server non riuscita");
                throw new RuntimeException(ex);
            }

        }
    }
}

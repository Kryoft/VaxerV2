package client;

import client.centrivaccinali.CentriVaccinaliGUI;
import client.centrivaccinali.SwingAwt;
import interfaccia.DBInterface;
import shared.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * La classe <strong>ClientGUI</strong> estende la classe <code>JFrame</code> e implementa
 * l'interfaccia <code>ActionListener</code>;
 * Il suo compito è quello d'istanziare la finestra principale in cui l'utente dovrà
 * inserire l'ip e la porta del server a cui connettersi.
 *
 * @author Cristian Corti
 */
public class ClientGUI extends JFrame implements ActionListener {

    /**
     * È il riferimento all'interfaccia proxy utilizzata dal client per accedere ai servizi del server.
     */
    public static DBInterface dbobj = null;

    private static JFrame current_window;

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

    private final JButton connetti = new JButton("CONNETTI");

    private final int labels_width = 150;
    private final int txt_width = 400, base_height = 40;

    private final int title_width = labels_width + txt_width, title_height = 30;

    private final int button_width = 200, button_height = 100;
    private final int margin_txt_y = 40, margin_title_y = 50, margin_button_y = 60;

    private final int base_x = SwingAwt.centerItemOnXorY(window_width, labels_width + txt_width);
    private final int txt_x = base_x + labels_width;
    private final int button_x = SwingAwt.centerItemOnXorY(window_width, button_width);
    private final int first_row_y = SwingAwt.centerItemOnXorY(window_height,
            base_height * 2 + title_height + margin_txt_y + margin_title_y + margin_button_y + button_height),
                        second_row_y = first_row_y + base_height + margin_title_y,
                        third_row_y = second_row_y + base_height + margin_txt_y,
                        fourth_row_y = third_row_y + base_height + margin_button_y;


    public ClientGUI(boolean redirected) {
        if (redirected)
            JOptionPane.showMessageDialog(this, "Connessione Persa", "Errore", JOptionPane.INFORMATION_MESSAGE);
        initWindow();
    }

    /**
     * Metodo utile per inizializzare la finestra JFrame e il <strong>LayeredPane</strong>,
     * al quale verranno aggiunti tutti i vari componenti su diversi livelli.
     *
     * @author Cristian Corti
     */
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


    /**
     * Metodo utile per l'inizializzazione dei componenti JFrame riguardanti l'inserimento
     * degli eventi avversi.
     *
     * @author Cristian Corti
     */
    private void initWindow() {
        settings();
        current_window = this;

        background_panel.setLayout(null);
        background_panel.setOpaque(true);
        add(background_panel, BorderLayout.CENTER);

        instructions_label.setSize(title_width, title_height);
        background_panel.add(instructions_label, 2, 0);
        panelSettings(0, new Rectangle(base_x, first_row_y,
                        instructions_label.getWidth(), instructions_label.getHeight()),
                        20, 1);

        ip_label.setSize(labels_width, base_height);
        background_panel.add(ip_label, 2, 0);
        panelSettings(0, new Rectangle(base_x, second_row_y,
                        ip_label.getWidth(), ip_label.getHeight()),
                        18, 0);

        txt_ip.setSize(txt_width, base_height);
        txt_ip.setHorizontalAlignment(JTextField.CENTER);
        background_panel.add(txt_ip, 2, 0);
        panelSettings(0, new Rectangle(txt_x, second_row_y,
                        txt_ip.getWidth(), txt_ip.getHeight()),
                        18, 0);

        port_label.setSize(labels_width, base_height);
        background_panel.add(port_label, 2, 0);
        panelSettings(0, new Rectangle(base_x, third_row_y,
                        port_label.getWidth(), port_label.getHeight()),
                        18, 0);

        txt_port.setSize(txt_width, base_height);
        txt_port.setHorizontalAlignment(JTextField.CENTER);
        background_panel.add(txt_port, 2, 0);
        panelSettings(0, new Rectangle(txt_x, third_row_y,
                        txt_port.getWidth(), txt_port.getHeight()),
                        18, 0);

        connetti.setSize(button_width, button_height);
        background_panel.add(connetti, 2, 0);
        panelSettings(0, new Rectangle(button_x, fourth_row_y,
                        connetti.getWidth(), connetti.getHeight()),
                        20, 1);

        connetti.addActionListener(this);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ClientGUI(false);
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

    public static void setCurrentWindow(JFrame window) {
        current_window = window;
    }

    public static void redirectToClientGUI() {
        current_window.dispose();
        new ClientGUI(true);
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
            Registry registry;
            try {
                registry = LocateRegistry.getRegistry(txt_ip.getText(),Integer.parseInt(txt_port.getText()));
                dbobj = (DBInterface) registry.lookup("DBInterface");
                JOptionPane.showMessageDialog(this,"Connessione al server riuscita");
                new CentriVaccinaliGUI();
                this.dispose();
            } catch (RemoteException | NotBoundException ex) {
                JOptionPane.showMessageDialog(this,"ERRORE: Connessione al server non riuscita");
                throw new RuntimeException(ex);
            }

        }
    }
}

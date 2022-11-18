/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * la classe Registrazioni estende la classe <code>JFrame</code> e implementa l'interfaccia <code>ActionListener</code>;
 * Permette la registrazione al programma di un centro vaccinale oppure di un vaccinato in base alla scelta effettuata in <code>OperazioniCentro</code>
 *
 * @author Daniele Caspani, Manuel Marceca
 */
public abstract class Registrazioni extends JFrame implements ActionListener {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int displayWidth = (int)screenSize.getWidth();
    int displayHeight = (int)screenSize.getHeight();

    Utility utility = new Utility();
    SwingAwt swing_awt = new SwingAwt();
    protected JLabel nome_label;
    protected JButton conferma_registrazione_centro, conferma_registrazione_vaccinato, annulla;
    protected PTextField nome, via, numcivico, comune, sigla, cap;
    protected final String[] array_tipologia = new String[]{"Ospedaliero", "Aziendale", "Hub"};
    protected final String[] array_qualificatori = new String[]{"Via", "Piazza", "Viale"};
    protected JComboBox<String> jtipologia, jqualificatore, jvaccino;
    protected JTextField txtNomeC, txtnome, txtcognome, txtcodice, txtdata;
    protected Border border;
    protected final String[] array_vaccini = new String[]{"JJ", "Moderna", "Pfizer", "AstraZeneca"};
    /**
     * contenitore che utilizza più livelli di inserimento.
     */
    protected JLayeredPane layered_pane;

    /**
     * permette la creazione di una finestra e di un LayeredPane, al quale vengono aggiunti
     * tutti i componenti.
     *
     * @param title
     */
    protected void settings(String title) {


        //TODO Da rimuovere se si vuole permettere il ridimensionamento!
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setBounds(0,0,displayWidth,displayHeight);
        ////////

        setTitle(title);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(displayWidth, displayHeight));

        layered_pane = new JLayeredPane();
        add(layered_pane, BorderLayout.CENTER);
        layered_pane.setBounds(0, 0, displayWidth, displayHeight);

        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setBounds(0, 0, displayWidth, displayHeight);
        panel.setOpaque(true);

        JPanel bg_panel = new JPanel();
        bg_panel.setBackground(Color.WHITE);
        bg_panel.setBorder(new LineBorder(Color.CYAN, 30, false));
        bg_panel.setBounds((int) (0.025 * displayWidth), (int) (0.025 * displayHeight),
                (int) (0.95 * displayWidth), (int) (0.85 * displayHeight));

        bg_panel.setOpaque(true);

        layered_pane.add(panel, 0, 0);
        layered_pane.add(bg_panel, 1, 0);

        //pack();

    }

    /**
     * permette la definizione di alcune caratteristiche dei vari componenti situati nel LayeredPane
     *
     * @param button definisce l'ordine di inserimento dei componenti JFrame
     * @param r      definisce la misura
     * @param d      definisce la dimensione della scritta
     * @param font   definisce il tipo di scritta(BOLD o PLAIN)
     * @param text   definisce il colore della scritta
     */
    public void layeredPaneSettings(int button, Rectangle r, int d, int font, boolean text) {
        layered_pane.getComponent(button).setBounds(r);
        layered_pane.getComponent(button).setFont(new Font("Arial", font, d));
        if (text)
            layered_pane.getComponent(button).setForeground(Color.LIGHT_GRAY);
    }

}
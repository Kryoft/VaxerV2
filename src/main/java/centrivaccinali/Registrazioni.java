/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import shared.Utility;

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
    protected int display_width = Utility.getDisplayWidth();
    protected int display_height = Utility.getDisplayHeight();

    SwingAwt swing_awt = new SwingAwt();
    //protected JLabel nome_label;
    //protected JTextField txtNomeC, txtnome, txtcognome, txtcodice, txtdata;
    //protected PTextField nome, via, numcivico, comune, sigla, cap;
    //protected JButton conferma_registrazione_centro, conferma_registrazione_vaccinato, annulla;
    protected final String[] array_tipologia = new String[]{"Ospedaliero", "Aziendale", "Hub"};
    protected final String[] array_qualificatori = new String[]{"Via", "Piazza", "Viale"};
    //protected JComboBox<String> jtipologia, jqualificatore, jvaccino;
    protected final String[] array_vaccini = new String[]{"JJ", "Moderna", "Pfizer", "AstraZeneca"};
    protected Border border;
    /**
     * contenitore che utilizza più livelli d'inserimento.
     */
    protected JLayeredPane layered_pane;
    protected JPanel panel, background_panel;

    /**
     * Permette la creazione di una finestra e di un LayeredPane, al quale vengono aggiunti
     * tutti i componenti.
     *
     * @param title Titolo della finestra
     */
    protected void settings(String title) {
        //TODO: Da rimuovere se si vuole permettere il ridimensionamento!
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);
        setBounds(0,0, display_width, display_height);
        ////////

        setTitle(title);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(display_width, display_height));

        layered_pane = new JLayeredPane();
        add(layered_pane, BorderLayout.CENTER);
        layered_pane.setBounds(0, 0, display_width, display_height);

        panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setBounds(0, 0, display_width, display_height);
        panel.setOpaque(true);

        background_panel = new JPanel();
        background_panel.setBackground(Color.WHITE);
        background_panel.setBorder(new LineBorder(Color.CYAN, 30, false));
        background_panel.setBounds((int) (0.025 * display_width), (int) (0.025 * display_height),
                (int) (0.95 * display_width), (int) (0.85 * display_height));
        background_panel.setOpaque(true);

        layered_pane.add(panel, 0, 0);
        layered_pane.add(background_panel, 1, 0);
        //pack();
    }

    /**
     * Permette la definizione di alcune caratteristiche dei vari componenti situati nel LayeredPane
     *
     * @param index           definisce l'indice del componente a cui si fa riferimento
     * @param rect            definisce la misura
     * @param size            definisce la dimensione della scritta
     * @param font            definisce il tipo di scritta (BOLD o PLAIN)
     * @param light_gray_text definisce il colore della scritta
     */
    public void layeredPaneSettings(int index, Rectangle rect, int size, int font, boolean light_gray_text) {
        layered_pane.getComponent(index).setBounds(rect);
        layered_pane.getComponent(index).setFont(new Font("Arial", font, size));
        if (light_gray_text)
            layered_pane.getComponent(index).setForeground(Color.LIGHT_GRAY);
    }

}
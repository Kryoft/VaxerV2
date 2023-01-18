package server;

import shared.Utility;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe astratta che estende l'Interfaccia <code> JFrame</code>,utile per generalizzare alcune variabili riguardanti l'interfaccia grafica
 * @author Daniele Caspani
 */

public abstract class ServerGraphics extends JFrame implements ActionListener {

    /**
     * dimensione standard delle caselle di testo
     */
    protected final int width_text = 310;

    /**
     * dimensione standard delle <code>JLabel</code>
     */
    protected final int width_label = 250;

    /**
     * dimensioni calcolate del proprio display
     */
    protected int display_width = Utility.getDisplayWidth(),
            display_height = Utility.getDisplayHeight();

    /**
     * creazione dei <code>JButton</code> utilizzati nelle interfacce che estenderanno questa
     */
    protected JButton conferma = new JButton("CONFERMA"),
            annulla = new JButton("TORNA INDIETRO");

    /**
     * dimensioni relative ai bottoni
     */
    protected final int base_height = 40,
            width_buttons = 200;
    /**
     * bordo standard
     */
    protected Border border;
    /**
     * Contenitore che utilizza più livelli d'inserimento.
     */
    protected JLayeredPane layered_pane;
    protected JPanel panel, background_panel;


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

    /**
     * Permette la creazione di una finestra e di un LayeredPane, al quale vengono aggiunti
     * tutti i componenti.
     *
     * @param title Titolo della finestra
     */

    protected void settings(String title) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setResizable(false);  // Da rimuovere se si vuole permettere il ridimensionamento!
        setSize(display_width, display_height);
        setLocationRelativeTo(null);
        setTitle(title);
        setFocusable(true);
        requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Utility.setWindowLogo(this, "logo.png");

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
     * permette di gestire gli eventi relativi all'interfaccia grafica
     * @param e the event to be processed
     */

    public abstract void actionPerformed(ActionEvent e);
}

/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package client.cittadini;

import client.centrivaccinali.CentroVaccinale;

import shared.Utility;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Classe che estende <code>JFrame</code> e implementa l'interfaccia <code>ActionListener</code>;
 * Contiene il codice per la creazione delle GUI per la registrazione degli eventi avversi e
 * dei client.cittadini.
 *
 * @author Daniele Caspani, Cristian Corti
 */
public abstract class Registrazioni extends JFrame implements ActionListener {

    protected int display_width = Utility.getDisplayWidth(),
                    display_height = Utility.getDisplayHeight();

    protected JButton conferma = new JButton("CONFERMA"),
                        annulla = new JButton("ANNULLA");
    protected CentroVaccinale struttura_vaccinale;

    protected String cod_fiscale;
    protected Border border;
    protected JLayeredPane layered_pane;
    protected JPanel background_panel, panel;

    protected final String[] array_eventi = EventoAvverso.Eventi.getEventiToStringArray();

    /**
     * Metodo utile per inizializzare la finestra JFrame e il <strong>LayeredPane</strong>,
     * al quale verranno aggiunti tutti i vari componenti su diversi livelli.
     *
     * @author Daniele Caspani
     */
    protected void settings(String title) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        add(background_panel);
        background_panel.setLayout(null);
        background_panel.setBackground(Color.WHITE);

        background_panel = new JPanel();
        background_panel.setBackground(Color.WHITE);
        background_panel.setBorder(new LineBorder(Color.CYAN, 30, false));
        background_panel.setBounds((int) (0.025 * display_width), (int) (0.025 * display_height),
                (int) (0.95 * display_width), (int) (0.85 * display_height));
        background_panel.setOpaque(true);

        layered_pane.add(panel, 0, 0);
        layered_pane.add(background_panel, 1, 0);
    }

    /**
     * Metodo utile per settare alcune caratteristiche dei componenti JFrame
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

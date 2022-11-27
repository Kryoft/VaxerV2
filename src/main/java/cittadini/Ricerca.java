/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.StruttureVaccinali;
import shared.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashSet;

/**
 * In questa classe vengono implementate le ricerche per comune e tipologia e per nome del centro;
 * dopo aver cliccato il bottone 'Conferma' sarà possibile visualizzare le informazioni relative a quel centro
 *
 * @author Daniele Caspani
 */
public abstract class Ricerca extends JFrame implements ActionListener {

    protected int display_width = Utility.getDisplayWidth(),
                    display_height = Utility.getDisplayHeight();
    protected StruttureVaccinali strutture_vaccinali = null;
    protected DefaultListModel<String> list_model;
    /**
     * Utilizzata per contenere i nomi dei centri ricercati
     */
    protected JList<String> lista_centri;

    protected HashSet<String> hash_set = new HashSet<>();
    protected JPanel background = new JPanel();
    protected JButton cerca = new JButton(new ImageIcon(ClassLoader.getSystemResource("search.png"))),
                    conferma = new JButton("CONFERMA"),
                    annulla = new JButton("INDIETRO");
    protected final String[] tipologia = new String[]{"Seleziona", "Aziendale", "Hub", "Ospedaliero"};
    protected JComboBox<String> centro_combo = new JComboBox<>(tipologia);
    protected JScrollPane scroll;

    /**
     * Metodo utilizzato per inizializzare la finestra JFrame
     *
     * @author Daniele Caspani
     */
    protected void settings(String title) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(display_width, display_height);
        setTitle(title);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(background);
        background.setLayout(null);
        background.setBackground(Color.WHITE);
    }

    /**
     * Metodo utilizzato per la creazione di un componente JList
     *
     * @author Daniele Caspani
     */
    protected void creaLista() {
        list_model = new DefaultListModel<>();
        lista_centri = new JList<>(list_model);
        lista_centri.setBounds(0, 400, 1800, 1000);
        lista_centri.setFont(new Font("Arial", Font.BOLD, 18));
        background.add(lista_centri);
        scroll = new JScrollPane();
        scroll.setBounds(400, 250, 1000, 680);
        scroll.setViewportView(lista_centri);
        background.add(scroll);
    }

    /**
     * Metodo utilizzato per settare alcune caratteristiche dei componenti JFrame
     *
     * @param index  definisce l'ordine d'inserimento dei componenti JFrame
     * @param rect   definisce la misura
     * @param size   definisce la dimensione della scritta
     * @param font   definisce il tipo di scritta(BOLD o PLAIN)
     */
    public void backgroundSettings(int index, Rectangle rect, int size, int font) {
        background.getComponent(index).setBounds(rect);
        background.getComponent(index).setFont(new Font("Arial", font, size));
        if (index == 0)
            background.getComponent(index).setBackground(Color.WHITE);
    }

}
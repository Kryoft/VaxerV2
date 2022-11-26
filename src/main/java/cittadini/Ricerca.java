/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.StruttureVaccinali;
import centrivaccinali.SwingAwt;
import shared.Utility;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;

/**
 * In questa classe vengono implementate le ricerche per comune e tipologia e per nome del centro;
 * dopo aver cliccato il bottone 'Conferma' sarà possibile visualizzare le informazioni relative a quel centro
 *
 * @author Daniele Caspani
 */
public class Ricerca extends JFrame implements ActionListener {

    StruttureVaccinali strutture_vaccinali = null;
    DefaultListModel<String> list_model;
    /**
     * Utilizzata per contenere i nomi dei centri ricercati
     */
    JList<String> lista_centri = new JList<>();
    SwingAwt swing_awt = new SwingAwt();

    private int display_width = Utility.getDisplayWidth();
    private int display_height = Utility.getDisplayHeight();
    private HashSet<String> hash_set = new HashSet<>();
    private int i = -1;
    private JPanel background;
    private JButton cerca, annulla, cerca1, conferma;
    private JLabel centro_label, comune_label, tipologia_label;
    private JTextField centro_txt, comune_txt;
    private JComboBox<String> centro_combo;
    private final String[] tipologia = new String[]{"Seleziona", "Aziendale", "Hub", "Ospedaliero"};
    private JScrollPane scroll;

    public Ricerca(int i) {
        if (i == 1)
            init();
        if (i == 2)
            init2();
    }

    /**
     * Metodo utilizzato per inizializzare la finestra JFrame
     *
     * @author Daniele Caspani
     */
    private void settings(String title) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(display_width, display_height);
        setTitle(title);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        background = new JPanel();
        add(background);
        background.setLayout(null);
        background.setBackground(Color.WHITE);
    }

    /**
     * Metodo utilizzato per l'inizializzazione dei componenti JFrame per quanto riguarda la ricerca
     * per comune e tipologia
     *
     * @author Daniele Caspani
     */
    public void init() {
        settings("RICERCA PER Comune e Tipologia");

        tipologia_label = new JLabel("Tipologia Centro");
        centro_combo = new JComboBox<>(tipologia);
        comune_label = new JLabel("Comune");
        comune_txt = new JTextField();

        cerca1 = new JButton(new ImageIcon(ClassLoader.getSystemResource("search.png")));
        conferma = new JButton("CONFERMA");
        annulla = new JButton("INDIETRO");

        background.add(cerca1);
        background.add(annulla);
        background.add(conferma).setEnabled(false);
        background.add(comune_label);
        background.add(tipologia_label);
        background.add(centro_combo);
        background.add(comune_txt);

        backgroundSettings(0, new Rectangle(1350, 130, 40, 40), 15, 0);
        backgroundSettings(1, new Rectangle(24, 965, 120, 35), 15, 1);
        backgroundSettings(2, new Rectangle(1790, 965, 120, 35), 15, 1);
        backgroundSettings(3, new Rectangle(910, 90, 520, 120), 16, 1);
        backgroundSettings(4, new Rectangle(270, 90, 520, 120), 16, 1);
        backgroundSettings(5, new Rectangle(410, 130, 310, 40), 15, 0);
        backgroundSettings(6, new Rectangle(1040, 130, 310, 40), 15, 0);

        creaLista();

        cerca1.addActionListener(this);
        annulla.addActionListener(this);
        conferma.addActionListener(this);
        /*
         * A lista_centri viene aggiunto SelectionListener per assegnare all'oggetto di tipo
         * StruttureVaccinali strutture_vaccinali l'elemento selezionato
         */
        lista_centri.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Iterator<String> it = hash_set.iterator();
                String[] a;
                IndirizzoComposto ic;
                while (it.hasNext()) {
                    String s = it.next();
                    a = s.split(",");
                    if (a[0].equals(lista_centri.getSelectedValue())) {
                        ic = new IndirizzoComposto(Utility.decidiQualificatore(a[2]), a[3], Integer.parseInt(a[4]), a[5], a[6], a[7]);
                        strutture_vaccinali = new StruttureVaccinali(a[0], Utility.decidiTipo(a[1]), ic);
                        break;
                    }
                }
            }
        });

        setVisible(true);
    }

    /**
     * Metodo utilizzato per inizializzare i vari componenti JFrame nella finestra relativa alla ricerca per nome del centro
     *
     * @author Daniele Caspani
     */
    public void init2() {
        settings("RICERCA PER CENTRO VACCINALE");

        cerca = new JButton(new ImageIcon(ClassLoader.getSystemResource("search.png")));
        annulla = new JButton("INDIETRO");
        conferma = new JButton("CONFERMA");
        centro_label = new JLabel("Nome Centro:");
        centro_txt = new JTextField();
        centro_combo = new JComboBox<>(tipologia);

        centro_combo.setSelectedIndex(0);

        background.add(cerca);
        background.add(annulla);
        background.add(conferma).setEnabled(false);
        background.add(centro_label);
        background.add(centro_txt);

        backgroundSettings(0, new Rectangle(660, 70, 40, 40), 15, 0);
        backgroundSettings(1, new Rectangle(24, 965, 120, 35), 15, 1);
        backgroundSettings(2, new Rectangle(1790, 965, 120, 35), 15, 1);
        backgroundSettings(3, new Rectangle(210, 30, 520, 120), 16, 1);
        backgroundSettings(4, new Rectangle(350, 70, 310, 40), 16, 0);

        cerca.addActionListener(this);
        annulla.addActionListener(this);
        conferma.addActionListener(this);

        creaLista();

        /**
         * a <code>JList1</code> viene aggiunto SelectionListener per assegnare all'oggetto di tipo <code>StruttureVaccinali</code>
         * sv l'elemento selezionato
         * @author Daniele Caspani
         */
        lista_centri.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Iterator<String> it = hash_set.iterator();
                String[] a;
                IndirizzoComposto ic = null;
                while (it.hasNext()) {
                    String s = (String) it.next();
                    a = s.split(",");
                    if (a[0].equals(lista_centri.getSelectedValue())) {
                        ic = new IndirizzoComposto(Utility.decidiQualificatore(a[2]), a[3], Integer.parseInt(a[4]), a[5], a[6], a[7]);
                        strutture_vaccinali = new StruttureVaccinali(a[0], Utility.decidiTipo(a[1]), ic);
                        break;
                    }
                }
            }
        });
        setVisible(true);
    }

    /**
     * Metodo utilizzato per la creazione di un componente JList
     *
     * @author Daniele Caspani
     */
    private void creaLista() {
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
     * @param button definisce l'ordine d'inserimento dei componenti JFrame
     * @param rect   definisce la misura
     * @param size   definisce la dimensione della scritta
     * @param font   definisce il tipo di scritta(BOLD o PLAIN)
     */
    public void backgroundSettings(int button, Rectangle rect, int size, int font) {
        background.getComponent(button).setBounds(rect);
        background.getComponent(button).setFont(new Font("Arial", font, size));
        if (button == 0)
            background.getComponent(button).setBackground(Color.WHITE);
    }

    /**
     * Metodo appartenente all'interfaccia ActionListener
     *
     * @param e
     * @author Daniele Caspani
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int i = 0;

        if (e.getSource() == cerca) {
            conferma.setEnabled(false);
            list_model.removeAllElements();
            hash_set.clear();
            String copy = null;
            String s = centro_txt.getText();
            String[] a;
            hash_set = Utility.caricaFileInHashSet("./data/CentriVaccinali.dati.txt");
            Iterator<String> it = hash_set.iterator();

            if (!s.equals("")) {
                while (it.hasNext()) {
                    copy = (String) it.next();
                    a = copy.split(",");
                    if (a[0].toLowerCase().contains(s.toLowerCase())) {
                        list_model.addElement(a[0]);
                        i++;
                    }
                }

                if (i == 0) {
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Nessun elemento trovato");
                } else if (i > 0) {
                    lista_centri.setBackground(Color.LIGHT_GRAY);
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Elementi trovati: " + i);
                    conferma.setEnabled(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Errore: Campo nome centro vaccinale non valorizzato");
            }
        }

        if (e.getSource() == cerca1) {
            conferma.setEnabled(false);
            lista_centri.setBackground(Color.LIGHT_GRAY);
            hash_set.clear();
            list_model.removeAllElements();
            String comune = comune_txt.getText().toUpperCase();
            boolean s1 = false;
            if (swing_awt.decidiTipologia(centro_combo) != null && !comune.equals("")) {

                hash_set = Utility.caricaFileInHashSet("./data/CentriVaccinali.dati.txt");
                Iterator<String> it = hash_set.iterator();
                String[] a = null;
                while (it.hasNext()) {

                    String s = (String) it.next();
                    if (!s.equals("")) {
                        a = s.split(",");
                        if (comune.equals(a[5]) && swing_awt.decidiTipologia(centro_combo) == Utility.decidiTipo(a[1])) {
                            list_model.addElement(a[0]);
                            s1 = true;
                        }
                    }
                }

                if (s1) {
                    JOptionPane.showMessageDialog(this, "Operazione completata con Successo, Elementi trovati: " + list_model.size());
                    conferma.setEnabled(true);
                } else
                    JOptionPane.showMessageDialog(this, " Nessun elemento trovato");
            } else
                JOptionPane.showMessageDialog(this, "Tipologia centro e/o comune non selezionato");
        }

        if (e.getSource() == conferma) {
            if (strutture_vaccinali == null)
                JOptionPane.showMessageDialog(this, "Non e' stato selezionato alcun elemento", "Errore", JOptionPane.INFORMATION_MESSAGE);
            else {
                VisualizzaInfo Vi = new VisualizzaInfo(strutture_vaccinali);
                this.dispose();
            }
        }

        if (e.getSource() == annulla) {
            CittadiniGUI Ac = new CittadiniGUI();
            this.dispose();
        }
    }
}
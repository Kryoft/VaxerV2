/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.StruttureVaccinali;
import centrivaccinali.SwingAwt;
import centrivaccinali.Utility;

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
 * dopo aver cliccato il bottone conferma sarà possibile visualizzare le informazioni relative a quel centro
 *
 * @author daniele Caspani
 */
public class Ricerca extends JFrame implements ActionListener {

    StruttureVaccinali sv = null;
    DefaultListModel<String> listModel;
    /**
     * utilizzata per contenere i nomi dei centri ricercati
     *
     * @author Daniele Caspani
     */
    JList<String> jList1 = new JList();
    Utility u = new Utility();
    SwingAwt sw = new SwingAwt();
    private HashSet<String> v = new HashSet();
    private int i = -1;
    private JPanel background;
    private JButton cerca, annulla;
    private JLabel lblcentro;
    private JTextField txtcentro;
    private JLabel lblcomune, lbltipologia;
    private JTextField txtcomune;
    private JComboBox<String> jcentro;
    private String[] Combo = new String[]{"Seleziona", "Aziendale", "Hub", "Ospedaliero"};
    private JButton cerca1, conferma;
    private JScrollPane scroll;

    public Ricerca(int i) {
        if (i == 1)
            init();
        if (i == 2)
            init2();
    }

    /**
     * metodo utilizzato per inizializzare la finestra JFrame
     *
     * @author Daniele Caspani
     */
    private void settings(String title) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1920, 1080);
        setTitle(title);
        this.setFocusable(true);
        this.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        background = new JPanel();

        add(background);
        background.setLayout(null);
        background.setBackground(Color.WHITE);
    }

    /**
     * metodo utilizzato per l'inizializzazione dei componenti JFrame per quanto riguarda la ricerca
     * per comune e tipologia
     *
     * @author Daniele Caspani
     */
    public void init() {
        settings("RICERCA PER Comune e Tipologia");

        txtcomune = new JTextField();
        lblcomune = new JLabel("Comune");
        lbltipologia = new JLabel("Tipologia Centro");
        jcentro = new JComboBox(Combo);

        cerca1 = new JButton(new ImageIcon(ClassLoader.getSystemResource("search.png")));
        conferma = new JButton("CONFERMA");
        annulla = new JButton("INDIETRO");

        background.add(cerca1);
        background.add(annulla);
        background.add(conferma).setEnabled(false);
        background.add(lblcomune);
        background.add(lbltipologia);
        background.add(jcentro);
        background.add(txtcomune);

        Bsettings(0, new Rectangle(1350, 130, 40, 40), 15, 0);
        Bsettings(1, new Rectangle(24, 965, 120, 35), 15, 1);
        Bsettings(2, new Rectangle(1790, 965, 120, 35), 15, 1);
        Bsettings(3, new Rectangle(910, 90, 520, 120), 16, 1);
        Bsettings(4, new Rectangle(270, 90, 520, 120), 16, 1);
        Bsettings(5, new Rectangle(410, 130, 310, 40), 15, 0);
        Bsettings(6, new Rectangle(1040, 130, 310, 40), 15, 0);

        creaLista();

        cerca1.addActionListener(this);
        annulla.addActionListener(this);
        conferma.addActionListener(this);
        /**
         * a <code> JList1 </code> viene aggiunto SelectionListener per assegnare all' oggetto di tipo <code> StruttureVaccinali </code>
         * sv l'elemento selezionato
         * @author Daniele Caspani
         */
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Iterator it = v.iterator();
                String[] a;
                IndirizzoComposto ic = null;
                while (it.hasNext()) {
                    String s = (String) it.next();
                    a = s.split(",");
                    if (a[0].equals(jList1.getSelectedValue())) {
                        ic = new IndirizzoComposto(u.DecidiQualifier(a[2]), a[3], Integer.parseInt(a[4]), a[5], a[6], a[7]);
                        sv = new StruttureVaccinali(a[0], u.Deciditipo(a[1]), ic);
                        break;
                    }
                }
            }
        });

        setVisible(true);
    }

    /**
     * metodo utilizzato per inizializzare i vari componenti JFrame nella finestra relativa alla ricerca per nome del centro
     *
     * @author Daniele Caspani
     */
    public void init2() {
        settings("RICERCA PER CENTRO VACCINALE");

        cerca = new JButton(new ImageIcon(ClassLoader.getSystemResource("search.png")));
        annulla = new JButton("INDIETRO");
        conferma = new JButton("CONFERMA");
        lblcentro = new JLabel("Nome Centro:");
        txtcentro = new JTextField();
        jcentro = new JComboBox<String>(Combo);

        jcentro.setSelectedIndex(0);

        background.add(cerca);
        background.add(annulla);
        background.add(conferma).setEnabled(false);
        background.add(lblcentro);
        background.add(txtcentro);

        Bsettings(0, new Rectangle(660, 70, 40, 40), 15, 0);
        Bsettings(1, new Rectangle(24, 965, 120, 35), 15, 1);
        Bsettings(2, new Rectangle(1790, 965, 120, 35), 15, 1);
        Bsettings(3, new Rectangle(210, 30, 520, 120), 16, 1);
        Bsettings(4, new Rectangle(350, 70, 310, 40), 16, 0);

        cerca.addActionListener(this);
        annulla.addActionListener(this);
        conferma.addActionListener(this);

        creaLista();

        /**
         * a <code> JList1 </code> viene aggiunto SelectionListener per assegnare all' oggetto di tipo <code> StruttureVaccinali </code>
         * sv l'elemento selezionato
         * @author Daniele Caspani
         */
        jList1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Iterator it = v.iterator();
                String[] a;
                IndirizzoComposto ic = null;
                while (it.hasNext()) {
                    String s = (String) it.next();
                    a = s.split(",");
                    if (a[0].equals(jList1.getSelectedValue())) {
                        ic = new IndirizzoComposto(u.DecidiQualifier(a[2]), a[3], Integer.parseInt(a[4]), a[5], a[6], a[7]);
                        sv = new StruttureVaccinali(a[0], u.Deciditipo(a[1]), ic);
                        break;
                    }
                }
            }
        });
        setVisible(true);
    }

    /**
     * metodo utilizzato per la creazione di un componente JList
     *
     * @author Daniele Caspani
     */
    private void creaLista() {
        listModel = new DefaultListModel();
        jList1 = new JList<>(listModel);
        jList1.setBounds(0, 400, 1800, 1000);
        jList1.setFont(new Font("Arial", Font.BOLD, 18));
        background.add(jList1);
        scroll = new JScrollPane();
        scroll.setBounds(400, 250, 1000, 680);
        scroll.setViewportView(jList1);
        background.add(scroll);
    }

    /**
     * metodo utilizzato per settare alcune caratteristiche dei componenti JFrame
     *
     * @param button definisce l'ordine di inserimento dei componenti JFrame
     * @param r      Definisce la misura
     * @param d      definisce la dimensione della scritta
     * @param font   definisce il tipo di scritta(BOLD o PLAIN)
     */
    public void Bsettings(int button, Rectangle r, int d, int font) {
        background.getComponent(button).setBounds(r);
        background.getComponent(button).setFont(new Font("Arial", font, d));
        if (button == 0)
            background.getComponent(button).setBackground(Color.WHITE);
    }

    /**
     * metodo appartenente all' interfaccia ActionListener
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
            listModel.removeAllElements();
            v.clear();
            String copy = null;
            String s = txtcentro.getText();
            String[] a;
            v = u.CaricaFile("./data/CentriVaccinali.dati.txt");
            Iterator it = v.iterator();

            if (!s.equals("")) {
                while (it.hasNext()) {
                    copy = (String) it.next();
                    a = copy.split(",");
                    if (a[0].toLowerCase().contains(s.toLowerCase())) {
                        listModel.addElement(a[0]);
                        i++;
                    }
                }

                if (i == 0) {
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Nessun elemento trovato");
                } else if (i > 0) {
                    jList1.setBackground(Color.LIGHT_GRAY);
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Elementi trovati: " + i);
                    conferma.setEnabled(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Errore: Campo nome centro vaccinale non valorizzato");
            }
        }

        if (e.getSource() == cerca1) {
            conferma.setEnabled(false);
            jList1.setBackground(Color.LIGHT_GRAY);
            v.clear();
            listModel.removeAllElements();
            String comune = txtcomune.getText().toUpperCase();
            boolean s1 = false;
            if (sw.DecidiTipologia(jcentro) != null && !comune.equals("")) {

                v = u.CaricaFile("./data/CentriVaccinali.dati.txt");
                Iterator it = v.iterator();
                String[] a = null;
                while (it.hasNext()) {

                    String s = (String) it.next();
                    if (!s.equals("")) {
                        a = s.split(",");
                        if (comune.equals(a[5]) && sw.DecidiTipologia(jcentro) == u.Deciditipo(a[1])) {
                            listModel.addElement(a[0]);
                            s1 = true;
                        }
                    }
                }

                if (s1) {
                    JOptionPane.showMessageDialog(this, "Operazione completata con Successo, Elementi trovati: " + listModel.size());
                    conferma.setEnabled(true);
                } else
                    JOptionPane.showMessageDialog(this, " Nessun elemento trovato");
            } else
                JOptionPane.showMessageDialog(this, "Tipologia centro e/o comune non selezionato");
        }

        if (e.getSource() == conferma) {
            if (sv == null)
                JOptionPane.showMessageDialog(this, "Non e' stato selezionato alcun elemento", "Errore", JOptionPane.INFORMATION_MESSAGE);
            else {
                VisualizzaInfo Vi = new VisualizzaInfo(sv);
                this.dispose();
            }
        }

        if (e.getSource() == annulla) {
            ApplicazioneCittadini Ac = new ApplicazioneCittadini();
            this.dispose();
        }
    }
}
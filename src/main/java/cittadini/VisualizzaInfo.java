/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.StruttureVaccinali;
import centrivaccinali.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * la classe visualizza le informazioni relative ad un dato centro
 * calcolando anche la media dell'indice di gravità e il numero di segnalazioni per ogni evento avverso
 *
 * @author daniele Caspani
 */
public class VisualizzaInfo extends JFrame implements ActionListener {

    Utility u = new Utility();

    private JLabel lblnome, lblcomune, lblsigla, lblcap, lblindirizzo, lbltipo, lblmedia, lblnum, lblinizio, lblevento;
    private JPanel background;
    private JButton menu;
    private ArrayList<String> v = new ArrayList();

    /**
     * @param sv oggetto di tipo StruttureVaccinali da prendere in considerazione
     */
    public VisualizzaInfo(StruttureVaccinali sv) {
        init(sv);
    }

    /**
     * utilizzato per l'inizializzazione delle finestre JFrame.
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
        setLayout(new BorderLayout());

        background = new JPanel();

        background.setLayout(null);
        add(background);
    }

    /**
     * utiizzato per l'inizializzazione dei componenti JFrame
     *
     * @param sv
     */
    private void init(StruttureVaccinali sv) {
        settings("VISUALIZZA INFORMAZIONI");
        lblnome = new JLabel("Nome_Centro: " + sv.getNome_centro());
        lblindirizzo = new JLabel("Indirizzo: " + sv.getIndirizzo().getQualificatore() + " " + sv.getIndirizzo().getNomeVia() + " " + sv.getIndirizzo().getNumCivico());
        lblcomune = new JLabel("Comune: " + sv.getIndirizzo().getComune());
        lblsigla = new JLabel("Sigla :" + sv.getIndirizzo().getSiglaProvincia());
        lblcap = new JLabel("Cap :" + sv.getIndirizzo().getCap());
        lbltipo = new JLabel("Tipologia: " + sv.getTipologia());
        lblnum = new JLabel();
        lblmedia = new JLabel();
        lblinizio = new JLabel("Informazioni Centro " + sv.getNome_centro());
        lblevento = new JLabel("Prospetto Riassuntivo Eventi Avversi");

        v = u.CaricaFile1("./data/Vaccinati_" + sv.getNome_centro() + ".dati.txt");
        int j = 0;
        double media = 0.00d;
        String s;

        String[] a;
        for (int i = 0; i < v.size(); i++) {
            s = (String) v.get(i);
            a = s.split(",");
            if (a.length == 4) {
                media = media + Integer.parseInt(a[1]);
                j++;
            }
        }
        if (j != 0)
            media = media / j;

        lblnum.setText("Numero di Segnalazioni: " + j);
        lblmedia.setText("Severità media: " + media);

        menu = new JButton("Torna al menu");

        background.add(lblinizio);
        background.add(lblnome);
        background.add(lbltipo);
        background.add(lblindirizzo);
        background.add(lblcomune);
        background.add(lblsigla);
        background.add(lblcap);
        background.add(lblevento);
        background.add(lblnum);
        background.add(lblmedia);
        background.add(menu);

        Bsettings(0, new Rectangle(380, 50, 520, 120), 24, 1, true);
        Bsettings(1, new Rectangle(280, 150, 520, 120), 18, 1, false);
        Bsettings(2, new Rectangle(280, 180, 520, 120), 18, 1, false);
        Bsettings(3, new Rectangle(280, 210, 520, 120), 18, 1, false);
        Bsettings(4, new Rectangle(280, 240, 520, 120), 18, 1, false);
        Bsettings(5, new Rectangle(280, 270, 520, 120), 18, 1, false);
        Bsettings(6, new Rectangle(280, 300, 520, 120), 18, 1, false);
        Bsettings(7, new Rectangle(380, 440, 520, 120), 24, 1, true);
        Bsettings(8, new Rectangle(280, 540, 520, 120), 18, 1, false);
        Bsettings(9, new Rectangle(280, 570, 520, 120), 18, 1, false);
        Bsettings(10, new Rectangle(200, 900, 150, 50), 14, 1, false);

        menu.addActionListener(this);
        setVisible(true);
    }

    /**
     * utilizzato per la definizione di alcune caratteristiche dei componenti JFrame
     *
     * @param button componente JFrame in ordine di inserimento
     * @param r      Ogetto che definisce la misura
     * @param d      dimensioni della scritta
     * @param font   definisce le caratteristiche della scritta(BOLD o PLAIN)
     * @param b      definisce il colore della scritta
     */
    public void Bsettings(int button, Rectangle r, int d, int font, boolean b) {
        background.getComponent(button).setBounds(r);
        background.getComponent(button).setFont(new Font("Arial", font, d));
        if (b)
            background.getComponent(button).setForeground(Color.red);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            ApplicazioneCittadini Ap = new ApplicazioneCittadini();
            this.dispose();
        }
    }
}
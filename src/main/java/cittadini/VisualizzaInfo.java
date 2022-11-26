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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Questa classe visualizza le informazioni relative a un dato centro calcolando anche
 * la media dell'indice di gravità e il numero di segnalazioni per ogni evento avverso
 *
 * @author Daniele Caspani
 */
public class VisualizzaInfo extends JFrame implements ActionListener {


    private JLabel nome_label, comune_label, sigla_label, cap_label, indirizzo_label, tipo_label, media_label, num_segnalazioni_label, inizio_label, evento_label;
    private JPanel background;
    private JButton menu;
    private ArrayList<String> v = new ArrayList<>();

    /**
     * @param strutture_vaccinali oggetto di tipo StruttureVaccinali da prendere in considerazione
     */
    public VisualizzaInfo(StruttureVaccinali strutture_vaccinali) {
        init(strutture_vaccinali);
    }

    /**
     * Utilizzato per l'inizializzazione delle finestre JFrame.
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
     * Utiizzato per l'inizializzazione dei componenti JFrame
     *
     * @param sv
     */
    private void init(StruttureVaccinali sv) {
        settings("VISUALIZZA INFORMAZIONI");
        nome_label = new JLabel("Nome_Centro: " + sv.getNomeCentro());
        indirizzo_label = new JLabel("Indirizzo: " + sv.getIndirizzo().getQualificatore() + " " + sv.getIndirizzo().getNomeVia() + " " + sv.getIndirizzo().getNumCivico());
        comune_label = new JLabel("Comune: " + sv.getIndirizzo().getComune());
        sigla_label = new JLabel("Sigla :" + sv.getIndirizzo().getSiglaProvincia());
        cap_label = new JLabel("Cap :" + sv.getIndirizzo().getCap());
        tipo_label = new JLabel("Tipologia: " + sv.getTipologia());
        num_segnalazioni_label = new JLabel();
        media_label = new JLabel();
        inizio_label = new JLabel("Informazioni Centro " + sv.getNomeCentro());
        evento_label = new JLabel("Prospetto Riassuntivo Eventi Avversi");

        v = Utility.caricaFileInArrayList("./data/Vaccinati_" + sv.getNomeCentro() + ".dati.txt");
        int j = 0;
        double media = 0.00d;
        String s;

        String[] a;
        for (int i = 0; i < v.size(); i++) {
            s = v.get(i);
            a = s.split(",");
            if (a.length == 4) {
                media = media + Integer.parseInt(a[1]);
                j++;
            }
        }
        if (j != 0)
            media = media / j;

        num_segnalazioni_label.setText("Numero di Segnalazioni: " + j);
        media_label.setText("Severità media: " + media);

        menu = new JButton("Torna al menu");

        background.add(inizio_label);
        background.add(nome_label);
        background.add(tipo_label);
        background.add(indirizzo_label);
        background.add(comune_label);
        background.add(sigla_label);
        background.add(cap_label);
        background.add(evento_label);
        background.add(num_segnalazioni_label);
        background.add(media_label);
        background.add(menu);

        backgroundSettings(0, new Rectangle(380, 50, 520, 120), 24, 1, true);
        backgroundSettings(1, new Rectangle(280, 150, 520, 120), 18, 1, false);
        backgroundSettings(2, new Rectangle(280, 180, 520, 120), 18, 1, false);
        backgroundSettings(3, new Rectangle(280, 210, 520, 120), 18, 1, false);
        backgroundSettings(4, new Rectangle(280, 240, 520, 120), 18, 1, false);
        backgroundSettings(5, new Rectangle(280, 270, 520, 120), 18, 1, false);
        backgroundSettings(6, new Rectangle(280, 300, 520, 120), 18, 1, false);
        backgroundSettings(7, new Rectangle(380, 440, 520, 120), 24, 1, true);
        backgroundSettings(8, new Rectangle(280, 540, 520, 120), 18, 1, false);
        backgroundSettings(9, new Rectangle(280, 570, 520, 120), 18, 1, false);
        backgroundSettings(10, new Rectangle(200, 900, 150, 50), 14, 1, false);

        menu.addActionListener(this);
        setVisible(true);
    }

    /**
     * utilizzato per la definizione di alcune caratteristiche dei componenti JFrame
     *
     * @param button componente JFrame in ordine d' inserimento
     * @param rect      Ogetto che definisce la misura
     * @param size      dimensioni della scritta
     * @param font   definisce le caratteristiche della scritta(BOLD o PLAIN)
     * @param b      definisce il colore della scritta
     */
    public void backgroundSettings(int button, Rectangle rect, int size, int font, boolean b) {
        background.getComponent(button).setBounds(rect);
        background.getComponent(button).setFont(new Font("Arial", font, size));
        if (b)
            background.getComponent(button).setForeground(Color.red);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            new CittadiniGUI();
            this.dispose();
        }
    }
}
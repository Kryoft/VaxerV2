/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.CentroVaccinale;
import shared.DBManager;
import shared.Tripla;
import shared.Utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private int display_width = Utility.getDisplayWidth(),
                display_height = Utility.getDisplayHeight();
    private JLabel nome_label, comune_label, sigla_label, cap_label, indirizzo_label, tipo_label, media_label, num_segnalazioni_label, inizio_label, evento_label;
    private JPanel background;
    private JButton menu;
    private ArrayList<EventoAvverso> segnalazioni = new ArrayList<>();

    /**
     * @param strutture_vaccinali oggetto di tipo StruttureVaccinali da prendere in considerazione
     */
    public VisualizzaInfo(CentroVaccinale strutture_vaccinali) {
        initWindow(strutture_vaccinali);
    }

    /**
     * Utilizzato per l'inizializzazione delle finestre JFrame.
     *
     * @author Daniele Caspani
     */
    private void settings() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(display_width, display_height);
        setLocationRelativeTo(null);
        setTitle("Visualizza Informazioni");
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Utility.setWindowLogo(this, "logo.png");

        background = new JPanel();
        background.setLayout(null);
        add(background);
    }

    /**
     * Utilizzato per l'inizializzazione dei componenti JFrame
     *
     * @param strutture_vaccinali
     */
    private void initWindow(CentroVaccinale strutture_vaccinali) {
        settings();

        nome_label = new JLabel("Nome Centro: " + strutture_vaccinali.getNomeCentro());
        indirizzo_label = new JLabel("Indirizzo: " + strutture_vaccinali.getIndirizzo().getQualificatore() + " " + strutture_vaccinali.getIndirizzo().getNomeVia() + " " + strutture_vaccinali.getIndirizzo().getNumCivico());
        comune_label = new JLabel("Comune: " + strutture_vaccinali.getIndirizzo().getComune());
        sigla_label = new JLabel("Sigla: " + strutture_vaccinali.getIndirizzo().getSiglaProvincia());
        cap_label = new JLabel("Cap: " + strutture_vaccinali.getIndirizzo().getCap());
        tipo_label = new JLabel("Tipologia: " + strutture_vaccinali.getTipologia());
        num_segnalazioni_label = new JLabel();
        media_label = new JLabel();
        inizio_label = new JLabel("Informazioni Centro " + strutture_vaccinali.getNomeCentro());
        evento_label = new JLabel("Prospetto Riassuntivo Eventi Avversi");

        //vaccinati = Utility.caricaFileInArrayList("./data/Vaccinati_" + strutture_vaccinali.getNomeCentro() + ".dati.txt");

        //vaccinati = DBClient.getVaccinatiListByCentro(strutture_vaccinali.getNomeCentro());

        segnalazioni = DBManager.getSegnalazioniByCentro(strutture_vaccinali.getNomeCentro());

        double somma_indici = 0.00d;
        for(EventoAvverso s: segnalazioni){
            somma_indici += s.getIndice();
        }

        int numero_segnalazioni = segnalazioni.size();
        double media = numero_segnalazioni == 0 ? 0.00d : somma_indici / numero_segnalazioni;

        ArrayList<Tripla<String, Float, Integer>> dati_centro =
                DBManager.getValoriPerEventoAvverso(strutture_vaccinali.getNomeCentro());


        num_segnalazioni_label.setText("Numero di Segnalazioni: " + numero_segnalazioni);
        media_label.setText("Severità media: " + media);

        menu = new JButton("Torna al menu");

        background.add(inizio_label, 0);
        backgroundSettings(0, new Rectangle(380, 50,            //inizio_label
                520, 120), 24, 1, true);

        background.add(nome_label, 0);
        backgroundSettings(0, new Rectangle(280, 150,           //nome_label
                520, 120), 18, 1, false);

        background.add(tipo_label, 0);
        backgroundSettings(0, new Rectangle(280, 180,           //tipo_label
                520, 120), 18, 1, false);

        background.add(indirizzo_label, 0);
        backgroundSettings(0, new Rectangle(280, 210,           //indirizzo_label
                520, 120), 18, 1, false);

        background.add(comune_label, 0);
        backgroundSettings(0, new Rectangle(280, 240,           //comune_label
                520, 120), 18, 1, false);

        background.add(sigla_label, 0);
        backgroundSettings(0, new Rectangle(280, 270,           //sigla_label
                520, 120), 18, 1, false);

        background.add(cap_label, 0);
        backgroundSettings(0, new Rectangle(280, 300,           //cap_label
                520, 120), 18, 1, false);

        background.add(evento_label, 0);
        backgroundSettings(0, new Rectangle(380, 440,           //evento_label
                520, 120), 24, 1, true);

        background.add(num_segnalazioni_label, 0);
        backgroundSettings(0, new Rectangle(280, 540,           //num_segnalazioni_label
                520, 120), 18, 1, false);

        background.add(media_label, 0);
        backgroundSettings(0, new Rectangle(280, 570,           //media_label
                520, 120), 18, 1, false);

        background.add(menu, 0);
        backgroundSettings(0, new Rectangle(200, 900,           //menu
                150, 50), 14, 1, false);

        String[] colonne = {"Evento avverso", "Media", "Segnalazioni"};
        JTable table = new JTable(0, 3);
        table.setEnabled(false);
        table.setBounds(850,150,500,500);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(850, 150, 500, 500);
        scrollpane.setViewportView(table);
        background.add(scrollpane);

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(colonne);
        for(Tripla<String, Float, Integer> info_evento: dati_centro){
            Object[] riga = {info_evento.getPrimo(), info_evento.getSecondo(), info_evento.getTerzo()};
            model.addRow(riga);
        }

        menu.addActionListener(this);

        setVisible(true);
    }

    /**
     * Utilizzato per la definizione di alcune caratteristiche dei componenti JFrame
     *
     * @param index     componente JFrame in ordine d'inserimento
     * @param rect      oggetto che definisce la misura
     * @param size      dimensioni della scritta
     * @param font      definisce le caratteristiche della scritta(BOLD o PLAIN)
     * @param red_text  definisce il colore della scritta
     */
    public void backgroundSettings(int index, Rectangle rect, int size, int font, boolean red_text) {
        background.getComponent(index).setBounds(rect);
        background.getComponent(index).setFont(new Font("Arial", font, size));
        if (red_text)
            background.getComponent(index).setForeground(Color.red);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu) {
            new CittadiniGUI();
            this.dispose();
        }
    }
}
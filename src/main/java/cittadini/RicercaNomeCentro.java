/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.RegistraVaccinato;
//import centrivaccinali.StruttureVaccinali;
import shared.DBClient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RicercaNomeCentro extends Ricerca {

    private final JLabel centro_label = new JLabel("Nome Centro:");
    private final JTextField centro_txt = new JTextField();

    /**
     * Questa classe implementa la ricerca di un centro vaccinale registrato all'applicazione tramite
     * il suo nome.
     *
     * @param operazione Questo parametro viene utilizzato per "ricordare" l'operazione che va eseguita
     *                   dopo la selezione di un centro vaccinale. I valori possibili sono:
     *                   <p> 1 - Visualizzazione delle informazioni riguardanti il centro selezionato </p>
     *                   <p> 2 - Registrazione di un vaccinato </p>
     *                   <p> 3 - Registrazione di un cittadino all'applicazione </p>
     *                   <p> 4 - Inserimento eventi avversi post-vaccinazione </p>
     */
    public RicercaNomeCentro(int operazione) {
        operazione_scelta = operazione;
        initWindow();
    }

    public RicercaNomeCentro(int operazione, String cod_fiscale){
        operazione_scelta = operazione;
        this.cod_fiscale = cod_fiscale;
        initWindow();
    }

    /**
     * Metodo utilizzato per inizializzare i vari componenti JFrame nella finestra relativa alla ricerca per nome del centro
     *
     * @author Daniele Caspani
     */
    public void initWindow() {
        settings("Ricerca per Nome del Centro Vaccinale");

//        centro_combo.setSelectedIndex(0);   // istruzione inutile?

        background.add(centro_label, 0);
        backgroundSettings(0, new Rectangle(210, 30,            //centro_label
                520, 120), 16, 1, false);

        background.add(centro_txt, 0);
        backgroundSettings(0, new Rectangle(350, 70,            //centro_txt
                310, 40), 16, 0, false);

        background.add(cerca, 0);
        backgroundSettings(0, new Rectangle(660, 70,            //cerca
                40, 40), 15, 0, true);

        background.add(annulla, 0);
        backgroundSettings(0, new Rectangle(24, 965,            //annulla
                120, 35), 15, 1, false);

        background.add(conferma, 0).setEnabled(false);
        //backgroundSettings(0, new Rectangle(1790, 965,          //conferma
        //        120, 35), 15, 1, false);

        backgroundSettings(0, new Rectangle(900, 500,          //conferma
                        120, 35), 15, 1, false);


        cerca.addActionListener(this);
        conferma.addActionListener(this);
        annulla.addActionListener(this);

        creaLista();

        /*
         * A lista_centri viene aggiunto SelectionListener per assegnare all'oggetto di tipo
         * CentroVaccinale strutture_vaccinali l'elemento selezionato
         */
        lista_centri.addListSelectionListener(new ListSelectionListener() {

            //TODO! Rivedere indici, che ora non hanno più una logica, non utilizzando più gli array.
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("HO CHIAMATO valueChanged");
                //Iterator<String> it = centri_trovati.iterator();
                String centro_selezionato = lista_centri.getSelectedValue();
                strutture_vaccinali = DBClient.getCentroVaccinaleByName(centro_selezionato);
                //String[] a;
                //IndirizzoComposto ic;


                /*
                while (it.hasNext()) {
                    String s = it.next();
                    a = s.split(",");
                    if (a[0].equals(lista_centri.getSelectedValue())) {
                        ic = new IndirizzoComposto(Utility.decidiQualificatore(a[2]), a[3], Integer.parseInt(a[4]), a[5], a[6], a[7]);
                        strutture_vaccinali = new CentroVaccinale(a[0], Utility.decidiTipo(a[1]), ic);
                        break;
                    }
                }
                */
            }
        });

        setVisible(true);
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
        if (e.getSource() == cerca) {
            conferma.setEnabled(false);
            list_model.removeAllElements();
            centri_trovati.clear();
            //String copy;
            String nome_centro = centro_txt.getText();
            //String[] a;
            centri_trovati = DBClient.cercaCentri(nome_centro);
            //Iterator<String> it = centri_trovati.iterator();

            int num_risultati = centri_trovati.size();
            if (!nome_centro.isBlank()) {
                for(String nome: centri_trovati) {
                    list_model.addElement(nome);
                }

                if (num_risultati == 0) {
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Nessun elemento trovato");
                } else if (num_risultati > 0) {
                    lista_centri.setBackground(Color.LIGHT_GRAY);
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Elementi trovati: " + num_risultati);
                    conferma.setEnabled(true);
                }
            } else
                JOptionPane.showMessageDialog(this, "Errore: Campo nome centro vaccinale non valorizzato");
        }

        else if (e.getSource() == conferma) {
            if (strutture_vaccinali == null)
                JOptionPane.showMessageDialog(this, "Non e' stato selezionato alcun elemento", "Errore", JOptionPane.INFORMATION_MESSAGE);
            else {
                switch (operazione_scelta) {
                    case 1 -> new VisualizzaInfo(strutture_vaccinali);
                    case 2 -> new RegistraVaccinato(strutture_vaccinali);
                    case 3 -> new RegistraCittadini(strutture_vaccinali);
                    case 4 -> new RegistraEventiAvversi(strutture_vaccinali, cod_fiscale);
                    default -> { }
                }
                this.dispose();
            }

        } else if (e.getSource() == annulla) {
            new CittadiniGUI();
            this.dispose();
        }
    }

}

/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package client.centrivaccinali;


import client.cittadini.CittadiniGUI;
import client.ClientGUI;
import shared.Utility;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>La classe <strong>CentriVaccinaliGUI</strong> estende la classe <code>JFrame</code> e implementa
 * l'interfaccia <code>ActionListener</code>;</p>
 * Si tratta della schermata principale a cui l'utente arriva dopo essersi connesso a un server tramite l'inserimento
 * dell'IP e della porta e viene utilizzata per la scelta del tipo di utente (Cittadino o Centro Vaccinale)
 * attraverso il bottone <code>centro</code> e il bottone <code>client.cittadini</code>
 *
 * @author Daniele Caspani
 * @author Cristian Corti
 */
public class CentriVaccinaliGUI extends JFrame implements ActionListener {

    private int display_width = Utility.getDisplayWidth(),
                display_height = Utility.getDisplayHeight();
    private JButton cittadini_button = new JButton(),
                    centro_button = new JButton();

    public CentriVaccinaliGUI() {
        initWindow();
    }

    public static void main(String[] args) {
        new CentriVaccinaliGUI();
    }

    /**
     * Questo metodo viene utilizzato per impostare vari parametri
     * dell'interfaccia grafica (es. dimensioni, titolo e logo della finestra)
     *
     * @author Daniele Caspani
     * @author Cristian Corti
     */
    private void settings() {
        // setExtendedState(JFrame.MAXIMIZED_BOTH);  // ingrandisce al massimo la finestra

        // istruzioni utili nel caso l'utente voglia ridimensionare la finestra
        setMinimumSize(new Dimension(800, 450));  // non permette alla finestra di essere ridimensionata al di sotto dei valori specificati
        setSize((int) (display_width / 1.5), (int) (display_height / 1.5));
        // setBounds(100, 100, (int) (display_width / 1.5), (int) (display_height / 1.5));  // sostituita da setSize(...);

        setLocationRelativeTo(null);  // utilizzata per centrare la finestra quando non è maximized
        setTitle("Seleziona Tipo di Utente");
        setFocusable(true);
        requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // assegnazione immagine all'icona della finestra
        Utility.setWindowLogo(this, "logo.png");
    }

    /**
     * Inserisce i componenti necessari nella finestra e la rende visibile.
     *
     * @author Daniele Caspani
     */
    private void initWindow() {
        settings();
        ClientGUI.setCurrentWindow(this);

        // assegnazione immagine al bottone centro
        ImageIcon icon_centro = new ImageIcon(ClassLoader.getSystemResource("Hospital.png"));
        Image img_centro = icon_centro.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        icon_centro = new ImageIcon(img_centro);
        centro_button.setIcon(icon_centro);

        // assegnazione immagine al bottone client.cittadini
        ImageIcon icon_cittadini = new ImageIcon(ClassLoader.getSystemResource("Person.png"));
        Image img_cittadini = icon_cittadini.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        icon_cittadini = new ImageIcon(img_cittadini);
        cittadini_button.setIcon(icon_cittadini);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cittadini_button, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                                .addGap(22)
                                .addComponent(centro_button, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(centro_button, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                                        .addComponent(cittadini_button, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE))
                                .addContainerGap())
        );
        getContentPane().setLayout(groupLayout);

        cittadini_button.addActionListener(this);
        centro_button.addActionListener(this);

        setVisible(true);
    }

    /**
     * Metodo appartenente all'interfaccia ActionListener
     *
     * @param e evento che si è verificato
     * @author Daniele Caspani
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == centro_button) {
            new OperazioniCentroGUI();
        } else if (e.getSource() == cittadini_button) {
            new CittadiniGUI();
        }
        this.dispose();
    }

}
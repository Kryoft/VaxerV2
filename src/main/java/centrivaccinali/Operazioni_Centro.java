/*
    * Daniele Caspani - Matricola n. 744628 - CO
    * Tommaso Valente - Matricola n.744505 - CO
*/
package centrivaccinali;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Dimension;

/**
 * La classe Operazioni_Centro estende la classe <code> JFrame </code> e implementa l'interfaccia <code> ActionListener </code>;
 * Presenta un menu nel quale vi è la possibilità di scegliere se registrare un nuovo vaccinato o un nuovo centro vaccinale;
 * @author daniele Caspani
 */

public class Operazioni_Centro extends JFrame implements ActionListener{
    
   private JPanel bgPanel,buttonPanel;
   private JButton Menu,centro,vaccinato;
   private JLabel logolabel;
  
    /**
     * metodo per settare la finestra che sviluppa l' interfaccia grafica
     * @author Daniele Caspani
     */
   
    private void init(){
        
        setSize(1366, 768);
        setTitle("Seleziona Tipo di Utente(Dopo aver selezionato un opzione cliccare avanti)");
        this.setFocusable(true);
        this.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        Menu = new JButton("Menu");
        Menu.setBounds(24, 669, 95, 35);
        Menu.addActionListener(this);
       getContentPane().setLayout(null);
        getContentPane().add(Menu);
        
        bgPanel = new JPanel();
        bgPanel.setLayout(null);
        bgPanel.setBackground(Color.GREEN);
        bgPanel.setBounds(182, 71, 985, 587);
        getContentPane().add(bgPanel);
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(12, 12, 961, 561);
        bgPanel.add(buttonPanel);
        
        centro = new JButton("Registra centro ");
        centro.setFont(new Font("Dialog", Font.PLAIN, 30));
        centro.setBounds(305, 207, 350, 50);
        buttonPanel.add(centro);
        centro.addActionListener(this) ;
        
        vaccinato = new JButton("Registra vaccinato");
        vaccinato.setFont(new Font("Dialog", Font.PLAIN, 30));
        vaccinato.setBounds(305, 269, 350, 50);
        vaccinato.addActionListener(this);
        buttonPanel.add(vaccinato);
        
         
    	   	
       //resizing img
       
        logolabel = new JLabel( new ImageIcon(ClassLoader.getSystemResource("logo.png")));
        logolabel.setBounds(12, 12, 163, 163);
         getContentPane().add(logolabel);
        
        this.setVisible(true);
    }
    
    public Operazioni_Centro(){
    	setMinimumSize(new Dimension(1366, 768));
        init();
        setLocationRelativeTo(null);
         
    }
    
    
   
    /**
 * metodo appartenente all' interfaccia ActionListener
 * @param e evento che si è verificato
 * @see ActionListener
 * @author Daniele Caspani
    */
    
    @Override
    public void actionPerformed(ActionEvent e)  {
         if(e.getSource()==centro) {
                Registrazioni r  = new Registrazioni(1);
                this.dispose();
            }
          if(e.getSource()==vaccinato) {
                Registrazioni r  = new Registrazioni(2);
                this.dispose();
            }
          
          if(e.getSource()==Menu) {
                CentriVaccinali cv = new CentriVaccinali();
                this.dispose();
            }
    }
 
}

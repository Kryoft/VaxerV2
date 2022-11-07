/*
    * Daniele Caspani - Matricola n. 744628 - CO
    * Tommaso Valente - Matricola n.744505 - CO
*/
package centrivaccinali;


import cittadini.ApplicazioneCittadini;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.Image;

/**
 * La classe <strong> CentriVaccinali </strong> estende la classe <code> JFrame </code> e implementa l'interfaccia <code> ActionListener </code> ;
 * Si tratta della schermata del main e viene utilizzata per la scelta del tipo di utente(Cittadino o Centro Vaccinale) attraverso 
 * il bottone <code> centro </code> e il bottone <code> cittadini </code>
  @author Daniele Caspani
 */


public class CentriVaccinali extends JFrame implements ActionListener{
	
    private JButton centro,cittadini;
	
      /**
     * metodo per settare la finestra che sviluppa l' interfaccia grafica
     * @author Daniele Caspani
     */
        private void init(){
        
     	
                  setExtendedState(JFrame.MAXIMIZED_BOTH); 
    	setBounds(100,100,1366,768);
                 setTitle("Seleziona Tipo di Utente");
                 this.setFocusable(true);
                  this.requestFocus();
                 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
                 centro = new JButton();
                 cittadini = new JButton();
        
        
                  //resizing image centro
                 ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("Person.png"));
                 Image img = icon.getImage();
                 Image newimg = img.getScaledInstance(300,300, java.awt.Image.SCALE_SMOOTH);
                 icon = new ImageIcon(newimg);
                 centro.setIcon(icon);
        
        
                 //resizing image cittadini 
                 ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("Hospital.png"));
                 Image img1 = icon1.getImage();
                 Image newimg1 = img1.getScaledInstance(300,300, java.awt.Image.SCALE_SMOOTH);
                 icon1 = new ImageIcon(newimg1);
                 cittadini.setIcon(icon1);
       
                 GroupLayout groupLayout = new GroupLayout(getContentPane());
                 groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(centro, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        			.addGap(22)
        			.addComponent(cittadini, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
        			.addContainerGap())
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(cittadini, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
        				.addComponent(centro, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE))
        			.addContainerGap())
        );
        getContentPane().setLayout(groupLayout);
      
        centro.addActionListener(this);
        cittadini.addActionListener(this);
        
        setVisible(true);
       
    }
    
    public CentriVaccinali(){
        init();
    }
    
    public static void main(String[] args) {
        CentriVaccinali c = new CentriVaccinali();
        
    }
    
    /**
 * metodo appartenente all' interfaccia ActionListener
 * @param e evento che si è verificato
 * @see ActionListener
 * @author Daniele Caspani
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == cittadini){
            Operazioni_Centro Oc  = new Operazioni_Centro();
            this.dispose();
        }
        
        if(e.getSource() == centro){
            ApplicazioneCittadini Ac = new ApplicazioneCittadini();
            this.dispose();
        }
    }
}
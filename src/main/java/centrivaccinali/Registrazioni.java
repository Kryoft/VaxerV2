/*
    * Daniele Caspani - Matricola n. 744628 - CO
    * Tommaso Valente - Matricola n.744505 - CO
*/
package centrivaccinali;


import cittadini.Vaccinati;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * la classe registrazioni estende la classe <code>JFrame </code> e implementa l'nterfaccia <code> ActionListener </code>;
 * Permette La registrazione al programma di un centro vaccinale oppure di un vaccinato in base alla scelta effettuata in <code> Operazioni_Centro </code>
 * @author Daniele Caspani
 */
public class Registrazioni extends JFrame implements ActionListener{
    
    private JLabel lblnome,lblindirizzo,lbltipologia;
    private JPanel background;
    private JButton conferma1,annulla;
    private PTextField txtNome,via,numcivico,comune,sigla,cap;
    private String[] Combo = new String[] {"Ospedaliero","Aziendale","Hub"};
    private String[] Combo1 = new String[] {"Via","Piazza","Viale"};
    private JComboBox<String> jtipologia,jqualifier;
    
    private JButton conferma2;
    private JLabel lblnomeC,lblcognome,lblcodice,lbldata;
    private JTextField txtNomeC,txtnome,txtcognome,txtcodice,txtdata;
    
    private Border border;
    private JComboBox<String> jvaccino;
    private String[] Combo2 = new String[] {"JJ","Moderna","Pfizer","AstraZeneca"};
    /**
     * contenitore che utilizza più livelli di inserimento.
     * @author Daniele Caspani
     */
    private JLayeredPane lpane;
    private JPanel panel1;
    Utility u = new Utility();
    SwingAwt sw = new SwingAwt();
 /**
  * permette la creazione di una finestra e di un LayeredPane, al quale vengono aggiunti
  * tutti i componenti.
  * @param title 
  */
    private void settings(String title){
        
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setTitle(title);
        this.setFocusable(true);
        this.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1870, 1040));
        lpane = new JLayeredPane();
        panel1 = new JPanel();
        background = new JPanel();
        add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, 2000, 1000);
        panel1.setBackground(Color.GRAY);
        panel1.setBounds(0, 0, 2000, 1000);
        panel1.setOpaque(true);
        background.setBackground(Color.WHITE);
        background.setBorder(new LineBorder(java.awt.Color.CYAN,30, false));
        background.setBounds(300, 100, 1250, 800);
        background.setOpaque(true);
        lpane.add(panel1, 0, 0);
        lpane.add(background, 1, 0);
        pack();
    }
    
    /**
 * permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione del centro vaccinale
 * @author Daniele Caspani
 */ 
    
    private void init(){
        
        settings("Registra Centro Vaccinale");
       
        conferma1 = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");
        jtipologia = new JComboBox(Combo);
        jqualifier = new JComboBox(Combo1);
        
        lpane.add(annulla, 2, 0);
        lpane.add(conferma1, 2, 0);
        
        lblnome = new JLabel("Nome_Centro e Tipologia:");
        lblindirizzo = new JLabel("Indirizzo:");
        
        txtNome = new PTextField("Nome centro");
        via=new PTextField("Nome Via");
        numcivico=new PTextField("numero civico");
        comune = new PTextField("comune");
        sigla = new PTextField("sigla");
        cap=new PTextField("cap");
        
        lpane.add(lblnome, 2, 0);
        lpane.add(lblindirizzo, 2, 0);
        
        lpane.add(jtipologia, 2, 0);
        lpane.add(jqualifier, 2, 0);
        
        lpane.add(txtNome, 2, 0);
        lpane.add(via, 2, 0);
        lpane.add(numcivico, 2, 0);
        lpane.add(cap, 2, 0);
        lpane.add(comune, 2, 0);
        lpane.add(sigla, 2, 0);
        
        Bsettings(0,new Rectangle(940, 530, 110, 40),15,0,true);
        Bsettings(1,new Rectangle(680, 530, 250, 40),15,0,true);
        Bsettings(2,new Rectangle(1200, 430, 110, 40),15,0,true);
        Bsettings(3,new Rectangle(1060, 430, 100, 40),15,0,true);
        Bsettings(4,new Rectangle(800, 430, 250, 40),15,0,true);
        Bsettings(5,new Rectangle(680, 200, 310, 40),15,0,true);
        Bsettings(6,new Rectangle(680, 430, 110, 40),12,0,false);
        Bsettings(7,new Rectangle(990,200,310,40),12,0,false);
        Bsettings(8,new Rectangle(450, 390, 520, 120),16,1,false);
        Bsettings(9,new Rectangle(450, 160, 520, 120),16,1,false);
        Bsettings(10,new Rectangle(650, 700, 200, 100),15,1,false);
        Bsettings(11,new Rectangle(1050, 700, 200, 100),15,1,false);
        
        border = txtNome.getBorder();
        
        annulla.addActionListener(this);
        conferma1.addActionListener(this);
        
        setVisible(true);
    }
    
   /**
 * permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione di un vaccinato
 * @author Daniele Caspani
 */ 
    
    private void init2(){
        
        settings("Registra Vaccinato");
          
        conferma2 = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");
        jvaccino = new JComboBox(Combo2);
        
        lpane.add(annulla, 2, 0);
        lpane.add(conferma2, 2, 0);
        lpane.add(jvaccino, 2, 0);
        
        
        lblnomeC = new JLabel("Nome_Centro:");
        lbltipologia=new JLabel("Tipologia");
        lblnome = new JLabel("Nome:");
        lblcognome = new JLabel("Cognome:");
        lblcodice = new JLabel("Codice Fiscale");
        lbldata = new JLabel("Data Vaccinazione(dd/mm/yy):");
        
        txtNomeC = new JTextField();
        txtnome = new JTextField();
        txtcognome=new JTextField();
        txtcodice=new JTextField();
        txtdata= new JTextField();
        
        lpane.add(lblnomeC, 2, 0);
        lpane.add(lbltipologia, 2, 0);
        lpane.add(lblnome, 2, 0);
        lpane.add(lblcognome, 2, 0);
        lpane.add(lblcodice, 2, 0);
        lpane.add(lbldata, 2, 0);
           
        lpane.add(txtNomeC, 2, 0);
        lpane.add(txtnome, 2, 0);
        lpane.add(txtcognome, 2, 0);
        lpane.add(txtcodice, 2, 0);
        lpane.add(txtdata, 2, 0);
        
        Bsettings(0,new Rectangle(1090, 530, 310, 40),15,1,false);
        Bsettings(1,new Rectangle(530, 530, 310, 40),15,1,false);
        Bsettings(2,new Rectangle(1090, 380, 310, 40),15,1,false);
        Bsettings(3,new Rectangle(530, 380, 310, 40),15,1,false);
        Bsettings(4,new Rectangle(530, 230, 310, 40),15,1,false);
        Bsettings(5,new Rectangle(870, 490, 520, 120),16,0,false);
        Bsettings(6,new Rectangle(410, 490, 520, 120),16,0,false);
        Bsettings(7,new Rectangle(930, 340, 520, 120),16,0,false);
        Bsettings(8,new Rectangle(410, 340, 520, 120),16,0,false);
        Bsettings(9,new Rectangle(930, 190, 520, 120),16,0,false);
        Bsettings(10,new Rectangle(410, 190, 520, 120),16,0,false);
        Bsettings(11,new Rectangle(1090,230,310,40),12,1,false);
        Bsettings(12,new Rectangle(1100, 700, 200, 100),18,1,false);
        Bsettings(13,new Rectangle(630, 700, 200, 100),18,1,false);
       
        conferma2.addActionListener(this);
        annulla.addActionListener(this);
        border = txtNomeC.getBorder();
        
        setVisible(true);
        
    }

    public Registrazioni(int i){
        
        if(i==1)
            init();
        if(i==2)
            init2();
    }
    
    /**
     * permette la definizione di alcune caratteristiche dei vari componenti situati nel LayeredPane
     * @param button definisce l'ordine di inserimento dei componenti JFrame
     * @param r definisce la misura
     * @param d definisce la dimensione della scritta
     * @param font definisce il tipo di scritta(BOLD o PLAIN)
     * @param text definisce il colore della scritta
     */
    
    public void Bsettings(int button,Rectangle r, int d,int font,boolean text){
        
        lpane.getComponent(button).setBounds(r);
        lpane.getComponent(button).setFont(new Font("Arial",font,d));
        if(text)
            lpane.getComponent(button).setForeground(Color.LIGHT_GRAY);
    }
    
    
    /**
     * metodo ereditato dall'interfaccia <code> ActionListener </code>
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            
            if(e.getSource() == conferma1){
                String message=null;
                Indirizzo_composto Ic;
                int codice=Integer.parseInt(numcivico.getText());
                String centro=txtNome.getText();
                String Via=via.getText();
                String Comune= comune.getText().toUpperCase();
                String Sigla= sigla.getText().toUpperCase();
                String Cap= cap.getText();
                numcivico.setBorder(border);
                if(!centro.equals("") && !Via.equals("") && !Comune.equals("") && !Sigla.equals("") && !Cap.equals("") && !centro.equals("Nome centro") && !Via.equals("Nome Via") && !Comune.equals("comune") && !Sigla.equals("sigla") && !Cap.equals("cap"))
                {
                    sw.Bordo(centro, txtNome, border);
                    sw.Bordo(Via, via, border);
                    comune.setBorder(border);
                    sigla.setBorder(border);
                    try{
                        
                        Ic= new Indirizzo_composto (sw.DecidiQualificatore(jqualifier),Via,codice,Comune,Sigla,Cap);
                        
                        if(!Ic.controllacap(Cap)){
                            cap.setBorder(new LineBorder(java.awt.Color.RED, 3, true));
                            message = "Il cap contiene 5 cifre decimali";
                            throw new eccezione(); 
                        }
                        else
                          sw.Bordo(Cap, cap, border);
                        
                        if(!Ic.controllacomune(Comune)){
                            comune.setBorder(new LineBorder(java.awt.Color.RED, 3, true));
                            message = "Un comune puo' contenere solo caratteri letterali";
                            throw new eccezione(); 
                        }
                        else
                            sw.Bordo(Comune, comune, border);
                        
                        if(!Ic.controllasigla(Sigla)){
                            sigla.setBorder(new LineBorder(java.awt.Color.RED, 3, true));
                            message = "Una sigla di provincia contiene solo 2 caratteri letterali";
                            throw new eccezione(); 
                        }
                        else
                            sw.Bordo(Sigla, sigla, border);
                        
                        
                        StruttureVaccinali sv= new StruttureVaccinali(centro,sw.DecidiTipologia(jtipologia),Ic);
                        if(u.EsisteCentro(0,sv.getNome_centro(),"./data/CentriVaccinali.dati.txt")){
                            JOptionPane.showMessageDialog(this,"Centro gia' esistente nell'applicazione; Cambiare Nome","error",JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            u.ScriviFile("./data/CentriVaccinali.dati.txt",sv.toString());
                           
                            txtNome.setBorder(border);
                            via.setBorder(border);
                            JOptionPane.showMessageDialog(this,"Operazione Completata Con Successo");  
                            CentriVaccinali Cv = new CentriVaccinali();
                            this.dispose();
                        }
                            
                    }catch(eccezione ex){
                        JOptionPane.showMessageDialog(this, message,"errore", JOptionPane.ERROR_MESSAGE); 
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, ex);
                    }  
                }
                else{
                   sw.Bordo(centro,txtNome,border);
                   sw.Bordo(Via,via,border);
                   sw.Bordo(Sigla,sigla,border);
                   sw.Bordo(Comune,comune,border);
                   sw.Bordo(Cap,cap,border); 
                    JOptionPane.showMessageDialog(this," Riempire Tutti i Campi", "Error",JOptionPane.ERROR_MESSAGE);
                }
            }       
        }catch(NumberFormatException ec){
            numcivico.setBorder(new LineBorder(java.awt.Color.RED, 3, true));
            JOptionPane.showMessageDialog(this,"Errore di formattazione numerico nel numero civico","Eror112",JOptionPane.WARNING_MESSAGE);    
        }
        
        if(e.getSource() == conferma2){
        
            String centro=txtNomeC.getText();
            String Nome=txtnome.getText();
            String Cognome= txtcognome.getText();
            String SData = txtdata.getText();
            DateFormat fData = DateFormat.getDateInstance(DateFormat.SHORT);
            Date Data = null;
            String Codice= txtcodice.getText().toUpperCase();
            short id = 0;
            Vaccinati va = new Vaccinati();
            try {
                
                Data = fData.parse(SData);
                if(!centro.equals("") && !Nome.equals("") && !Cognome.equals("") && !SData.equals("") && !Codice.equals("")){
                    
                     txtNomeC.setBorder(border);
                     txtdata.setBorder(border);
                     txtnome.setBorder(border);
                     txtcognome.setBorder(border);
                     
                    if(u.EsisteCentro(0,centro,"./data/CentriVaccinali.dati.txt") )
                    {
                        
                        if(va.controllacf(Codice)){
                            txtcodice.setBorder(border);
                            
                            Random random = new Random();
                            id= (short) ((short) random.nextInt(65534) - 32767);
                            id = u.Idcontrol(2,String.valueOf(id),"./data/Vaccinati_"+ centro + ".dati.txt");
                            
                            if(id!=0){
                                va= new Vaccinati(Data,sw.DecidiVaccino(jvaccino),centro,id,Nome,Cognome,Codice);
                                u.ScriviFile("./data/Vaccinati_"+ va.getNome_centro() + ".dati.txt",va.toString());
                            }
                            
                            else
                                JOptionPane.showMessageDialog(this,"Non e' possibile inserire più vaccinati per questo centro","Errore",JOptionPane.WARNING_MESSAGE);
                            txtdata.setBorder(border);
                            txtnome.setBorder(border);
                            txtcognome.setBorder(border);
                            JOptionPane.showMessageDialog(this,"Operazione Completata Con Successo");
                            JOptionPane.showMessageDialog(this,"L' Identificativo Associato e' " + (int)(id + 32767));
                            CentriVaccinali cv = new CentriVaccinali();
                            this.dispose();
                        }
                        else{
                           txtcodice.setBorder(new LineBorder(java.awt.Color.RED, 3, true));
                           JOptionPane.showMessageDialog(this,"Errore nel formato del codice fiscale", "Errore", JOptionPane.ERROR_MESSAGE); 
                        }
                    }
                    else{
                        txtNomeC.setBorder(new LineBorder(java.awt.Color.RED, 3, true));
                        JOptionPane.showMessageDialog(this,"Il centro da lei indicato non esiste o non si e' registarto all'applicazione", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    
                    sw.Bordo(centro,txtNomeC,border);
                    sw.Bordo(Nome,txtnome,border);
                    sw.Bordo(Cognome,txtcognome,border);
                    sw.Bordo(Codice,txtcodice,border);
                    sw.Bordo(SData,txtdata,border);
                    JOptionPane.showMessageDialog(this,"Riempire Tutti i Campi","Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException ex) {
                 txtdata.setBorder(new LineBorder(java.awt.Color.RED, 3, true));
                 JOptionPane.showMessageDialog(this,"Formato della data errato", "Error112", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
        if(e.getSource()==annulla){
            Operazioni_Centro op = new Operazioni_Centro();
            this.dispose();
        }
       
    }

}

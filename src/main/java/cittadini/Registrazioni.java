package cittadini;

import centrivaccinali.PTextField;
import centrivaccinali.SwingAwt;
import centrivaccinali.Utility;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public abstract class Registrazioni extends JFrame implements ActionListener {

    Utility utility = new Utility();
    SwingAwt swing_awt = new SwingAwt();
    protected JLabel nome_label, codice_label, login_label, centro_label;
    protected JTextField codice_txt, centro_txt;
    protected PTextField nome_txt, cognome_txt, email_txt, password_txt, user_txt, id_txt;
    protected Border border;
    protected JButton conferma_registrazione_cittadino, annulla;
    protected JLabel lblnomeC, lblnote, lblevento;
    protected JTextField txtNomeC, txtevento;
    protected TextArea txtnote;
    protected PTextField txtindice;
    protected JButton conferma_registrazione_evento_avverso;
    protected JLayeredPane lpane;
    protected JPanel background, panel1;

    /**
     * Metodo utile per inizializzare la finestra JFrame e il <strong>LayeredPane</strong>,
     * al quale verranno aggiunti tutti i vari componenti su diversi livelli.
     *
     * @author Daniele Caspani
     */
    protected void settings(String title) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(title);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1870, 1040));

        background = new JPanel();
        add(background);
        background.setLayout(null);
        background.setBackground(Color.WHITE);

        lpane = new JLayeredPane();
        panel1 = new JPanel();
        background = new JPanel();
        add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, 2000, 1000);
        panel1.setBackground(Color.GRAY);
        panel1.setBounds(0, 0, 2000, 1000);
        panel1.setOpaque(true);

        background.setBackground(Color.WHITE);
        background.setBorder(new LineBorder(Color.CYAN, 30, false));
        background.setBounds(300, 100, 1250, 800);
        background.setOpaque(true);

        lpane.add(panel1, 0, 0);
        lpane.add(background, 1, 0);
        pack();
    }

    /**
     * Metodo utile per settare alcune caratteristiche dei componenti JFrame
     *
     * @param button          definisce l'ordine di inserimento dei componenti JFrame
     * @param rect            definisce la misura
     * @param size            definisce la dimensione della scritta
     * @param font            definisce il tipo di scritta (BOLD o PLAIN)
     * @param light_gray_text definisce il colore della scritta
     */
    public void Bsettings(int button, Rectangle rect, int size, int font, boolean light_gray_text) {
        lpane.getComponent(button).setBounds(rect);
        lpane.getComponent(button).setFont(new Font("Arial", font, size));
        if (light_gray_text)
            lpane.getComponent(button).setForeground(Color.lightGray);
    }

}

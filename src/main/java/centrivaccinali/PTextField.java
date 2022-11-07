/*
    * Daniele Caspani - Matricola n. 744628 - CO
    * Tommaso Valente - Matricola n.744505 - CO
*/
package centrivaccinali;
    
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * Classe che estende il componente JTextField, utile per creare un testo che al momento dell'
 * inserimento di un input dell'utente, viene cancellato.
 * @author Daniele Caspani
 */
public class PTextField extends JTextField {

    public PTextField(final String proptText) {
        super(proptText);
        addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if(getText().isEmpty()) {
                    setText(proptText);
                    setForeground(Color.lightGray);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if(getText().equals(proptText)) {
                    setForeground(Color.BLACK);
                    setText("");
                }
            }
        });

    }


}

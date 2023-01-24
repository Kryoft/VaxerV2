/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package shared;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Classe che estende il componente JTextField, utile per creare un testo che al momento
 * dell'inserimento di un input dell'utente, viene cancellato.
 *
 * @author Daniele Caspani
 */
public class PlaceholderTextField extends JTextField {

    public PlaceholderTextField(final String promptText) {
        super(promptText);
        addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(promptText);
                    setForeground(Color.lightGray);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(promptText) && getForeground().equals(Color.lightGray)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }
        });
    }

}
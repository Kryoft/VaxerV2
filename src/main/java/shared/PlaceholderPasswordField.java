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
 * Classe che estende il componente JPasswordField, utile per creare un testo che al momento
 * dell'inserimento di un input dell'utente, viene cancellato.
 *
 * @author Daniele Caspani
 */
public class PlaceholderPasswordField extends JPasswordField {

    public PlaceholderPasswordField(final String promptText) {
        super(promptText);
        setEchoChar((char) 0);

        addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setEchoChar((char) 0);
                    setText(promptText);
                    setForeground(Color.lightGray);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(getPassword()).equals(promptText) && getForeground().equals(Color.lightGray)) {
                    setEchoChar('*');
                    setForeground(Color.BLACK);
                    setText("");
                }
            }
        });
    }

}
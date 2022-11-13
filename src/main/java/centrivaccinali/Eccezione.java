/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

/**
 * Si tratta di un eccezione per la verifica della corretta formattazione di codice fiscale, sigla e comune
 *
 * @author danie
 */
public class Eccezione extends Exception {
    public Eccezione() {
        super();
    }

    public Eccezione(String message) {
        super(message);
    }
}
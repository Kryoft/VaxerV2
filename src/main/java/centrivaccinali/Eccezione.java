/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

/**
 * Si tratta di un'eccezione generale utilizzata per la verifica della corretta formattazione di codice fiscale, sigla e comune
 *
 * @author Daniele Caspani
 */
public class Eccezione extends Exception {

    /**
     * Lancia una nuova eccezione con {@code null} come messaggio.
     */
    public Eccezione() {
        super();
    }

    /**
     * Lancia una nuova eccezione con {@code message} come messaggio.
     *
     * @param message il messaggio che questa eccezione deve mostrare quando lanciata
     */
    public Eccezione(String message) {
        super(message);
    }
}
package shared;

import java.sql.SQLException;

/**
 * ...
 *
 * @author Daniele Caspani
 */
public class DBException extends SQLException {
    public DBException() {
        super();
    }

    public DBException(String source_window, String sql_state, String message) {
        System.err.println(manageException(source_window, sql_state, message));
    }

    public String manageException(String source_window, String state, String message){
        String exception_message = null;
        switch (state) {
            case "23503":
                if (source_window.equals("Vaccinato"))
                   exception_message= "Il centro indicato non esiste";
                else if (source_window.equals("Iscritto"))
                    exception_message="Non esiste alcun vaccinato per il codice fiscale selezionato";
                else if (source_window.equals("Log_Evento"))
                    if (message.contains("ISCRITTI"))
                        exception_message = "Non è presente alcun utente per l'email inserita";
                    else
                        System.err.println("Non esiste l'evento indicato");
                break;

            case "23505":
                if (source_window.equals("Vaccinato")){
                    if (message.contains("Identifier"))
                        exception_message = "continuare ciclo di creazione numeri casuali";
                    else
                        exception_message = "Il codice fiscale selezionato è già presente";}

                else if (source_window.equals("Iscritto")) {
                    if (message.contains("Username")){
                        exception_message = "Nome utente già inserito";}
                    else if (message.contains("Cod_Fiscale")){
                        exception_message = "Il codice fiscale selezionato appartiene già ad un utente";}
                    else
                        exception_message = "Email già inserita";}

                else if (source_window.equals("Log_Evento"))
                    exception_message = "Hai già inserito questo evento avverso";
                break;
            default:
                exception_message = "DB Exception: (SQL State = " + state + "), Exception message : " + message;
        }
        return exception_message;
    }
}

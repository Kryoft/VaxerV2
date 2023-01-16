package shared;

import java.sql.SQLException;

public class DBException extends SQLException {
    public DBException() {
        super();
    }

    public DBException(String tab,String state,String message) {
        System.err.println(manageException(tab,state,message));
    }

    public String manageException(String tab, String state,String message){
        String s=null;
        switch(state) {
            case "23503":
                if(tab.equals("Vaccinato"))
                   s= "Il centro indicato non esiste";
                if(tab.equals("Iscritto"))
                    s="Non esiste alcun vaccinato per il codice fiscale selezionato";
                if(tab.equals("Log_Evento"))
                    if (message.contains("ISCRITTI"))
                        s="Non è presente alcun utente per l'email inserita";
                    else
                        System.err.println("Non esiste l'evento indicato");
                break;

            case "23505":
                if(tab.equals("Vaccinato")){
                    if(message.contains("Identifier"))
                        s="continuare ciclo di creazione numeri casuali";
                    else
                        s= "Il codice fiscale selezionato è già presente";}

                if(tab.equals("Iscritto")) {
                    if(message.contains("Username")){
                        s="Nome utente già inserito";}
                    else if (message.contains("Cod_Fiscale")){
                        s="Il codice fiscale selezionato appartiene già ad un utente";}
                    else
                        s= "Email già inserita";}
                if(tab.equals("Log_Evento"))
                    s="Hai già inserito questo evento avverso";

                break;
            default:s="Error:" + state + " " + message;
        }
        return s;
    }
}

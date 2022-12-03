package shared;

import java.sql.SQLException;

public class DBException extends SQLException {
    public DBException() {
        super();
    }

    public DBException(int tab, String state,String message) {
        switch(tab){

            case 1:
                if(state.equals("22003"))
                    System.err.println("Errore la stringa è troppo lunga");
                if(state.equals("22001"))
                    System.err.println("Errore l'intero deve essere più piccolo");
                if(state.equals("23503"))
                    System.err.println("violato il vincolo foreign key");
                if(state.equals("22005"))
                    if(message.contains("login"))
                        System.err.println("vincolo unico login venuto meno");
                    else
                        System.err.println("non rispettato il vincolo primary key key");
                    break;

            case 2:
            case 3:
            case 4:
        }
    }
}

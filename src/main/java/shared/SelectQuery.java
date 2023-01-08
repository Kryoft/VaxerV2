package shared;

import centrivaccinali.CentroVaccinale;
import centrivaccinali.IndirizzoComposto;
import cittadini.Cittadino;
import cittadini.EventoAvverso;
import cittadini.Vaccinato;

public class SelectQuery {

    private static String putApices(String s){ return "'" + s + "'";}

    public static String insertCentro(CentroVaccinale centro){
        IndirizzoComposto indirizzo = centro.getIndirizzo();

        String nome = putApices( centro.getNomeCentro() );
        String comune = putApices( indirizzo.getComune() );
        String sigla = putApices( indirizzo.getSiglaProvincia() );
        String qualificatore = putApices( indirizzo.getQualificatore().toString() );
        String nome_via = putApices( indirizzo.getNomeVia() );
        String num_civico = putApices( Integer.toString(indirizzo.getNumCivico()) );
        String cap = putApices( indirizzo.getCap() );
        String tipologia = putApices(centro.getTipologia().toString());

        String ins_centro = "INSERT INTO centrovaccini(nome,comune,sigla,qualificatore,nome_via,num_civico,cap,Tipologia)\n"
                + "VALUES(" + nome +","+ comune +","+ sigla +","+ qualificatore +","
                + nome_via +","+ num_civico +","+ cap +","+ tipologia + ")";
        return ins_centro;
    }

    public static String insertVaccinato(Vaccinato vaccinato){
        String cod_centro = Integer.toString(DBClient.getIdCentroByName(vaccinato.getNomeCentro()));

        String cod_fiscale = putApices(vaccinato.getCodiceFiscale());
        String nome = putApices(vaccinato.getNome());
        String cognome = putApices(vaccinato.getCognome());
        String data = putApices(vaccinato.getData().toString());
        String vaccino = putApices(vaccinato.getVaccino().toString());
        cod_centro = putApices(cod_centro);

        String ins_vaccinato = "INSERT INTO Vaccinati(cod_fiscale,nome,cognome,data,vaccino,cod_centro)\n" +
                "VALUES(" + cod_fiscale +","+ nome +","+ cognome +","+ data +","+ vaccino +","+ cod_centro + ")";
        return ins_vaccinato;
    }

    public static String insertIscritto(Cittadino cittadino){
        String email = putApices(cittadino.getEmail());
        String username = putApices(cittadino.getLogin().getUserId());
        String password = putApices(cittadino.getLogin().getPassword());
        String cod_fiscale = putApices(cittadino.getCodiceFiscale());

        String ins_iscritto = "INSERT INTO Iscritti VALUES(" + email +","+ username +","+ password +","+ cod_fiscale + ")";
        return ins_iscritto;
    }

    public static String insertEvento(EventoAvverso evento_avverso){
        String codice_centro = Integer.toString(DBClient.getIdCentroByName(evento_avverso.getNomeCentro()));

        String cod_fiscale = putApices(evento_avverso.getCod_fiscale());
        String cod_centro = putApices(codice_centro);
        String evento = putApices(evento_avverso.getEvento().toString());
        System.out.println(evento_avverso.getEvento().toString());
        String indice = putApices(Integer.toString(evento_avverso.getIndice()));
        String note = putApices(evento_avverso.getNoteOpzionali());

        String ins_evento = "INSERT INTO Log_Eventi VALUES(" + cod_centro +","+ cod_fiscale +","+ evento +","+ indice +","+ note + ")";
        return ins_evento;
    }

    public static String getCentroVaccinaleByName(String nome_centro){
        nome_centro = putApices(nome_centro);
        String select_centro = "SELECT * FROM CentroVaccini WHERE Nome = " + nome_centro;
        return select_centro;
    }

    public static String getIdCentroByName(String nome_centro){
        nome_centro = putApices(nome_centro);
        String select_centro = "SELECT Codice FROM CentroVaccini WHERE Nome = " + nome_centro + ";";
        return select_centro;
    }

    public static String getNomeCentroById(int id_centro){
        String id_centro_string = putApices(String.valueOf(id_centro));
        String select_centro = "SELECT Nome FROM CentroVaccini WHERE codice = " + id_centro_string + ";";
        return select_centro;
    }

    public static String getCfFromUsername(String username){
        username = putApices(username);
        String select_iscritto = "SELECT cod_fiscale FROM Iscritti WHERE username = " + username + ";";
        return select_iscritto;
    }

    public static String getCittadinoByUsername(String username){
        username = putApices(username);
        String select_iscritto = "SELECT Email, Centrovaccini.nome AS Nome_Centro, Vaccinati.Nome AS Nome_Vaccinato, " +
                "Cognome, Vaccinati.Cod_Fiscale AS Cf_Vaccinato, Username, Password, Identificativo FROM Iscritti, Vaccinati, Centrovaccini " +
                "WHERE Username = " + username + ";";
        return select_iscritto;
    }

    public static String getCountVaccinatiByCentro(){
        String select_count = "SELECT Identificativo FROM Vaccinati JOIN CentroVaccini ON Cod_Centro = codice";
        return select_count;
    }

    public static String getSegnalazioniByCentro(String id_centro){
        String select_eventi = "SELECT * FROM Log_Eventi WHERE Cod_Centro = " + putApices(id_centro) + ";";
        return select_eventi;
    }

    public static String checkEventoGiaSegnalato(EventoAvverso evento){
        String cf = putApices(evento.getCod_fiscale());
        String id_centro = putApices(String.valueOf(DBClient.getIdCentroByName(evento.getNomeCentro())));
        String nome_evento = putApices(evento.getEvento().toString());

        String select = "SELECT * FROM Log_Eventi " +
                "WHERE cod_centro = " + id_centro + " AND cod_fiscale = " + cf + " AND nome_evento = " + nome_evento + ";";
        return select;
    }

    public static String getValoriPerEventoAvverso(String nome_centro){
        String cod_centro = putApices(String.valueOf(DBClient.getIdCentroByName(nome_centro)));
        String select = "SELECT Nome_Evento, AVG(indice) AS Media_Indici, COUNT(Indice) AS Numero_Segnalazioni " +
                "FROM Log_Eventi WHERE Cod_Centro = "+ cod_centro +" GROUP BY Nome_Evento";
        return select;
    }

    public static String getVaccinatiListByCentro(String nome_centro){

        String id_centro = String.valueOf(DBClient.getIdCentroByName(nome_centro));
        String select_vaccinato = "SELECT Data, Vaccino, CentroVaccini.Nome AS Nome_Centro, Identificativo, " +
                "Vaccinati.Nome AS Nome_Vaccinato, Cognome FROM Vaccinati JOIN CentroVaccini ON Cod_Centro = codice " +
                "WHERE cod_centro = " + id_centro + ";";
        return select_vaccinato;
    }

    public static String getVaccinatoByCF(String cf){
        cf = putApices(cf);
        String select_vaccinato = "SELECT Data, Vaccino, CentroVaccini.Nome AS Nome_Centro, Identificativo, " +
                "Vaccinati.Nome AS Nome_Vaccinato, Cognome FROM Vaccinati JOIN CentroVaccini ON Cod_Centro = codice " +
                "WHERE Cod_Fiscale = " + cf + ";";
        return select_vaccinato;
    }
    public static String getVaccinatoIdByCF(String cf ){
        cf = putApices(cf);
        String select_vaccinato = "SELECT Identificativo FROM Vaccinati WHERE Cod_Fiscale = " + cf + ";" ;
        return select_vaccinato;
    }

    public static String cercaCentri(String nome_centro){
        final String select_centri = "SELECT Nome FROM CentroVaccini WHERE Nome LIKE '" + nome_centro + "%';";
        return select_centri;
    }
}

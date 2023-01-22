package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Classe astratta che contiene i parametri (indirizzo ip e porta) utili ad accedere al database
 */
public abstract class RemoteInformation {

    /**
     * Indirizzo IP dell'host del server
     */
    private static String ip_address;

    /**
     * Porta su cui il servizio remoto viene fornito. Il valore di default è 54234, ma è successivamente modificabile
     */
    private static String PORT = "54234";

    /**
     * Ottiene l'IP locale di questo computer e imposta l'IP di questa classe a tale valore
     *
     * @throws UnknownHostException nel caso in cui non sia possibile ottenere l'indirizzo IP dal nome del localhost
     */
    public static void setIpToLocalHost() throws UnknownHostException {
        ip_address = InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * Imposta l'IP memorizzato da questa classe al valore del parametro <code>ip_address</code>
     *
     * @param ip_address l'IP da impostare per questa classe
     */
    public static void setIpAddress(String ip_address) {
        RemoteInformation.ip_address = ip_address;
    }

    /**
     * Restituisce il valore dell'indirizzo IP memorizzato da questa classe
     *
     * @return l'IP memorizzato da questa classe
     */
    public static String getIpAddress()  {
       return ip_address;
    }

    /**
     * Restituisce il valore della porta
     *
     * @return la porta memorizzata da questa classe, dove il servizio remoto viene esposto
     */
    public static String getPORT() {
        return PORT;
    }

    /**
     * Imposta il valore della porta a quello fornito come argomento
     *
     * @param PORT la porta desiderata
     */
    public static void setPORT(String PORT) {
        RemoteInformation.PORT = PORT;
    }
}

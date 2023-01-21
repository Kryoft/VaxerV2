package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * classe che contiene i parametri (porta e indirizzo ip)
 * utili per accedere al database
 */

public abstract class RemoteInformation {

    /**
     * ip dell'host server
     */
    private static String ip_address;

    /**
     * porta inizialmente di default per il servizio remoto, successivamente modificabile
     */
    private static String PORT = "54234";

    /**
     * determina l'ip dinamicamente
     * @return
     * @throws UnknownHostException
     */
    public static void setIpToLocalHost() throws UnknownHostException {
        ip_address = InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * setta l'ip nel caso in cui l'indirizzo calcolato dinamicamente sia di una macchina virtuale o comunque incorretto
     */
    public static void setIpAddress(String ip_address) {
        RemoteInformation.ip_address = ip_address;
    }

    /**
     * ritorna il valore del campo ip_host
     * @return
     */
    public static String getIpAddress()  {
       return ip_address;
    }

    /**
     * ritorna il valore della porta
     * @return
     */
    public static String getPORT() {
        return PORT;
    }

    /**
     * permette di stabilire il valore della porta
     * @param PORT
     */
    public static void setPORT(String PORT) {
        RemoteInformation.PORT = PORT;
    }
}

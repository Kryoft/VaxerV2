package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * classe che contiene i parametri (porta e indirizzo ip)
 * utili per accedere al database
 */

public class RemoteInformation {

    /**
     * informazioni riguardanti l'host locale
     */
    private static InetAddress address ;

    /**
     * porta inizialmente di default per il servizio remoto, successivamente modificabile
     */
    private static String PORT="54234";
    /**
     * ip dell'host server
     */
    private static String ip_host;

    /**
     * determina l'ip dinamicamente
     * @return
     * @throws UnknownHostException
     */

    public static String setIp_default() throws UnknownHostException{
        address= InetAddress.getLocalHost();
        ip_host= address.getHostAddress();
        return ip_host;
    }

    /**
     * setta l'ip nel caso in cui l'indirizzo calcolato dinamicamente sia di una macchina virtuale o comunque incorretto
     */

    public static void setIp_host(String ip_host) {
        RemoteInformation.ip_host = ip_host;
    }

    /**
     * ritorna il valore del campo ip_host
     * @return
     */
    public static String getIp_host()  {
       return ip_host;
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

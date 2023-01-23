package server;

import java.net.*;
import java.util.Enumeration;

/**
 * Classe astratta che contiene i parametri (indirizzo ip e porta) utili ad accedere al database
 *
 * @author Daniele Caspani
 */
public abstract class RemoteInformation {

    /**
     * Indirizzo IP dell'host del server
     */
    private static String ip_address = "";

    /**
     * Porta su cui il servizio remoto viene fornito. Il valore di default è 54234, ma è successivamente modificabile
     */
    private static int PORT = 54234;

    /**
     * Ottiene l'IP locale di questo computer e imposta l'IP di questa classe a tale valore
     */
    public static void setIpToLocalHost() {
//        ip_address = InetAddress.getLocalHost().getHostAddress();

        /* https://www.educative.io/answers/how-to-get-the-ip-address-of-a-localhost-in-java
         * Un computer può avere più interfacce di rete, e ognuna di esse può essere assegnata a più indirizzi IP.
         * Ognuno di questi IP potrebbe o potrebbe non essere raggiungibile da un'altra macchina nella rete locale.
         * Questo metodo fa un loop di tutti gli indirizzi IP per tutte le interfacce, controllando
         * se tale IP è quello locale (di questa macchina) oppure no. Nel caso lo sia, imposta l'indirizzo IP
         * memorizzato da questa classe a tale valore.
         */
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
                    if (interfaceAddress.getAddress().isSiteLocalAddress())
                        ip_address = interfaceAddress.getAddress().getHostAddress();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
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
    public static int getPORT() {
        return PORT;
    }

    /**
     * Imposta il valore della porta a quello fornito come argomento
     *
     * @param PORT la porta desiderata
     */
    public static void setPORT(int PORT) {
        RemoteInformation.PORT = PORT;
    }
}

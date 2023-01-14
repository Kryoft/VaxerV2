package server;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class RemoteInformation {
    private static InetAddress address ;
    private static String ip_host;
    private static String PORT="54234";

    public static InetAddress getAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    public static void setAddress(InetAddress address) {
        RemoteInformation.address = address;
    }

    public static String getIp_host() throws UnknownHostException {
       address= InetAddress.getLocalHost();
        return address.getHostAddress();
    }

    public static void setIp_host(String ip_host) {
        RemoteInformation.ip_host = ip_host;
    }

    public static String getPORT() {
        return PORT;
    }

    public static void setPORT(String PORT) {
        RemoteInformation.PORT = PORT;
    }
}

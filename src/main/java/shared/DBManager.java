package shared;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DBManager implements DBInterface, Remote {
    static int PORT = 54323;

    @Override
    public ArrayList<String[]> executeQuery(String query) throws RemoteException, SQLException {
        ArrayList<String[]> a = new ArrayList<String[]>();
        String[] s= new String[4];
        String rs_mail=null,rs_username=null,rs_password=null,rs_cod_fisc=null;
        Connection conn = DBInterface.connected();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        int i=0;
        while(rs.next()){
            rs_mail = rs.getString("email");
            rs_username=rs.getString("username");
            rs_password=rs.getString("password");
            rs_cod_fisc=rs.getString("cod_fiscale");
            s[0] = rs_mail;
            s[1] = rs_username;
            s[2]= rs_password;
            s[3]=rs_cod_fisc;
            a.add(s);
            i++;
        }
        return a;
    }
}
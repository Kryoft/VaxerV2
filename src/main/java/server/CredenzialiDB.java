package server;

public class CredenzialiDB {
    private static String user = "";
    private static String password ="";
    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        CredenzialiDB.password = password;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CredenzialiDB.user = user;
    }

}

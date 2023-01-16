package server;

public class CredenzialiDB {
    private static String user = "";
    private static String password ="";

    private static String[] message= new String[3];
    public static String getPassword() {
        return password;
    }

    public static String getIndiceMessage(int indice) {
        return message[indice];
    }
    public static String[] getMessage() {
        return message;
    }

    public static void setMessage(String message,int indice) {
        CredenzialiDB.message[indice] = message;
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

    public static boolean isValid(String user,String password,String conferma_password){

        setMessage("",0);
        setMessage("",1);
        setMessage("",2);

        boolean b=true;
        if(!user.equals(getUser())){
            String s = "Username errato";
            setMessage("Username errato",0);
            b=false;
        }

        if(!password.equals(getPassword())) {
            setMessage("password incorretta",1);
            b=false;
        }

        if(!password.equals(conferma_password)) {
            setMessage("Le due password non coincidono",2);
            b=false;
        }

        return b;
    }
}

package ExtraClasses;

public class User {
    public boolean isValidUserCredentials(String sUserName, String sUserPassword){
        if (sUserName.equals("test") && sUserPassword.equals("test123")){
            return true;
        } else {
            return false;
        }
    }
}

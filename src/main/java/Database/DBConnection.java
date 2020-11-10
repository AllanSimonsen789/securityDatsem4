package Database;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    String url;
    String user;
    String passowrd;

    //  Database credentials
    public DBConnection(){
        try {
            InputStream prob = null;
            prob = DBConnection.class.getResourceAsStream("/db.properties");
            Properties pros = new Properties();
            pros.load(prob);
            url = pros.getProperty("url");
            user = pros.getProperty("user");
            passowrd = pros.getProperty("password");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassowrd() {
        return passowrd;
    }
}

package Database;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBConnection {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3307/users";
    static String url;
    static String user;
    static String passowrd;

    //  Database credentials
    public DBConnection(Boolean testMode){
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
}

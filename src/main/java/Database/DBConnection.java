package Database;

import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    // JDBC driver name and database URL
    private static String url;
    private static String user;
    private static String passowrd;
    private static String driver;

    private static DBConnection instance;

    public static DBConnection getInstance() throws IOException {
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public DBConnection() throws IOException {
        InputStream prob = null;
        prob = DBConnection.class.getResourceAsStream("/db.properties");
        Properties pros = new Properties();
        pros.load(prob);
        //assign parameters
        url = pros.getProperty("url");
        user = pros.getProperty("user");
        passowrd = pros.getProperty("password");
        driver = pros.getProperty("driver");
    }

    //  Database credentials
    public Connection getConnection(){
        Connection conn = null;
        try {
            //Create connection
            if (conn == null){
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, passowrd);
            }
        } catch ( SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
        return conn;
    }


}

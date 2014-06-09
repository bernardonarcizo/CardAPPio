/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardAPPio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sbvb && pedromrnd
 */
public class Global {

    public static  Logger log = Logger.getLogger("CardAPPio");
    public static final String driver = "org.postgresql.Driver";
    public static final String driverStr = "postgresql";
    public static final String database = "//localhost/db_cardAPPio";
    public static final String user = "postgres";
    public static final String pwd = "postgres";
    public static final String token = "|";

    public static final String OK = "OK";
    public static final String NOT_OK = "NOT-OK - ";

    public static Connection connectDB() throws ClassNotFoundException, SQLException {
        Class.forName(driver);

        // for mysql
        Connection con = DriverManager.getConnection(
                "jdbc:" + driverStr + ":" + database + "?"
                + "user=" + user + "&password=" + pwd);

        return con;

    }

    public static String getUrlData(String url) {
        try {
            URL website = new URL(url);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            return response.toString();
        } catch (Exception ex) {
            return "Exception happened";
        }
    }

    public static String encodeURI(String s) {
        StringBuilder o = new StringBuilder();
        for (char ch : s.toCharArray()) {
            if (isUnsafe(ch)) {
                o.append('%');
                o.append(toHex(ch / 16));
                o.append(toHex(ch % 16));
            } else {
                o.append(ch);
            }
        }
        return o.toString();
    }

    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    private static boolean isUnsafe(char ch) {
        if (ch > 128 || ch < 0) {
            return true;
        }
        return " %$&+,/:;=?@<>#%".indexOf(ch) >= 0;
    }

}

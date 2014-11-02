package alexacountry;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author alex_ag
 */
public class AlexaCountry {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection con = null   ;
        PreparedStatement pst = null;
        ResultSet rs = null;
        PreparedStatement insert = null;
        Properties prop = new Properties();
        InputStream input = null;

        try {         
            String country = "";
            
            input = new FileInputStream("config.properties");
            prop.load(input);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement("SELECT * FROM alexa_language"
                    + " where ranking not in (select alexa_country.ranking from alexa_country)"
                    + " and language = 'ar'");
            rs = pst.executeQuery();
            insert = con.prepareStatement("INSERT INTO alexa_country(ranking, link, language, country) VALUES(?, ?, ?, ?)");

            while (rs.next()) {
                try {
                    Document doc = Jsoup.connect("http://www.alexa.com/siteinfo/"+ rs.getString(2))
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0")
                    .timeout(30000)
                    .get();

                    country = doc.getElementsByAttributeValue("data-cat", "countryRank").get(0).getElementsByTag("a").get(0).text();
                }
                catch (Exception ex2) {
                    try {
                        if (ex2.getMessage().length() < 93)
                            country = "Error: " + ex2.getMessage();
                        else
                            country = "Error: " + ex2.getMessage().substring(0, 93);
                    }
                    catch (Exception ex3) {
                        country = "Error: Probably NullPointerException";
                    }
                }
                
                insert.setInt(1, rs.getInt(1));
                insert.setString(2, rs.getString(2));
                insert.setString(3, rs.getString(3));
                insert.setString(4, country);
                insert.executeUpdate();
                System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " + country);
            }
        } catch (SQLException | IOException ex) {
                Logger lgr = Logger.getLogger(AlexaCountry.class.getName());
                lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (insert != null) {
                    insert.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(AlexaCountry.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }        
    }
}



import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
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

/**
 *
 * @author alex
 */
public class AlexaLanguage {

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
            input = new FileInputStream("config.properties");
            prop.load(input);
            String url = prop.getProperty("url");
            String user = prop.getProperty("user");
            String password = prop.getProperty("password");
          
            DetectorFactory.loadProfile("/home/alex_ag/Dropbox/Programming/Alexa/profiles");
            Detector detector;
            
            String language;
            
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement("SELECT * FROM alexa"
                    + " where ranking not in (select alexa_language.ranking from alexa_language) "
                    + " and ranking >= " + args[0]
                    + " and ranking <= " + args[1]
                    + " and (link like '%.com' or "
                    + "      link like '%.edu' or "
                    + "      link like '%.net' or "
                    + "      link like '%.org' or "
                    + "      link like '%.com/%' or "
                    + "      link like '%.edu/%' or "
                    + "      link like '%.net/%' or "
                    + "      link like '%.org/%')"
                    + " and link not like 'youtube.com/%'"
                    + " order by ranking");
            rs = pst.executeQuery();
            insert = con.prepareStatement("INSERT INTO alexa_language(ranking, link, language) VALUES(?, ?, ?)");

            while (rs.next()) {
                try {
                    Document doc = Jsoup.connect("http://www." + rs.getString(2))
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:21.0) Gecko/20100101 Firefox/21.0")
                    .timeout(30000)
                    .get();

                    detector = DetectorFactory.create();
                    detector.append(doc.text());
                    language = detector.detect();
                }
                catch (Exception ex2) {
                    try {
                        if (ex2.getMessage().length() < 93)
                            language = "Error: " + ex2.getMessage();
                        else
                            language = "Error: " + ex2.getMessage().substring(0, 93);
                    }
                    catch (Exception ex3) {
                        language = "Error: Probably NullPointerException";
                    }
                }
                
                insert.setInt(1, rs.getInt(1));
                insert.setString(2, rs.getString(2));
                insert.setString(3, language);
                insert.executeUpdate();
                System.out.println(rs.getInt(1) + " - " + rs.getString(2) + " - " + language);
            }
        } catch (SQLException | LangDetectException | IOException ex) {
                Logger lgr = Logger.getLogger(AlexaLanguage.class.getName());
                lgr.log( Level.SEVERE, ex.getMessage(), ex);

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
                Logger lgr = Logger.getLogger(AlexaLanguage.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }        
    }
}
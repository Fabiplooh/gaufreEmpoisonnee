package Global;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class OurConfiguration {
    Properties properties;
    public static OurConfiguration is_instance = null;

    private OurConfiguration(String fileName) throws IOException {
        properties = new Properties();
        FileInputStream my_file = new FileInputStream(fileName);
        properties.load(my_file);
    }

    public static OurConfiguration instance() {
        if (is_instance == null){
            try{
                is_instance = new OurConfiguration("default.cfg");
            } catch (IOException e) {
                System.out.println("pas de fichier trouvé");
            }
        }
        return is_instance;
    }

    public String getProperty (String propertyName) {
        String result = properties.getProperty(propertyName);
        if ( result == null){
            result = "";
        }
        return result;
    }

}

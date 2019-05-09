import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConstantsProvider {

    private static String propertyFilePath = System.getProperty("user.dir")+"/src/constants.properties";
    private static ConstantsProvider instance = new ConstantsProvider();


    private Properties properties;

    private ConstantsProvider(){
        properties = new Properties();
        try {
            //properties.load(this.getClass().getClassLoader().getResourceAsStream("../../config.properties"));
            properties.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConstantsProvider getInstance(){
        return instance;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }
}

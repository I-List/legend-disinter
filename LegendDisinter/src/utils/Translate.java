/**
 * Created for a student project at Penn State
 */
package utils;

import java.io.*;
import java.net.*;
import javax.net.ssl.HttpsURLConnection;
import gui.Logger;


/**
 *
 * @author Isaiah List
 */
public class Translate
{
    static String subscriptionKey = "Enter Your Microsoft Translator Key Here";

    
    public static String Translate (String text) throws Exception {
        String encoded_query = URLEncoder.encode (text, "UTF-8");
        URL url = new URL ("https://api.microsofttranslator.com/V2/Http.svc/Translate?text=" 
                + encoded_query + "&to=en");


        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        Logger.log("Connected");
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);
        Logger.log("Key sent");
        connection.setDoOutput(true);
        Logger.log("Connected");

        StringBuilder response = new StringBuilder ();
        BufferedReader in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()));
        Logger.log("Translating");
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        
        Logger.log("Complete.");
        int index = response.indexOf(">");
        System.out.println(response.toString());
        response.replace(0, index + 1, "");
        System.out.println(response.toString());
        index = response.indexOf("<");
        int endex = response.length();
        response.replace(index, endex, "");
        System.out.println(response.toString());
        
        return response.toString();
    }
}


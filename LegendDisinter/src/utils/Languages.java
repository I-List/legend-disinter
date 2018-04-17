/*
 * Created for a student project at Penn State
 */
package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.util.Pair;

/**
 *
 * @author Isaiah List
 */
public class Languages
{
    protected final static Map<String, Pair<String, String>> languages = 
            new HashMap<String, Pair<String, String>>();
    protected static Set<String> languageKeys;
    
    public static void initialize(){
        languages.put("Afrikaans", new Pair("af","afr"));
        languages.put("Arabic", new Pair("ar","ara"));
        languages.put("Bosnian", new Pair("bs","bos"));
        languages.put("Bulgarian", new Pair("bg","bul"));
        languages.put("Catalan", new Pair("ca","cat"));
        languages.put("Chinese Simplified", new Pair("zh-Hans","chi_sim"));
        languages.put("Chinese Traditional", new Pair("zh-Hant","chi-tra"));
        languages.put("Croatian", new Pair("hr","hrv"));
        languages.put("Czech", new Pair("cs","ces"));
        languages.put("Danish", new Pair("da","dan"));
        languages.put("German", new Pair("de","deu"));
        languages.put("Greek", new Pair("el","ell"));
        languages.put("English", new Pair("en","eng"));
        languages.put("Estonian", new Pair("et","est"));
        languages.put("Finnish", new Pair("fi","fin"));
        languages.put("French", new Pair("fr","fra"));
        languages.put("Haitian", new Pair("ht","hat"));
        languages.put("Hebrew", new Pair("he","heb"));
        languages.put("Hindi", new Pair("hi","hin"));
        languages.put("Hungarian", new Pair("hu","hun"));
        languages.put("Indonesian", new Pair("id","ind"));
        languages.put("Italian", new Pair("it","ita"));
        languages.put("Japanese", new Pair("ja","jpn"));
        languages.put("Klingon", new Pair("tlh","eng"));
        languages.put("Korean", new Pair("ko","kor"));
        languages.put("Latvian", new Pair("lv","lav"));
        languages.put("Lithuanian", new Pair("lt","lit"));
        languages.put("Maltese", new Pair("mt","mlt"));
        languages.put("Norwegian", new Pair("nb","nor"));
        languages.put("Persian", new Pair("fa","fas"));
        languages.put("Polish", new Pair("pl","pol"));
        languages.put("Portuguese", new Pair("pt","por"));
        languages.put("Romanian", new Pair("ro","ron"));
        languages.put("Russian", new Pair("ru","rus"));
        languages.put("Slovak", new Pair("sk","slk"));
        languages.put("Slovenian", new Pair("sl","slv"));
        languages.put("Swedish", new Pair("sv","swe"));
        languages.put("Tamil", new Pair("ta","tam"));
        languages.put("Thai", new Pair("th","tha"));
        languages.put("Turkish", new Pair("tr","tur"));
        languages.put("Urdu", new Pair("ur","urd"));
        languages.put("Vietnamese", new Pair("vi","vie"));
        languages.put("Welsh", new Pair("cy","cym"));
        
        languageKeys = languages.keySet();
    }
    
    public static String[] getLanguageOptionsLong(){
        String[] langArray = 
                languageKeys.toArray(new String[languageKeys.size()]);
        
        return langArray;
    }
    
    public static String get2Code(String language){
        Pair<String, String> codes = languages.get(language);
        String code2 = codes.getKey();
        
        return code2;
    }
    
    public static String get3Code(String language){
        Pair<String, String> codes = languages.get(language);
        String code3 = codes.getValue();
        
        return code3;
    }

}

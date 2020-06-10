/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Elit.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author megapc
 */
public class WikipediaApi {
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType());
        return map;
    }
    
    public String getRes(String searchTerm)
    {
        
     String urlString = "https://en.wikipedia.org/w/api.php?action=query"
             + "&format=json"
             + "&generator=search"
             + "&gsrsearch="+searchTerm
             +"&gsrnamespace=0"
             + "&gsrlimit=1"
             + "&prop=extracts"
             +"&exchars=300"
             +"&exlimit=max"
             + "&explaintext=true"
             + "&exintro=true";
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            Map<String, Object> respMap = jsonToMap("{"+result.toString().substring(72));
       System.out.println(result);
      //      System.out.println(result.toString().substring(72));
        //    List<Object> l = (List<Object>) respMap.get("query");
 LinkedTreeMap<String, Object> llll = (LinkedTreeMap<String, Object>) respMap.get("query");
    //        System.out.println(llll.get("pages"));
             LinkedTreeMap<String, Object> l2 = (LinkedTreeMap<String, Object>) llll.get("pages");
     //        System.out.println(l2.keySet());
     //        System.out.println(l2.values());
             
             System.out.println("sss");
              //   System.out.println(llll.get("pages").toString().replace("(extract)",""));
            //      Map<String, Object> mainMap = jsonToMap(llll.get("pages").toString().replace("(",""));
     
String extract = result.substring(result.indexOf("extract")+10,result.indexOf(".")+1);
            System.out.println(extract);
            System.out.println(extract.substring(extract.length()-3,extract.length()));
            
            return extract;
    //        List<Object> l = (List<Object>) respMap.get("list");

            //     System.out.println(l.get(0));
       //     LinkedTreeMap<String, Object> llll = (LinkedTreeMap<String, Object>) l.get(0);
            //    LocalDateTime x = LocalDateTime.

//            System.out.println(min.until(x,ChronoUnit.SECONDS));
            //            System.out.println(x);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return  e.getMessage();
        }
     
    }
    
}

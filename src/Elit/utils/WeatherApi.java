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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author megapc
 */


public class WeatherApi {

    /**
     * @param args the command line arguments
     */
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType());
        return map;
    }

    public Elem get(LocalDateTime minTime) {

        // TODO code application logic here
        String urlString = "http://api.openweathermap.org/data/2.5/forecast?q=tunis&APPID=92a1a30c76d9df27ab451e77adf22b3b&units=metric";
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

            Map<String, Object> respMap = jsonToMap(result.toString());

            List<Object> l = (List<Object>) respMap.get("list");

            //     System.out.println(l.get(0));
            LinkedTreeMap<String, Object> llll = (LinkedTreeMap<String, Object>) l.get(0);
            //    LocalDateTime x = LocalDateTime.

//            System.out.println(min.until(x,ChronoUnit.SECONDS));
            //            System.out.println(x);
            long minDif = 10000000;
         //   LocalDateTime minTime = LocalDateTime.of(2020, 4, 17, 23, 13, 0);

            Elem e = new Elem(llll.get("main").toString().substring(6, 8),
                    llll.get("weather").toString().substring(llll.get("weather").toString().indexOf("description=") + 12, llll.get("weather").toString().lastIndexOf(",")),
                    llll.get("weather").toString().substring(llll.get("weather").toString().indexOf("icon=") + 5, llll.get("weather").toString().lastIndexOf("}")),
                    llll.get("dt_txt").toString());
            System.out.println("First element : " + e);
            for (Object o : l) {

                LinkedTreeMap<String, Object> lll = (LinkedTreeMap<String, Object>) o;
//              System.out.println("DateTime :"+lll.get("dt_txt"));
//            System.out.println("Temperature :"+Integer.parseInt(lll.get("main").toString().substring(6, 8)));
//            System.out.println("Description : "+lll.get("weather").toString().substring(lll.get("weather").toString().indexOf("description=")+12,lll.get("weather").toString().lastIndexOf(",")));
//            System.out.println("Icon : "+lll.get("weather").toString().substring(lll.get("weather").toString().indexOf("icon=")+5,lll.get("weather").toString().lastIndexOf("}")));
//            
// 
                String date = lll.get("dt_txt").toString();
                String desc = lll.get("weather").toString().substring(lll.get("weather").toString().indexOf("description=") + 12, lll.get("weather").toString().lastIndexOf(","));
                String temp = lll.get("main").toString().substring(6, 8);
                String image = lll.get("weather").toString().substring(lll.get("weather").toString().indexOf("icon=") + 5, lll.get("weather").toString().lastIndexOf("}"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime current = LocalDateTime.parse(date, formatter);
                if (Math.abs(minTime.until(current, ChronoUnit.SECONDS)) < minDif) {
                    minDif = Math.abs(minTime.until(current, ChronoUnit.SECONDS));

                    e.setDate(date);
                    e.setDesc(desc);
                    e.setImage(image);
                    e.setTemp(temp);

                }
            }
            System.out.println("Min : " + e);
            return e;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}


import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class JSONTest {

    public static void main(String[] args) {
        HashMap<String, Integer> counter = new HashMap<String, Integer>();

        try {
            BufferedReader bf = new BufferedReader(new FileReader(args[0]));
            String line; 
            Gson gson = new Gson();

            while( (line = bf.readLine()) != null ) {
                HashMap<String, Object> json = gson.fromJson(line, new TypeToken<HashMap<String,Object>>() {}.getType());
                HashMap<String, Pattern> toMatch = new HashMap<String, Pattern>();
               
                toMatch.put("travyon", Pattern.compile("trayvon", Pattern.CASE_INSENSITIVE));
                toMatch.put("#iamtravyonmartin", Pattern.compile("#iamtrayvonmartin", Pattern.CASE_INSENSITIVE));
                toMatch.put("zimmerman", Pattern.compile("zimmerman", Pattern.CASE_INSENSITIVE));
            
                String created_at = (String) json.get("created_at");
                String text       = (String) json.get("text");

                // get the user
                JsonObject user = (JsonObject) gson.toJsonTree(json.get("user"));
                long user_id  = ((JsonPrimitive) user.get("id")).getAsLong();

                // Sat Mar 12 01:49:55 +0000 2011
                SimpleDateFormat strptime = new SimpleDateFormat("EEE MMM dd kk:mm:ss ZZZZZ yyyy");
                SimpleDateFormat strftime = new SimpleDateFormat("yyyy-MM-dd");

                StringBuffer sf = strftime.format(strptime.parse(created_at, new ParsePosition(0)),
                                                  new StringBuffer(),
                                                  new FieldPosition(0));               
                
                System.out.println(user_id);
                
                // check if word is in tweet
                // for( String k : toMatch.keySet() ) {
                //     if (toMatch.get(k).matcher(text).find()) {
                //         int newVal = 1;
                //         if (counter.containsKey(k)) {
                //             newVal = counter.get(k).intValue() + 1;
                //             counter.remove(k);
                //         }
                //         counter.put(k, new Integer(newVal));
                //     }
                // }
            }

        } catch (Exception e) {
            System.err.println( e.getMessage());
        }

        for (String k : counter.keySet()) {
            System.out.println(k + "\t" + counter.get(k).toString());
        }
    }
}

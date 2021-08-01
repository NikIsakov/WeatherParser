
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Weather {

    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q="+
                message+"&units=metric&appid=b59f21ae2c41dc3fd2babc116d0d6514");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()){
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONObject structure = (JSONObject) object.get("city");
        model.setName((String) structure.get("name"));
        JSONArray getArray = object.getJSONArray("list");
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for (int i = 0; i<getArray.length();i++){
            JSONObject obj = getArray.getJSONObject(i);
            jsonObjectList.add(obj);
        }

        List<Integer> pressureList = new ArrayList<>();
        Map<Double, String> differenceList = new HashMap<>();
        Double night = 0.0;
        Double morning = 0.0;

        for (int i = 0; i < jsonObjectList.size(); i++) {
            model.setDt((String) jsonObjectList.get(i).get("dt_txt"));
            JSONObject main = jsonObjectList.get(i).getJSONObject("main");
            model.setTemp(main.getDouble("temp"));
            model.setPressure(main.getInt("pressure"));
            pressureList.add(model.getPressure());

            String text = model.getDt();
            String dateText = text.substring(text.length()-8);

            if (dateText.equals("00:00:00") || dateText.equals("06:00:00")){

                if (dateText.equals("00:00:00")){ //ночь
                    night = model.getTemp();
                }
                if (dateText.equals("06:00:00")){ //утро
                    morning = model.getTemp();
                }
                if (night!=0.0 && morning!=0.0) {
                    Double difference = Math.abs(night - morning);
                    differenceList.put(difference, model.getDt());
                }
            }
        }

        System.out.println("город: "+model.getName());
        maxPressure(pressureList);
        System.out.println("Минимальная разница температуры ночью (00:00) и утром (06:00) "+
                differenceList.get(Collections.min(differenceList.keySet()))+" = "+
                Collections.min(differenceList.keySet())+"C");
        return "";
    }

    private static void maxPressure(List<Integer> pressureList) {
        int min = pressureList.get(0);
        int max = pressureList.get(0);

        for (Integer y: pressureList) {
            if(y < min)
                min = y;
            if(y > max)
                max = y;
        }
        System.out.println("Максимальное давление в предстоящие 5 дней: "+max);
    }
}

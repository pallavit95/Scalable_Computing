// All API/sensor functionality done by Jiaming and discussed as a whole to the team
package API;

import Model.Weather;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAPI {
    // weather information API url
    public final static String API_URL = "https://api.help.bj.cn/apis/weather2d/?id=";

    public InputStream getInputStream(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("accept", "*/*");
        httpURLConnection.setRequestProperty("connection", "Keep-Alive");
        httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        //get stream
        return httpURLConnection.getInputStream();
    }
    //return temperature information format as String
    public String queryWeather(String cityName){
        BufferedReader br;
        String temperature1 = "";
        try {
            //get the url
            URL url = new URL(API_URL + cityName);
            //crawl data from the web
            InputStream is = getInputStream(url);
            //transfor steam to bufferReader
            br = new BufferedReader(new InputStreamReader(is));
            String str = "";
            String temperature = "";
            while((str = br.readLine()) != null){
                //get temperature data from json format
                if(str.length() > 1 && str.charAt(1) == 't') {
                    temperature = str.substring(8);
                    temperature = temperature.substring(1, temperature.length()-3);
                    temperature = temperature+" temperature";
                }
            }
            temperature1 = temperature;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temperature1;
    }
    // return temperature information format as String
    public String queryWeatherVianumber(int num){
        //1 - Beijing
        //2 - Shanghai
        //3 - Hongkong
        //4 - Xian
        //5 - Urumqi
        //6 - Lhasa
        //7 - Wuhan
        //8 - Nanchang
        //9 - Changsha
        //10 - Xi'ning
        if(num == 1){
            return queryWeather("北京");
        } else if(num == 2){
            return queryWeather("上海");
        } else if(num == 3){
            return queryWeather("香港");
        } else if(num == 4){
            return queryWeather("上饶");
        } else if(num == 5){
            return queryWeather("乌鲁木齐");
        } else if(num == 6){
            return queryWeather("拉萨");
        } else if(num == 7){
            return queryWeather("武汉");
        } else if(num == 8){
            return queryWeather("南昌");
        } else if(num == 9){
            return queryWeather("长沙");
        } else if(num == 10){
            return queryWeather("西宁");
        }
        return null;
    }
    // test
    public static void main(String[] args) {
        WeatherAPI api = new WeatherAPI();
        for(int i = 1; i <= 10; i++){
            String s = api.queryWeatherVianumber(i);
            System.out.println(s);
        }

    }
}

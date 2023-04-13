// All API/sensor functionality done by Jiaming and discussed as a whole to the team
package Model;

public class Weather {
    private String temperature;
    private String pm25;

    public void setTemperature(String temperature){
        this.temperature = temperature;
    }

    public void setPm25(String pm25){
        this.pm25 = pm25;
    }

    public String getTemperature(){
        return temperature;
    }

    public String getPm25(){
        return pm25;
    }
}

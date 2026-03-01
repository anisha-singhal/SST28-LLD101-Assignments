public class AirConditioner implements PowerControl, TemperatureControl{
    public void turnOn(){
        System.out.println("AC ON");
    }
    public void turnOff(){
        System.out.println("AC OFF");
    }
    public void setTemperature(int temp) {
        System.out.println("AC set to " + temp + "C");
    }
}

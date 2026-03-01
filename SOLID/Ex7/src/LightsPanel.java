public class LightsPanel implements PowerControl, BrightnessControl{
    public void turnOn(){
        System.out.println("Lights ON");
    }
    public void turnOff(){
        System.out.println("Lights OFF");
    }
    public void setBrightness(int level) {
        System.out.println("Lights set to " + level + "%");
    }
}
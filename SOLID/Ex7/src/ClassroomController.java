import java.util.*;

class ClassroomController {
    private final DeviceRegistry reg;

    ClassroomController(DeviceRegistry reg) {
        this.reg = reg;
    }

    void startClass() {
        reg.getFirst(PowerControl.class).turnOn();              // projector
        reg.getFirst(BrightnessControl.class).setBrightness(60); // lights
        reg.getFirst(TemperatureControl.class).setTemperature(24); // AC
        reg.getFirst(Attendance.class).scanAttendance();          // scanner
    }

    void endClass() {
        System.out.println("Shutdown sequence:");
        for (PowerControl p : reg.getAll(PowerControl.class)) {
            p.turnOff();   // turns off projector, lights, AC (all PowerControl devices)
        }
    }
}

import java.util.*;

public class DeviceRegistry {
    private final List<Object> devices = new ArrayList<>();

    public void add(Object d) { devices.add(d); }

    // Get first device that has this capability
    public <T> T getFirst(Class<T> capability) {
        for (Object d : devices) {
            if (capability.isInstance(d)) {
                return capability.cast(d);
            }
        }
        throw new IllegalStateException("No device with: " + capability.getSimpleName());
    }

    // Get ALL devices that have this capability
    public <T> List<T> getAll(Class<T> capability) {
        List<T> result = new ArrayList<>();
        for (Object d : devices) {
            if (capability.isInstance(d)) {
                result.add(capability.cast(d));
            }
        }
        return result;
    }
}

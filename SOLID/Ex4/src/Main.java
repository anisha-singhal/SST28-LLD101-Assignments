import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");

        RoomPricing roomPricing = new RoomPricing();
        roomPricing.register(LegacyRoomTypes.SINGLE, 14000.0);
        roomPricing.register(LegacyRoomTypes.DOUBLE, 15000.0);
        roomPricing.register(LegacyRoomTypes.TRIPLE, 12000.0);

        AddOnPricing addOnPricing = new AddOnPricing();
        addOnPricing.register(AddOn.MESS, 1000.0);
        addOnPricing.register(AddOn.LAUNDRY, 500.0);
        addOnPricing.register(AddOn.GYM, 300.0);

        List<FeeComponent> components = List.of(roomPricing, addOnPricing);

        HostelFeeCalculator calc = new HostelFeeCalculator(components, new FakeBookingRepo());

        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        calc.process(req);
    }
}

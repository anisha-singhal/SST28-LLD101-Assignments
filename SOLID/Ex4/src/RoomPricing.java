import java.util.*;

public class RoomPricing implements FeeComponent{
    private final Map<Integer, Double> prices = new HashMap<>();

    public void register(int roomType, double price){
        prices.put(roomType, price);
    }

    public Money calculate(BookingRequest req){
        return new Money(prices.getOrDefault(req.roomType, 16000.0));
    }
}
import java.util.*;

public class AddOnPricing implements FeeComponent{
    private final Map<AddOn, Double> prices = new HashMap<>();

    public void register(AddOn addOn, double price){
        prices.put(addOn, price);
    }

    public Money calculate(BookingRequest req){
        double total = 0.0;
        for (AddOn a : req.addOns) {
            total += prices.getOrDefault(a, 0.0); 
        }
        return new Money(total);
    }
}
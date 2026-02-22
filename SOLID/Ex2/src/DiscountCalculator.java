import java.util.*;

public class DiscountCalculator{
    public double calculateDiscount(String customerType, double subtotal, List<OrderLine> lines){
        double discount = DiscountRules.discountAmount(customerType, subtotal, lines.size());
        return discount;
    }
}
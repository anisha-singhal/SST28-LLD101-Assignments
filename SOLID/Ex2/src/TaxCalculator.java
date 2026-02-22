import java.util.*;

public class TaxCalculator{
    public double calculateTax(double subtotal, String customerType){
        double taxPct = TaxRules.taxPercent(customerType);
        double tax = subtotal * (taxPct / 100.0);

        return tax;
    }
}
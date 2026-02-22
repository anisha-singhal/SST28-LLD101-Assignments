import java.util.*;

public class CafeteriaSystem {

    private final Map<String, MenuItem> menu;
    private final PriceCalculator priceCalculator;
    private final TaxCalculator taxCalculator;
    private final DiscountCalculator discountCalculator;
    private final InvoicePrinter printer;
    private final InvoiceRepository repository;

    private int invoiceSeq = 1000;

    public CafeteriaSystem(Map<String, MenuItem> menu, PriceCalculator priceCalculator, TaxCalculator taxCalculator, DiscountCalculator discountCalculator, InvoicePrinter printer, InvoiceRepository repository) {
        this.menu = menu;
        this.priceCalculator = priceCalculator;
        this.taxCalculator = taxCalculator;
        this.discountCalculator = discountCalculator;
        this.printer = printer;
        this.repository = repository;
    }

    public void checkout(String customerType, List<OrderLine> lines) {

        String invId = "INV-" + (++invoiceSeq);

        double subtotal = priceCalculator.calculateSubTotal(menu, lines);

        double taxPct = TaxRules.taxPercent(customerType);
        double tax = taxCalculator.calculateTax(subtotal, customerType);

        double discount = discountCalculator.calculateDiscount(customerType, subtotal, lines);

        double total = subtotal + tax - discount;

        String invoiceText = printer.format(invId, menu, lines, subtotal, taxPct, tax, discount, total);
        System.out.print(invoiceText);

        repository.save(invId, invoiceText);
        System.out.println("Saved invoice: " + invId + " (lines=" + repository.countLines(invId) + ")");
    }
}

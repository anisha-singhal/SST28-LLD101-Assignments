import java.util.*;

public class HostelFeeCalculator {
    private final List<FeeComponent> components;
    private final FakeBookingRepo repo;

    public HostelFeeCalculator(List<FeeComponent> components, FakeBookingRepo repo) {
        this.components = components;
        this.repo = repo;
    }

    public void process(BookingRequest req) {
        // loop through all fee components
        Money monthly = new Money(0);
        for (FeeComponent c : components) {
            monthly = monthly.plus(c.calculate(req));
        }

        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }
}

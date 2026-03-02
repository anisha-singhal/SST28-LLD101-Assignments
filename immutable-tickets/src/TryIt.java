import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demo showing that immutability actually works.
 *
 * - No setters to call, so direct mutation won't compile
 * - Tags list is unmodifiable, so external tampering throws an exception
 * - Service "updates" return a new object; the original stays unchanged
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // service methods now return new tickets — original is untouched
        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nAfter assign + escalate (new object): " + escalated);
        System.out.println("Original still unchanged            : " + t);

        // try to tamper with the tag list from outside
        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("BUG — list should be unmodifiable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked (UnsupportedOperationException)");
        }

        // quick proof that builder validation works
        try {
            new IncidentTicket.Builder("", "bad-email", "")
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught bad input: " + e.getMessage());
        }
    }
}

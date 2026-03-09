import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        IncidentTicket t = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created: " + t);

        // assign and escalate - these return new objects, original doesnt change
        IncidentTicket assigned = service.assign(t, "agent@example.com");
        IncidentTicket escalated = service.escalateToCritical(assigned);
        System.out.println("\nAfter assign + escalate (new object): " + escalated);
        System.out.println("Original still unchanged            : " + t);

        // try to mess with tags from outside - should throw
        List<String> tags = escalated.getTags();
        try {
            tags.add("HACKED_FROM_OUTSIDE");
            System.out.println("BUG - list should be unmodifiable!");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nExternal tag mutation blocked (UnsupportedOperationException)");
        }

        // check that validation works
        try {
            new IncidentTicket.Builder("", "bad-email", "")
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught bad input: " + e.getMessage());
        }
    }
}

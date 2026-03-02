package com.example.tickets;

import java.util.Arrays;

/**
 * Service layer that creates and "updates" tickets.
 *
 * Since IncidentTicket is now immutable, every operation that needs
 * to change something returns a brand-new ticket instead of mutating.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        return new IncidentTicket.Builder(id, reporterEmail, title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(Arrays.asList("NEW"))
                .build();
    }

    /**
     * Creates a new ticket that's a copy of the original but with
     * CRITICAL priority and an ESCALATED tag added.
     */
    public IncidentTicket escalateToCritical(IncidentTicket t) {
        return t.toBuilder()
                .priority("CRITICAL")
                .addTag("ESCALATED")
                .build();
    }

    /** Returns a new ticket with the assignee set. */
    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        return t.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}

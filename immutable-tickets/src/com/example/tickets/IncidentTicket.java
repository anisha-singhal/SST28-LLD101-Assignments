package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable incident ticket. All state is set at construction time
 * through the Builder, and validated before the object is created.
 *
 * Once built, nothing can change — no setters, no mutable leaks.
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;

    private final String description;
    private final String priority;       // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags;
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;
    private final String source;         // e.g. "CLI", "WEBHOOK", "EMAIL"

    // only the builder can create tickets
    private IncidentTicket(Builder b) {
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        this.tags = Collections.unmodifiableList(new ArrayList<>(b.tags));
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    // --- getters (no setters!) ---

    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; }  // already unmodifiable
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    /**
     * Returns a new Builder pre-filled with this ticket's data,
     * so you can "modify" by creating a slightly different copy.
     */
    public Builder toBuilder() {
        Builder b = new Builder(this.id, this.reporterEmail, this.title);
        b.description = this.description;
        b.priority = this.priority;
        b.tags = new ArrayList<>(this.tags);
        b.assigneeEmail = this.assigneeEmail;
        b.customerVisible = this.customerVisible;
        b.slaMinutes = this.slaMinutes;
        b.source = this.source;
        return b;
    }

    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }

    // ========================= Builder =========================

    public static class Builder {
        // required
        private String id;
        private String reporterEmail;
        private String title;

        // optional – sensible defaults
        private String description;
        private String priority;
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail;
        private boolean customerVisible;
        private Integer slaMinutes;
        private String source;

        public Builder(String id, String reporterEmail, String title) {
            this.id = id;
            this.reporterEmail = reporterEmail;
            this.title = title;
        }

        public Builder description(String val) { this.description = val; return this; }
        public Builder priority(String val)    { this.priority = val;    return this; }
        public Builder assigneeEmail(String v) { this.assigneeEmail = v; return this; }
        public Builder customerVisible(boolean v) { this.customerVisible = v; return this; }
        public Builder slaMinutes(Integer val)  { this.slaMinutes = val; return this; }
        public Builder source(String val)       { this.source = val;     return this; }

        public Builder tags(List<String> val) {
            // defensive copy so caller can't mess with our list later
            this.tags = new ArrayList<>(val);
            return this;
        }

        public Builder addTag(String tag) {
            this.tags.add(tag);
            return this;
        }

        /**
         * Validates everything and returns an immutable IncidentTicket.
         * All validation is centralised here — nowhere else.
         */
        public IncidentTicket build() {
            // required fields
            Validation.requireTicketId(id);
            Validation.requireEmail(reporterEmail, "reporterEmail");
            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");

            // optional but constrained
            Validation.requireOneOf(priority, "priority",
                    "LOW", "MEDIUM", "HIGH", "CRITICAL");

            if (assigneeEmail != null && !assigneeEmail.trim().isEmpty()) {
                Validation.requireEmail(assigneeEmail, "assigneeEmail");
            }

            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");

            return new IncidentTicket(this);
        }
    }
}

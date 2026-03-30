package com.moviebooking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BookingService {
    private List<MovieTicket> tickets;
    private List<Payment> payments;
    private List<SeatHold> holds;
    private List<PricingRule> pricingRules;
    private final ReentrantLock bookingLock;
    private int paymentCounter;

    public BookingService() {
        this.tickets = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.holds = new ArrayList<>();
        this.pricingRules = new ArrayList<>();
        this.bookingLock = new ReentrantLock();
        this.paymentCounter = 0;
    }

    public void addPricingRule(PricingRule rule) {
        pricingRules.add(rule);
    }

    public void removePricingRule(PricingRule rule) {
        pricingRules.remove(rule);
    }

    public List<PricingRule> getPricingRules() {
        return pricingRules;
    }

    public double calculatePrice(Show show, Seat seat) {
        double basePrice = seat.getPrice();
        double finalPrice = basePrice;
        for (PricingRule rule : pricingRules) {
            finalPrice = rule.applyRule(finalPrice, basePrice, show, seat);
        }
        return Math.max(finalPrice, basePrice);
    }

    public SeatHold holdSeats(Show show, List<String> seatNumbers, String userId) {
        bookingLock.lock();
        try {
            releaseExpiredHolds();

            for (String seat : seatNumbers) {
                if (!show.isSeatAvailable(seat)) {
                    System.out.println("Seat " + seat + " is not available. Hold failed.");
                    return null;
                }
            }

            for (String seatNum : seatNumbers) {
                show.holdSeat(seatNum);
            }

            SeatHold hold = new SeatHold(show, seatNumbers, userId);
            holds.add(hold);
            System.out.println("Seats held: " + hold.getHoldId() + " | Seats: " + seatNumbers);
            return hold;
        } finally {
            bookingLock.unlock();
        }
    }

    public MovieTicket confirmBooking(String holdId, PaymentMode paymentMode) {
        bookingLock.lock();
        try {
            releaseExpiredHolds();

            SeatHold hold = null;
            for (SeatHold h : holds) {
                if (h.getHoldId().equals(holdId)) {
                    hold = h;
                    break;
                }
            }

            if (hold == null) {
                System.out.println("Hold not found: " + holdId);
                return null;
            }

            if (hold.isExpired()) {
                System.out.println("Hold expired: " + holdId);
                return null;
            }

            if (hold.isConfirmed()) {
                System.out.println("Hold already confirmed: " + holdId);
                return null;
            }

            double total = 0;
            Show show = hold.getShow();
            for (String seatNum : hold.getSeatNumbers()) {
                Seat seat = show.getSeatByNumber(seatNum);
                if (seat != null) {
                    total += calculatePrice(show, seat);
                }
            }

            for (String seatNum : hold.getSeatNumbers()) {
                show.bookSeat(seatNum);
            }
            hold.confirm();

            MovieTicket ticket = new MovieTicket(show, hold.getSeatNumbers(), total, hold.getUserId(), paymentMode);
            tickets.add(ticket);

            Payment payment = new Payment("PAY-" + (++paymentCounter), ticket.getTicketId(), total, "CHARGE", paymentMode);
            payments.add(payment);
            System.out.println("Booked " + ticket.getTicketId() + " | Seats: " + hold.getSeatNumbers()
                    + " | Amount: " + total + " | Payment: " + paymentMode);

            return ticket;
        } finally {
            bookingLock.unlock();
        }
    }

    public Payment cancelTicket(MovieTicket ticket) {
        bookingLock.lock();
        try {
            if (ticket.getStatus() == BookingStatus.CANCELLED) {
                System.out.println("Ticket already cancelled.");
                return null;
            }

            for (String seatNum : ticket.getBookedSeats()) {
                ticket.getShow().freeSeat(seatNum);
            }

            ticket.cancel();

            Payment refund = new Payment("PAY-" + (++paymentCounter), ticket.getTicketId(),
                    ticket.getTotalAmount(), "REFUND", ticket.getPaymentMode());
            payments.add(refund);
            System.out.println("Cancelled " + ticket.getTicketId() + " | Refund: " + refund.getAmount()
                    + " to " + refund.getPaymentMode());

            return refund;
        } finally {
            bookingLock.unlock();
        }
    }

    private void releaseExpiredHolds() {
        for (SeatHold hold : holds) {
            if (hold.isExpired() && !hold.isConfirmed()) {
                for (String seatNum : hold.getSeatNumbers()) {
                    hold.getShow().freeSeat(seatNum);
                }
                System.out.println("Hold expired, seats released: " + hold.getHoldId());
            }
        }
        holds.removeIf(h -> h.isExpired() && !h.isConfirmed());
    }
}

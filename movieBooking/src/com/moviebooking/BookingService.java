package com.moviebooking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class BookingService {
    private List<MovieTicket> tickets;
    private List<Payment> payments;
    private final ReentrantLock bookingLock;
    private int paymentCounter;

    public BookingService() {
        this.tickets = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.bookingLock = new ReentrantLock();
        this.paymentCounter = 0;
    }

    public MovieTicket bookTickets(Show show, List<String> seatNumbers) {
        bookingLock.lock();
        try {
            for (String seat : seatNumbers) {
                if (!show.isSeatAvailable(seat)) {
                    System.out.println("Seat " + seat + " is not available. Booking failed.");
                    return null;
                }
            }

            double total = 0;
            for (String seatNum : seatNumbers) {
                show.bookSeat(seatNum);
                Seat seat = show.getSeatByNumber(seatNum);
                if (seat != null) total += seat.getPrice();
            }

            MovieTicket ticket = new MovieTicket(show, seatNumbers, total);
            tickets.add(ticket);

            Payment payment = new Payment("PAY-" + (++paymentCounter), ticket.getTicketId(), total, "CHARGE");
            payments.add(payment);
            System.out.println("Booked " + ticket.getTicketId() + " | Seats: " + seatNumbers + " | Amount: " + total);

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

            Payment refund = new Payment("PAY-" + (++paymentCounter), ticket.getTicketId(), ticket.getTotalAmount(), "REFUND");
            payments.add(refund);
            System.out.println("Cancelled " + ticket.getTicketId() + " | Refund: " + refund.getAmount());

            return refund;
        } finally {
            bookingLock.unlock();
        }
    }
}

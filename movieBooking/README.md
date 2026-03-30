# Movie Ticket Booking System

A movie ticket booking system in Java that handles theatres, shows, seat booking with temporary holds, dynamic pricing with admin-configurable rules, multiple payment modes, concurrency control, and cancellation with refunds.

## How to Run

```bash
cd movieBooking/src
javac com/moviebooking/*.java
java com.moviebooking.Main
```

## APIs

### Browse APIs

| Method | What it does |
|--------|-------------|
| `showTheatres(city)` | Returns all theatres in a city |
| `showMovies(city)` | Returns all movies playing in theatres in that city |
| `showShowsForMovie(movieId, city)` | User picks a movie, sees all theatres and time slots showing it |
| `showShowsInTheatre(theatreId)` | User picks a theatre, sees all movies and time slots in it |
| `showAvailableSeats(showId)` | Shows the seat map with availability for a show |
| `getPrice(showId, seatNumber)` | Returns dynamic price for a seat after all pricing rules are applied |

### Booking APIs

| Method | What it does |
|--------|-------------|
| `holdSeats(showId, seats, userId)` | Temporarily holds seats for 5 minutes while user pays |
| `confirmBooking(holdId, paymentMode)` | Completes payment (UPI/Card/NetBanking) and generates ticket |
| `cancelTicket(ticket)` | Cancels booking, frees seats, processes refund to original payment mode |

### Admin APIs

| Method | What it does |
|--------|-------------|
| `addMovie(movie)` | Adds a movie to the system |
| `addTheatre(theatre)` | Adds a theatre to the system |
| `addShow(show)` | Adds a show (movie + screen + time slot), thread-safe |
| `addPricingRule(rule)` | Injects a pricing rule at runtime |
| `removePricingRule(rule)` | Removes a pricing rule at runtime |

### User Management

| Method | What it does |
|--------|-------------|
| `registerUser(id, name, email)` | Registers user, rejects duplicate emails |

## Classes

| Class | What it does |
|-------|-------------|
| `MovieBookingSystem` | Main entry point, ties everything together, exposes all APIs |
| `BookingService` | Handles seat holds, booking confirmation, cancellation, pricing calculation with thread safety |
| `ShowService` | Manages shows, thread-safe addition by admins using ReentrantLock |
| `Theatre` | Has an id, name, city, and list of screens |
| `Screen` | Has an id, name, and list of seats (Flyweight - seats shared across shows) |
| `Show` | Links a movie to a screen at a time, tracks seat availability and holds |
| `Movie` | Has id, title, duration |
| `Seat` | Has seat number, type (SILVER/GOLD/PLATINUM), and base price |
| `SeatHold` | Temporary 5-minute hold on seats during payment window |
| `MovieTicket` | Generated after payment, holds show, seats, amount, status, userId, paymentMode |
| `Payment` | Tracks charges and refunds with payment mode (UPI/Card/NetBanking) |
| `User` | Has id, name, email (unique per email) |
| `City` | Simple wrapper for city name |
| `PricingRule` | Strategy interface for dynamic pricing rules |
| `DemandPricingRule` | Increases price when occupancy exceeds a threshold |
| `WeekdayPricingRule` | Increases price on weekends |
| `ShowTimePricingRule` | Increases price during prime time slots |
| `SeatType` | Enum: SILVER, GOLD, PLATINUM |
| `BookingStatus` | Enum: CONFIRMED, CANCELLED |
| `PaymentMode` | Enum: UPI, CARD, NET_BANKING |

## Design Patterns

**Flyweight Pattern** for seats - Seat objects are created once in a Screen and shared across multiple Shows. Each Show only tracks its own availability (extrinsic state) via a HashMap, while seat number, type, and base price (intrinsic state) stay shared.

**Strategy Pattern** for pricing - `PricingRule` is an interface with multiple implementations (`DemandPricingRule`, `WeekdayPricingRule`, `ShowTimePricingRule`). Admin can inject/remove rules at runtime. Rules are stacked - each rule takes the current price and can increase it, but the final price can never go below the base price.

## How Booking Works

1. User browses by city → picks movies or theatres
2. User picks a show → sees the seat map with availability
3. User selects seats → `holdSeats()` temporarily locks them for 5 minutes
4. During hold, other users see these seats as unavailable
5. User picks payment mode (UPI/Card/NetBanking) → `confirmBooking()` calculates dynamic price, processes payment, generates ticket
6. If hold expires before payment, seats are automatically released

## How Dynamic Pricing Works

1. Every seat has a base price set per theatre (same for all movies)
2. Admin picks which pricing rules to apply at runtime
3. When calculating price, all active rules are applied in order
4. Each rule can increase the price (e.g., 1.5x for high demand, 1.2x for weekends)
5. Final price can never go below the base price

## How Concurrency is Handled

1. **Booking seats** - `BookingService` uses a `ReentrantLock`. Hold and confirm operations are atomic. Two users trying to hold the same seat - second one fails.
2. **Adding shows by admins** - `ShowService` uses its own `ReentrantLock` so concurrent show additions dont corrupt the list.
3. **Seat hold window** - Seats are available during viewing. Once a user initiates payment (hold), seats become unavailable to others. If payment isnt completed in 5 minutes, hold expires and seats are released.

## How Cancellation Works

1. Check if ticket is already cancelled
2. Free all booked seats back in the show
3. Mark ticket status as CANCELLED
4. Create a REFUND payment for the full amount to the **original payment mode**
5. All of this happens inside the lock so no race conditions

## Class Diagram

![Class Diagram](class-diagram.png)

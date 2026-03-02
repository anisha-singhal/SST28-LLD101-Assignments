# SST28-LLD101 — SOLID Refactoring Assignments

Solved refactoring exercises for the **Low-Level Design** module.

Each exercise starts with intentionally messy but working code that violates a SOLID principle. The task is to refactor while preserving the original output.

## Exercises

| # | Principle | Topic | Key Refactoring |
|---|-----------|-------|-----------------|
| Ex1 | SRP | Student Onboarding Registration | Extract parser, validator, ID generator, printer, repository from god method |
| Ex2 | SRP | Campus Cafeteria Billing | Extract pricing, tax, discount, invoice printer, repository from checkout |
| Ex3 | OCP | Placement Eligibility Rules Engine | Replace if/else chain with EligibilityRule interface and rule classes |
| Ex4 | OCP | Hostel Fee Calculator | Replace switch/if-else with FeeComponent interface and Map-based pricing |
| Ex5 | LSP | File Exporter Hierarchy | Fix exporter contract — return error results instead of throwing |
| Ex6 | LSP | Notification Sender Hierarchy | Fix sender contract — return error results instead of throwing |
| Ex7 | ISP | Smart Classroom Devices | Split fat interface into capability-based interfaces (power, brightness, temperature, scanning) |
| Ex8 | ISP | Student Club Admin Tools | Split fat ClubAdminTools interface into role-specific interfaces (finance, minutes, events) |
| Ex9 | DIP | Assignment Evaluation Pipeline | Inject abstractions (grader, checker, writer) into pipeline instead of hard-coded `new` |
| Ex10 | DIP | Campus Transport Booking | Inject abstractions (payment, driver allocator, distance calc) into booking service |

## Design Pattern Exercises

| Exercise | Pattern | Topic | Key Refactoring |
|----------|---------|-------|-----------------|
| singleton-metrics | Singleton | PulseMeter Metrics Registry | Thread-safe lazy init (Bill Pugh holder), reflection guard, serialization safety |
| immutable-tickets | Immutability + Builder | HelpLite Incident Tickets | Immutable ticket class with Builder pattern, centralized validation, defensive copying |

## How to run

SOLID exercises (Ex1–Ex10):

```bash
cd SOLID/Ex1/src
javac *.java
java Main
```

Singleton Metrics:

```bash
cd singleton-metrics/src
javac com/example/metrics/*.java
java com.example.metrics.App
```

Immutable Tickets:

```bash
cd immutable-tickets/src
javac com/example/tickets/*.java TryIt.java
java TryIt
```

- Java 17
- No Maven/Gradle
- No external libraries

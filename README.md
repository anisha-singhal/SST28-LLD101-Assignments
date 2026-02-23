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

## How to run

From any exercise folder:

```bash
cd SOLID/Ex1/src
javac *.java
java Main
```

- Java 17
- No Maven/Gradle
- Default package (no `package` lines)
- No external libraries

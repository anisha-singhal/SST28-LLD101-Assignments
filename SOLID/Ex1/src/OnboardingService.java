import java.util.*;

public class OnboardingService {

    private final StudentRepository repository;
    private final StudentParser parser;
    private final StudentValidator validator;
    private final StudentIdGenerator idGenerator;
    private final ConsolePrinter printer;

    // Constructor -> dependency injection
    public OnboardingService(
            StudentRepository repository,
            StudentParser parser,
            StudentValidator validator,
            StudentIdGenerator idGenerator,
            ConsolePrinter printer) {

        this.repository = repository;
        this.parser = parser;
        this.validator = validator;
        this.idGenerator = idGenerator;
        this.printer = printer;
    }

    // Orchestrates the onboarding workflow
    public void registerFromRawInput(String raw) {

        // Step 1: print input
        printer.printInput(raw);

        // Step 2: parse raw input
        Map<String, String> kv = parser.parse(raw);

        // Step 3: validate parsed data
        List<String> errors = validator.validate(kv);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        // Step 4: generate student ID
        int count = repository.count();
        String id = idGenerator.generate(count);

        // Step 5: extract fields
        String name = kv.get("name");
        String email = kv.get("email");
        String phone = kv.get("phone");
        String program = kv.get("program");

        // Step 6: create StudentRecord
        StudentRecord record = new StudentRecord(id, name, email, phone, program);

        // Step 7: save record
        repository.save(record);

        // Step 8: print success and confirmation
        printer.printSuccess(id, repository.count());
        printer.printConfirmation(record);
    }
}
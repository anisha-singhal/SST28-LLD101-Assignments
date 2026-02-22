public class Main {

    public static void main(String[] args) {

        System.out.println("=== Student Onboarding ===");

        FakeDb db = new FakeDb();

        // create repository using FakeDb
        StudentRepository repository =
                new FakeStudentRepository(db);

        // create other dependencies
        StudentParser parser = new StudentParser();
        StudentValidator validator = new StudentValidator();
        StudentIdGenerator idGenerator = new StudentIdGenerator();
        ConsolePrinter printer = new ConsolePrinter();

        // inject dependencies into OnboardingService
        OnboardingService svc =
                new OnboardingService(
                        repository,
                        parser,
                        validator,
                        idGenerator,
                        printer);

        String raw =
            "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";

        svc.registerFromRawInput(raw);

        System.out.println();
        System.out.println("-- DB DUMP --");

        System.out.print(TextTable.render3(db));
    }
}
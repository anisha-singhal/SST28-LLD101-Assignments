public class Main {
    public static void main(String[] args) {
        System.out.println("=== Evaluation Pipeline ===");
        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");

        PlagiarismCheck checker = new PlagiarismChecker();
        CodeGrading grader = new CodeGrader();
        ReportWriting writer = new ReportWriter();
        Rubric rubric = new Rubric();

        EvaluationPipeline pipeline = new EvaluationPipeline(checker, grader, writer, rubric);
        pipeline.evaluate(sub);
    }
}

public class EvaluationPipeline {

    private final PlagiarismCheck plagiarismCheck;
    private final CodeGrading codeGrading;
    private final ReportWriting reportWriting;
    private final Rubric rubric;

    public EvaluationPipeline(
            PlagiarismCheck plagiarismCheck,
            CodeGrading codeGrading,
            ReportWriting reportWriting,
            Rubric rubric) {

        this.plagiarismCheck = plagiarismCheck;
        this.codeGrading = codeGrading;
        this.reportWriting = reportWriting;
        this.rubric = rubric;
    }

    public void evaluate(Submission sub) {

        int plag = plagiarismCheck.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = codeGrading.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = reportWriting.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
public class ReportWriter implements ReportWriting{
    public String write(Submission s, int plag, int code) {
        return "report-" + s.roll + ".txt";
    }
}

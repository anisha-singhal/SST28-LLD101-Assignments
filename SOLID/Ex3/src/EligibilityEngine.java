import java.util.*;

public class EligibilityEngine {
    private final List<EligibilityRule> rules;
    private final FakeEligibilityStore store;

    public EligibilityEngine(List<EligibilityRule> rules, FakeEligibilityStore store){
        this.rules = rules; 
        this.store = store; 
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();

        for (EligibilityRule rule : rules) {
            String reason = rule.check(s);
            if (reason != null) {
                reasons.add(reason);
                break;  // matches original else-if behavior: stop at first failure
            }
        }

        String status = reasons.isEmpty() ? "ELIGIBLE" : "NOT_ELIGIBLE";
        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}

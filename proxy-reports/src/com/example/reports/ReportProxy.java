package com.example.reports;

// proxy sits between the client and real report
// handles: 1) access control  2) lazy loading  3) caching
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    private RealReport realReport; // created only when needed

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        // block unauthorized users before doing anything expensive
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED: " + user.getName()
                    + " (" + user.getRole() + ") cannot view "
                    + classification + " report " + reportId);
            return;
        }

        // only load from disk the first time someone accesses it
        if (realReport == null) {
            realReport = new RealReport(reportId, title, classification);
        }
        realReport.display(user);
    }
}

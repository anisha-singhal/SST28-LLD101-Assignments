package com.example.reports;

// changed to depend on Report interface instead of concrete ReportFile
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}

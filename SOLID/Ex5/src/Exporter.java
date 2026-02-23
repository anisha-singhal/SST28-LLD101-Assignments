public abstract class Exporter {

    // Contract: given a non-null request, always return a non-null ExportResult. Never throw.
    public ExportResult export(ExportRequest req) {
        if (req == null) {
            return ExportResult.error("Request must not be null");
        }
        return doExport(req);
    }

    protected abstract ExportResult doExport(ExportRequest req);
}

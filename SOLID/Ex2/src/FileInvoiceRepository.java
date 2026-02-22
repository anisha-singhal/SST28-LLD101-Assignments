public class FileInvoiceRepository implements InvoiceRepository {

    private final FileStore store;

    public FileInvoiceRepository(FileStore store) {
        this.store = store;
    }

    @Override
    public void save(String invId, String invoiceText) {
        store.save(invId, invoiceText);
    }

    @Override
    public int countLines(String invId) {
        return store.countLines(invId);
    }
}
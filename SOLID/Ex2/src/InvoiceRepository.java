import java.util.*;

public interface InvoiceRepository{
    void save(String invId, String invoiceText);
    int countLines(String invId);
}
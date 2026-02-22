import java.util.*;

public class FakeStudentRepository implements StudentRepository{
    private final FakeDb db;

    public FakeStudentRepository(FakeDb db) {
        this.db = db;
    }

    @Override
    public void save(StudentRecord record) {
        db.save(record);
    }

    @Override
    public int count() {
        return db.count();
    }
}
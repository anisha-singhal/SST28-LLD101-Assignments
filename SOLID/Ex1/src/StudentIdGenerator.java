public class StudentIdGenerator{
    public String generate(int count){
        String id = IdUtil.nextStudentId(count);
        return id;
    }
}
public class Student {
    private String name;
    private String rollNo;
    private String password;
    private String score;

    public Student(String name, String password) {
        this.name = name;
        this.password = password;
    }

    String getName() {
        return name;
    }

    String getPassword() {
        return password;
    }
}
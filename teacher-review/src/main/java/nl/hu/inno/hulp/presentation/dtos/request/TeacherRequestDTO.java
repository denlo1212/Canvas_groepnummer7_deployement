package nl.hu.inno.hulp.presentation.dtos.request;

public class TeacherRequestDTO {
    private String firstName;
    private String lastName;
    private String teacherNumber;

    public TeacherRequestDTO(String firstName, String lastName, String teacherNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.teacherNumber = teacherNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }
}
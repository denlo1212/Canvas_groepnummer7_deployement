package nl.hu.inno.hulp.presentation.dtos.request;

public class StudentRequestDto {
    private String studentID;
    private String firstName;
    private String lastName;
    private String Major;

    public StudentRequestDto(String StudentID, String firstName, String lastName, String Major) {
        this.studentID = StudentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Major = Major;
    }

    public String getStudentID() {
        return studentID;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMajor() {
        return Major;
    }
}
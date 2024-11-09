package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.Teacher;

public class TeacherDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public TeacherDTO(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static TeacherDTO build(Teacher teacher) {
        return new TeacherDTO(teacher.getId(), teacher.getFirstName(), teacher.getLastName());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
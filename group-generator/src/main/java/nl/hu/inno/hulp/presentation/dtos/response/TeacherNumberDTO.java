package nl.hu.inno.hulp.presentation.dtos.response;

public record TeacherNumberDTO(String id) {

    public static TeacherNumberDTO build(String id) {
        return new TeacherNumberDTO(id);
    }
}

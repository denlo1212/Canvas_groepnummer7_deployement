package nl.hu.inno.hulp.presentation.dtos.response;

public record StudentNumberDTO(String id) {

    public static StudentNumberDTO build(String id) {
        return new StudentNumberDTO(id);
    }
}
package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.Registration;

public record RegistrationDTO(
        Long id,
        String studentID,
        String courseID,
        PreferenceDTO preference
) {
    public static RegistrationDTO build(Registration registration) {
        return new RegistrationDTO(
                registration.getId(),
                registration.getStudentID().getStudentNumber(),
                registration.getCourseID().getCourseNumber(),
                PreferenceDTO.build(registration.getPreference())
        );
    }
}

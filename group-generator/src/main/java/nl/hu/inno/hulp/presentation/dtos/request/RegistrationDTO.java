package nl.hu.inno.hulp.presentation.dtos.request;

import nl.hu.inno.hulp.domain.Registration;

public record RegistrationDTO(
        Long id,
        String studentID,
        String courseID,
        PreferenceDTO preference
) {
}

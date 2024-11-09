package nl.hu.inno.hulp.presentation.dtos.request;

import java.util.List;

public record CourseRegistrationsDTO(
        Long id,
        String courseID,
        String name,
        String description,
        String major,
        List<RegistrationDTO> registrations
) {
}


package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.Course;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

public record CourseResponseDto(
        Long id,
        String courseID,
        String name,
        String description,
        String major,
        List<RegistrationDTO> registrations
) {
    public static CourseResponseDto build(Course course) {
        List<RegistrationDTO> registrationDTOs = course.getRegistrations() != null
                ? course.getRegistrations().stream()
                .map(RegistrationDTO::build)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new CourseResponseDto(
                course.getId(),
                course.getCourseID().getCourseNumber(),
                course.getName(),
                course.getDescription(),
                course.getMajor() != null ? course.getMajor().getName() : null,
                registrationDTOs
        );
    }
}


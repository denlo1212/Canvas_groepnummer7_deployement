package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.domain.id.CourseID;
import nl.hu.inno.hulp.domain.Registration;
import nl.hu.inno.hulp.domain.id.StudentID;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRegistrationsDTO;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationDTO;

import java.util.List;
import java.util.Objects;

public class CourseRegistrationConverter {

    public static Course convertToEntity(CourseRegistrationsDTO dto) throws NotFoundException {
        CourseID courseID = new CourseID(dto.courseID());
        Course course = new Course(courseID);

        if (dto.registrations() != null && !dto.registrations().isEmpty()) {
            List<Registration> registrations = dto.registrations().stream()
                    .map(registrationDTO -> {
                        try {
                            return CourseRegistrationConverter.convertRegistrationToEntity(registrationDTO);
                        } catch (NotFoundException exception) {
                            throw new RuntimeException("Failed to convert registration: " +
                                    (registrationDTO != null ? registrationDTO.studentID() : "null"),
                                    exception);
                        }
                    })
                    .filter(Objects::nonNull) // Filter out null registrations
                    .toList();

            registrations.forEach(course.getRegistrations()::add);
        }

        return course;
    }


    private static Registration convertRegistrationToEntity(RegistrationDTO registrationDTO) throws NotFoundException {
        StudentID studentID = new StudentID(registrationDTO.studentID());
        CourseID courseID = new CourseID(registrationDTO.courseID());

        Registration registration = new Registration(studentID, courseID);

        registrationDTO.preference().preferenceList().forEach(preferenceStr -> {
            StudentID prefStudentID;
            try {
                prefStudentID = new StudentID(preferenceStr);
                registration.getPreference().addStudentPreference(prefStudentID);
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        return registration;
    }
}

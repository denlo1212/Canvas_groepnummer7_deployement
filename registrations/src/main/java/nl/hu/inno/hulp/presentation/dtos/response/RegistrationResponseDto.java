package nl.hu.inno.hulp.presentation.dtos.response;

import nl.hu.inno.hulp.domain.CourseID;
import nl.hu.inno.hulp.domain.Registration;
import nl.hu.inno.hulp.domain.StudentID;
import nl.hu.inno.hulp.domain.enums.RegistrationStatus;

import java.util.Date;

public record RegistrationResponseDto(

        Long id,

        StudentID student,
        CourseID course,

        RegistrationStatus status,
        Date registrationDate,
        String validationMessage
)  {

    public static RegistrationResponseDto build(Registration registration) {
        return new RegistrationResponseDto(
                registration.getId(),
                registration.getStudentID(),
                registration.getCourseID(),
                registration.getStatus(),
                registration.getRegistrationDate(),
                registration.getValidationMessage()
        );
    }


}

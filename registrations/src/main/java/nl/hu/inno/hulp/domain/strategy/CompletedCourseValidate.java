package nl.hu.inno.hulp.domain.strategy;

import nl.hu.inno.hulp.domain.Registration;
import nl.hu.inno.hulp.domain.enums.RegistrationStatus;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationCheckValidtyDTO;

public class CompletedCourseValidate implements RegistrationValidator {
    @Override
    public ValidationResult validate(RegistrationCheckValidtyDTO registrationRequest) {
        for (Registration registration : Registration.getPreviousRegistrations()) {
            if (registration.getStudentID().equals(registrationRequest.getStudentId()) &&
                    registration.getCourseID().equals(registrationRequest.getCourseId()) &&
                    registration.getStatus() == RegistrationStatus.COMPLETED) {
                return ValidationResult.failure("Student has already completed this course.");
            }
        }
        return ValidationResult.success("Valid registration request.");
    }
}

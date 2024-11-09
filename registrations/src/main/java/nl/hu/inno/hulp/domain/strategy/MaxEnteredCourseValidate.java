package nl.hu.inno.hulp.domain.strategy;

import nl.hu.inno.hulp.domain.Registration;
import nl.hu.inno.hulp.domain.enums.RegistrationStatus;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationCheckValidtyDTO;

public class MaxEnteredCourseValidate implements RegistrationValidator {
    private static final int MAX_REGISTRATIONS = 6;
    @Override
    public ValidationResult validate(RegistrationCheckValidtyDTO registrationRequest) {
        long count = Registration.getPreviousRegistrations().stream()
                .filter(r -> r.getStudentID().equals(registrationRequest.getStudentId())
                        && r.getStatus() != RegistrationStatus.ENTERED)
                .count();
        if (count >= MAX_REGISTRATIONS) {
            return ValidationResult.failure("Student has reached the maximum number of registrations.");
        }
        return ValidationResult.success("Valid registration request.");
    }
}


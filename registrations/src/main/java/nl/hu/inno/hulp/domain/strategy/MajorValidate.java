package nl.hu.inno.hulp.domain.strategy;

import nl.hu.inno.hulp.presentation.dtos.request.RegistrationCheckValidtyDTO;

public class MajorValidate implements RegistrationValidator {
    @Override
    public ValidationResult validate(RegistrationCheckValidtyDTO registrationRequest) {
        if (!registrationRequest.getStudentMajor().equals(registrationRequest.getCourseMajor())) {
            return ValidationResult.failure("Student major does not match course major");
        }
        return ValidationResult.success("Valid registration request");
    }
}


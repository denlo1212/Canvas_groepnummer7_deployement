package nl.hu.inno.hulp.domain.strategy;

import nl.hu.inno.hulp.presentation.dtos.request.RegistrationCheckValidtyDTO;

// https://www.baeldung.com/java-strategy-pattern

public interface RegistrationValidator {
    ValidationResult validate(RegistrationCheckValidtyDTO registrationRequest);
}

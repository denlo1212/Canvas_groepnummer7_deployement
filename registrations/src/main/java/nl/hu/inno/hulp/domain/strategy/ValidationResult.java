package nl.hu.inno.hulp.domain.strategy;

import java.util.Objects;
import java.util.Optional;

public final class ValidationResult {
    private final Optional<String> statusMessage;
    private final boolean isError;

    private ValidationResult(Optional<String> statusMessage, boolean isError) {
        this.statusMessage = statusMessage;
        this.isError = isError;
    }

    public Optional<String> getStatusMessage() {
        return statusMessage;
    }

    public static ValidationResult success(String message) {
        return new ValidationResult(Optional.of(message), false);
    }

    public static ValidationResult failure(String message) {
        return new ValidationResult(Optional.of(message), true);
    }

    public boolean hasError() {
        return isError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidationResult that)) return false;
        return isError == that.isError && Objects.equals(getStatusMessage(), that.getStatusMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusMessage(), isError);
    }
}
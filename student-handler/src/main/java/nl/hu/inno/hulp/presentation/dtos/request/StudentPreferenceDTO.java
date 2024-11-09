package nl.hu.inno.hulp.presentation.dtos.request;

public record StudentPreferenceDTO(
        long registrationId,
        String preferenceId
) {
    public static StudentPreferenceDTO build(long registrationId, String preferenceId) {
        return new StudentPreferenceDTO(registrationId, preferenceId);
    }
}
package nl.hu.inno.hulp.presentation.dtos.response;



import nl.hu.inno.hulp.domain.Preference;
import nl.hu.inno.hulp.domain.StudentID;

import java.util.List;
import java.util.stream.Collectors;

public record PreferenceDTO(
        List<String> preferenceList
) {
    public static PreferenceDTO build(Preference preference) {
        List<String> studentIDs = preference.getPreferenceList().stream()
                .map(StudentID::getStudentNumber)
                .collect(Collectors.toList());

        return new PreferenceDTO(studentIDs);
    }
}


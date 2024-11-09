package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.api.ReadOnlyStudent;
import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class RestReadOnlyStudent implements ReadOnlyStudent {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${urlstudentmodule}")
    private String urlStudent;

    @Override
    public Optional<Student> findByStudentID(StudentID studentID) {
        ResponseEntity<Student> response = restTemplate.getForEntity(urlStudent + "/student/" + studentID, Student.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }
}

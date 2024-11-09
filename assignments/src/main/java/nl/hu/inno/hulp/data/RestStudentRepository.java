package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
@Primary
@Component
public class RestStudentRepository implements StudentRepository{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Optional<Student> findByStudentID(StudentID studentID) {
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:8080/student/" + studentID, Student.class);

        System.out.println(response.getBody());
        if (response.getStatusCode().is2xxSuccessful()) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }
}
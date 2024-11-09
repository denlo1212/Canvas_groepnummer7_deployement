package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;

import java.util.Optional;

public interface StudentRepository {
    Optional<Student> findByStudentID (StudentID studentID);
}
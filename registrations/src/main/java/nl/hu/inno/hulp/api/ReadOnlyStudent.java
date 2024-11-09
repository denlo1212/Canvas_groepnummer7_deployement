package nl.hu.inno.hulp.api;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;

import java.util.Optional;


public interface ReadOnlyStudent {
    Optional<Student> findByStudentID (StudentID studentID) throws NotFoundException;
}

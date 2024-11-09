package nl.hu.inno.hulp.data;


import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentID (StudentID studentID);
}

package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findById(Long teacherId);
}
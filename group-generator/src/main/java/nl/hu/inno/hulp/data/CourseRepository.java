package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.domain.id.CourseID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseID (CourseID courseID);
}

package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.domain.CourseID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseID (CourseID courseID);
    @Query("SELECT c FROM Course c JOIN c.registrations r WHERE r.id = :registrationId")
    Optional<Course> findCourseByRegistrationId(@Param("registrationId") Long registrationId);

}

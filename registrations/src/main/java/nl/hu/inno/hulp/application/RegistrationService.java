package nl.hu.inno.hulp.application;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.data.CourseRepository;
import nl.hu.inno.hulp.api.ReadOnlyStudent;
import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.domain.Registration;
import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationRequestDto;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationUpdateDto;
import nl.hu.inno.hulp.presentation.dtos.response.RegistrationResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
public class RegistrationService {

    private ReadOnlyStudent readOnlyStudent;

    private CourseRepository courseRepository;


    public RegistrationService(ReadOnlyStudent readOnlyStudent, CourseRepository courseRepository) {
        this.readOnlyStudent = readOnlyStudent;
        this.courseRepository = courseRepository;
    }

    public RegistrationResponseDto newRegistration(RegistrationRequestDto registrationRequestDto) throws NotFoundException {
        Student student = readOnlyStudent.findByStudentID(registrationRequestDto.getStudentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Course course = courseRepository.findByCourseID(registrationRequestDto.getCourseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        course.registerStudent(student.getStudentID(), student.getMajor().toString(), new Date());
        courseRepository.save(course);
        Registration registration = course.getRegistrations().get(course.getRegistrations().size() - 1);

        return RegistrationResponseDto.build(registration);
    }

    public RegistrationResponseDto getRegistration(Long id) throws NotFoundException {
        Course course = courseRepository.findCourseByRegistrationId(id)
                .orElseThrow(() -> new NotFoundException("Course not found"));
        Registration registration = course.getRegistrations().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Registration not found"));
        return RegistrationResponseDto.build(registration);
    }

    public RegistrationResponseDto updateRegistration(Long id, RegistrationUpdateDto updateDto) throws NotFoundException {

        Course course = courseRepository.findCourseByRegistrationId(id)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        Registration registration = course.getRegistrations().stream()
                .filter(reg -> reg.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Registration not found"));

        course.updateRegistration(updateDto.getStatus(), course.getCourseID(), registration.getStudentID());
        courseRepository.save(course);

        return RegistrationResponseDto.build(registration);
    }


    public RegistrationResponseDto deleteRegistration(Long id) throws NotFoundException {
        Course course = courseRepository.findCourseByRegistrationId(id)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        Registration registration = course.getRegistrations().stream()
                .filter(reg -> reg.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Registration not found"));

        course.getRegistrations().remove(registration);
        courseRepository.save(course);
        return RegistrationResponseDto.build(registration);
    }
}
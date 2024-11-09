package nl.hu.inno.hulp.application;

import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.data.CourseRepository;
import nl.hu.inno.hulp.domain.*;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.CourseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {
    private final CourseRepository courseRepository;


    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) throws NotFoundException {
        CourseID courseID = new CourseID(courseRequestDto.getCourseID());
        Course course = new Course(courseID, courseRequestDto.getName(), courseRequestDto.getDescription(), new Major(courseRequestDto.getMajor()));
        courseRepository.save(course);
        return CourseResponseDto.build(course);
    }

    public CourseResponseDto getCourse(String courseID) throws NotFoundException {
        CourseID id = new CourseID(courseID);
        Course course = courseRepository.findByCourseID(id).orElseThrow(() -> new NotFoundException("Course not found"));
        return CourseResponseDto.build(course);
    }

    public CourseResponseDto updateCourse(Long id, CourseRequestDto courseRequestDto) throws NotFoundException {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course not found"));
        course.updateCourse(courseRequestDto);
        courseRepository.save(course);
        return CourseResponseDto.build(course);
    }

    public CourseResponseDto deleteCourse(Long id) throws NotFoundException {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course not found"));
        courseRepository.delete(course);
        return CourseResponseDto.build(course);
    }

    public List<CourseResponseDto> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(CourseResponseDto::build)
                .collect(Collectors.toList());
    }
}
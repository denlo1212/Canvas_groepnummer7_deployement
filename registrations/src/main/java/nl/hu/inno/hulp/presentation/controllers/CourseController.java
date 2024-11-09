package nl.hu.inno.hulp.presentation.controllers;

import nl.hu.inno.hulp.application.CourseService;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.CourseResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public CourseResponseDto createCourse(@RequestBody CourseRequestDto courseRequestDto) throws NotFoundException {
        return service.createCourse(courseRequestDto);
    }

    @GetMapping("/{courseID}")
    public CourseResponseDto getCourseById(@PathVariable String courseID) {
        try {
            return service.getCourse(courseID);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public CourseResponseDto updateCourse(@PathVariable long id, @RequestBody CourseRequestDto courseRequestDto) {
        try {
            return service.updateCourse(id, courseRequestDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public CourseResponseDto deleteCourse(@PathVariable long id) {
        try {
            return service.deleteCourse(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}

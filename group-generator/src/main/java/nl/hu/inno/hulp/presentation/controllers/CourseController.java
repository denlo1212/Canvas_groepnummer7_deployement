package nl.hu.inno.hulp.presentation.controllers;

import nl.hu.inno.hulp.application.CourseService;
import nl.hu.inno.hulp.presentation.dtos.response.GroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

//    @PostMapping("/generate/{id}")
//    public List<GroupDTO> generateClassesFromCourse(@PathVariable String id){
//        try {
//            return service.generateClasses(id);
//        } catch (NotFoundException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
//        }
//    }
}
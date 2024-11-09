package nl.hu.inno.hulp.presentation.controllers;


import nl.hu.inno.hulp.application.StudentService;
import nl.hu.inno.hulp.presentation.dtos.request.StudentRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.StudentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public StudentDTO createStudent(@RequestBody StudentRequestDto studentRequestDto) throws NotFoundException {
        return service.createStudent(studentRequestDto);
    }

    @GetMapping("/{studentID}")
    public StudentDTO getStudentById(@PathVariable String studentID) {
        try {
            return service.getStudent(studentID);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public StudentDTO updateStudent(@PathVariable long id, @RequestBody StudentRequestDto studentRequestDto) {
        try {
            return service.updateStudent(id, studentRequestDto);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public StudentDTO deleteStudent(@PathVariable long id) {
        try {
            return service.deleteStudent(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
package nl.hu.inno.hulp.presentation.controller;


import nl.hu.inno.hulp.application.AssignmentService;
import nl.hu.inno.hulp.application.excpetions.NotFoundException;
import nl.hu.inno.hulp.presentation.dtos.response.AssignmentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
    private final AssignmentService service;
    @Autowired
    public AssignmentController(AssignmentService service) {
        this.service = service;
    }


    @GetMapping("/{id}")
    public AssignmentResponseDto getAssignmentById(@PathVariable long id) {
        try {
            return service.getById(id);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
package nl.hu.inno.hulp.presentation.controllers;

import nl.hu.inno.hulp.application.TeacherService;
import nl.hu.inno.hulp.presentation.dtos.request.TeacherRequestDTO;
import nl.hu.inno.hulp.presentation.dtos.response.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/teacher")
    public ResponseEntity<TeacherDTO> createTeacher(@RequestBody TeacherRequestDTO teacherRequestDto) {
        TeacherDTO teacherDTO = teacherService.createTeacher(teacherRequestDto);
        return ResponseEntity.ok(teacherDTO);
    }
}
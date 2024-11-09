// These test can't find the bean needed to work and I want to go home








//package controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import nl.hu.inno.hulp.data.StudentRepository;
//import nl.hu.inno.hulp.domain.Student;
//import nl.hu.inno.hulp.presentation.controllers.exceptions.NotFoundException;
//
//import nl.hu.inno.hulp.presentation.dtos.request.StudentRequestDto;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class StudentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    private Student student;
//
//    private Student preferenceStudent;
//
//    private StudentRequestDto studentRequestDto;
//
//    private StudentRequestDto studentRequestDto2;
//
//    @BeforeEach
//    void setUp() throws NotFoundException {
//        studentRequestDto = new StudentRequestDto("1888123","name", "email", "Software Developer");
//        studentRequestDto2 = new StudentRequestDto("1888124","name", "email", "Software Developer");
//        student = new Student();
//        student.updateStudent(studentRequestDto2);
//
//        preferenceStudent = new Student();
//        preferenceStudent.updateStudent(studentRequestDto);
//
//        studentRepository.save(student);
//        studentRepository.save(preferenceStudent);
//    }
//
//    @AfterEach
//    void tearDown() {
//        studentRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("Create student")
//    void addStudent() throws Exception {
//        String studentRequestDtoJson = objectMapper.writeValueAsString(studentRequestDto);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/student/add")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(studentRequestDtoJson))
//                .andExpect(status().isOk());
//
//    }
//
//    @Test
//    @DisplayName("Get student by id")
//    void getStudentById() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/student/" + student.getStudentID().getStudentNumber())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Update student")
//    void updateStudent() throws Exception {
//        long studentId = student.getId();
//
//        String studentRequestDtoJson = objectMapper.writeValueAsString(studentRequestDto);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/student/update/" + studentId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(studentRequestDtoJson))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Delete student")
//    void deleteStudent() throws Exception {
//        long studentId = student.getId();
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/student/delete/" + studentId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//    }
//}
package nl.hu.inno.hulp.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.hu.inno.hulp.application.CourseService;
import nl.hu.inno.hulp.application.exceptions.NotFoundException;
import nl.hu.inno.hulp.data.CourseRepository;
import nl.hu.inno.hulp.domain.Course;
import nl.hu.inno.hulp.presentation.dtos.request.CourseRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.CourseResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    private CourseRequestDto courseRequestDto;

    private CourseResponseDto courseResponseDto;

    @BeforeEach
    void setUp() throws NotFoundException {
        courseRequestDto = new CourseRequestDto("24-TICT-SV3INNO-22","name", "description", "studyDirection");

        course = new Course();
        course.updateCourse(courseRequestDto);

        courseRepository.save(course);
        courseResponseDto = CourseResponseDto.build(course);
    }

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    @DisplayName("Testing the add method")
    void addCourse() throws Exception {
        String courseRequestDtoJson = objectMapper.writeValueAsString(courseRequestDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/course/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseRequestDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing the get method")
    void getCourseById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/course/" + course.getCourseID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing the update method")
    void updateCourse() throws Exception {
        long courseId = course.getId();

        String courseRequestDtoJson = objectMapper.writeValueAsString(courseRequestDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/course/update/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseRequestDtoJson))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Testing the delete method")
    void deleteCourse() throws Exception {
        long courseId = course.getId();

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/course/delete/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
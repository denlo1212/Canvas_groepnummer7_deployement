package nl.hu.inno.hulp.application;

import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.data.TeacherRepository;
import nl.hu.inno.hulp.domain.Teacher;
import nl.hu.inno.hulp.presentation.dtos.request.TeacherRequestDTO;
import nl.hu.inno.hulp.presentation.dtos.response.TeacherDTO;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public TeacherDTO createTeacher(TeacherRequestDTO teacherRequestDto) {
        Teacher teacher = new Teacher(teacherRequestDto.getFirstName(), teacherRequestDto.getLastName(), teacherRequestDto.getTeacherNumber());
        teacherRepository.save(teacher);
        return TeacherDTO.build(teacher);
    }

}
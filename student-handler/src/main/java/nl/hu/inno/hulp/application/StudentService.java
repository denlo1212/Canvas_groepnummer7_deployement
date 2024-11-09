package nl.hu.inno.hulp.application;

import nl.hu.inno.hulp.domain.Major;
import nl.hu.inno.hulp.domain.Student;
import nl.hu.inno.hulp.domain.StudentID;
import nl.hu.inno.hulp.presentation.controllers.NotFoundException;
import nl.hu.inno.hulp.data.StudentRepository;
import nl.hu.inno.hulp.presentation.dtos.request.StudentRequestDto;
import nl.hu.inno.hulp.presentation.dtos.response.StudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDTO createStudent(StudentRequestDto studentRequestDto) throws NotFoundException {
        StudentID studentID = new StudentID(studentRequestDto.getStudentID());
        Student student = new Student(studentID, studentRequestDto.getFirstName(), studentRequestDto.getLastName(), new Major(studentRequestDto.getMajor()));
        studentRepository.save(student);
        return StudentDTO.build(student);
    }

    public StudentDTO getStudent(String studentID) throws NotFoundException {
        StudentID id = new StudentID(studentID);
        Student student = studentRepository.findByStudentID(id).orElseThrow(() -> new NotFoundException("Student not found"));
        return StudentDTO.build(student);
    }

    public StudentDTO updateStudent(Long id, StudentRequestDto studentRequestDto) throws NotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
        student.updateStudent(studentRequestDto);
        studentRepository.save(student);
        return StudentDTO.build(student);
    }

    public StudentDTO deleteStudent(Long id) throws NotFoundException {
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
        studentRepository.delete(student);
        return StudentDTO.build(student);
    }
}
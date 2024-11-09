package nl.hu.inno.hulp.application;
import jakarta.transaction.Transactional;
import nl.hu.inno.hulp.data.StaticData;
import nl.hu.inno.hulp.domain.Assignment;
import nl.hu.inno.hulp.application.excpetions.NotFoundException;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.presentation.dtos.response.AssignmentResponseDto;
import nl.hu.inno.hulp.data.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AssignmentService {
    private final AssignmentRepository repository;
    private final StaticData staticData = new StaticData();

    @Autowired
    public AssignmentService(AssignmentRepository repository) throws NotFoundException {
        this.repository = repository;
        this.loadDummyData();
    }


    private void loadDummyData() {
        Assignment assignment = staticData.getAssignments().get(0);
        List<Submission> submissions = staticData.getSubmissions();
        assignment.fillWithSubmission(submissions);
        repository.save(assignment);
    }


    public AssignmentResponseDto getById(long id) throws NotFoundException {
        return AssignmentResponseDto.build(findAssignment(id));
    }


    private Assignment findAssignment(long id) throws NotFoundException {
        Optional<Assignment> assignment = repository.findById(id);

        if (assignment.isEmpty()) {
            throw new NotFoundException("Assignment with ID: " + id + ",could not be found.");
        }
        return assignment.get();
    }
}
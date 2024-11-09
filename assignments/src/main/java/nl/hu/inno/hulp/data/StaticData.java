package nl.hu.inno.hulp.data;

import nl.hu.inno.hulp.application.excpetions.NotFoundException;
import nl.hu.inno.hulp.domain.Assignment;
import nl.hu.inno.hulp.domain.StudentID;
import nl.hu.inno.hulp.domain.Submission;
import nl.hu.inno.hulp.domain.Team;
import nl.hu.inno.hulp.domain.enums.SubmissionType;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StaticData {
    private final Long assignmentId = 1L;
    private final StudentID studentIDOne = new StudentID("1000000");
    private final StudentID studentIDTwo = new StudentID("2000000");
    private final StudentID studentIDThree = new StudentID("3000000");
    private final String workToSubmit = "This is my assignment.";

    private final List<Submission> submissions = List.of(
            new Submission(assignmentId, studentIDOne, workToSubmit),
            new Submission(assignmentId, studentIDTwo, workToSubmit),
            new Submission(assignmentId, studentIDThree, workToSubmit)
    );

    private final List<Team> teams = List.of(
            new Team(),
            new Team()
    );

    private final List<Assignment> assignments = List.of(
            new Assignment(
                    "Sustainability in Urban Development",
                    "In this assignment, you will explore the principles of sustainable urban development",
                    getDueDate(),
                    100.0,
                    SubmissionType.TEXT,
                    true
            )
    );

    public StaticData() throws NotFoundException {
    }

    public List<Submission> getSubmissions() {
        return submissions;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    private Date getDueDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 15); // Set to November 15, 2024
        return calendar.getTime();
    }
}

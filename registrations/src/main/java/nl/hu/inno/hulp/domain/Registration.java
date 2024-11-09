package nl.hu.inno.hulp.domain;

import jakarta.persistence.*;
import nl.hu.inno.hulp.domain.enums.RegistrationStatus;
import nl.hu.inno.hulp.domain.strategy.CombinedStrategy;
import nl.hu.inno.hulp.domain.strategy.RegistrationValidator;
import nl.hu.inno.hulp.domain.strategy.ValidationResult;
import nl.hu.inno.hulp.presentation.dtos.request.RegistrationCheckValidtyDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Registration {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private StudentID studentID;

    @Embedded
    private CourseID courseID;

    @OneToOne(cascade = CascadeType.ALL)
    private Preference preference;

    private RegistrationStatus status;
    private Date registrationDate;

    private String validationMessage;


    // Extra velden voor validatie
    private String studentMajor;
    private String courseMajor;

    private static List<Registration> previousRegistrations = new ArrayList<>();

    public Registration(StudentID studentID, CourseID courseID, String studentMajor, String courseMajor, Date registrationDate) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.studentMajor = studentMajor;
        this.courseMajor = courseMajor;
        this.registrationDate = registrationDate;
        this.status = RegistrationStatus.PENDING;
        this.preference = new Preference();

        checkRegistrationValidity(studentID, courseID);
        previousRegistrations.add(this);
    }

    private void checkRegistrationValidity(StudentID studentID, CourseID courseID) {
        CombinedStrategy strategy = new CombinedStrategy();
        RegistrationCheckValidtyDTO registrationCheckValidityDTO = new RegistrationCheckValidtyDTO(studentID, courseID, studentMajor, courseMajor);

        ValidationResult validationResult = validateRegistration(strategy, registrationCheckValidityDTO);

        updateRegistrationStatus(validationResult);
    }

    private ValidationResult validateRegistration(CombinedStrategy strategy, RegistrationCheckValidtyDTO registrationCheckValidityDTO) {
        StringBuilder validationMessages = new StringBuilder();

        for (RegistrationValidator validator : strategy.getValidators()) {
            ValidationResult result = validator.validate(registrationCheckValidityDTO);
            if (result.hasError()) {
                validationMessages.append(result.getStatusMessage().orElse("Error occurred")).append("\n");
            }
        }

        boolean isValid = validationMessages.length() == 0;
        return isValid ? ValidationResult.success("Registration is valid.") : ValidationResult.failure(validationMessages.toString());
    }

    private void updateRegistrationStatus(ValidationResult validationResult) {
        if (!validationResult.hasError()) {
            this.status = RegistrationStatus.ENTERED;
        } else {
            this.status = RegistrationStatus.FAILED;
        }
        this.validationMessage = validationResult.getStatusMessage().orElse("No validation messages.");
    }

    protected Registration() {
    }

    public Registration updateRegistration(RegistrationStatus status) {
        this.status = status;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentID getStudentID() {
        return studentID;
    }
    public CourseID getCourseID() {
        return courseID;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public static List<Registration> getPreviousRegistrations() {
        return previousRegistrations;
    }

    public Preference getPreference() {
        return preference;
    }
}

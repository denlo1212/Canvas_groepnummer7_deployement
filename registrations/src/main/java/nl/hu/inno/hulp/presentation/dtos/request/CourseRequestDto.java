package nl.hu.inno.hulp.presentation.dtos.request;

public class CourseRequestDto {

    private String courseID;
    private String name;
    private String description;
    private String Major;

    public CourseRequestDto(String courseID, String name, String description, String major) {
        this.courseID = courseID;
        this.name = name;
        this.description = description;
        this.Major = major;
    }

    public String getCourseID() {
        return courseID;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getMajor() {
        return Major;
    }
}
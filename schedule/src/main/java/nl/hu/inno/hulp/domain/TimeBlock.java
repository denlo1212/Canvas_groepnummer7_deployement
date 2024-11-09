package nl.hu.inno.hulp.domain;

public class TimeBlock {
    private String startTime;
    private String endTime;
    private String day;

    public TimeBlock(String startTime, String endTime, String day) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDay() {
        return day;
    }
}

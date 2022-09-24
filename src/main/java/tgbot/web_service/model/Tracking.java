package tgbot.web_service.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Tracking {

    private long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String trackingNote;

    private Task task;

    private long user;

    public Tracking() {
    }

    public Tracking(String trackingNote, Task task, long user) {
        this.startTime = LocalDateTime.now();
        this.trackingNote = trackingNote;
        this.task = task;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTrackingNote() {
        return trackingNote;
    }

    public void setTrackingNote(String trackingNote) {
        this.trackingNote = trackingNote;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tracking tracking = (Tracking) o;
        return id == tracking.id && startTime.equals(tracking.startTime) && trackingNote.equals(tracking.trackingNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, trackingNote);
    }

    @Override
    public String toString() {
        return "Tracking{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", trackingNote='" + trackingNote + '\'' +
                ", user=" + user +
                '}';
    }
}

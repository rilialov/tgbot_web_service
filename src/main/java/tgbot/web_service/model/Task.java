package tgbot.web_service.model;

import java.util.Objects;

public class Task {

    private long id;
    private String taskName;
    private String taskNote;

    public Task() {
    }

    public Task(String taskName, String taskNote) {
        this.taskName = taskName;
        this.taskNote = taskNote;
    }

    public long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && taskName.equals(task.taskName) && taskNote.equals(task.taskNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskName, taskNote);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                '}';
    }
}

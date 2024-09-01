package kg.azamat.todo.models;


public enum TaskStatus {
    COMPLETED("Выполнено"),
    NOT_COMPLETED("Не выполнено");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

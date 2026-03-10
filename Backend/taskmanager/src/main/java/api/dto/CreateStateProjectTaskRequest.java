package api.dto;

public class CreateStateProjectTaskRequest {

    private String name;

    public CreateStateProjectTaskRequest() {}

    public CreateStateProjectTaskRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
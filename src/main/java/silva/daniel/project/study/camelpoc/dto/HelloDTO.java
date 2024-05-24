package silva.daniel.project.study.camelpoc.dto;

public class HelloDTO {

    private String cod;
    private String message;

    public HelloDTO(String cod, String message) {
        this.cod = cod;
        this.message = message;
    }

    public HelloDTO() {
        //VAZIO
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

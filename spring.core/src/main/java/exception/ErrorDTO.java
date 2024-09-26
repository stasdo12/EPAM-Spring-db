package exception;

import lombok.Data;

@Data
public class ErrorDTO {

    private String message;
    private int statusCode;


    public ErrorDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }


}

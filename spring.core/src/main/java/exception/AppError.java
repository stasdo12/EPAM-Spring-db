package exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class AppError {
    private HttpStatus status;
    private String message;
    private Date timestamp;

    public AppError(HttpStatus status, String message){
        this.status = status;
        this.message = message;
        this.timestamp = new Date();
    }
}

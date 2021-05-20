package project.watch_and_relax.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Actor not found")
public class ActorNotFoundException extends RuntimeException{
    public ActorNotFoundException(String message) {
        super(message);
    }
}

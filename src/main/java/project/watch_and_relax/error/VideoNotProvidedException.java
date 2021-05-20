package project.watch_and_relax.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason = "Video not provided")
public class VideoNotProvidedException extends RuntimeException{
}

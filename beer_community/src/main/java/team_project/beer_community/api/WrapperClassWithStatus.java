package team_project.beer_community.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class WrapperClassWithStatus<E, HttpStatus> {
    private E data;
    private HttpStatus status;
}

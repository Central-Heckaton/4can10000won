package team_project.beer_community.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WrapperClassWithUserId<E> {
    private Long userId;
    private E data;
}

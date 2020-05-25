package ua.nure.moleculis.emulator.http.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.nure.moleculis.emulator.http.models.UserResponse;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PeopleResponse {
    private List<UserResponse> users;
}

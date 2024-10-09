package net.in.spacekart.backend.payloads.get.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link net.in.spacekart.backend.database.entities.User}
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSummaryDtoPublic implements Serializable {

    String firstName;
    String lastName;
    String username;
    List<String> spaceSpaceIds;
    String profilePictureUrl;

    public UserSummaryDtoPublic(String firstName, String lastName, String username, String profilePictureUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }
}
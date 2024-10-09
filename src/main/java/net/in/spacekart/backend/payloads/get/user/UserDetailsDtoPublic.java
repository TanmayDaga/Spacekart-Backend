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
public class UserDetailsDtoPublic implements Serializable {

    String firstName;
    String lastName;
    String username;
    List<String> spaceSpaceIds;
    String profilePictureUrl;
}
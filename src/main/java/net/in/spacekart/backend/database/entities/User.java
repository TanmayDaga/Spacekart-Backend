package net.in.spacekart.backend.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.constraints.AgeLimit;
import net.in.spacekart.backend.database.enums.Role;
import org.hibernate.annotations.*;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
@NamedQuery(name = "User.updateUser", query = "UPDATE User u  SET u.phoneNumber = :phoneNumber, u.emailId = :emailId, u.address.line = :line, u.address.landmark = :landmark, u.address.city = :city, u.address.state = :state, u.address.postalCode = :postalCode, u.birthday = :birthday WHERE u.username = :username")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;


    @Column(nullable = false)
    @NotBlank(message = "FirstName=cannot be Empty")
    private String firstName;


    private String lastName;

    private String username;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{10}$", message = "Invalid Phone number. Phone number must be of 10 digits only")
    private String phoneNumber;

    @Column(nullable = false)
    @NotBlank(message = "Password=cannot be Empty")
    @Size(min = 8, message = "Password must be of minimum 8 characters.")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(unique = true)
    @Email(message = "Invalid Email")
    private String emailId;

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private List<Space> spaces;



    @OneToMany(fetch = FetchType.LAZY, mappedBy = "renter", cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<AllocationTime> allocationTimes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bidder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reviewer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    private Media profilePicture;


    @AgeLimit(minimumAge = 18)
    private OffsetDateTime birthday;


    @CreationTimestamp
    private OffsetDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    private OffsetDateTime updatedAt;





    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String firstName, String lastName, String username, String phoneNumber, String password, Address address, String emailId, Role role, List<Space> spaces, OffsetDateTime birthday, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.emailId = emailId;
        this.role = role;
        this.spaces = spaces;
        this.birthday = birthday;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + username + " " + phoneNumber + " " + password + " " + emailId;
    }


}

package net.in.spacekart.backend.database.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.in.spacekart.backend.constraints.AgeLimit;
import net.in.spacekart.backend.database.enums.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;


    @Column(nullable = false)
    @NotBlank(message = "First Name cannot be Empty")
    private String firstName;


    private String lastName;

    private String username;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^\\d{10}$", message = "Invalid Phone number. Phone number must be of 10 digits only")
    private String phoneNumber;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be Empty")
    @Size(min = 8, message = "Password must be of minimum 8 characters.")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @Column(unique = true)
    @Email(message = "Invalid Email")
    private String emailId;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Basic(optional = false)
    private Role role;


    @OneToMany(fetch = FetchType.LAZY)
    private List<Space> spaces;

    @OneToOne(cascade = CascadeType.ALL)
    private Media profilePicture;


    @AgeLimit(minimumAge = 18)
    private OffsetDateTime birthday;


    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


    @Override
    public String toString() {
        return firstName + " " + lastName + " " + username + " " + phoneNumber + " " + password + " " + emailId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}

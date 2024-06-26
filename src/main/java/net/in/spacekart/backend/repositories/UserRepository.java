package net.in.spacekart.backend.repositories;


import net.in.spacekart.backend.database.entities.User;
import net.in.spacekart.backend.payloads.user.GuestUserProjection;
import net.in.spacekart.backend.payloads.user.RegularUserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import net.in.spacekart.backend.payloads.user.AdminUserProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    // For authentication
    User findByEmailId(String emailId);

    // For authentication
    User findByPhoneNumber(String phoneNumber);

    Long  countByUsername(String username);

//    List<User> findAllBy();
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<GuestUserProjection> findGuestUserById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<RegularUserProjection> findRegularUserById(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<AdminUserProjection> findAdminUserById(@Param("id") Long id);





}

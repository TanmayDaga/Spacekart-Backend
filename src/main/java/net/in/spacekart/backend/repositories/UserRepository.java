package net.in.spacekart.backend.repositories;


import net.in.spacekart.backend.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    // For authentication
    User findByEmailId(String emailId);

    // For authentication
    User findByPhoneNumber(String phoneNumber);

    Long  countByUsername(String username);





}

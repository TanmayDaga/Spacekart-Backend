package net.in.spacekart.backend.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.*;
import net.in.spacekart.backend.payloads.get.user.UserSummaryDtoPublic;
import net.in.spacekart.backend.payloads.put.user.UserPutDto;
import net.in.spacekart.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    EntityManager em;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }


    @Override
    public UserAuthentication getUserAuthenticationByUsername(String username) {
        try {
            return em.createQuery("SELECT new net.in.spacekart.backend.database.entities.UserAuthentication(u.username, u.password, u.role) from User  u where username=  :username", UserAuthentication.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception ignored) {

        }
        return null;
    }

    @Transactional
    @Override
    public void updateUser(UserPutDto userPutDto, String username) {

        Long id  = em.createQuery("SELECT  u.id from User  u where  u.username = :username",Long.class).setParameter("username", username).getSingleResult();
        User u = em.getReference(User.class, id);
        u.setPhoneNumber(userPutDto.getPhoneNumber());
        u.setEmailId(userPutDto.getEmailId());
        u.getAddress().setLine(userPutDto.getAddressLine());
        u.getAddress().setLandmark(userPutDto.getAddressLandmark());
        u.getAddress().setPostalCode(userPutDto.getAddressPostalCode());
        u.getAddress().setCity(userPutDto.getAddressCity());
        u.getAddress().setState(userPutDto.getAddressState());
        u.setBirthday(userPutDto.getBirthday());
        em.merge(u);


    }

    @Override
    public String getUserNameFromEmailId(String emailId) {
        return em.createQuery("SELECT u.username FROM User u WHERE u.emailId = :emailId", String.class)
                .setParameter("emailId", emailId)
                .getSingleResult();
    }

    @Override
    public String getUserNameFromPhoneNumber(String phoneNumber) {
        return em.createQuery("SELECT u.username FROM User u WHERE u.phoneNumber = :phoneNumber", String.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
    }

    @Transactional
    @Override
    public UserSummaryDtoPublic getUserSummary(String username) {
        UserSummaryDtoPublic userSummaryDtoPublic = null;
        try {
            userSummaryDtoPublic = em.createQuery("select new net.in.spacekart.backend.payloads.get.user.UserSummaryDtoPublic(u.firstName, u.lastName, u.username, u.profilePicture.url) from User u where u.username = :username", UserSummaryDtoPublic.class).setParameter("username", username).getSingleResult();
            List<Space> spacesList = em.createQuery("select u.spaces from User u where  username = :username", Space.class).setParameter("username", username).getResultList();
            if (spacesList != null && spacesList.size() > 0) {
                userSummaryDtoPublic.setSpaceSpaceIds(spacesList.stream().map(Space::getSpaceId).toList());
            }
        } catch (NoResultException e) {

        }
        return userSummaryDtoPublic;
    }

    @Modifying
    @Transactional
    @Override
    public Long insertUser(User user) {
        User u = em.merge(user);
        return u.getId();
    }


    @Transactional
    @Override
    public boolean checkPhoneNumberExist(String phoneNumber) {
        return (boolean) em.createNativeQuery("select exists(select 1 from users where phone_number = ?)", boolean.class).setParameter(1, phoneNumber).getSingleResult();

    }

    @Transactional
    @Override
    public boolean checkEmailExist(String email) {
        return (boolean) em.createNativeQuery("select exists(select 1 from users where email_id = ?)", boolean.class).setParameter(1, email).getSingleResult();

    }

    @Transactional
    @Override
    public Long getIdByUsername(String username) {
        return em.createQuery("select u.id from User u where username = :username", Long.class).setParameter("username", username).getSingleResult();
    }
    @Transactional
    @Override
    public void deleteUser(String username) {
        User u = em.getReference(User.class, getIdByUsername(username));
        em.remove(u);
    }


    @Transactional
    @Override
    public void deleteProfilePicture(String username) {
        try {
            Long id = em.createQuery("select u.profilePicture.id from User u where  u.username = :username", Long.class).setParameter("username", username).getSingleResult();
            if (id != null) {
                Media m = em.find(Media.class, id);
                em.remove(m);
            }
        } catch (NoResultException e) {

        }
    }
}

package net.in.spacekart.backend.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import net.in.spacekart.backend.database.entities.Media;
import net.in.spacekart.backend.database.entities.SpaceType;
import net.in.spacekart.backend.payloads.get.spaceType.SpaceTypePublicDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class SpaceTypeRepository extends SimpleJpaRepository<SpaceType, Long> {


    @PersistenceContext
    EntityManager entityManager;

    public SpaceTypeRepository( EntityManager entityManager) {
        super(SpaceType.class, entityManager);
    }


    @Transactional
    public List<SpaceTypePublicDto> findPublicDtoAll() {
        return entityManager.createQuery("select new net.in.spacekart.backend.payloads.get.spaceType.SpaceTypePublicDto(st.name, st.imageUrl.url) from SpaceType st", SpaceTypePublicDto.class).getResultList();
    }

    @Transactional
    public Long getIdByName(String name) {
        return entityManager.createQuery("select id from SpaceType s where name = :name", Long.class).setParameter("name", name).getSingleResult();
    }


    @Transactional
    public Long saveMy(SpaceType spaceType) {
        entityManager.persist(spaceType);
        return spaceType.getId();
    }


    @Modifying
    @Transactional
    public void deleteByName(String name) {
        Long id = entityManager.createQuery("select s.id from SpaceType s where name = :name", Long.class).setParameter("name", name).getSingleResult();
        SpaceType st = entityManager.getReference(SpaceType.class, id);
        entityManager.remove(st);
    }



    public List<String> getSpaceTypeNames() {
        return entityManager.createQuery("select name from SpaceType ", String.class).getResultList();
    }

    @Transactional
    @Modifying
    public void changeNameByName(String name, String newName) {
        entityManager.createQuery("UPDATE SpaceType s SET s.name = :newName WHERE name = :name").setParameter("newName", newName).setParameter("name", name).executeUpdate();
    }


    @Transactional
    public void deleteMedia(String spaceTypeName) {
        Long id = entityManager.createQuery("select  s.imageUrl.id from SpaceType s where s.name = :name", Long.class).setParameter("name", spaceTypeName).getSingleResult();
        Media m = entityManager.getReference(Media.class, id);
        entityManager.remove(m);

    }

}

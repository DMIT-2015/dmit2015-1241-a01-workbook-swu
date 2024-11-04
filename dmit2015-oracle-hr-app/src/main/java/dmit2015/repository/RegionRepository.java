package dmit2015.repository;

import dmit2015.entity.Region;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

/**
 * This Jakarta Persistence class contains methods for performing CRUD operations on a
 * Jakarta Persistence managed entity.
 */
@ApplicationScoped
public class RegionRepository {

    // Assign a unitName if there are more than one persistence unit defined in persistence.xml
    @PersistenceContext //(unitName="pu-name-in-persistence.xml")
    private EntityManager _entityManager;

    @Transactional
    public void add(@Valid Region newRegion) {
        // If the primary key is not an identity column then write code below here to
        // 1) Generate a new primary key value
        // 2) Set the primary key value for the new entity

        _entityManager.persist(newRegion);
    }

    public Optional<Region> findById(Long regionId) {
        try {
            Region querySingleResult = _entityManager.find(Region.class, regionId);
            if (querySingleResult != null) {
                return Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            // regionId value not found
            throw new RuntimeException(ex);
        }
        return Optional.empty();
    }

    public List<Region> findAll() {
        return _entityManager.createQuery("SELECT o FROM Region o ", Region.class)
                .getResultList();
    }

    @Transactional
    public Region update(Long id, @Valid Region updatedRegion) {
        Optional<Region> optionalRegion = findById(id);
        if (optionalRegion.isEmpty()) {
            String errorMessage = String.format("The id %s does not exists in the system.", id);
            throw new RuntimeException(errorMessage);
        } else {
            var existingRegion = optionalRegion.orElseThrow();
            // Update only properties that is editable by the end user
            existingRegion.setRegionName(updatedRegion.getRegionName());

            updatedRegion = _entityManager.merge(existingRegion);
        }
        return updatedRegion;
    }

    @Transactional
    public void delete(Region existingRegion) {
        // Write code to throw a RuntimeException if this entity contains child records

        if (_entityManager.contains(existingRegion)) {
            _entityManager.remove(existingRegion);
        } else {
            _entityManager.remove(_entityManager.merge(existingRegion));
        }
    }

    @Transactional
    public void deleteById(Long regionId) {
        Optional<Region> optionalRegion = findById(regionId);
        if (optionalRegion.isPresent()) {
            Region existingRegion = optionalRegion.orElseThrow();
            // Write code to throw a RuntimeException if this entity contains child records

            _entityManager.remove(existingRegion);
        }
    }

    public long count() {
        return _entityManager.createQuery("SELECT COUNT(o) FROM Region o", Long.class).getSingleResult();
    }

    @Transactional
    public void deleteAll() {
        _entityManager.flush();
        _entityManager.clear();
        _entityManager.createQuery("DELETE FROM Region").executeUpdate();
    }

}
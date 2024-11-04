package dmit2015.repository;

import dmit2015.entity.Country;
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
public class CountryRepository {

    // Assign a unitName if there are more than one persistence unit defined in persistence.xml
    @PersistenceContext //(unitName="pu-name-in-persistence.xml")
    private EntityManager _entityManager;

    @Transactional
    public void add(@Valid Country newCountry) {
        // If the primary key is not an identity column then write code below here to
        // 1) Generate a new primary key value
        // 2) Set the primary key value for the new entity

        _entityManager.persist(newCountry);
    }

    public Optional<Country> findById(String countryId) {
        try {
            Country querySingleResult = _entityManager.find(Country.class, countryId);
            if (querySingleResult != null) {
                return Optional.of(querySingleResult);
            }
        } catch (Exception ex) {
            // countryId value not found
            throw new RuntimeException(ex);
        }
        return Optional.empty();
    }

    public List<Country> findAll() {
        return _entityManager.createQuery("SELECT o FROM Country o ", Country.class)
                .getResultList();
    }

    @Transactional
    public Country update(String id, @Valid Country updatedCountry) {
        Optional<Country> optionalCountry = findById(id);
        if (optionalCountry.isEmpty()) {
            String errorMessage = String.format("The id %s does not exists in the system.", id);
            throw new RuntimeException(errorMessage);
        } else {
            var existingCountry = optionalCountry.orElseThrow();
            // Update only properties that is editable by the end user
            existingCountry.setCountryName(updatedCountry.getCountryName());

            updatedCountry = _entityManager.merge(existingCountry);
        }
        return updatedCountry;
    }

    @Transactional
    public void delete(Country existingCountry) {
        // Write code to throw a RuntimeException if this entity contains child records

        if (_entityManager.contains(existingCountry)) {
            _entityManager.remove(existingCountry);
        } else {
            _entityManager.remove(_entityManager.merge(existingCountry));
        }
    }

    @Transactional
    public void deleteById(String countryId) {
        Optional<Country> optionalCountry = findById(countryId);
        if (optionalCountry.isPresent()) {
            Country existingCountry = optionalCountry.orElseThrow();
            // Write code to throw a RuntimeException if this entity contains child records

            _entityManager.remove(existingCountry);
        }
    }

    public long count() {
        return _entityManager.createQuery("SELECT COUNT(o) FROM Country o", Long.class).getSingleResult();
    }

    @Transactional
    public void deleteAll() {
        _entityManager.flush();
        _entityManager.clear();
        _entityManager.createQuery("DELETE FROM Country").executeUpdate();
    }

}
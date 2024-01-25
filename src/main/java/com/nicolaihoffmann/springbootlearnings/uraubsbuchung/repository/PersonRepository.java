package com.nicolaihoffmann.springbootlearnings.uraubsbuchung.repository;

import com.nicolaihoffmann.springbootlearnings.uraubsbuchung.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    /***
     * Zu empfehlen
     * @param lastName
     * @return
     */
    // Beispiel 1: Suche nach Personen mit einem bestimmten Nachnamen
    List<Person> findByLastName(String lastName);

    /***
     * Zu empfehlen
     * @param age
     * @return
     */
    // Beispiel 2: Suche nach Personen, die älter als ein bestimmtes Alter sind
    List<Person> findByAgeGreaterThan(int age);

    /***
     * Zu empfehlen
     * @param firstName
     * @param lastName
     * @return
     */
    // Beispiel 3: Suche nach Personen mit einem bestimmten Vornamen und Nachnamen
    Person findByFirstNameAndLastName(String firstName, String lastName);

    // Beispiel 4: Benutzerdefinierte JPQL-Abfrage, um Personen mit einem bestimmten Alter zu finden
    @Query("SELECT p FROM Person p WHERE p.age = :age")
    List<Person> findPeopleByAge(int age);

    // Beispiel 5: Benutzerdefinierte native SQL-Abfrage
    @Query(value = "SELECT * FROM Person WHERE age >= :age", nativeQuery = true)
    List<Person> findPeopleByAgeNativeQuery(int age);
}

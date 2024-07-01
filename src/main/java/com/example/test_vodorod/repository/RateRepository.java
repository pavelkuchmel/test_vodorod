package com.example.test_vodorod.repository;

import com.example.test_vodorod.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    /*
    Returns true if courses with the specified date are already in the database.
    */
    boolean existsRatesByDate(LocalDate date);

    /*
    Returns a Rate object from the database, if there is no suitable object, returns null.
    */
    Rate findRateByDateAndCurAbbreviation(LocalDate date, String curCode);
}

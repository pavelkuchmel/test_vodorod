package com.example.test_vodorod.service;

import com.example.test_vodorod.dto.RateDto;

public interface RateService {

    /*
    Loads courses for the specified date into the database,
    and returns the number of objects loaded.
    If the date is incorrect, or courses for this
    date have already been downloaded, it returns null
    */
    Integer loadAllRatesByDate(String date);

    /*
    Returns the RateDto from the database for the specified parameters.
    If the database does not have a Rate with these parameters,
    loads the Rate for the specified date into the database
    and returns the Rate for the specified parameters.
    In case of incorrectly entered data, it returns null.
    */
    RateDto findRateDtoByDateAndCurCode(String date, String curCode);
}

package com.example.test_vodorod.service;

import com.example.test_vodorod.dto.RateDto;
import com.example.test_vodorod.model.Rate;
import com.example.test_vodorod.repository.RateRepository;
import com.example.test_vodorod.util.RateExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;

@Service
public class RateServiceImpl implements RateService{

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private RateExtractor rateExtractor;

    /*
    Loads courses for the specified date into the database,
    and returns the number of objects loaded.
    If the date is incorrect, or courses for this
    date have already been downloaded, it returns null
    */
    @Override
    public Integer loadAllRatesByDate(String date) throws ResourceAccessException {
        if (!rateRepository.existsRatesByDate(LocalDate.parse(date))){
            List<Rate> rates = rateExtractor.getAllRates(date);
            if (!rates.isEmpty()) return rateRepository.saveAll(rates).size();
        }
        return null;
    }

    /*
    Returns the Rate from the database for the specified parameters.
    If the database does not have a Rate with these parameters,
    loads the Rate for the specified date into the database
    and returns the Rate for the specified parameters.
    In case of incorrectly entered data, it returns null.
    */
    @Override
    public RateDto findRateDtoByDateAndCurCode(String date, String curCode) throws ResourceAccessException{
        Rate rate = rateRepository.findRateByDateAndCurAbbreviation(LocalDate.parse(date), curCode);
        if (rate != null){
            return new RateDto(rate);
        }
        if (loadAllRatesByDate(date) != null){
            rate = rateRepository.findRateByDateAndCurAbbreviation(LocalDate.parse(date), curCode);
            if (rate != null) return new RateDto(rate);
        }
        return null;
    }
}

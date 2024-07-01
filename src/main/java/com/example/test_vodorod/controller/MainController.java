package com.example.test_vodorod.controller;

import com.example.test_vodorod.dto.RateDto;
import com.example.test_vodorod.service.RateService;
import com.example.test_vodorod.util.RateExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;


@RestController
@RequestMapping("/currencies")
public class MainController{

    @Autowired
    private RateExtractor rateExtractor;

    @Autowired
    private RateService rateService;

    @Value("${load.successfully}")
    private String loadSuccessfully;

    /*
    GET request:
    http://localhost:8080/currencies/load
    Params:
    - date (In the format: "yyyy-MM-dd")
    Returns the number of Rate objects loaded into the database.
    Possible status codes:
    - 502: no access to API (https://api.nbrb.by/exrates/rates)
    - 404: exchange rates for this date have already been loaded or the date is incorrect
    */

    @GetMapping("/load")
    public ResponseEntity<?> loadAllRates(@RequestParam() String date){
        Integer quantity = null;
        try{
            quantity = rateService.loadAllRatesByDate(date);
        }catch (ResourceAccessException resourceAccessException){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if (quantity == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(loadSuccessfully + quantity, HttpStatus.OK);
    }

    /*
    GET request:
    http://localhost:8080/currencies/get
    Params:
    - date (In the format: "yyyy-MM-dd")
    - curCode (Three currency symbols (ISO 4217) (USD, EUR, CNY))
    Returns a JSON object containing fields:
    - date
    - name of the currency
    - currency scale
    - rate
    Possible status codes:
    - 502: no access to API (https://api.nbrb.by/exrates/rates)
    - 406: invalid parameters
    */

    @GetMapping("/get")
    public ResponseEntity<?> getRate(@RequestParam() String date,
                                           @RequestParam() String curCode){
        RateDto rate = null;
        try {
            rate = rateService.findRateDtoByDateAndCurCode(date, curCode);
        }catch (ResourceAccessException resourceAccessException){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if (rate == null) return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }

    //TODO @GetMapping()
    //TODO public ResponseEntity<List<Rate>> showTodayRates(){}
}

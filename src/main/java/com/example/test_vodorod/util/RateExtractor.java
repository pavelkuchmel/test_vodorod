package com.example.test_vodorod.util;

import com.example.test_vodorod.model.Rate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class RateExtractor {

    @Value("${rates.uri}")
    private String ratesUri;

    /*
    Returns an array of Rate objects
    in JSON format for the specified date,
    if the date is not correct, returns empty array
    */
    private String getAllRatesJson(String date) throws ResourceAccessException{
            return RestClient.create()
                    .get()
                    .uri(ratesUri.replace("<span>", date))
                    .retrieve()
                    .toEntity(String.class)
                    .getBody();
    }

    /*
    Returns a collection of Rate objects,
    if the date is not correct, returns empty array
    */
    public List<Rate> getAllRates(String date) throws ResourceAccessException{
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        List<Rate> result = null;
        try {
            result = mapper.readValue(getAllRatesJson(date), new TypeReference<List<Rate>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    /*
    Returns a Rate object for the specified date and currency abbreviation.
    Or returns NoSuchElementException
    */
    public Rate getRateByCurAbbreviation(String date, String curAbbreviation){
        return getAllRates(date).stream()
                .filter(a -> a.getCurAbbreviation().equalsIgnoreCase(curAbbreviation))
                .findFirst()
                .get();
    }

}

package com.example.test_vodorod.dto;

import com.example.test_vodorod.model.Rate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateDto {

    @JsonProperty(value = "Date")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonProperty(value = "Cur_Scale")
    private Integer curScale;

    @JsonProperty(value = "Cur_Name")
    private String curName;

    @JsonProperty(value = "Cur_OfficialRate")
    private Double curOfficialRate;

    public RateDto(Rate rate){
        this.setDate(rate.getDate());
        this.setCurScale(rate.getCurScale());
        this.setCurName(rate.getCurName());
        this.setCurOfficialRate(rate.getCurOfficialRate());
    }
}

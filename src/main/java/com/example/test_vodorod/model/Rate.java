package com.example.test_vodorod.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "rates")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(value = "Cur_ID")
    @Column(name = "cur_code")
    private Integer curCode;

    @JsonProperty(value = "Date")
    @Column(name = "date")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @JsonProperty(value = "Cur_Abbreviation")
    @Column(name = "cur_abbreviation")
    private String curAbbreviation;

    @JsonProperty(value = "Cur_Scale")
    @Column(name = "cur_scale")
    private Integer curScale;

    @JsonProperty(value = "Cur_Name")
    @Column(name = "cur_name")
    private String curName;

    @JsonProperty(value = "Cur_OfficialRate")
    @Column(name = "cur_official_rate")
    private Double curOfficialRate;
}

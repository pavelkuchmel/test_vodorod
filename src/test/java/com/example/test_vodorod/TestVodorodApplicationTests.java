package com.example.test_vodorod;

import com.example.test_vodorod.controller.MainController;
import com.example.test_vodorod.util.RateExtractor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class TestVodorodApplicationTests {

    @Autowired
    private RateExtractor rateExtractor;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void firstAndSecondLoadingRates() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/currencies/load?date=2024-06-30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("successfully")));
        this.mockMvc.perform(get("http://localhost:8080/currencies/load?date=2024-06-30"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void loadingRatesWithWrongDate() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/currencies/load?date=2099-01-01"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void returnTodayRates() throws Exception {
        this.mockMvc.perform(get("http://localhost:8080/currencies/get?date=" + LocalDate.now() + "&curCode=USD"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{\"Date\":\"" + LocalDate.now() + "\",\"Cur_Scale\":1,\"Cur_Name\":\"Доллар США\",\"Cur_OfficialRate\":" + rateExtractor.getRateByCurAbbreviation(LocalDate.now().toString(), "USD").getCurOfficialRate() + "}"));
    }
}

package com.example.swiftcodes.controller;

import com.example.swiftcodes.model.SwiftCode;
import com.example.swiftcodes.repository.SwiftCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

class SwiftCodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SwiftCodeRepository swiftCodeRepository;

    @InjectMocks
    private SwiftCodeController swiftCodeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(swiftCodeController).build();
    }

    @Test
    void testGetSwiftCode() throws Exception {
        SwiftCode swiftCode = new SwiftCode("ABC123", "Bank ABC", "123 Bank St", "PL", "Poland", false);
        when(swiftCodeRepository.findBySwiftCode("ABC123")).thenReturn(swiftCode);

        mockMvc.perform(get("/v1/swift-codes/ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("ABC123"));
    }

    @Test
    void testGetSwiftCodesByCountry() throws Exception {
        SwiftCode swiftCode1 = new SwiftCode("ABC123", "Bank ABC", "123 Bank St", "PL", "Poland", false);
        SwiftCode swiftCode2 = new SwiftCode("DEF456", "Bank DEF", "456 Bank St", "PL", "Poland", false);
        List<SwiftCode> swiftCodes = Arrays.asList(swiftCode1, swiftCode2);

        when(swiftCodeRepository.findByCountryISO2("PL")).thenReturn(swiftCodes);

        mockMvc.perform(get("/v1/swift-codes/country/PL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testAddSwiftCode() throws Exception {
        SwiftCode swiftCode = new SwiftCode("XYZ789", "Bank XYZ", "789 Bank St", "US", "USA", false);

        when(swiftCodeRepository.existsBySwiftCode("XYZ789")).thenReturn(false);
        when(swiftCodeRepository.save(swiftCode)).thenReturn(swiftCode);

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content("{\"swiftCode\": \"XYZ789\", \"bankName\": \"Bank XYZ\", \"address\": \"789 Bank St\", \"countryISO2\": \"US\", \"countryName\": \"USA\", \"isHeadquarter\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code added successfully"));
    }

    @Test
    void testDeleteSwiftCode() throws Exception {
        SwiftCode swiftCode = new SwiftCode("ABC123", "Bank ABC", "123 Bank St", "PL", "Poland", false);

        when(swiftCodeRepository.findBySwiftCode("ABC123")).thenReturn(swiftCode);

        mockMvc.perform(delete("/v1/swift-codes/ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code deleted successfully"));

        verify(swiftCodeRepository, times(1)).delete(swiftCode);
    }
}

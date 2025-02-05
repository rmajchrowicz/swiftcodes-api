package com.example.swiftcodes.controller;

import com.example.swiftcodes.model.SwiftCode;
import com.example.swiftcodes.repository.SwiftCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SwiftCodeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SwiftCodeRepository swiftCodeRepository;

    @BeforeEach
    void setUp() {
        swiftCodeRepository.deleteAll();
    }

    @Test
    void testGetSwiftCode_existingCode_returnsSwiftCode() throws Exception {
        SwiftCode swiftCode = new SwiftCode("ABC123", "Bank ABC", "123 Bank St", "PL", "Poland", false);
        swiftCodeRepository.save(swiftCode);

        mockMvc.perform(get("/v1/swift-codes/ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.swiftCode").value("ABC123"))
                .andExpect(jsonPath("$.bankName").value("Bank ABC"));
    }

    @Test
    void testGetSwiftCodesByCountry_existingCodes_returnsList() throws Exception {

        SwiftCode swiftCode1 = new SwiftCode("ABC123", "Bank ABC", "123 Bank St", "PL", "Poland", false);
        SwiftCode swiftCode2 = new SwiftCode("DEF456", "Bank DEF", "456 Bank St", "PL", "Poland", false);
        swiftCodeRepository.saveAll(Arrays.asList(swiftCode1, swiftCode2));

        mockMvc.perform(get("/v1/swift-codes/country/PL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].swiftCode").value("ABC123"))
                .andExpect(jsonPath("$[1].swiftCode").value("DEF456"));
    }

    @Test
    void testAddSwiftCode_validData_returnsSuccessMessage() throws Exception {
        SwiftCode swiftCode = new SwiftCode("XYZ789", "Bank XYZ", "789 Bank St", "US", "USA", false);

        mockMvc.perform(post("/v1/swift-codes")
                        .contentType("application/json")
                        .content("{\"swiftCode\": \"XYZ789\", \"bankName\": \"Bank XYZ\", \"address\": \"789 Bank St\", \"countryISO2\": \"US\", \"countryName\": \"USA\", \"isHeadquarter\": false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code added successfully"));

        SwiftCode savedSwiftCode = swiftCodeRepository.findBySwiftCode("XYZ789");
        assertNotNull(savedSwiftCode);
        assertEquals("Bank XYZ", savedSwiftCode.getBankName());
    }

    @Test
    void testDeleteSwiftCode_existingCode_returnsSuccessMessage() throws Exception {
        SwiftCode swiftCode = new SwiftCode("ABC123", "Bank ABC", "123 Bank St", "PL", "Poland", false);
        swiftCodeRepository.save(swiftCode);

        mockMvc.perform(delete("/v1/swift-codes/ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("SWIFT code deleted successfully"));

        SwiftCode deletedSwiftCode = swiftCodeRepository.findBySwiftCode("ABC123");
        assertNull(deletedSwiftCode);
    }
}

package com.example.swiftcodes.controller;

import com.example.swiftcodes.model.SwiftCode;
import com.example.swiftcodes.repository.SwiftCodeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {
    private final SwiftCodeRepository repository;

    public SwiftCodeController(SwiftCodeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{swiftCode}")
    public SwiftCode getSwiftCode(@PathVariable String swiftCode) {
        return repository.findBySwiftCode(swiftCode);
    }

    @GetMapping("/country/{countryISO2}")
    public List<SwiftCode> getSwiftCodesByCountry(@PathVariable String countryISO2) {
        return repository.findByCountryISO2(countryISO2);
    }

    @PostMapping
    public String addSwiftCode(@RequestBody SwiftCode swiftCode) {
        if (repository.existsBySwiftCode(swiftCode.getSwiftCode())) {
            return "{\"message\": \"SWIFT code already exists\"}";
        }

        repository.save(swiftCode);
        return "{\"message\": \"SWIFT code added successfully\"}";
    }

    @DeleteMapping("/{swiftCode}")
    public String  deleteSwiftCode(@PathVariable String swiftCode) {
        SwiftCode swift = repository.findBySwiftCode(swiftCode);
        if (swift != null) {
            repository.delete(swift);
            return "{\"message\": \"SWIFT code deleted successfully\"}";
        }
        return "{\"message\": \"SWIFT code not found\"}";
    }
}

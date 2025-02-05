package com.example.swiftcodes.repository;

import com.example.swiftcodes.model.SwiftCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SwiftCodeRepository extends JpaRepository<SwiftCode, Long> {
    SwiftCode findBySwiftCode(String swiftCode);
    List<SwiftCode> findByCountryISO2(String countryISO2);
    boolean existsBySwiftCode(String swiftCode);
}

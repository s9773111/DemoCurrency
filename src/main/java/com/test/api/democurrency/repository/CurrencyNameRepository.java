package com.test.api.democurrency.repository;

import com.test.api.democurrency.entity.CurrencyName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyNameRepository extends JpaRepository<CurrencyName, Integer> {
    List<CurrencyName> findAll();

    CurrencyName findByCurrencyCode(String code);
}

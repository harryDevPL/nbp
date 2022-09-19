package com.sygnity.npb.repository;

import com.sygnity.npb.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {

    Optional<Currency> findByCurrencyCodeAndDate(final String currencyCode, final String date);

}

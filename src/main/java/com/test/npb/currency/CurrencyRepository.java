package com.test.npb.currency;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {

    Optional<CurrencyEntity> findByCurrencyCodeAndDate(final String currencyCode, final String date);
}

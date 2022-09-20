package com.test.npb.currency;

import org.springframework.data.repository.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

interface CurrencyRepository extends Repository<CurrencyEntity, UUID> {

    CurrencyEntity saveAndFlush(CurrencyEntity currencyEntity);

    Optional<CurrencyEntity> findByCurrencyCodeAndDate(final String currencyCode, final String date);
}

class InMemoryCurrencyRepository implements CurrencyRepository {

    private final Map<UUID, CurrencyEntity> currencies = new ConcurrentHashMap<>();

    @Override
    public CurrencyEntity saveAndFlush(final CurrencyEntity currencyEntity) {
        currencyEntity.setUuid(UUID.randomUUID());
        currencies.put(currencyEntity.getUuid(), currencyEntity);
        return currencyEntity;
    }

    @Override
    public Optional<CurrencyEntity> findByCurrencyCodeAndDate(final String currencyCode, final String date) {
        return Optional.of(
                currencies.values().stream()
                        .filter(e -> e.getCurrencyCode().equals(currencyCode))
                        .filter(e -> e.getDate().equals(date))
                        .toList()
                        .get(0)
        );
    }
}

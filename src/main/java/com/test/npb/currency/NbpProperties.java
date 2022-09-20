package com.test.npb.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "nbp.http")
@NoArgsConstructor
@AllArgsConstructor
@Data
class NbpProperties implements ClientProperties {

    private String baseUrl;

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }


}

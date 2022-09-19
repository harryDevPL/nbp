package com.sygnity.npb.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "nbp.http")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
class NbpProperties {

    private String baseUrl;
}

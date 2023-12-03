package com.platinabank.loans.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "info")
@Getter
@Setter
public class ConfigProperties {

    private String javaVersion;
    private String build;
    private String microservice;

}

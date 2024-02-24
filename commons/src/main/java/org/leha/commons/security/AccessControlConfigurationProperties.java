package org.leha.commons.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("org.leha.security")
@Data
public class AccessControlConfigurationProperties {
}

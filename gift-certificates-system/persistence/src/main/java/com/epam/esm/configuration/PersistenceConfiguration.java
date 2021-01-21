package com.epam.esm.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Class {@code PersistenceConfiguration} contains spring configuration for persistence subproject.
 *
 * @author Oleg Borikov
 * @version 1.0
 */
@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan(basePackages = "com.epam.esm")
public class PersistenceConfiguration {
}

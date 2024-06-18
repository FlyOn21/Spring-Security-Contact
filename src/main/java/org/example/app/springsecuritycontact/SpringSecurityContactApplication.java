package org.example.app.springsecuritycontact;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringSecurityContactApplication {

    private static final Logger APP_LOGGER =
            LogManager.getLogger(SpringSecurityContactApplication.class);
    private static final Logger CONSOLE_LOGGER =
            LogManager.getLogger("console_logger");

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringSecurityContactApplication.class)
                .bannerMode(Banner.Mode.CONSOLE)
                .run(args);
        APP_LOGGER.info("App started");
        CONSOLE_LOGGER.info("App started");
    }

}

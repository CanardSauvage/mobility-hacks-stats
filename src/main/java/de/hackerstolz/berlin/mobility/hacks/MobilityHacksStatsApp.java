package de.hackerstolz.berlin.mobility.hacks;

import de.hackerstolz.berlin.mobility.hacks.eventbrite.OauthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"me.ramswaroop.jbot", "de.hackerstolz.berlin.mobility.hacks"})
@EnableAutoConfiguration
public class MobilityHacksStatsApp {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
        SpringApplication.run(MobilityHacksStatsApp.class, args);
    }

    @Value("${EVENTBRITE_PERSONAL_OAUTH_TOKEN}")
    private String token;


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getInterceptors().add(new OauthRequestInterceptor(token));

        return restTemplate;
    }

}

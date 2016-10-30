package de.hackerstolz.berlin.mobility.hacks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
public class MobilityHacksStatsApp  {

    public static void main(String[] args) {
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

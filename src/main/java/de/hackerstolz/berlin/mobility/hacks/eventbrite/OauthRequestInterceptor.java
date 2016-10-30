package de.hackerstolz.berlin.mobility.hacks.eventbrite;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class OauthRequestInterceptor implements ClientHttpRequestInterceptor {

    private final String oauthToken;

    public OauthRequestInterceptor(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Bearer " + oauthToken);
        return execution.execute(request, body);
    }
}
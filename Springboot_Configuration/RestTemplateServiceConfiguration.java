import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Utility method for HTTP POST with HttpHeaders and generic request and response types
    public <T, R> ResponseEntity<R> post(String url, T requestBody, HttpHeaders headers, Class<R> responseType) {
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
    }

    // Utility method for HTTP GET with HttpHeaders and generic response type
    public <R> ResponseEntity<R> get(String url, HttpHeaders headers, Class<R> responseType) {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    // Utility method for HTTP PUT with HttpHeaders and generic request and response types
    public <T, R> ResponseEntity<R> put(String url, T requestBody, HttpHeaders headers, Class<R> responseType) {
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    // Utility method for HTTP DELETE with HttpHeaders and generic response type
    public <R> ResponseEntity<R> delete(String url, HttpHeaders headers, Class<R> responseType) {
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }
}

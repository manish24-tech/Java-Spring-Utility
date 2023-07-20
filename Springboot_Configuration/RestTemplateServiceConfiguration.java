import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RestTemplate restTemplate;

    // Utility method for HTTP POST with HttpHeaders
    public <T> ResponseEntity<T> post(String url, T requestBody, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, responseType);
    }

    // Utility method for HTTP GET with HttpHeaders
    public <T> ResponseEntity<T> get(String url, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<T> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);
    }

    // Utility method for HTTP PUT with HttpHeaders
    public <T> ResponseEntity<T> put(String url, T requestBody, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<T> requestEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, requestEntity, responseType);
    }

    // Utility method for HTTP DELETE with HttpHeaders
    public <T> ResponseEntity<T> delete(String url, Class<T> responseType, HttpHeaders headers) {
        HttpEntity<T> requestEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, responseType);
    }
}

package ar.gov.posadas.mbe.ports.apirest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class RestService {
    private static RestTemplateBuilder rtb;

    public RestService(RestTemplateBuilder rtb) {
        RestService.rtb = rtb;
    }

    public static <T> ResponseEntity<T> post(String url,
                             MediaType contentType,
                             List<MediaType> acceptTypes,
                             @Nullable MultiValueMap<String, String> headValues,
                             Class<T> responseType,
                             @Nullable String bearerToken,
                             Object body) throws RestClientException {
        // armo el compendio del requerimiento http
        var request = getRequest(contentType, headValues, bearerToken, acceptTypes, body);

        // armo y configuro el servicio de llamada rest
        var restTemplate = getRestTemplate();

        // ejecuta la llamada y retorna el resultado
        return restTemplate.postForEntity(url, request, responseType);
    }

    public static <T> ResponseEntity<T> get(String url,
                            MediaType contentType,
                            MultiValueMap<String, String> headValues,
                            String bearerToken,
                            List<MediaType> acceptTypes,
                            Class<T> responseType,
                            Object body) throws RestClientException {
        // armo el compendio del requerimiento http
        var request = getRequest(contentType, headValues, bearerToken, acceptTypes, body);

        // armo y configuro el servicio de llamada rest
        var restTemplate = getRestTemplate();

        // ejecuta la llamada y retorna el resultado
        return restTemplate.getForEntity(url, responseType, request);
    }

    public static <T> ResponseEntity<T> exchange(String url,
                                 HttpMethod method,
                                 MediaType contentType,
                                 MultiValueMap<String, String> headValues,
                                 String bearerToken,
                                 List<MediaType> acceptTypes,
                                 Class<T> responseType,
                                 Object body) throws RestClientException {
        // armo el compendio del requerimiento http
        var request = getRequest(contentType, headValues, bearerToken, acceptTypes, body);

        // armo y configuro el servicio de llamada rest
        var restTemplate = getRestTemplate();

        // ejecuta la llamada y retorna el resultado
        return restTemplate.exchange(url, method, request, responseType);
    }

    private static HttpEntity<?> getRequest(MediaType contentType,
                                            MultiValueMap<String, String> headValues,
                                            String bearerToken,
                                            List<MediaType> acceptTypes,
                                            Object body) {
        // armo y completo la cabecera de la llamada
        HttpHeaders headers = getHttpHeaders(contentType, headValues, bearerToken, acceptTypes);

        // verifico si en realidad el body es un archivo
        MultiValueMap<String, Object> rbody = new LinkedMultiValueMap<>();
        if (body instanceof MultipartFile) {
            rbody.add("file", ((MultipartFile) body).getResource());
        }

        // armo el compendio del requerimiento http
        return (body instanceof MultipartFile) ? new HttpEntity<>(rbody, headers)
                                               : new HttpEntity<>(body, headers);
    }

    private static RestTemplate getRestTemplate() {
        RestTemplate restTemplate = rtb.build();
        restTemplate.setMessageConverters(getMediaTypeConverter(Collections.singletonList(MediaType.ALL)));
        return restTemplate;
    }

    private static HttpHeaders getHttpHeaders(MediaType contentType,
                                              MultiValueMap<String, String> headValues,
                                              String bearerToken,
                                              List<MediaType> acceptTypes) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(contentType);

        if (acceptTypes != null && !acceptTypes.isEmpty()) {
            headers.setAccept(acceptTypes);
        }

        if (bearerToken != null) {
            headers.setBearerAuth(bearerToken);
        }

        if (headValues != null && !headValues.isEmpty()) {
            headers.addAll(headValues);
        }

        return headers;
    }

    private static List<HttpMessageConverter<?>> getMediaTypeConverter(List<MediaType> mediaTypes) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(mediaTypes);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(converter);

        return messageConverters;
    }
}

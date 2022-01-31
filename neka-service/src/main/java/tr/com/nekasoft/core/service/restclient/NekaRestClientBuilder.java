package tr.com.nekasoft.core.service.restclient;

import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.web.client.RestTemplate;

public class NekaRestClientBuilder implements RestTemplateCustomizer {
    private NekaClientProperties properties;

    public NekaRestClientBuilder(NekaClientProperties properties) {
        this.properties = properties;
    }

    @Override
    public void customize(RestTemplate restTemplate) {
        RootUriTemplateHandler.addTo(restTemplate, properties.getBaseUrl());
        //restTemplate.
    }

    //public RestTemplate buildRestTemplate() {
    //    return configureRestTemplate().build();
    //
    //}
    //
    //public <T extends RestTemplate> T buildRestTemplate(Class<T> restTemplateClass) {
    //    RestTemplateBuilder restTemplateBuilder = configureRestTemplate();
    //    return restTemplateBuilder.build(restTemplateClass);
    //}
    //
    //private RestTemplateBuilder configureRestTemplate() {
    //    RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    //    properties.getHeaders().entrySet().forEach(item -> {
    //        restTemplateBuilder = restTemplateBuilder.defaultHeader(item.getKey(),item.getValue());
    //    });
    //    restTemplateBuilder = restTemplateBuilder.rootUri(properties.getBaseUrl());
    //    return restTemplateBuilder;
    //}

}

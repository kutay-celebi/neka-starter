package tr.com.nekasoft.core.jpa.core.service.restclient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.TestPropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import tr.com.nekasoft.core.service.restclient.NekaRestClientProperties;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestPropertySources({
        @TestPropertySource(properties = "neka.rest.clients.testapi.baseUrl=http://testapi.com"),
        @TestPropertySource(properties = "neka.rest.clients.testapi.headers.content-type=application/json")
})
@ActiveProfiles({"test"})
@SpringBootTest(classes = {NekaRestClientProperties.class})
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties({NekaRestClientProperties.class})
class NekaRestClientTest {

    @Autowired
    private NekaRestClientProperties nekaRestClientProperties;

    private MockRestServiceServer mockRestServiceServer;

    @Test
    @DisplayName("test properties")
    void testProperties() {
        assertNotNull(nekaRestClientProperties);
        assertNotNull(nekaRestClientProperties.getClients());
        assertNotNull(nekaRestClientProperties.getClients().get("testapi"));
        assertNotNull(nekaRestClientProperties.getClients().get("testapi").getBaseUrl());
        assertNotNull(nekaRestClientProperties.getClients().get("testapi").getHeaders());
        assertNotNull(nekaRestClientProperties.getClients().get("testapi").getHeaders().get("content-type"));
    }


    @Test
    void buildRestTemplate() {
        //NekaRestClientBuilder restClient = new NekaRestClientBuilder(nekaRestClientProperties.getClients().get("testapi"));
        //assertNotNull(restClient);
        //
        //RestTemplate restTemplate = restClient.buildRestTemplate();
        //assertNotNull(restTemplate);
        //
        //mockRestServiceServer = MockRestServiceServer.bindTo(restTemplate).build();
        //mockRestServiceServer.expect(requestTo("http://testapi.com/test")).andExpect(header("content-type", "application/json"))
        //        .andRespond(withSuccess());
        //
        //restTemplate.getForEntity("http://testapi.com/test", String.class);
        //mockRestServiceServer.verify();
    }

    @Test
    void testBuildRestTemplate() {
    }
}

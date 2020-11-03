package com.jimspence.springbootcamel.router;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration
public class MainRouterTest extends AbstractJUnit4SpringContextTests {

    @EndpointInject(uri="mock:endpoint")
    protected MockEndpoint resultEndpoint;

    @Produce(uri="direct:start")
    protected ProducerTemplate template;

    @DirtiesContext
    @Test
    public void testSendMatchingMessage() throws Exception {
        String expectedResult = "[{\"firstName\":\"Jim\",\"surname\":\"Spence\",\"extraInformation\":\"extraStuff\"},{\"firstName\":\"Nina\",\"surname\":\"Spence\",\"extraInformation\":\"extraStuff\"}]";
        resultEndpoint.expectedBodiesReceived(expectedResult);

        String body = "[\n" +
                "{ \"firstName\" : \"Jim\",\n" +
                "\"surname\" : \"Spence\"},\n" +
                "{ \"firstName\" : \"Nina\",\n" +
                "\"surname\" : \"Spence\"}\n" +
                "\n" +
                "]";

        template.sendBody(body);

//        template.asse

        resultEndpoint.assertIsSatisfied();
    }
}
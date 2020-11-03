package com.jimspence.springbootcamel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = SpringBootCamelApplication.class
)
@TestPropertySource(locations = "classpath:application-test.properties")
//@AutoConfigureMockMvc
public class SpringBootCamelApplicationTests {

//	@Autowired
//	MockMvc mockMvc;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void sayHelloTest() {
		//when
//		MvcResult mvcResult = mockMvc.perform(
//				MockMvcRequestBuilders.post("/springbootcamel/incoming")
//						.accept(MediaType.APPLICATION_JSON)
//						.content("[\n" +
//								"{ \"firstName\" : \"Jim\",\n" +
//								"\"surname\" : \"Spence\"},\n" +
//								"{ \"firstName\" : \"Nina\",\n" +
//								"\"surname\" : \"Spence\"}\n" +
//								"\n" +
//								"]")
//		).andExpect(status().isOk())
//				.andReturn();
		String body = "[\n" +
								"{ \"firstName\" : \"Jim\",\n" +
								"\"surname\" : \"Spence\"},\n" +
								"{ \"firstName\" : \"Nina\",\n" +
								"\"surname\" : \"Spence\"}\n" +
								"\n" +
								"]";
		ResponseEntity<String> response = restTemplate.postForEntity("/springbootcamel/incoming", body, String.class);
		System.out.println(response);



	}
}

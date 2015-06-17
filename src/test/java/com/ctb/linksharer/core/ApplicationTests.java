package com.ctb.linksharer.core;

import com.ctb.linksharer.Application;
import com.ctb.linksharer.core.domain.Link;
import com.ctb.linksharer.core.repository.LinkRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by izeye on 15. 6. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
@DirtiesContext
public class ApplicationTests {

	Link link = new Link("http://www.google.com/");
	
	@Value("${local.server.port}")
	int port;
	
	@Autowired
	LinkRepository linkRepository;
	
	@Before
	public void setUp() {
		this.linkRepository.save(link);
	}
	
	@After
	public void tearDown() {
		this.linkRepository.delete(link);
	}
	
	@Test
	public void testHome() {
		RestTemplate restTemplate = new RestTemplate();
		
		String expected = "Hello, world!";
		
		String url = "http://localhost:{port}";

		String response = restTemplate.getForObject(url, String.class, port);
		System.out.println(response);
		assertThat(response, containsString(expected));
	}
	
	@Test
	public void testApiLinks() {
		RestTemplate restTemplate = getHalRestTemplate();
		
		String url = "http://localhost:{port}/api/links";
		ResponseEntity<PagedResources<Link>> responseEntity = restTemplate.exchange(
				url, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<Link>>() {}, port);
		PagedResources<Link> resources = responseEntity.getBody();
		resources.getContent().forEach(System.out::println);
	}

	private RestTemplate getHalRestTemplate() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jackson2HalModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);
		return new RestTemplate(Arrays.asList(converter));
	}
	
}

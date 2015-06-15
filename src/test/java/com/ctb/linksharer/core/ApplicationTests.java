package com.ctb.linksharer.core;

import com.ctb.linksharer.Application;
import com.ctb.linksharer.core.domain.Link;
import com.ctb.linksharer.core.repository.LinkRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by izeye on 15. 6. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class ApplicationTests {
	
	@Value("${local.server.port}")
	int port;
	
	@Autowired
	LinkRepository linkRepository;
	
	RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		this.linkRepository.save(new Link("http://www.google.com/"));
		
		this.restTemplate = getRestTemplate();
	}
	
	@Test
	public void test() {
		String url = "http://localhost:{port}/api/links";
		ResponseEntity<PagedResources<Link>> responseEntity = this.restTemplate.exchange(
				url, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<Link>>() {}, port);
		PagedResources<Link> resources = responseEntity.getBody();
		resources.getContent().forEach(System.out::println);
	}

	private RestTemplate getRestTemplate() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new Jackson2HalModule());

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);
		return new RestTemplate(Arrays.asList(converter));
	}
	
}

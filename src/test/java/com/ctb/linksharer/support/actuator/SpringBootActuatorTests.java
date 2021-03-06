package com.ctb.linksharer.support.actuator;

import com.ctb.linksharer.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by izeye on 15. 6. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort = true)
public class SpringBootActuatorTests {
	
	@Value("${security.user.name}")
	String username;

	@Value("${security.user.password}")
	String password;

	@Value("${local.server.port}")
	int port;
	
	RestTemplate restTemplate;
	
	@Before
	public void setUp() {
		this.restTemplate = new TestRestTemplate(username, password);
	}
	
	@Test
	public void testHealth() {
		String url = "http://localhost:{port}/management/health";
		
		String response = this.restTemplate.getForObject(url, String.class, port);
		System.out.println(response);
		assertThat(response, containsString("UP"));
	}
	
	@Test
	public void testConfigProps() {
		String url = "http://localhost:{port}/management/configprops";

		String response = this.restTemplate.getForObject(url, String.class, port);
		System.out.println(response);
		assertThat(response, containsString("serverProperties"));
	}
	
}

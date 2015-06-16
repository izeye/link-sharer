package com.ctb.linksharer.core.repository;

import com.ctb.linksharer.Application;
import com.ctb.linksharer.core.domain.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by izeye on 15. 6. 15..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
//@ActiveProfiles("production")
public class LinkRepositoryTests {
	
	@Autowired
	LinkRepository linkRepository;
	
	@Test
	@Transactional
	public void test() {
		Link link = new Link("http://www.google.com/");
		linkRepository.save(link);
		
		linkRepository.findAll().stream().forEach(System.out::println);
	}
	
}

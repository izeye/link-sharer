package com.ctb.linksharer.core.repository;

import com.ctb.linksharer.core.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by izeye on 15. 6. 15..
 */
public interface LinkRepository extends JpaRepository<Link, Long> {
}

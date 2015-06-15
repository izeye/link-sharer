package com.ctb.linksharer.core.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Created by izeye on 15. 6. 15..
 */
@Entity
@Data
public class Link {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String url;

	private Date createdTime;
	private Date modifiedTime;
	private Date deletedTime;
	
	public Link() {
	}
	
	public Link(String url) {
		this.url = url;
	}

	@PrePersist
	private void onCreate() {
		setCreatedTime(new Date());
	}

	@PreUpdate
	private void onUpdate() {
		setModifiedTime(new Date());
	}
	
}

package com.report.app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class QfundProperties {

	@Id
	private Long serialNumber;
	private String propertyName;
	private String propertyValue;
	private Character isActive;

}

package com.report.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.report.app.entity.QfundProperties;

@Repository
public interface QfundPropertiesRepository extends JpaRepository<QfundProperties, Long> {

}

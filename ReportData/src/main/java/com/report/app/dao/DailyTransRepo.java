package com.report.app.dao;

import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.report.app.dto.DailyTransJournalRequestDTO;
import com.report.app.dto.DailyTransJournalResponseDTO;

@Repository
public class DailyTransRepo {
	
	@Autowired
	private EntityManager entityManager;
	
	/*@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private DataSource dataSource;*/
	
	 @org.springframework.transaction.annotation.Transactional
	 public Stream<DailyTransJournalResponseDTO> getTransStream(DailyTransJournalRequestDTO request) {
		 
		 	Session session=entityManager.unwrap(Session.class);
	        return session.getSessionFactory().openStatelessSession()
	            .createNamedQuery("DailyTransJournalQuery", DailyTransJournalResponseDTO.class)
	            .setParameter(1, request.getLevel())
	    		.setParameter(2, 0)
	    		.setParameter(3, request.getTransType())
	    		.setParameter(4, request.getBeginDate())
	    		.setParameter(5, request.getEndDate())
	    		.setParameter(6, "getOne")
	    		.setParameter(7, "DESC")
	    		.setParameter(8, request.getOffset())
	    		.setParameter(9, request.getLimit())
	    		.setParameter(10, request.getTotalCountFlag())
	            .setReadOnly(true)
	            .setFetchSize(1000)
	            .stream();
	    }

}

package com.report.app.dao;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import com.opencsv.CSVWriter;
import com.report.app.dto.DailyTransJournalRequestDTO;
import com.report.app.dto.DailyTransJournalResponseDTO;

@Repository
public class DailyTranJournalDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public List<DailyTransJournalResponseDTO> getAll(DailyTransJournalRequestDTO request) {
		TypedQuery<DailyTransJournalResponseDTO> query = entityManager.createNamedQuery("DailyTransJournalQuery",
				DailyTransJournalResponseDTO.class);
		//set parameters
		query.setParameter(1, request.getLevel());
		query.setParameter(2, 0);
		query.setParameter(3, request.getTransType());
		query.setParameter(4, request.getBeginDate());
		query.setParameter(5, request.getEndDate());
		query.setParameter(6, "getOne");
		query.setParameter(7, "DESC");
		query.setParameter(8, request.getOffset());
		query.setParameter(9, request.getLimit());
		query.setParameter(10, request.getTotalCountFlag());

		
		List<DailyTransJournalResponseDTO> dailyTransJournalDTOs = query.getResultList();
		return dailyTransJournalDTOs;
	}
	
	public Stream<DailyTransJournalResponseDTO> getAllTransStream(DailyTransJournalRequestDTO request) {
		TypedQuery<DailyTransJournalResponseDTO> query = entityManager.createNamedQuery("DailyTransJournalQuery",
				DailyTransJournalResponseDTO.class);
				//.setHint("HINT_FETCH_SIZE", Integer.MIN_VALUE);
				//.setHint(QueryHints.HINT_READONLY, true)
				//.setHint(QueryHints.HINT_CACHEABLE, false);
		//set parameters
		query.setParameter(1, request.getLevel());
		query.setParameter(2, 0);
		query.setParameter(3, request.getTransType());
		query.setParameter(4, request.getBeginDate());
		query.setParameter(5, request.getEndDate());
		query.setParameter(6, "getOne");
		query.setParameter(7, "DESC");
		query.setParameter(8, request.getOffset());
		query.setParameter(9, request.getLimit());
		query.setParameter(10, request.getTotalCountFlag());
		return query.getResultStream();
		}

}

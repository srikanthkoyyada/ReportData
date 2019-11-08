package com.report.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.report.app.dao.DailyTranJournalDAO;
import com.report.app.dao.DailyTransRepo;
import com.report.app.dto.DailyTransJournalRequestDTO;
import com.report.app.dto.DailyTransJournalResponseDTO;

@RestController
@RequestMapping("/dailyTrans")
public class DailyTransJournalController {

	@Autowired
	private DailyTranJournalDAO dailyTransJournalDAO;
	
	@Autowired
	private DailyTransRepo dailyTransRepo;

	@RequestMapping(value="/getTransJournal",method=RequestMethod.POST)
	public List<DailyTransJournalResponseDTO> getAllTrans(@RequestBody DailyTransJournalRequestDTO request) {
		List<DailyTransJournalResponseDTO> trans= dailyTransJournalDAO.getAll(request);
		System.out.println("trans.size()::"+trans.size());
		return trans;
	}
	
	@RequestMapping(value="/getTransJournalExcel",method=RequestMethod.POST)
	public void getTransJournalExcel(@RequestBody DailyTransJournalRequestDTO request,HttpServletResponse response) {
		
		try(Stream<DailyTransJournalResponseDTO> transStream = dailyTransRepo.getTransStream(request)) {
			PrintWriter out = response.getWriter();
			
			out.write(transStream.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Exception occurred while exporting results", e);
		}
	}
	

}

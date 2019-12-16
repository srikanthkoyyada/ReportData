package com.report.app.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.report.app.dao.DailyTranJournalDAO;
import com.report.app.dto.BOInAppErrorsDTO;
import com.report.app.dto.DailyTransJournalRequestDTO;
import com.report.app.dto.DailyTransJournalResponseDTO;
import com.report.app.dto.PrintResponse;
import com.report.app.dto.TransStreamResponse;
import com.report.app.service.QfundPropertiesService;
import com.report.app.service.ReportServiceImpl;
import com.report.app.util.AppConstants;
import com.report.app.util.ExcelEportUtility;
import com.report.app.util.NoXmlJasperTemplate;

import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/dailyTrans")
public class DailyTransJournalController {

	@Autowired
	private DailyTranJournalDAO dailyTransJournalDAO;

	@Autowired
	private NoXmlJasperTemplate jasper;
	
	@Autowired
	private ReportServiceImpl reportService;
	
	@Autowired
	private QfundPropertiesService propertiesService;
	

	@RequestMapping(value = "/getTransJournal", method = RequestMethod.POST)
	@ResponseBody
	public TransStreamResponse getAllTrans(@RequestBody DailyTransJournalRequestDTO request) {
		List<DailyTransJournalResponseDTO> trans=new ArrayList<DailyTransJournalResponseDTO>();
		TransStreamResponse response=new TransStreamResponse();
		
		String fetchType=propertiesService.getQfundPropertiesMap().get(AppConstants.PG_DATA_FETCH_TYPE);
		response.setPaginationDataFetchType(fetchType);
		
		if(fetchType.equalsIgnoreCase("DB")) {
		trans = dailyTransJournalDAO.getAll(request);
		response.setDailyTransJournals(trans);
		response.setTransRecordCount(Integer.parseInt(trans.get(0).getV_count()));
		}
		
		if(fetchType.equalsIgnoreCase("FILE")) {
			if(request.getTransDataFileName().isEmpty())
			response=reportService.getAllTransStream(request);
			else
				response.setTransFileName(request.getTransDataFileName());
			try {
				trans=reportService.getTransactions(response.getTransFileName(),request.getOffset(),request.getLimit());
				response.setDailyTransJournals(trans);
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
		return response;
	}
	
	@RequestMapping(value = "/printDTJReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PrintResponse> printDTJReport(@RequestBody DailyTransJournalRequestDTO request) {
		PrintResponse response=new PrintResponse(); 
		try {
			jasper.printReport(request);
			response.setStatus("success");
			response.setMessage("printing is initaited");
		} catch (JRException  | SQLException | IOException e) {
			e.printStackTrace();
			response.setStatus("failure");
			response.setMessage("printing job failed due to an exception");
			return new ResponseEntity<PrintResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		return new ResponseEntity<PrintResponse>(response, HttpStatus.OK);
	}
	
	//testing purpose and we are not using this service
	@RequestMapping(value = "/writeObjectToFile", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<DailyTransJournalResponseDTO>> writeObjectToFile() throws IOException {
		DailyTransJournalRequestDTO request=new DailyTransJournalRequestDTO();
		request.setBeginDate("12/02/2019");
		request.setEndDate("12/03/2019");
		request.setLevel(0);
		request.setSortColumn("getOne");
		request.setSortOrder("Desc");
		request.setTransType("ALL");
		TransStreamResponse response=reportService.getAllTransStream(request);
		return new ResponseEntity<List<DailyTransJournalResponseDTO>>(
				reportService.getTransactions(response.getTransFileName(),1000,1000), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateCache", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> updateCache()  {
		propertiesService.updateQfundPropertiesCache();
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}


	@RequestMapping(value = "/getTransJournalExcel", method = RequestMethod.GET)
	public void getTransJournalExcel(HttpServletResponse response,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("level") int level,
			@RequestParam("transType") String transType,
			@RequestParam("sortColumn") String sortColumn,
			@RequestParam("sortOrder") String sortOrder)
			
			throws IOException {
		
		DailyTransJournalRequestDTO request=new DailyTransJournalRequestDTO();
		request.setBeginDate(startDate);
		request.setEndDate(endDate);
		request.setLevel(level);
		request.setSortColumn(sortColumn);
		request.setSortOrder(sortOrder);
		request.setTransType(transType);
		List<DailyTransJournalResponseDTO> trans = dailyTransJournalDAO.getAll(request);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_ss");
		String excelFileName = "DTJReport_" + formatter.format(LocalDateTime.now()) + ".xlsx";
		SXSSFWorkbook wb = (new ExcelEportUtility().exportExcel(new String[] {"STORE NBR","DRAWER NBR",
				"DATE AND TIME","CUSTOMER NBR","CUSTOMER NAME","LOAN NBR","TRAN NBR",
				"CHECK NBR","ISSUING CHECK AMT","TRAN AMT","PRINCIPAL AMT","FEE AMT",
				"INTEREST AMT","NSF FEE AMT","ORIG FEE AMT","LATE FEE AMT","OTHER FEE AMT",
				"WAIVE FEE AMT","LIEN FEE AMT","WAIVE LIEN FEE AMT","REPO FEE AMT",
				"SALE FEE AMT","CASH AMT","CHECK AMT","CC/MO AMT","DEBIT CARD AMT",
				"ACH AMT","PREPAID CARD","RCC AMT","EMP NBR","EMP NAME","LOAN TYPE",
				"VOID FLAG","COLLATERAL TYPE","ORIG STORE NBR","PAYMENT PLAN FEE","MHC FEE AMT" }, trans));
		try {
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/vnd.ms-excel");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
			Cookie waitingCookie=new Cookie("waitingCookie", "done");
			response.addCookie(waitingCookie);
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			wb.dispose();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	//tested for large data set
	@RequestMapping(value = "/getAppErrors", method = RequestMethod.GET)
	public void getAppErrorsExcel(HttpServletResponse response)
			
			throws IOException {
		
		
		List<BOInAppErrorsDTO> trans = dailyTransJournalDAO.getBoAppErrors();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_ss");
		String excelFileName = "AppErrors_" + formatter.format(LocalDateTime.now()) + ".xlsx";
		SXSSFWorkbook wb = (new ExcelEportUtility().exportAppErrorsExcel(new String[] {"STORE NBR","DRAWER NBR",
				"DATE AND TIME","CUSTOMER NBR","CUSTOMER NAME","LOAN NBR","TRAN NBR",
				"CHECK NBR","ISSUING CHECK AMT","TRAN AMT","PRINCIPAL AMT","FEE AMT"
				 }, trans));
		try {
			ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
			wb.write(outByteStream);
			byte[] outArray = outByteStream.toByteArray();
			response.setContentType("application/vnd.ms-excel");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename=" + excelFileName);
			OutputStream outStream = response.getOutputStream();
			outStream.write(outArray);
			outStream.flush();
			wb.dispose();
			wb.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

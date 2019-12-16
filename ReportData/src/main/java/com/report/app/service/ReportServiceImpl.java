package com.report.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.report.app.dao.DailyTranJournalDAO;
import com.report.app.dto.DailyTransJournalRequestDTO;
import com.report.app.dto.DailyTransJournalResponseDTO;
import com.report.app.dto.TransStreamResponse;
import com.report.app.util.AppConstants;

@Service
public class ReportServiceImpl {

	@Autowired
	private DailyTranJournalDAO dailyTranJournalDAO;

	@Autowired
	private QfundPropertiesService propertiesService;
	
	@Autowired
	private ResourceLoader resourceLoader;

	@Transactional
	public TransStreamResponse getAllTransStream(DailyTransJournalRequestDTO request) {

		TransStreamResponse response = new TransStreamResponse();
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder streamObjectsString = new StringBuilder();

		String fileName = System.currentTimeMillis() + ".txt";
		String filePath = propertiesService.getQfundPropertiesMap().get(AppConstants.REPORT_DATA_FILE_PATH);
		try (Stream<DailyTransJournalResponseDTO> stream = dailyTranJournalDAO.getAllTransStream(request)) {
			stream.forEach(dto -> {
				try {
					streamObjectsString.append(mapper.writeValueAsString(dto) + System.lineSeparator());

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			// set file name in response
			response.setTransFileName(fileName);
			
			//Directory creation 
			File directory=new File(filePath);
			if(!directory.isDirectory()) {
				directory.mkdir();
			}
			
			//File Creation
			File file=new File(directory, fileName);
			file.createNewFile();
			
			//Access file as System reource
			FileSystemResource resource=new FileSystemResource(file);
			
			// write contents to file
			Files.write(Paths.get(resource.getFile().getAbsolutePath()).normalize(), streamObjectsString.toString().getBytes());
			
			// set total records by using file for pagination
			response.setTransRecordCount(Files.readAllLines(Paths.get(resource.getFile().getAbsolutePath()).normalize()).size());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	public List<DailyTransJournalResponseDTO> getTransactions(String fileName, int offset, int limit)
			throws IOException {

		List<DailyTransJournalResponseDTO> dailyTrans = new ArrayList<DailyTransJournalResponseDTO>();
		ObjectMapper objectMapper = new ObjectMapper();
		String fileNameWithPath =propertiesService.getQfundPropertiesMap().get(AppConstants.REPORT_DATA_FILE_PATH)+fileName;
		try {
			List<String> fileLines = Files.readAllLines(Paths.get(fileNameWithPath));
			int lineNumber = offset + 1;
			while (lineNumber <= (offset + limit) && lineNumber < fileLines.size()) {
				dailyTrans.add(objectMapper.readValue(fileLines.get(lineNumber), DailyTransJournalResponseDTO.class));
				lineNumber += 1;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dailyTrans;
	}

}

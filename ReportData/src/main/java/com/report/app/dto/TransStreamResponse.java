package com.report.app.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransStreamResponse {
	
	private int transRecordCount;
	private String transFileName;
	private List<DailyTransJournalResponseDTO>  dailyTransJournals;
	private String paginationDataFetchType;

}

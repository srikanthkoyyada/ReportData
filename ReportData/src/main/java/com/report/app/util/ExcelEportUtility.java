package com.report.app.util;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.report.app.dto.BOInAppErrorsDTO;
import com.report.app.dto.DailyTransJournalResponseDTO;

public class ExcelEportUtility {

	protected SXSSFWorkbook wb;
	protected Sheet sh;
	protected static final String EMPTY_VALUE = " ";

	private void autoResizeColumns(int listSize) {
		for (int colIndex = 0; colIndex < listSize; colIndex++) {
			sh.autoSizeColumn(colIndex);
		}
	}

	private void fillHeader(String[] columns) {
		wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
		sh = wb.createSheet("DTJ Report Data");

		for (int rownum = 0; rownum < 1; rownum++) {
			Row row = sh.createRow(rownum);
			for (int cellnum = 0; cellnum < columns.length; cellnum++) {
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(columns[cellnum]);

			}
		}
	}

	public final SXSSFWorkbook exportExcel(String[] columns, List<DailyTransJournalResponseDTO> dataList) {
		fillHeader(columns);
		fillData(dataList);
		// autoResizeColumns(columns.length);
		return wb;
	}

	void fillData(List<DailyTransJournalResponseDTO> dataList) {

		int rownum = 1;
		for (DailyTransJournalResponseDTO rev : dataList) {
			Row row = sh.createRow(rownum);

			Cell cell_0 = row.createCell(0);
			cell_0.setCellValue(rev.getStore());

			Cell cell_1 = row.createCell(1);
			cell_1.setCellValue(rev.getDrawer_code());

			Cell cell_2 = row.createCell(2);
			cell_2.setCellValue(rev.getDttrandate());

			Cell cell_3 = row.createCell(3);
			cell_3.setCellValue(rev.getCustnbr());

			Cell cell_4 = row.createCell(4);
			cell_4.setCellValue(rev.getName());

			Cell cell_5 = row.createCell(5);
			cell_5.setCellValue(rev.getLc_code());

			Cell cell_6 = row.createCell(6);
			cell_6.setCellValue(rev.getTransaction_number());

			Cell cell_7 = row.createCell(7);
			cell_7.setCellValue(rev.getBo_check_num());

			Cell cell_8 = row.createCell(8);
			cell_8.setCellValue(rev.getIssuing_check_amount());

			Cell cell_9 = row.createCell(9);
			cell_9.setCellValue(rev.getTran_amt());

			Cell cell_10 = row.createCell(10);
			cell_10.setCellValue(rev.getPrincipal());

			Cell cell_11 = row.createCell(11);
			cell_11.setCellValue(rev.getFees());

			Cell cell_12 = row.createCell(12);
			cell_12.setCellValue(rev.getInterestfee());

			Cell cell_13 = row.createCell(13);
			cell_13.setCellValue(rev.getNsfamt());

			Cell cell_14 = row.createCell(14);
			cell_14.setCellValue(rev.getOrig_fee());

			Cell cell_15 = row.createCell(15);
			cell_15.setCellValue(rev.getLate_fee_charged());

			Cell cell_16 = row.createCell(16);
			cell_16.setCellValue(rev.getOther_fee());

			Cell cell_17 = row.createCell(17);
			cell_17.setCellValue(rev.getWaived_fee_amt());

			Cell cell_18 = row.createCell(18);
			cell_18.setCellValue(rev.getLien_fee());

			Cell cell_19 = row.createCell(19);
			cell_19.setCellValue(rev.getWaive_lien_fee());

			Cell cell_20 = row.createCell(20);
			cell_20.setCellValue(rev.getRepo_fee());

			Cell cell_21 = row.createCell(21);
			cell_21.setCellValue(rev.getSale_fee());

			Cell cell_22 = row.createCell(22);
			cell_22.setCellValue(rev.getCashamt());

			Cell cell_23 = row.createCell(23);
			cell_23.setCellValue(rev.getCheckamt());

			Cell cell_24 = row.createCell(24);
			cell_24.setCellValue(rev.getCcmoamt());

			Cell cell_25 = row.createCell(25);
			cell_25.setCellValue(rev.getDebitcardamt());

			Cell cell_26 = row.createCell(26);
			cell_26.setCellValue(rev.getAch_amt());

			Cell cell_27 = row.createCell(27);
			cell_27.setCellValue(rev.getPrepaidcard_amt());

			Cell cell_28 = row.createCell(28);
			cell_28.setCellValue(rev.getRcc_fee());

			Cell cell_29 = row.createCell(29);
			cell_29.setCellValue(rev.getEmp_number());

			Cell cell_30 = row.createCell(30);
			cell_30.setCellValue(rev.getEmpname());

			Cell cell_31 = row.createCell(31);
			cell_31.setCellValue(rev.getType());

			Cell cell_32 = row.createCell(32);
			cell_32.setCellValue(rev.getVoid_flag());

			Cell cell_33 = row.createCell(33);
			cell_33.setCellValue(rev.getCollateral_type());

			Cell cell_34 = row.createCell(34);
			cell_34.setCellValue(rev.getOrig_store_number());

			Cell cell_35 = row.createCell(35);
			cell_35.setCellValue(rev.getPpf_fee());

			Cell cell_36 = row.createCell(36);
			cell_36.setCellValue(rev.getMhc_fee());

			rownum++;

		}
	}
	
	public final SXSSFWorkbook exportAppErrorsExcel(String[] columns, List<BOInAppErrorsDTO> dataList) {
		fillHeader(columns);
		fillAppErrorData(dataList);
		// autoResizeColumns(columns.length);
		return wb;
	}

	void fillAppErrorData(List<BOInAppErrorsDTO> dataList) {

		int rownum = 1;
		for (BOInAppErrorsDTO rev : dataList) {
			Row row = sh.createRow(rownum);

			Cell cell_0 = row.createCell(0);
			cell_0.setCellValue(rev.getApp_no());

			Cell cell_1 = row.createCell(1);
			cell_1.setCellValue(rev.getBo_code());

			Cell cell_2 = row.createCell(2);
			cell_2.setCellValue(rev.getCreated_by());

			Cell cell_3 = row.createCell(3);
			cell_3.setCellValue(rev.getDate_created());

			Cell cell_4 = row.createCell(4);
			cell_4.setCellValue(rev.getError_code());

			Cell cell_5 = row.createCell(5);
			cell_5.setCellValue(rev.getError_description());

			Cell cell_6 = row.createCell(6);
			cell_6.setCellValue(rev.getError_type());

			Cell cell_7 = row.createCell(7);
			cell_7.setCellValue(rev.getMob_seq_num());

			Cell cell_8 = row.createCell(8);
			cell_8.setCellValue(rev.getProduct_type());

			Cell cell_9 = row.createCell(9);
			cell_9.setCellValue(rev.getSeq_num());

			Cell cell_10 = row.createCell(10);
			cell_10.setCellValue(rev.getSt_code());

			Cell cell_11 = row.createCell(11);
			cell_11.setCellValue(rev.getStatus());

			rownum++;
		}
	}

}

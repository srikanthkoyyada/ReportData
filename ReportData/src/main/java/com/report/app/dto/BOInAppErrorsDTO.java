package com.report.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BOInAppErrorsDTO {
	
	private String seq_num;
	private String app_no;
	private String error_code;
	private String error_type;
	private String date_created;
	private String created_by;
	private String status;
	private String mob_seq_num;
	private String bo_code;
	private String error_description;
	private String product_type;
	private String st_code;
	public BOInAppErrorsDTO(String seq_num, String app_no, String error_code, String error_type, String date_created,
			String created_by, String status, String mob_seq_num, String bo_code, String error_description,
			String product_type, String st_code) {
		super();
		this.seq_num = seq_num;
		this.app_no = app_no;
		this.error_code = error_code;
		this.error_type = error_type;
		this.date_created = date_created;
		this.created_by = created_by;
		this.status = status;
		this.mob_seq_num = mob_seq_num;
		this.bo_code = bo_code;
		this.error_description = error_description;
		this.product_type = product_type;
		this.st_code = st_code;
	}
	
	
	
}

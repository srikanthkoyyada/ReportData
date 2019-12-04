package com.report.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyTransJournalResponseDTO {

	private String store;
	private String drawer_code;
	private String dttrandate;
	private String custnbr;
	private String name;
	private String lc_code;
	private String transaction_number;
	private String bo_check_num;
	private String issuing_check_amount;
	private String tran_amt;
	private String principal;
	private String fees;
	private String interestfee;
	private String nsfamt;
	private String orig_fee;
	private String late_fee_charged;
	private String other_fee;
	private String waived_fee_amt;
	private String lien_fee;
	private String waive_lien_fee;
	private String repo_fee;
	private String sale_fee;
	private String cashamt;
	private String checkamt;
	private String ccmoamt;
	private String debitcardamt;
	private String ach_amt;
	private String prepaidcard_amt;
	private String rcc_fee;
	private String emp_number;
	private String empname;
	private String type;
	private String void_flag;
	private String collateral_type;
	private String orig_store_number;
	private String ppf_fee;
	private String mhc_fee;
	private String v_count;

	public DailyTransJournalResponseDTO(String store, String drawer_code, String dttrandate, String custnbr,
			String name, String lc_code, String transaction_number, String bo_check_num, String issuing_check_amount,
			String tran_amt, String principal, String fees, String interestfee, String nsfamt, String orig_fee,
			String late_fee_charged, String other_fee, String waived_fee_amt, String lien_fee, String waive_lien_fee,
			String repo_fee, String sale_fee, String cashamt, String checkamt, String ccmoamt, String debitcardamt,
			String ach_amt, String prepaidcard_amt, String rcc_fee, String emp_number, String empname, String type,
			String void_flag, String collateral_type, String orig_store_number, String ppf_fee, String mhc_fee,String v_count) {
		super();
		this.store = store;
		this.drawer_code = drawer_code;
		this.dttrandate = dttrandate;
		this.custnbr = custnbr;
		this.name = name;
		this.lc_code = lc_code;
		this.transaction_number = transaction_number;
		this.bo_check_num = bo_check_num;
		this.issuing_check_amount = issuing_check_amount;
		this.tran_amt = tran_amt;
		this.principal = principal;
		this.fees = fees;
		this.interestfee = interestfee;
		this.nsfamt = nsfamt;
		this.orig_fee = orig_fee;
		this.late_fee_charged = late_fee_charged;
		this.other_fee = other_fee;
		this.waived_fee_amt = waived_fee_amt;
		this.lien_fee = lien_fee;
		this.waive_lien_fee = waive_lien_fee;
		this.repo_fee = repo_fee;
		this.sale_fee = sale_fee;
		this.cashamt = cashamt;
		this.checkamt = checkamt;
		this.ccmoamt = ccmoamt;
		this.debitcardamt = debitcardamt;
		this.ach_amt = ach_amt;
		this.prepaidcard_amt = prepaidcard_amt;
		this.rcc_fee = rcc_fee;
		this.emp_number = emp_number;
		this.empname = empname;
		this.type = type;
		this.void_flag = void_flag;
		this.collateral_type = collateral_type;
		this.orig_store_number = orig_store_number;
		this.ppf_fee = ppf_fee;
		this.mhc_fee = mhc_fee;
		this.v_count=v_count;
	}

	@Override
	public String toString() {
		return store + "," + drawer_code + ","
				+ dttrandate + "," + custnbr + "," + name + "," + lc_code
				+ "," + transaction_number + "," + bo_check_num
				+ "," + issuing_check_amount + "," + tran_amt + ","
				+ principal + "," + fees + "," + interestfee + "," + nsfamt + ","
				+ orig_fee + "," + late_fee_charged + "," + other_fee + ","
				+ waived_fee_amt + "," + lien_fee + "," + waive_lien_fee + ","
				+ repo_fee + "," + sale_fee + "," + cashamt + "," + checkamt + ","
				+ ccmoamt + "," + debitcardamt + "," + ach_amt + ","
				+ prepaidcard_amt + "," + rcc_fee + "," + emp_number + "," + empname
				+ "," + type + "," + void_flag + "," + collateral_type
				+ "," + orig_store_number + "," + ppf_fee + "," + mhc_fee
				+ "," + v_count +"\n";
	}
	
	

}

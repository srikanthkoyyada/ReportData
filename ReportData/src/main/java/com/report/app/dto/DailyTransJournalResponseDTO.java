package com.report.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	@JsonCreator
	public DailyTransJournalResponseDTO(@JsonProperty("store") String store,
			@JsonProperty("drawer_code") String drawer_code, @JsonProperty("dttrandate") String dttrandate,
			@JsonProperty("custnbr") String custnbr, @JsonProperty("name") String name,
			@JsonProperty("lc_code") String lc_code, @JsonProperty("transaction_number") String transaction_number,
			@JsonProperty("bo_check_num") String bo_check_num,
			@JsonProperty("issuing_check_amount") String issuing_check_amount,
			@JsonProperty("tran_amt") String tran_amt, @JsonProperty("principal") String principal,
			@JsonProperty("fees") String fees, @JsonProperty("interestfee") String interestfee,
			@JsonProperty("nsfamt") String nsfamt, @JsonProperty("orig_fee") String orig_fee,
			@JsonProperty("late_fee_charged") String late_fee_charged, @JsonProperty("other_fee") String other_fee,
			@JsonProperty("waived_fee_amt") String waived_fee_amt, @JsonProperty("lien_fee") String lien_fee,
			@JsonProperty("waive_lien_fee") String waive_lien_fee, @JsonProperty("repo_fee") String repo_fee,
			@JsonProperty("sale_fee") String sale_fee, @JsonProperty("cashamt") String cashamt,
			@JsonProperty("checkamt") String checkamt, @JsonProperty("ccmoamt") String ccmoamt,
			@JsonProperty("debitcardamt") String debitcardamt, @JsonProperty("ach_amt") String ach_amt,
			@JsonProperty("prepaidcard_amt") String prepaidcard_amt, @JsonProperty("rcc_fee") String rcc_fee,
			@JsonProperty("emp_number") String emp_number, @JsonProperty("empname") String empname,
			@JsonProperty("type") String type, @JsonProperty("void_flag") String void_flag,
			@JsonProperty("collateral_type") String collateral_type,
			@JsonProperty("orig_store_number") String orig_store_number, @JsonProperty("ppf_fee") String ppf_fee,
			@JsonProperty("mhc_fee") String mhc_fee, @JsonProperty("v_count") String v_count) {
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
		this.v_count = v_count;
	}

	@Override
	public String toString() {
		return store + "," + drawer_code + "," + dttrandate + "," + custnbr + "," + name + "," + lc_code + ","
				+ transaction_number + "," + bo_check_num + "," + issuing_check_amount + "," + tran_amt + ","
				+ principal + "," + fees + "," + interestfee + "," + nsfamt + "," + orig_fee + "," + late_fee_charged
				+ "," + other_fee + "," + waived_fee_amt + "," + lien_fee + "," + waive_lien_fee + "," + repo_fee + ","
				+ sale_fee + "," + cashamt + "," + checkamt + "," + ccmoamt + "," + debitcardamt + "," + ach_amt + ","
				+ prepaidcard_amt + "," + rcc_fee + "," + emp_number + "," + empname + "," + type + "," + void_flag
				+ "," + collateral_type + "," + orig_store_number + "," + ppf_fee + "," + mhc_fee + "," + v_count
				+ "\n";
	}

}

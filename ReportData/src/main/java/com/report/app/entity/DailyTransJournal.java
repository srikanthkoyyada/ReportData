package com.report.app.entity;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import com.report.app.dto.DailyTransJournalResponseDTO;

@MappedSuperclass
@SqlResultSetMapping( 
    name = "DailyTransJournalMapping",
    classes = @ConstructorResult(
        targetClass = DailyTransJournalResponseDTO.class,
        columns = {
        		@ColumnResult(name = "STORE", type = String.class),
        		@ColumnResult(name = "DRAWER_CODE", type = String.class),
        		@ColumnResult(name = "DTTRANDATE", type = String.class),
        		@ColumnResult(name = "CUSTNBR", type = String.class),
        		@ColumnResult(name = "NAME", type = String.class),
        		@ColumnResult(name = "LC_CODE", type = String.class),
        		@ColumnResult(name = "TRANSACTION_NUMBER", type = String.class),
        		@ColumnResult(name = "BO_CHECK_NUM", type = String.class),
        		@ColumnResult(name = "ISSUING_CHECK_AMOUNT", type = String.class),
        		@ColumnResult(name = "TRAN_AMT", type = String.class),
        		@ColumnResult(name = "PRINCIPAL", type = String.class),
				@ColumnResult(name = "FEES", type = String.class),
				@ColumnResult(name = "INTERESTFEE", type = String.class),
				@ColumnResult(name = "NSFAMT", type = String.class),
				@ColumnResult(name = "ORIG_FEE", type = String.class),
				@ColumnResult(name = "LATE_FEE_CHARGED", type = String.class),
				@ColumnResult(name = "OTHER_FEE", type = String.class),
				@ColumnResult(name = "WAIVED_FEE_AMT", type = String.class),
				@ColumnResult(name = "LIEN_FEE", type = String.class),
				@ColumnResult(name = "WAIVE_LIEN_FEE", type = String.class),
				@ColumnResult(name = "REPO_FEE", type = String.class),
				@ColumnResult(name = "SALE_FEE", type = String.class),
				@ColumnResult(name = "CASHAMT", type = String.class),
				@ColumnResult(name = "CHECKAMT", type = String.class),
				@ColumnResult(name = "CCMOAMT", type = String.class),
				@ColumnResult(name = "DEBITCARDAMT", type = String.class),
				@ColumnResult(name = "ACH_AMT", type = String.class),
				@ColumnResult(name = "PREPAIDCARD_AMT", type = String.class),
				@ColumnResult(name = "RCC_FEE", type = String.class),
				@ColumnResult(name = "EMP_NUMBER", type = String.class),
				@ColumnResult(name = "EMPNAME", type = String.class),
				@ColumnResult(name = "TYPE", type = String.class),
				@ColumnResult(name = "VOID_FLAG", type = String.class),
				@ColumnResult(name = "COLLATERAL_TYPE", type = String.class),
				@ColumnResult(name = "ORIG_STORE_NUMBER", type = String.class),
				@ColumnResult(name = "PPF_FEE", type = String.class),
				@ColumnResult(name = "MHC_FEE", type = String.class),
				@ColumnResult(name = "V_COUNT", type = String.class)
        }
    )
)
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "DailyTransJournalQuery",
            query = "SELECT STORE,DRAWER_CODE,DTTRANDATE,CUSTNBR,LC_CODE,TRANSACTION_NUMBER,NAME,BO_CHECK_NUM,ISSUING_CHECK_AMOUNT," + 
            		"TRAN_AMT,PRINCIPAL,FEES,INTERESTFEE,NSFAMT,ORIG_FEE,LATE_FEE_CHARGED,OTHER_FEE,WAIVED_FEE_AMT,LIEN_FEE,WAIVE_LIEN_FEE," + 
            		"REPO_FEE,SALE_FEE,CASHAMT,CHECKAMT,CCMOAMT,DEBITCARDAMT,ACH_AMT,PREPAIDCARD_AMT,RCC_FEE,EMP_NUMBER,EMPNAME,TYPE,VOID AS VOID_FLAG,COLLATERAL_TYPE," + 
            		"ORIG_STORE_NUMBER,PPF_FEE,MHC_FEE,V_COUNT FROM  TABLE(RPT_DAILYTRANJOURNAL_SAMPLE(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10))",
            resultSetMapping = "DailyTransJournalMapping"
    )
}) 
public abstract class DailyTransJournal {

}

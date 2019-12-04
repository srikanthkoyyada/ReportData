package com.report.app.entity;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import com.report.app.dto.BOInAppErrorsDTO;

@MappedSuperclass
@SqlResultSetMapping( 
    name = "MobileAppErrorsMapping",
    classes = @ConstructorResult(
        targetClass = BOInAppErrorsDTO.class,
        columns = {
        		@ColumnResult(name = "SEQ_NUM", type = String.class),
        		@ColumnResult(name = "APP_NO", type = String.class),
        		@ColumnResult(name = "ERROR_CODE", type = String.class),
        		@ColumnResult(name = "ERROR_TYPE", type = String.class),
        		@ColumnResult(name = "DATE_CREATED", type = String.class),
        		@ColumnResult(name = "CREATED_BY", type = String.class),
        		@ColumnResult(name = "STATUS", type = String.class),
        		@ColumnResult(name = "MOB_SEQ_NUM", type = String.class),
        		@ColumnResult(name = "BO_CODE", type = String.class),
        		@ColumnResult(name = "ERROR_DESCRIPTION", type = String.class),
        		@ColumnResult(name = "PRODUCT_TYPE", type = String.class),
				@ColumnResult(name = "ST_CODE", type = String.class)
				
        }
    )
)
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "MobileAppErrorsQuery",
            query = "select SEQ_NUM,APP_NO,ERROR_CODE,ERROR_TYPE,DATE_CREATED,CREATED_BY,STATUS,"
            		+ "MOB_SEQ_NUM,BO_CODE,ERROR_DESCRIPTION,PRODUCT_TYPE,ST_CODE " + 
            		"from bo_in_app_errors where to_char(date_created,'MM/DD/YYYY') between ? and ?",
            resultSetMapping = "MobileAppErrorsMapping"
    )
}) 
public abstract class BOInAppErrors {

}

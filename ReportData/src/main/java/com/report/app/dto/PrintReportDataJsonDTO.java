package com.report.app.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PrintReportDataJsonDTO {
	
	private String columnName;
	private String columnValue;
	private String columnDataType;
	private int X;
	private int Y;
	private int height;
	private int width;
	private String alignment;
	private String style;
	private String foreColor;
	private String backColor;
	
	@JsonCreator
	public PrintReportDataJsonDTO(@JsonProperty("columnName")  String columnName,
			@JsonProperty("columnValue")  String columnValue, 
			@JsonProperty("columnDataType")  String columnDataType, 
			@JsonProperty("x")  int x, 
			@JsonProperty("y")  int y, 
			@JsonProperty("height")  int height,
			@JsonProperty("width")  int width, 
			@JsonProperty("alignment")  String alignment, 
			@JsonProperty("style")  String style, 
			@JsonProperty("foreColor")  String foreColor, 
			@JsonProperty("backColor")  String backColor) {
		super();
		this.columnName = columnName;
		this.columnValue = columnValue;
		this.columnDataType = columnDataType;
		X = x;
		Y = y;
		this.height = height;
		this.width = width;
		this.alignment = alignment;
		this.style = style;
		this.foreColor = foreColor;
		this.backColor = backColor;
	}
	
	
	

}

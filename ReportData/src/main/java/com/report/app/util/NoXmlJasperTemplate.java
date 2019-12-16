package com.report.app.util;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import com.netflix.discovery.converters.Auto;
import com.report.app.dto.DailyTransJournalRequestDTO;
import com.report.app.service.QfundPropertiesService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignFrame;
import net.sf.jasperreports.engine.design.JRDesignLine;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;

@Service
public class NoXmlJasperTemplate {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private QfundPropertiesService propertiesService;

	private void compile(DailyTransJournalRequestDTO request) throws JRException,IOException {
		long start = System.currentTimeMillis();
		JasperDesign jasperDesign = getJasperDesign(request);
		String filePath = propertiesService.getQfundPropertiesMap().get(AppConstants.REPORT_DATA_FILE_PATH);
		
		File directory=new File(filePath);
		if(!directory.isDirectory()) {
			directory.mkdir();
		}
		
		//File Creation
		File file=new File(directory, "NoXmlDTJReport.jasper");
		file.createNewFile();
		
		
		
		JasperCompileManager.compileReportToFile(jasperDesign,file.getAbsolutePath());
		System.err.println("Compile time : " + (System.currentTimeMillis() - start));
	}

	private JasperPrint fillReport(DailyTransJournalRequestDTO request) throws JRException, SQLException {
		
		
		JRFileVirtualizer virtualizer=new JRFileVirtualizer(5, propertiesService.getQfundPropertiesMap().get(AppConstants.REPORT_DATA_FILE_PATH));
		// Preparing parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("startDate", request.getBeginDate());
		parameters.put("endDate", request.getEndDate());
		parameters.put("level", request.getLevel());
		parameters.put("transType", request.getTransType());
		parameters.put("sortColumn", request.getSortColumn());
		parameters.put("sortOrder", request.getSortOrder());
		String[] columns=request.getSelectedColumnValues();
		parameters.put("columnsToSelect", String.join(",", columns));
		parameters.put("ReportTitle", "Daily Tran Journal Report");
		
		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer); 
		String filePath = propertiesService.getQfundPropertiesMap().get(AppConstants.REPORT_DATA_FILE_PATH);
		
		return JasperFillManager.fillReport(filePath+"NoXmlDTJReport.jasper", parameters,
				dataSource.getConnection());
		
	}

	
	public void printReport(DailyTransJournalRequestDTO request) throws JRException, SQLException, IOException {
		compile(request);
		JasperPrint jp=fillReport(request);
		PrintReportToPrinter(jp);
		
	}
	
	private void PrintReportToPrinter(JasperPrint jasperPrint) throws JRException {

		//Get the printers names
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

		//Lets set the printer name based on the registered printers driver name (you can see the printer names in the services variable at debugging) 
		String selectedPrinter = "PDF995";   

		System.out.println("Number of print services available: " + services.length);
		PrintService selectedService = null;

		//Set the printing settings
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		printRequestAttributeSet.add(MediaSizeName.ISO_A4);
		printRequestAttributeSet.add(new Copies(1));
		if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) { 
		  printRequestAttributeSet.add(OrientationRequested.LANDSCAPE); 
		} else { 
		 printRequestAttributeSet.add(OrientationRequested.PORTRAIT); 
		} 
		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		printServiceAttributeSet.add(new PrinterName(selectedPrinter, null));

		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
		configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
		configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
		configuration.setDisplayPageDialog(false);
		configuration.setDisplayPrintDialog(false);

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setConfiguration(configuration);

		//Iterate through available printer, and once matched with our <selectedPrinter>, go ahead and print!
		if(services != null && services.length != 0){
		  for(PrintService service : services){
		      String existingPrinter = service.getName();
		      if(existingPrinter.equals(selectedPrinter))
		      {
		          selectedService = service;
		          break;
		      }
		  }
		}
		if(selectedService != null)
		{   
		  try{
		      //Lets the printer do its magic!
		      exporter.exportReport();
		  }catch(Exception e){
		System.out.println("JasperReport Error: "+e.getMessage());
		  }
		}else{
		  System.out.println("JasperReport Error: Printer not found!");
		}}
	
	

	
	private JasperDesign getJasperDesign(DailyTransJournalRequestDTO request) throws JRException {
		// JasperDesign
		JasperDesign jasperDesign = new JasperDesign();
		jasperDesign.setName("DTJReport");
		jasperDesign.setPageWidth(595);
		jasperDesign.setPageHeight(842);
		jasperDesign.setColumnWidth(555);
		jasperDesign.setColumnSpacing(0);
		jasperDesign.setLeftMargin(10);
		jasperDesign.setRightMargin(10);
		jasperDesign.setTopMargin(40);
		jasperDesign.setBottomMargin(30);

		// Fonts
		JRDesignStyle normalStyle = new JRDesignStyle();
		normalStyle.setName("Sans_Normal");
		normalStyle.setDefault(true);
		normalStyle.setFontName("DejaVu Sans");
		normalStyle.setFontSize(8f);
		normalStyle.setPdfFontName("Helvetica");
		normalStyle.setPdfEncoding("Cp1252");
		normalStyle.setPdfEmbedded(Boolean.FALSE);
		jasperDesign.addStyle(normalStyle);

		JRDesignStyle boldStyle = new JRDesignStyle();
		boldStyle.setName("Sans_Bold");
		boldStyle.setFontName("DejaVu Sans");
		boldStyle.setFontSize(9f);
		boldStyle.setBold(Boolean.TRUE);
		boldStyle.setPdfFontName("Helvetica-Bold");
		boldStyle.setPdfEncoding("Cp1252");
		boldStyle.setPdfEmbedded(Boolean.FALSE);
		jasperDesign.addStyle(boldStyle);

		

		// Parameters
		JRDesignParameter parameter = new JRDesignParameter();
		parameter.setName("ReportTitle");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);
		
		parameter = new JRDesignParameter();
		parameter.setName("startDate");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);
		
		parameter = new JRDesignParameter();
		parameter.setName("endDate");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);
		
		parameter = new JRDesignParameter();
		parameter.setName("level");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);
		
		parameter = new JRDesignParameter();
		parameter.setName("transType");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);
		
		parameter = new JRDesignParameter();
		parameter.setName("sortColumn");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);
		
		parameter = new JRDesignParameter();
		parameter.setName("sortOrder");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);

		parameter = new JRDesignParameter();
		parameter.setName("columnsToSelect");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);
		

		// Query
		JRDesignQuery query = new JRDesignQuery();
		String queryString="SELECT "+String.join(",", request.getSelectedColumnValues())+" FROM  TABLE(RPT_DAILYTRANJOURNAL_SAMPLE";
		query.setText(queryString+"($P{level},0,$P{transType},$P{startDate},$P{endDate},$P{sortColumn},$P{sortOrder},0,0,'N'))");
		jasperDesign.setQuery(query);
 
		// Fields
		for(int i=0;i<request.getSelectedColumnValues().length;i++) {
		JRDesignField field = new JRDesignField();
		field.setName(request.getSelectedColumnValues()[i]);
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);
		}

		

		JRDesignBand band = new JRDesignBand();
		band.setHeight(30);

		// Title
		JRDesignTextField titleField;
		titleField = new JRDesignTextField();
		titleField.setBlankWhenNull(true);
		titleField.setX(0);
		titleField.setY(5);
		titleField.setWidth(595);
		titleField.setHeight(20);
		titleField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
		titleField.setStyle(normalStyle);
		titleField.setFontSize(14f);
		titleField.setExpression(new JRDesignExpression("$P{ReportTitle}"));
		band.addElement(titleField);
		jasperDesign.setTitle(band);

		// Column Header
		band = new JRDesignBand();
		band.setHeight(25);
		JRDesignFrame frame = new JRDesignFrame();
		frame.setX(0);
		frame.setY(5);
		frame.setWidth(595);
		frame.setHeight(20);
		frame.setForecolor(new Color(0x33, 0x33, 0x33));
		frame.setBackcolor(new Color(0x33, 0x33, 0x33));
		frame.setMode(ModeEnum.OPAQUE);
		band.addElement(frame);
		
		
		//table column header
		int X=0;
		int width=45;
		for(int i=0;i<request.getSelectedColumnNames().length;i++) {
		
		JRDesignStaticText staticText = new JRDesignStaticText();
		staticText.setX(X);
		staticText.setY(5);
		staticText.setWidth(width);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
		staticText.setStyle(boldStyle);
		staticText.setText(request.getSelectedColumnNames()[i]);
		frame.addElement(staticText);
		X+=width;
		}
		
		
		jasperDesign.setColumnHeader(band);

		// Detail
		JRDesignBand detailedBand = new JRDesignBand();
		detailedBand.setHeight(20);
		
		X=0;
		width=45;
		for(int i=0;i<request.getSelectedColumnValues().length;i++) {
		JRDesignTextField textField = new JRDesignTextField();
		textField.setX(X);
		textField.setY(5);
		textField.setWidth(width);
		textField.setHeight(15);
		textField.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
		textField.setStyle(normalStyle);
		textField.setExpression(new JRDesignExpression("$F{"+request.getSelectedColumnValues()[i]+"}"));
		detailedBand.addElement(textField);
		X+=width;
		}
		
		
		JRDesignLine line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(595);
		line.setHeight(0);
		line.setForecolor(new Color(0x80, 0x80, 0x80));
		line.setPositionType(PositionTypeEnum.FLOAT);
		detailedBand.addElement(line);
		((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailedBand);

		// Column footer
		band = new JRDesignBand();
		jasperDesign.setColumnFooter(band);

		// Page footer
		band = new JRDesignBand();
		jasperDesign.setPageFooter(band);

		// Summary
		band = new JRDesignBand();
		jasperDesign.setSummary(band);

		return jasperDesign;
	}

}

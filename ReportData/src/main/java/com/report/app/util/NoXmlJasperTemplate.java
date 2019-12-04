package com.report.app.util;

import java.awt.Color;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
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
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.PositionTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

@Service
public class NoXmlJasperTemplate {

	@Autowired
	private DataSource dataSource;

	private void compile() throws JRException {
		long start = System.currentTimeMillis();
		JasperDesign jasperDesign = getJasperDesign();
		JasperCompileManager.compileReportToFile(jasperDesign, "src/main/resources/templates/NoXmlDTJReport.jasper");
		System.err.println("Compile time : " + (System.currentTimeMillis() - start));
	}

	private String fill() throws JRException, SQLException {
		long start = System.currentTimeMillis();
		JRFileVirtualizer virtualizer=new JRFileVirtualizer(5, "D://Temp");
		// Preparing parameters
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ReportTitle", "BO App Error Report");
		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		
		//System.err.println("Filling time : " + (System.currentTimeMillis() - start));
		return JasperFillManager.fillReportToFile("src/main/resources/templates/NoXmlDTJReport.jasper", parameters,
				dataSource.getConnection());
		
	}

	public void exportPdf() throws JRException, SQLException {
		compile();
		String printFileName=fill();
		long start = System.currentTimeMillis();
		JasperExportManager.exportReportToPdfFile(printFileName,"D://DTJReport.pdf");
		System.err.println("PDF creation time : " + (System.currentTimeMillis() - start));
	}
	
	public void exportCsv() throws JRException, SQLException {
		compile();
		String printFileName=fill();
		long start = System.currentTimeMillis();
		File sourceFile = new File(printFileName);

		JasperPrint jasperPrint = (JasperPrint)JRLoader.loadObject(sourceFile);

		File destFile = new File("D://DTJReport.csv");
		
		JRCsvExporter exporter = new JRCsvExporter();
		
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(destFile));
		
		exporter.exportReport();

		System.err.println("CSV creation time : " + (System.currentTimeMillis() - start));
	}

	
	private JasperDesign getJasperDesign() throws JRException {
		// JasperDesign
		JasperDesign jasperDesign = new JasperDesign();
		jasperDesign.setName("NoXmlDTJReport");
		jasperDesign.setPageWidth(595);
		jasperDesign.setPageHeight(842);
		jasperDesign.setColumnWidth(515);
		jasperDesign.setColumnSpacing(0);
		jasperDesign.setLeftMargin(40);
		jasperDesign.setRightMargin(40);
		jasperDesign.setTopMargin(50);
		jasperDesign.setBottomMargin(50);

		// Fonts
		JRDesignStyle normalStyle = new JRDesignStyle();
		normalStyle.setName("Sans_Normal");
		normalStyle.setDefault(true);
		normalStyle.setFontName("DejaVu Sans");
		normalStyle.setFontSize(12f);
		normalStyle.setPdfFontName("Helvetica");
		normalStyle.setPdfEncoding("Cp1252");
		normalStyle.setPdfEmbedded(Boolean.FALSE);
		jasperDesign.addStyle(normalStyle);

		JRDesignStyle boldStyle = new JRDesignStyle();
		boldStyle.setName("Sans_Bold");
		boldStyle.setFontName("DejaVu Sans");
		boldStyle.setFontSize(12f);
		boldStyle.setBold(Boolean.TRUE);
		boldStyle.setPdfFontName("Helvetica-Bold");
		boldStyle.setPdfEncoding("Cp1252");
		boldStyle.setPdfEmbedded(Boolean.FALSE);
		jasperDesign.addStyle(boldStyle);

		JRDesignStyle italicStyle = new JRDesignStyle();
		italicStyle.setName("Sans_Italic");
		italicStyle.setFontName("DejaVu Sans");
		italicStyle.setFontSize(12f);
		italicStyle.setItalic(Boolean.TRUE);
		italicStyle.setPdfFontName("Helvetica-Oblique");
		italicStyle.setPdfEncoding("Cp1252");
		italicStyle.setPdfEmbedded(Boolean.FALSE);
		jasperDesign.addStyle(italicStyle);

		// Parameters
		JRDesignParameter parameter = new JRDesignParameter();
		parameter.setName("ReportTitle");
		parameter.setValueClass(java.lang.String.class);
		jasperDesign.addParameter(parameter);

		

		// Query
		JRDesignQuery query = new JRDesignQuery();
		query.setText("SELECT SEQ_NUM,APP_NO,ERROR_CODE,ERROR_TYPE,ERROR_DESCRIPTION FROM BO_IN_APP_ERRORS"
				+ " WHERE TO_CHAR(DATE_CREATED,'MM/DD/YYYY')='05/19/2019'");
		jasperDesign.setQuery(query);
 
		// Fields
		JRDesignField field = new JRDesignField();
		field.setName("SEQ_NUM");
		field.setValueClass(java.lang.Integer.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("APP_NO");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("ERROR_CODE");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("ERROR_TYPE");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		field = new JRDesignField();
		field.setName("ERROR_DESCRIPTION");
		field.setValueClass(java.lang.String.class);
		jasperDesign.addField(field);

		JRDesignBand band = new JRDesignBand();
		band.setHeight(20);

		// Title
		JRDesignLine line;
		JRDesignTextField textField;
		band = new JRDesignBand();
		band.setHeight(50);
		line = new JRDesignLine();
		line.setX(0);
		line.setY(0);
		line.setWidth(515);
		line.setHeight(0);
		band.addElement(line);
		textField = new JRDesignTextField();
		textField.setBlankWhenNull(true);
		textField.setX(0);
		textField.setY(10);
		textField.setWidth(515);
		textField.setHeight(30);
		textField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
		textField.setStyle(normalStyle);
		textField.setFontSize(18f);
		textField.setExpression(new JRDesignExpression("$P{ReportTitle}"));
		band.addElement(textField);
		jasperDesign.setTitle(band);

		// Column Header
		band = new JRDesignBand();
		band.setHeight(20);
		JRDesignFrame frame = new JRDesignFrame();
		frame.setX(0);
		frame.setY(5);
		frame.setWidth(555);
		frame.setHeight(15);
		frame.setForecolor(new Color(0x33, 0x33, 0x33));
		frame.setBackcolor(new Color(0x33, 0x33, 0x33));
		frame.setMode(ModeEnum.OPAQUE);
		band.addElement(frame);
		JRDesignStaticText staticText = new JRDesignStaticText();
		staticText.setX(0);
		staticText.setY(0);
		staticText.setWidth(80);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
		staticText.setStyle(boldStyle);
		staticText.setText("SEQ_NUM");
		frame.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(80);
		staticText.setY(0);
		staticText.setWidth(60);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("APP_NO");
		frame.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(140);
		staticText.setY(0);
		staticText.setWidth(100);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("ERROR_CODE");
		frame.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(250);
		staticText.setY(0);
		staticText.setWidth(120);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("ERROR_TYPE");
		frame.addElement(staticText);
		staticText = new JRDesignStaticText();
		staticText.setX(370);
		staticText.setY(0);
		staticText.setWidth(140);
		staticText.setHeight(15);
		staticText.setForecolor(Color.white);
		staticText.setBackcolor(new Color(0x33, 0x33, 0x33));
		staticText.setMode(ModeEnum.OPAQUE);
		staticText.setStyle(boldStyle);
		staticText.setText("ERROR_DESRIPTION");
		frame.addElement(staticText);
		jasperDesign.setColumnHeader(band);

		// Detail
		JRDesignBand detailedBand = new JRDesignBand();
		detailedBand.setHeight(20);
		textField = new JRDesignTextField();
		textField.setX(0);
		textField.setY(5);
		textField.setWidth(80);
		textField.setHeight(15);
		textField.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
		textField.setStyle(normalStyle);
		textField.setExpression(new JRDesignExpression("$F{SEQ_NUM}"));
		detailedBand.addElement(textField);
		textField = new JRDesignTextField();
		//textField.setStretchWithOverflow(true);
		textField.setX(80);
		textField.setY(5);
		textField.setWidth(60);
		textField.setHeight(15);
		textField.setPositionType(PositionTypeEnum.FLOAT);
		textField.setStyle(normalStyle);
		textField.setExpression(new JRDesignExpression("$F{APP_NO}"));
		detailedBand.addElement(textField);
		
		textField = new JRDesignTextField();
		//textField.setStretchWithOverflow(true);
		textField.setX(140);
		textField.setY(5);
		textField.setWidth(100);
		textField.setHeight(15);
		textField.setPositionType(PositionTypeEnum.FLOAT);
		textField.setStyle(normalStyle);
		textField.setExpression(new JRDesignExpression("$F{ERROR_CODE}"));
		detailedBand.addElement(textField);
		
		textField = new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setX(250);
		textField.setY(5);
		textField.setWidth(120);
		textField.setHeight(15);
		textField.setPositionType(PositionTypeEnum.FLOAT);
		textField.setStyle(normalStyle);
		textField.setExpression(new JRDesignExpression("$F{ERROR_TYPE}"));
		detailedBand.addElement(textField);
		
		textField = new JRDesignTextField();
		textField.setStretchWithOverflow(true);
		textField.setX(370);
		textField.setY(5);
		textField.setWidth(140);
		textField.setHeight(15);
		textField.setPositionType(PositionTypeEnum.FLOAT);
		textField.setStyle(normalStyle);
		textField.setExpression(new JRDesignExpression("$F{ERROR_DESCRIPTION}"));
		detailedBand.addElement(textField);
		line = new JRDesignLine();
		line.setX(0);
		line.setY(19);
		line.setWidth(555);
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

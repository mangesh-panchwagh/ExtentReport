package extentReport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

//P5 - Log different types of information to the extent reports | Extent Reports |
/*
Text (Bold, Italic)
XML
JOSN
Collection Data (List, Set and Map)
Highlight any message
Exception
*/
public class Chapter5 {

	public static void main(String[] args) throws Exception {
		ExtentReports extentReports = new ExtentReports(); // Like Engine
		File file = new File("report.html"); 
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
		extentReports.attachReporter(sparkReporter);

		/*
		ExtentTest test1 = extentReports.createTest("Test1");
		test1.pass("This is pass");
		*/
		
		// Log text based information
		extentReports.createTest("Text based Test")
			.log(Status.INFO, "INFO 1")
			.log(Status.INFO, "<b>INFO 2</b>")
			.log(Status.INFO, "<i>INFO 3</i>")
			.log(Status.INFO, "<i><b>INFO 4 </b></i>");
			
		// https://www.json.org/example.html
		
	    // Log xml and JSON format data
		String xmlData = "<menu id=\"file\" value=\"File\">\r\n"
				+ "  <popup>\r\n"
				+ "    <menuitem value=\"New\" onclick=\"CreateNewDoc()\" />\r\n"
				+ "    <menuitem value=\"Open\" onclick=\"OpenDoc()\" />\r\n"
				+ "    <menuitem value=\"Close\" onclick=\"CloseDoc()\" />\r\n"
				+ "  </popup>\r\n"
				+ "</menu>";
		
		String jsonData = "{\"menu\": {\r\n"
				+ "  \"id\": \"file\",\r\n"
				+ "  \"value\": \"File\",\r\n"
				+ "  \"popup\": {\r\n"
				+ "    \"menuitem\": [\r\n"
				+ "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\r\n"
				+ "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\r\n"
				+ "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\r\n"
				+ "    ]\r\n"
				+ "  }\r\n"
				+ "}}";
		
		// This will not show xml data and will not shows json data in not stringified format
		// uncomment this to see unformatted data
		
		extentReports
			.createTest("XML Based Test unformatted data")
			.log(Status.INFO, xmlData);
		
		extentReports
		.createTest("JSON Based Test unformatted data")
		.log(Status.INFO, jsonData);
		
		extentReports
		.createTest("XML Based Test formatted data")
		.info(MarkupHelper.createCodeBlock(xmlData, CodeLanguage.XML));
		
		extentReports
		.createTest("JSON Based Test formatted data")
		//.info(MarkupHelper.createCodeBlock(jsonData,CodeLanguage.JSON));
		.log(Status.INFO , MarkupHelper.createCodeBlock(jsonData,CodeLanguage.JSON));
		
		// Log Collection data, List, Set Map
		
		List<String> listData = new ArrayList<>();
		listData.add("Rohit");
		listData.add("Virat");
		listData.add("Sachin");
		
		Map<Integer,String> mapData = new HashMap<>();
		mapData.put(101, "Rohit");
		mapData.put(102, "Virat");
		mapData.put(103, "Sachin");
		
		Set<Integer> setData = mapData.keySet(); 
		
		extentReports
		.createTest("List Based Test")
		.info(MarkupHelper.createOrderedList(listData))
		.info(MarkupHelper.createUnorderedList(listData));
		
		extentReports
		.createTest("Map Based Test")
		.info(MarkupHelper.createOrderedList(mapData))
		.info(MarkupHelper.createUnorderedList(mapData));
		
		extentReports
		.createTest("Set Based Test")
		.info(MarkupHelper.createOrderedList(setData))
		.info(MarkupHelper.createUnorderedList(setData));
		
		// Log the Highlight any message
		extentReports
		.createTest("Highlight log Test")
		.info("This is not a highlighted message")
		.info(MarkupHelper.createLabel("This is a highlighted message", ExtentColor.RED));
		
		// Log the Exception
		// Super class of all exceptions in java is Throwable
		try {
			int i = 5/0;
		} catch (Exception e) {
			extentReports.createTest("Exception Test 1")
			//.info(e)
			.fail(e);
		}
		
		// Customized exception
		Throwable t = new RuntimeException("This is a custom exception");
		extentReports.createTest("Exception Test 2")
		//.info(t)
		.fail(t);
		
		extentReports.flush();
		Desktop.getDesktop().browse(new File("report.html").toURI());

	}
}


package extentReport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
// P8 - Extent report configuration using Java, JSON and XML | Extent Reports |

public class Chapter8 {
	
	public static void main(String[] args) throws Exception {
		ExtentReports extentReports = new ExtentReports(); // Like Engine
		File file = new File("report.html"); 
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
		
		/*
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setReportName("Report Name");
		sparkReporter.config().setDocumentTitle("Document Title");
		sparkReporter.config().setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
		sparkReporter.config().setCss(".badge-primary {background-color: #dc4719");
		sparkReporter.config().setJs("document.getElementsByClassName(\"logo\")[0].style.display='none'");
		*/
		
		ExtentSparkReporterConfig config = sparkReporter.config();
		/*
		config.setTheme(Theme.DARK);
		config.setReportName("Report Name");
		config.setDocumentTitle("Document Title");
		config.setTimeStampFormat("dd-MM-yyyy hh:mm:ss");
		config.setCss(".badge-primary {background-color: #dc4719");
		config.setJs("document.getElementsByClassName(\"logo\")[0].style.display='none'");
		*/
		
		// https://github.com/extent-framework/extentreports-java/tree/master/config
		// For JSON File Load
		sparkReporter.loadJSONConfig(new File ("./src/test/resources/extent-reports-config.json"));
		
		// For XML Load
		//sparkReporter.loadXMLConfig(new File ("./src/test/resources/extent-reports-config.xml"));
		
		extentReports.attachReporter(sparkReporter);
		
		extentReports
		.createTest("Test1","This is test 1 description")
		.assignAuthor("Mangesh")
		.assignCategory("Smoke")
		.assignDevice("Chrome 136")
		.pass("This is pass test");
		
		extentReports
		.createTest("Test2","This is test 2 description")
		.assignAuthor("John")
		.assignCategory("Sanity")
		.assignDevice("Edge 136")
		.fail("This is fail test");
		
		// Change the order of author, category and device. Order does not matter
		extentReports
		.createTest("Test3","This is test 3 description")
		.assignDevice("Firefox 136")
		.assignCategory("Regression")
		.assignAuthor("Reebeca")
		.fail("This is fail test");
		
		// one test case can be the part of more than one category, author and device
		extentReports
		.createTest("Test4","This is test 4 description")
		.assignAuthor("Mangesh")
		.assignAuthor("John")
		.assignCategory("Smoke")
		.assignCategory("Regression")
		.assignDevice("Chrome 136")
		.assignDevice("Chrome 135")
		.pass("This is pass test");		

		// You can provide entire information in a single method also
		extentReports
		.createTest("Test5","This is test 5 description")
		.assignAuthor("Mangesh","Jeevan")
		.assignCategory("Smoke","Regression","Sanity")
		.assignDevice("Chrome 136","Chrome 135","Firefox 136")
		.pass("This is pass test");	
		
		// You can provide entire thing in the form of array also
		extentReports
		.createTest("Test6","This is test 6 description")
		.assignAuthor(new String[] {"Mangesh","Reebeca","Jeevan"})
		.assignCategory(new String[] {"Smoke","Sanity"})
		.assignDevice(new String [] {"Chrome 136","Chrome 135","Firefox 136"})
		.pass("This is pass test");	
		
		extentReports.flush();
		Desktop.getDesktop().browse(new File("report.html").toURI());
	}
}


package extentReport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//P4 - Understand the log levels in extent reports | Extent Reports |
// ExtentReports class will create reports for you
public class Chapter4 {

	public static void main(String[] args) throws Exception {
		ExtentReports extentReports = new ExtentReports(); // Like Engine
		File file = new File("report.html"); 
		//ExtentSparkReporter sparkReporter = new ExtentSparkReporter("E:\\Mangesh\\eclipse-workspace\\ExtentReport\\report.html");
		
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
		extentReports.attachReporter(sparkReporter);
		/*
		ExtentTest test1 = extentReports.createTest("Test 1");
		test1.pass("This is pass");
		*/
		// We use method chaining
		extentReports.createTest("Test 1")
			.log(Status.INFO, "info1")
			.log(Status.INFO, "info2")
			.log(Status.INFO, "info3")
			.log(Status.PASS, "pass")
			.log(Status.WARNING, "warning")
			.log(Status.WARNING, "pass")
			.log(Status.SKIP, "skip")
			.log(Status.FAIL, "fail1")
			.log(Status.FAIL, "fail1");
			
//		ORDER : 
//		FAIL
//		SKIP
//		WARNING
//		PASS
//		INFO
		
		extentReports.flush();
		Desktop.getDesktop().browse(new File("report.html").toURI());

	}
}


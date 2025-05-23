package extentReport;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

// ExtentReports class will create reports for you
public class Chapter3 {

	public static void main(String[] args) throws Exception {
		ExtentReports extentReports = new ExtentReports(); // Like Engine
		File file = new File("report.html"); 
		//ExtentSparkReporter sparkReporter = new ExtentSparkReporter("E:\\Mangesh\\eclipse-workspace\\ExtentReport\\report.html");
		
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
		extentReports.attachReporter(sparkReporter);
		
		ExtentTest test1 = extentReports.createTest("Test 1");
		test1.pass("This is pass");
		
		ExtentTest test2 = extentReports.createTest("Test 2");
		test2.log(Status.FAIL, "This is failed");
		
		//extentReports.createTest("Test 3").log(Status.SKIP, "This is skipped");
		extentReports.createTest("Test 3").skip("This is skipped");
		
		extentReports.flush();
		Desktop.getDesktop().browse(new File("report.html").toURI());
		
		
		
	}
}


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

import io.github.bonigarcia.wdm.WebDriverManager;

//P6 - Attach screenshots to the extent reports | Extent Reports |

/*
You can attach screenshot at
1. Test level
2. Log level

You can Capture Screenshot for
#. Only test failure
#. every test step
#. only end of the test
*/

// Once you capture the screenshot you have to attach it to report
// You cannot log exception under test level, you can only log under log level
public class Chapter6 {
	
	static WebDriver driver;

	public static void main(String[] args) throws Exception {
		ExtentReports extentReports = new ExtentReports(); // Like Engine
		File file = new File("report.html"); 
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
		extentReports.attachReporter(sparkReporter);

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.google.com");
		driver.manage().window().maximize();
		
		String base64Code = captureScreenshot();
		String path = captureScreenshot("Google.jpg");
		
		// Test level screenshot
		
		// Screenshot without title from Base64String
		extentReports
		.createTest("Screenshot Test 1", "This is for attaching the screenshots to the test at test level")
		.info("This is info msg")
		.addScreenCaptureFromBase64String(base64Code);
		
		// Screenshot with title from Base64String
		extentReports
		.createTest("Screenshot Test 2", "This is for attaching the screenshots to the test at test level")
		.info("This is info msg")
		.addScreenCaptureFromBase64String(base64Code,"Google homepage");
		
		// Screenshot with title from Base64String with multiple images
		extentReports
		.createTest("Screenshot Test 3", "This is for attaching the screenshots to the test at test level")
		.info("This is info msg")
		.addScreenCaptureFromBase64String(base64Code,"Google homepage1")
		.addScreenCaptureFromBase64String(base64Code,"Google homepage2")
		.addScreenCaptureFromBase64String(base64Code,"Google homepage3")
		.addScreenCaptureFromBase64String(base64Code,"Google homepage4");
		
		// Screenshot without title from path
		extentReports
		.createTest("Screenshot Test 4", "This is for attaching the screenshots to the test at test level")
		.info("This is info msg")
		.addScreenCaptureFromPath(path);
		
		// Screenshot with title from Path
		extentReports
		.createTest("Screenshot Test 5", "This is for attaching the screenshots to the test at test level")
		.info("This is info msg")
		.addScreenCaptureFromPath(path,"Google homepage");
		
		// Screenshot with title from Path with multiple images
		extentReports
		.createTest("Screenshot Test 6", "This is for attaching the screenshots to the test at test level")
		.info("This is info msg")
		.addScreenCaptureFromPath(path,"Google homepage1")
		.addScreenCaptureFromPath(path,"Google homepage2")
		.addScreenCaptureFromPath(path,"Google homepage3")
		.addScreenCaptureFromPath(path,"Google homepage4")
		.addScreenCaptureFromPath(path,"Google homepage5");
		
		// Log Level Screenshot (For every log event we can attach screenshot)
		// We can add screenshot for log level for fail, skip,warning etc.
		
		// Screenshot without title and with title from Base64String
		extentReports
		.createTest("Screenshot Test 7", "This is for attaching the screenshots to the test at log level")
		.info("This is info msg")
		.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build())
		.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code,"Google homepage").build());
		
		// Screenshot without title and with title from path
		extentReports
		.createTest("Screenshot Test 8", "This is for attaching the screenshots to the test at log level")
		.info("This is info msg")
		.fail(MediaEntityBuilder.createScreenCaptureFromPath(path).build())
		.fail(MediaEntityBuilder.createScreenCaptureFromPath(path,"Google homepage").build());
		
		extentReports
		.createTest("Screenshot Test 9", "This is for attaching the screenshots to the test at log level")
		.info("This is info msg")
		.fail("This is a info msg",MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build())
		.fail("This is a info msg",MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code,"Google homepage").build());
		
		extentReports
		.createTest("Screenshot Test 10", "This is for attaching the screenshots to the test at log level")
		.info("This is info msg")
		.fail("This is a info msg",MediaEntityBuilder.createScreenCaptureFromPath(path).build())
		.fail("This is a info msg",MediaEntityBuilder.createScreenCaptureFromPath(path,"Google homepage").build());
		
		Throwable t = new Throwable("This is throwable exception");
		// Screenshot without title and with title from Base64String with exception details
		extentReports
		.createTest("Screenshot Test 11", "This is for attaching the screenshots to the test at log level")
		.info("This is info msg")
		.fail(t,MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build())
		.fail(t,MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code,"Google homepage").build());
		
		// Screenshot without title and with title from path with exception details
		extentReports
		.createTest("Screenshot Test 12", "This is for attaching the screenshots to the test at log level")
		.info("This is info msg")
		.fail(t,MediaEntityBuilder.createScreenCaptureFromPath(path).build())
		.fail(t,MediaEntityBuilder.createScreenCaptureFromPath(path,"Google homepage").build());
			
		extentReports.flush();
		driver.quit();
		Desktop.getDesktop().browse(new File("report.html").toURI());
	}
	
	// Capturing screenshot storing in base64 and returning to the method. Not storing screenshot here
	public static String captureScreenshot() {
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		String base64Code = ts.getScreenshotAs(OutputType.BASE64);
		System.out.println("Screenshot saved successfully");
		return base64Code;
	}

	// Capture screenshot and store into local machine and return path 
	public static String captureScreenshot(String fileName) {
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./Screenshots/" + fileName);
		
		try {
			FileUtils.copyFile(sourceFile, destFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Screenshot saved successfully");
		return destFile.getAbsolutePath();
	}
}


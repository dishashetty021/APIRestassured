/*package tests;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.methods.HttpRequestBase;
import org.hamcrest.Matcher;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;

public class ExperienceAPI {
	String transactionID = "3F2504E0-4F89-11D3-9A0C-0305E82C3303";
	String RequestID = "3F2504E0-4F89-11D3-9A0C-0305E82C3303";
	String clientID = "10f17ccb2ed44baeac09e852f0835c68";
	String clientSecret = "2C5f53cDd3c845F596C305884bfFc0B0";
	long responseTime=5000;
	@Test
	public void TC_001() throws IOException {		
		
		RestAssured.baseURI = ExcelReader.getMapData("TC_001");
		RestAssured.given().header("X-TRANSACTION-ID", transactionID)
				.header("X-REQUEST-ID", "RequestID").header("client_id", clientID).header("client_secret", clientSecret)
				.queryParams("email", "disha.harish@allica.co.uk").log().all().when().get()
		.then().body("isValid", equalTo(true)).statusCode(200).statusLine("HTTP/1.1 200 OK").log().all();
	}
}*/

package tests;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.methods.HttpRequestBase;
import org.hamcrest.Matcher;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.RequestSpecificationImpl;
import com.jayway.restassured.internal.SpecificationMerger;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.response.ValidatableResponseLogSpec;
import com.jayway.restassured.specification.RequestSpecification;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExperienceAPI {
	ExtentReports extent;
	ExtentTest logger;

	String transactionID = "3F2504E0-4F89-11D3-9A0C-0305E82C3303";
	String RequestID = "3F2504E0-4F89-11D3-9A0C-0305E82C3303";
	String clientID = "10f17ccb2ed44baeac09e852f0835c68";
	String clientSecret = "2C5f53cDd3c845F596C305884bfFc0B0";
	String payload = "";
	int responseTime = 5000;
	Response response;

	@BeforeTest
	public void browser() throws Exception {
		// htmlReporter = new ExtentHtmlReporter("test-output/myExtentReport.html");
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/AllicaExtentReport.html", true);
		extent.addSystemInfo("API_Type", "ExperienceAPI").addSystemInfo("User Name", "Disha Shetty")
				.addSystemInfo("Environment", "SIT");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
	}

	@Test(groups = { "ExperienceAPI", "postive" })
	/* TestCase Description here */
	public void TC_001() throws Exception {
		logger = extent.startTest("TC01 - Valid Email");
		 payload = ExcelReader.getMapData("TC_001")[3];

		if (payload.contains("dynamicAlphaNum")) {
			String dynamicUsername = RandomStringUtils.randomAlphanumeric(8);
			payload = payload.replace("dynamicAlphaNum", dynamicUsername);
			System.out.println(payload);
		}

		RestAssured.baseURI = ExcelReader.getMapData("TC_001")[1];

		RequestSpecification request = RestAssured.given().headers("X-TRANSACTION-ID", transactionID, "X-REQUEST-ID",
				"RequestID", "client_id", clientID, "client_secret", clientSecret).log().all();
		if (ExcelReader.getMapData("TC_001")[0].contains("GET")) {
			response = request.when().get();
		} else if (ExcelReader.getMapData("TC_001")[0].contains("POST")) {
			response = request.contentType("application/json").body(payload).when().post();
		} else if (ExcelReader.getMapData("TC_001")[0].contains("PUT")) {
			response = request.contentType("application/json").body(payload).when().put();
		}
		String extract=response.then().extract().path(ExcelReader.getMapData("TC_001")[4]);
		ExcelWriter.LogResponse(1, response.getBody().asString(), response.getStatusCode(), response.getStatusLine(),
				response.getTime(), extract);
		logActualResponse(response);

		response.then().log().all().statusCode(201).statusLine("HTTP/1.1 201 Created").extract().path(ExcelReader.getMapData("TC_001")[4]);

		logger.log(LogStatus.PASS, "Actual Response Matches the Expected Response");

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}
		extent.endTest(logger);
	}

	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	public void logActualResponse(Response response) {
		logger.log(LogStatus.INFO, "Response Body ----->" + response.getBody().asString());
		logger.log(LogStatus.INFO, "Status Code----------->" + String.valueOf(response.getStatusCode()));
		logger.log(LogStatus.INFO, "Status Line------------>" + response.getStatusLine());
		logger.log(LogStatus.INFO, "Response Time------>" + (Long.toString(response.getTime()) + " milliSeconds"));
	}
}
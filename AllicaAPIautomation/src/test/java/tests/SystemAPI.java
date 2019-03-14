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
import com.jayway.restassured.response.ValidatableResponseLogSpec;
import com.jayway.restassured.specification.RequestSpecification;

public class SystemAPI {
	String transactionID = "3F2504E0-4F89-11D3-9A0C-0305E82C3303";
	String RequestID = "3F2504E0-4F89-11D3-9A0C-0305E82C3303";
	String clientID = "10f17ccb2ed44baeac09e852f0835c68";
	String clientSecret = "2C5f53cDd3c845F596C305884bfFc0B0";
	String ContentType = "application/json";
	String userCode = "APIUSER2";
	String userToken = "00E48C3DE68E2860A907C2686E43998AC26B40D0DB0DB6D96BA1465CF396D1E6";
	int responseTime = 5000;

	@Test(groups = { "SystemAPI", "postive" })
	/* TestCase Description here */
	public void TC_001() throws Exception {

		RestAssured.baseURI = ExcelReader.getMapData("TC_001")[0];

		RequestSpecification request = RestAssured.given().header("X-TRANSACTION-ID", transactionID)
				.header("X-REQUEST-ID", "RequestID").header("client_id", clientID).header("client_secret", clientSecret)
				.header("Content-Type", ContentType).header("userCode", userCode).header("userToken", userToken)
				.body("status:\"0\"").log().all();
		Response response = request.when().patch();

		response.then().log().all().body(null).statusCode(200).statusLine("HTTP/1.1 200 OK");

	//	ExcelWriter.LogResponse(1, response.getBody().asString(), response.getStatusCode(), response.getStatusLine(),
		//		response.getTime());
	}
}

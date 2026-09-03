//package TestLayer;
//
//import static io.restassured.RestAssured.given;
//
//import java.sql.SQLException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONObject;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import BaseLayer.BaseClass;
//import CommonLayer.ConfigReader;
//import CommonLayer.ScreenshotUtils;
//import CommonLayer.WordLogger;
//import PageLayer.OrangeHRMDemoPage;
//import UtilsLayer.DBUtility;
//import UtilsLayer.Log;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//
//public class OrangeHRM extends BaseClass {
//
//    private WebDriver driver;
//    private OrangeHRMDemoPage login;
//
//    // ================== BEFORE EACH TEST ==================
//    @BeforeMethod(alwaysRun = true)
//    public void setup() {
//        // Initialize DB
//        DBUtility.connectToDB();
//        Log.info("Database configuration done successfully");
//
//        // Launch App only for UI tests
//        if (!"REST".equalsIgnoreCase(getBrowser())) {
//            driver = BaseClass.launchApp("OrangeHRMurl");
//            BaseClass.setDriver(driver);
//        }
//    }
//
//    // ================== AFTER EACH TEST ==================
//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        // Close driver only if initialized
//        if (driver != null) {
//            BaseClass.quitDriver();
//        }
//
//        // Close WordLogger (saves Word docs)
//        WordLogger.close();
//    }
//
//    // ================== UI TEST ==================
//    @Test
//    public void loginFunctionality() throws InterruptedException {
//        if (driver == null) return;
//
//        login = new OrangeHRMDemoPage(driver);
//
//        login.logoIsDisplayed();
//        ScreenshotUtils.capture(driver, "Application launched");
//
//        login.enterUsername(ConfigReader.get("username"));
//        Log.info("User entered Username");
//        ScreenshotUtils.capture(driver, "User entered Username");
//
//        login.enterPassword(ConfigReader.get("password"));
//        Log.info("User entered Password");
//        ScreenshotUtils.capture(driver, "User entered Username & Password");
//
//        login.clickOnLoginBtn();
//        Log.info("User clicked on Login button");
//        ScreenshotUtils.capture(driver, "User clicked on Login button");
//
//        Thread.sleep(2000);
//
//        login.clickOnPIMmenu();
//        Log.info("User clicked on PIM option under Menu");
//        ScreenshotUtils.capture(driver, "User clicked on PIM option under Menu");
//    }
//
//    // ================== DATABASE TESTS ==================
//    @Test
//    public void databaseTest() throws Exception {
//        String query = "SELECT * FROM employee";
//        DBUtility.retriveDateFromDB(query);
//    }
//
//    @Test
//    public void databaseTest2() throws SQLException {
//        String query = "SELECT * FROM employee";
//        List<String> resultList = DBUtility.executeQueryToDB(query);
//        Log.info("executeQueryToDB: " + resultList);
//    }
//
//    @Test
//    public void databaseTest3() throws SQLException {
//        String query = "SELECT * FROM employee WHERE id = 99";
//        Map<String, String> resultMap = DBUtility.selectQueryMap(query);
//        resultMap.forEach((k, v) -> Log.info(k + " : " + v));
//        Log.info("Actual first_name from DB = " + resultMap.get("first_name"));
//        Assert.assertEquals(resultMap.get("first_name"), "Smith");
//    }
//
//    @Test
//    public void databaseTest4() throws SQLException {
//        String query = "SELECT * FROM employee";
//        List<Map<String, String>> resultList = DBUtility.selectQueryMapList(query);
//
//        int rowNum = 1;
//        for (Map<String, String> row : resultList) {
//            Log.info("----- DB ROW " + rowNum++ + " -----");
//            row.forEach((k, v) -> Log.info(k + " : " + v));
//        }
//    }
//
//    // ================== REST ASSURED TESTS ==================
//    @Test
//    public void postCall() {
//        BaseClass.initRestAssured();
//
//        Map<String, String> cookies = new HashMap<>();
//        cookies.put("SessionID_1", "S1211");
//        cookies.put("SessionID_2", "S1212");
//
//        Map<String, String> grades = new HashMap<>();
//        grades.put("SSC", "74%");
//        grades.put("HSC", "84%");
//        grades.put("BE", "81%");
//
//        JSONObject json = new JSONObject();
//        json.put("Student Name", "Sudip");
//        json.put("Pan", "BFYPC5805G");
//        json.put("Grades", grades);
//        json.put("Address", Arrays.asList("Shahu nagar", "Behind NilSagar,Vita", "Pin-415311"));
//
//        BaseClass.addCookies(cookies);
//        BaseClass.addRequestBody(json.toString());
//        BaseClass.sendRequest("POST");
//    }
//
//    @Test
//    public void restAssuredTest() {
//        RestAssured.baseURI = "http://localhost:3000";
//        Response response = given()
//                                .log().all()
//                            .when()
//                                .get("/employee/99")
//                            .then()
//                                .log().all()
//                                .statusCode(200)
//                                .extract().response();
//
//        Log.info("Response Body: " + response.asPrettyString());
//    }
//
//    @Test
//    public void customerAPI_GET() {
//        BaseClass.initRestAssured();
//        BaseClass.addQueryParams("id", "10");
//        BaseClass.sendRequest("GET");
//
//        Response response = BaseClass.getResponse();
//        Assert.assertEquals(response.jsonPath().getString("name"), "Jack Thompson");
//    }
//
//    @Test
//    public void customerAPI_PUT() {
//        BaseClass.initRestAssured();
//        // Implement PUT logic here
//    }
//
//}

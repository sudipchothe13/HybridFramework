package CommonLayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    // =====================================================
    // ONE EXTENT REPORT PER BROWSER
    // =====================================================

    private static final Map<String, ExtentReports> extentMap =
            new ConcurrentHashMap<>();

    // =====================================================
    // ONE EXTENT TEST PER THREAD
    // =====================================================

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    // =====================================================
    // COMMON REPORT FOLDER
    // =====================================================

    private static String reportFolder;

    // =====================================================
    // GET REPORT FOLDER
    // =====================================================

    private static synchronized String getReportFolder() {

        if (reportFolder == null) {

            String timestamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(new Date());

            reportFolder =
                    System.getProperty("user.dir")
                    + "/Reports/ExtentReports/"
                    + "Execution_"
                    + timestamp;

            File folder =
                    new File(reportFolder);

            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        return reportFolder;
    }

    // =====================================================
    // GET EXTENT REPORT FOR BROWSER
    // =====================================================

    public static ExtentReports getExtentReports(
            String browser) {

        String browserName =
                formatBrowserName(browser);

        return extentMap.computeIfAbsent(
                browserName,
                b -> createExtentReport(b)
        );
    }

    // =====================================================
    // CREATE EXTENT REPORT
    // =====================================================

    private static ExtentReports createExtentReport(
            String browser) {

        try {

            String reportPath =
                    getReportFolder()
                    + "/"
                    + browser
                    + "_SparkReport.html";

            System.out.println(
                    "Creating Extent Report: "
                    + reportPath
            );

            // =================================================
            // SPARK REPORTER
            // =================================================

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                            reportPath
                    );

            // =================================================
            // SAME BASIC CONFIGURATION AS OLD REPORT
            // =================================================

            spark.config()
                    .setReportName(
                            "UI Test Report"
                    );

            spark.config()
                    .setDocumentTitle(
                            "Automation Report"
                    );

            // =================================================
            // EXTENT REPORT
            // =================================================

            ExtentReports extent =
                    new ExtentReports();

            extent.attachReporter(spark);

            // =================================================
            // SYSTEM INFORMATION
            // =================================================

            extent.setSystemInfo(
                    "Tester",
                    "Sudip Chothe"
            );

            extent.setSystemInfo(
                    "OS",
                    System.getProperty("os.name")
            );

            extent.setSystemInfo(
                    "Java Version",
                    System.getProperty("java.version")
            );

            extent.setSystemInfo(
                    "Browser",
                    browser
            );

            return extent;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to initialize Extent Report for "
                    + browser,
                    e
            );
        }
    }

    // =====================================================
    // CREATE TEST
    // =====================================================

    public static ExtentTest createTest(
            String scenarioName,
            String browser) {

        ExtentReports extent =
                getExtentReports(browser);

        ExtentTest t =
                extent.createTest(
                        scenarioName
                );

        // Browser shown as category
        t.assignCategory(browser);

        test.set(t);

        return t;
    }

    // =====================================================
    // GET CURRENT THREAD TEST
    // =====================================================

    public static ExtentTest getTest() {

        return test.get();
    }

    // =====================================================
    // REMOVE THREAD TEST
    // =====================================================

    public static void removeTest() {

        test.remove();
    }

    // =====================================================
    // FLUSH ALL BROWSER REPORTS
    // =====================================================

    public static synchronized void flushReports() {

        System.out.println(
                "========== FLUSHING EXTENT REPORTS =========="
        );

        for (Map.Entry<String, ExtentReports> entry :
                extentMap.entrySet()) {

            System.out.println(
                    "Flushing: "
                    + entry.getKey()
            );

            entry.getValue().flush();
        }

        System.out.println(
                "========== EXTENT REPORTS FLUSHED =========="
        );
    }

    // =====================================================
    // FORMAT BROWSER NAME
    // =====================================================

    private static String formatBrowserName(
            String browser) {

        if (browser == null ||
                browser.trim().isEmpty()) {

            return "Unknown";
        }

        browser = browser.trim();

        return browser.substring(0, 1).toUpperCase()
                + browser.substring(1).toLowerCase();
    }
}
//----
//package CommonLayer;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//public class ExtentManager {
//
//    // =====================================================
//    // ONE EXTENT REPORT PER BROWSER
//    // =====================================================
//
//    private static final Map<String, ExtentReports> extentMap =
//            new ConcurrentHashMap<>();
//
//    // =====================================================
//    // ONE EXTENT TEST PER THREAD
//    // =====================================================
//
//    private static final ThreadLocal<ExtentTest> test =
//            new ThreadLocal<>();
//
//    // =====================================================
//    // COMMON REPORT FOLDER
//    // =====================================================
//
//    private static String reportFolder;
//
//    // =====================================================
//    // GET REPORT FOLDER
//    // =====================================================
//
//    private static synchronized String getReportFolder() {
//
//        if (reportFolder == null) {
//
//            String timestamp =
//                    new SimpleDateFormat("yyyyMMdd_HHmmss")
//                            .format(new Date());
//
//            reportFolder =
//                    System.getProperty("user.dir")
//                    + "/Reports/ExtentReports/"
//                    + "Execution_"
//                    + timestamp;
//
//            File folder = new File(reportFolder);
//
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//        }
//
//        return reportFolder;
//    }
//
//    // =====================================================
//    // GET EXTENT REPORT FOR BROWSER
//    // =====================================================
//
//    public static ExtentReports getExtentReports(
//            String browser) {
//
//        String browserName =
//                browser.substring(0, 1).toUpperCase()
//                + browser.substring(1).toLowerCase();
//
//        return extentMap.computeIfAbsent(
//                browserName,
//                b -> createExtentReport(b)
//        );
//    }
//
//    // =====================================================
//    // CREATE EXTENT REPORT
//    // =====================================================
//
//    private static ExtentReports createExtentReport(
//            String browser) {
//
//        try {
//
//            String reportPath =
//                    getReportFolder()
//                    + "/"
//                    + browser
//                    + "_SparkReport.html";
//
//            System.out.println(
//                    "Creating Extent Report: "
//                    + reportPath
//            );
//
//            ExtentSparkReporter spark =
//                    new ExtentSparkReporter(
//                            reportPath
//                    );
//
//            spark.config()
//                    .setReportName(
//                            "UI Test Report - " + browser
//                    );
//
//            spark.config()
//                    .setDocumentTitle(
//                            "Automation Report - " + browser
//                    );
//
//            ExtentReports extent =
//                    new ExtentReports();
//
//            extent.attachReporter(spark);
//
//            extent.setSystemInfo(
//                    "Tester",
//                    "Sudip Chothe"
//            );
//
//            extent.setSystemInfo(
//                    "Browser",
//                    browser
//            );
//
//            extent.setSystemInfo(
//                    "OS",
//                    System.getProperty("os.name")
//            );
//
//            extent.setSystemInfo(
//                    "Java Version",
//                    System.getProperty("java.version")
//            );
//
//            return extent;
//
//        } catch (Exception e) {
//
//            throw new RuntimeException(
//                    "Failed to initialize Extent Report for "
//                    + browser,
//                    e
//            );
//        }
//    }
//
//    // =====================================================
//    // CREATE TEST
//    // =====================================================
//
//    public static ExtentTest createTest(
//            String scenarioName,
//            String browser) {
//
//        ExtentReports extent =
//                getExtentReports(browser);
//
//        ExtentTest t =
//                extent.createTest(
//                        scenarioName
//                );
//
//        t.assignCategory(browser);
//
//        test.set(t);
//
//        return t;
//    }
//
//    // =====================================================
//    // GET CURRENT THREAD TEST
//    // =====================================================
//
//    public static ExtentTest getTest() {
//
//        return test.get();
//    }
//
//    // =====================================================
//    // REMOVE THREAD TEST
//    // =====================================================
//
//    public static void removeTest() {
//
//        test.remove();
//    }
//
//    // =====================================================
//    // FLUSH ALL BROWSER REPORTS
//    // =====================================================
//
//    public static synchronized void flushReports() {
//
//        System.out.println(
//                "========== FLUSHING EXTENT REPORTS =========="
//        );
//
//        for (Map.Entry<String, ExtentReports> entry :
//                extentMap.entrySet()) {
//
//            System.out.println(
//                    "Flushing: "
//                    + entry.getKey()
//            );
//
//            entry.getValue().flush();
//        }
//
//        System.out.println(
//                "========== EXTENT REPORTS FLUSHED =========="
//        );
//    }
//}
//---------
//package CommonLayer;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//public class ExtentManager {
//
//    private static ExtentReports extent;
//
//    private static ThreadLocal<ExtentTest> test =
//            new ThreadLocal<>();
//
//    // =====================================================
//    // EXTENT REPORT
//    // =====================================================
//
//    public static synchronized ExtentReports getExtentReports() {
//
//        if (extent == null) {
//
//            try {
//
//                String timestamp =
//                        new SimpleDateFormat(
//                                "yyyyMMdd_HHmmss")
//                                .format(new Date());
//
//                String reportPath =
//                        System.getProperty("user.dir")
//                        + "/Reports/ExtentReport_"
//                        + timestamp
//                        + ".html";
//
//                ExtentSparkReporter spark =
//                        new ExtentSparkReporter(reportPath);
//
//                spark.config()
//                        .setReportName("UI Test Report");
//
//                spark.config()
//                        .setDocumentTitle("Automation Report");
//
//                extent = new ExtentReports();
//
//                extent.attachReporter(spark);
//
//                extent.setSystemInfo(
//                        "Tester",
//                        "Sudip Chothe"
//                );
//
//                extent.setSystemInfo(
//                        "OS",
//                        System.getProperty("os.name")
//                );
//
//                extent.setSystemInfo(
//                        "Java Version",
//                        System.getProperty("java.version")
//                );
//
//            } catch (Exception e) {
//
//                throw new RuntimeException(
//                        "Failed to initialize ExtentReports",
//                        e
//                );
//            }
//        }
//
//        return extent;
//    }
//
//    // =====================================================
//    // CREATE TEST
//    // =====================================================
//
//    public static ExtentTest createTest(
//            String scenarioName,
//            String browser) {
//
//        ExtentTest t =
//                getExtentReports()
//                        .createTest(
//                                scenarioName
//                                + " - "
//                                + browser
//                        );
//
//        t.assignCategory(browser);
//
//        test.set(t);
//
//        return t;
//    }
//
//    // =====================================================
//    // GET CURRENT THREAD TEST
//    // =====================================================
//
//    public static ExtentTest getTest() {
//
//        return test.get();
//    }
//
//    // =====================================================
//    // REMOVE THREAD TEST
//    // =====================================================
//
//    public static void removeTest() {
//
//        test.remove();
//    }
//
//    // =====================================================
//    // FLUSH
//    // =====================================================
//
//    public static synchronized void flushReports() {
//
//        if (extent != null) {
//
//            extent.flush();
//        }
//    }
//}
//--------------
//package CommonLayer;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
///**
// * Unified Extent Reports manager
// * Handles ExtentReports creation, ExtentTest management (thread-safe), and flushing
// */
//public class ExtentManager {
//
//    private static ExtentReports extent;
//    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//
//    // ================= EXTENT REPORTS =================
//    public static ExtentReports getExtentReports() {
//        if (extent == null) {
//            try {
//                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//                String reportPath = System.getProperty("user.dir") + "/Reports/ExtentReport_" + timestamp + ".html";
//
//                ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
//                spark.config().setReportName("UI Test Report");
//                spark.config().setDocumentTitle("Automation Report");
//
//                extent = new ExtentReports();
//                extent.attachReporter(spark);
//                extent.setSystemInfo("Tester", "Sudip Chothe");
//                extent.setSystemInfo("OS", System.getProperty("os.name"));
//                extent.setSystemInfo("Java Version", System.getProperty("java.version"));
//
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to initialize ExtentReports", e);
//            }
//        }
//        return extent;
//    }
//
//    // ================= CREATE / GET / REMOVE TEST =================
//    public static ExtentTest createTest(String testName) {
//        ExtentTest t = getExtentReports().createTest(testName);
//        setTest(t);
//        return t;
//    }
//
//    public static void setTest(ExtentTest t) {
//        test.set(t);
//    }
//
//    public static ExtentTest getTest() {
//        return test.get();
//    }
//
//    public static void removeTest() {
//        test.remove();
//    }
//
//    // ================= FLUSH REPORT =================
//    public static void flushReports() {
//        if (extent != null) {
//            extent.flush();
//        }
//    }
//}

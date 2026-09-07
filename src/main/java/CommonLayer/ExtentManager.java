
package CommonLayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    // ============================================================
    // EXTENT REPORTS - ONE REPORT PER BROWSER
    // ============================================================

    private static final Map<String, ExtentReports> extentMap =
            new ConcurrentHashMap<>();

    // ============================================================
    // EXTENT TEST - THREAD LOCAL
    // ============================================================

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    // ============================================================
    // REPORT FOLDER
    // ============================================================

    private static String reportFolder;

    // ============================================================
    // CREATE REPORT FOLDER
    // ============================================================

    private static synchronized String getReportFolder() {

        if (reportFolder == null) {

            String timestamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(new Date());

            reportFolder =
                    System.getProperty("user.dir")
                    + File.separator
                    + "Reports"
                    + File.separator
                    + "ExtentReports"
                    + File.separator
                    + "Execution_"
                    + timestamp;

            File folder = new File(reportFolder);

            if (!folder.exists()) {

                if (!folder.mkdirs()) {

                    throw new RuntimeException(
                            "Unable to create Extent report directory: "
                            + reportFolder
                    );
                }
            }
        }

        return reportFolder;
    }

    // ============================================================
    // GET EXTENT REPORT FOR BROWSER
    // ============================================================

    public static ExtentReports getExtentReports(String browser) {

        String browserName =
                formatBrowserName(browser);

        return extentMap.computeIfAbsent(
                browserName,
                ExtentManager::createExtentReport
        );
    }

    // ============================================================
    // CREATE EXTENT SPARK REPORT
    // ============================================================

    private static ExtentReports createExtentReport(
            String browser) {

        try {

            String reportPath =
                    getReportFolder()
                    + File.separator
                    + browser
                    + "_SparkReport.html";

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "CREATING EXTENT REPORT"
            );

            System.out.println(
                    "Browser : " + browser
            );

            System.out.println(
                    "Path    : " + reportPath
            );

            System.out.println(
                    "=========================================="
            );

            // ====================================================
            // CREATE SPARK REPORTER
            // ====================================================

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);

            // ====================================================
            // IMPORTANT:
            // RUN REPORT IN OFFLINE MODE
            //
            // This prevents dependency on external CDN
            // CSS / JavaScript resources.
            // ====================================================

            spark.config().setOfflineMode(true);

            // ====================================================
            // UTF-8 ENCODING
            //
            // Prevents characters such as:
            // ✔
            // 🖥️
            // emojis
            // Indian / special characters
            // from appearing as mojibake.
            // ====================================================

            spark.config().setEncoding("UTF-8");

            // ====================================================
            // REPORT NAME
            // ====================================================

            spark.config().setReportName(
                    "UI Automation Execution"
            );

            // ====================================================
            // DOCUMENT TITLE
            // ====================================================

            spark.config().setDocumentTitle(
                    "Automation Test Report"
            );

            // ====================================================
            // REPORT THEME
            // ====================================================

            spark.config().setTheme(
                    Theme.STANDARD
            );

            // ====================================================
            // TIMESTAMP FORMAT
            // ====================================================

            spark.config().setTimeStampFormat(
                    "dd-MMM-yyyy HH:mm:ss"
            );

            // ====================================================
            // CREATE EXTENT REPORTS
            // ====================================================

            ExtentReports extent =
                    new ExtentReports();

            // ====================================================
            // ATTACH SPARK REPORTER
            // ====================================================

            extent.attachReporter(spark);

            // ====================================================
            // SYSTEM INFORMATION
            // ====================================================

            extent.setSystemInfo(
                    "Tester",
                    "Sudip Chothe"
            );

            extent.setSystemInfo(
                    "OS",
                    System.getProperty("os.name")
            );

            extent.setSystemInfo(
                    "OS Version",
                    System.getProperty("os.version")
            );

            extent.setSystemInfo(
                    "Java Version",
                    System.getProperty("java.version")
            );

            extent.setSystemInfo(
                    "Browser",
                    browser
            );

            extent.setSystemInfo(
                    "Execution Mode",
                    "Jenkins"
            );

            System.out.println(
                    "=========================================="
            );

            System.out.println(
                    "EXTENT REPORT CREATED SUCCESSFULLY"
            );

            System.out.println(
                    "Offline Mode : ENABLED"
            );

            System.out.println(
                    "Encoding     : UTF-8"
            );

            System.out.println(
                    "Report       : " + reportPath
            );

            System.out.println(
                    "=========================================="
            );

            return extent;

        } catch (Exception e) {

            System.err.println(
                    "=========================================="
            );

            System.err.println(
                    "EXTENT REPORT CREATION FAILED"
            );

            System.err.println(
                    "Browser : " + browser
            );

            System.err.println(
                    "=========================================="
            );

            e.printStackTrace();

            throw new RuntimeException(
                    "Failed to initialize Extent Report for "
                    + browser,
                    e
            );
        }
    }

    // ============================================================
    // CREATE TEST
    // ============================================================

    public static ExtentTest createTest(
            String scenarioName,
            String browser) {

        if (scenarioName == null ||
                scenarioName.trim().isEmpty()) {

            scenarioName = "Unnamed Scenario";
        }

        ExtentReports extent =
                getExtentReports(browser);

        ExtentTest extentTest =
                extent.createTest(
                        scenarioName
                );

        extentTest.assignCategory(
                formatBrowserName(browser)
        );

        test.set(extentTest);

        return extentTest;
    }

    // ============================================================
    // GET CURRENT TEST
    // ============================================================

    public static ExtentTest getTest() {

        return test.get();
    }

    // ============================================================
    // REMOVE CURRENT TEST
    // ============================================================

    public static void removeTest() {

        test.remove();
    }

    // ============================================================
    // FLUSH ALL REPORTS
    // ============================================================

    public static synchronized void flushReports() {

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "FLUSHING EXTENT REPORTS"
        );

        System.out.println(
                "=========================================="
        );

        if (extentMap.isEmpty()) {

            System.out.println(
                    "WARNING: No Extent Reports found."
            );

            return;
        }

        for (Map.Entry<String, ExtentReports> entry :
                extentMap.entrySet()) {

            String browser =
                    entry.getKey();

            ExtentReports extent =
                    entry.getValue();

            System.out.println(
                    "Flushing : " + browser
            );

            try {

                extent.flush();

                System.out.println(
                        "Successfully flushed : "
                        + browser
                );

            } catch (Exception e) {

                System.err.println(
                        "Failed to flush report for "
                        + browser
                );

                e.printStackTrace();
            }
        }

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "EXTENT REPORTS FLUSHED"
        );

        System.out.println(
                "=========================================="
        );
    }

    // ============================================================
    // FORMAT BROWSER NAME
    // ============================================================

    private static String formatBrowserName(
            String browser) {

        if (browser == null ||
                browser.trim().isEmpty()) {

            return "Unknown";
        }

        browser =
                browser.trim();

        return browser.substring(0, 1).toUpperCase()
                + browser.substring(1).toLowerCase();
    }
}


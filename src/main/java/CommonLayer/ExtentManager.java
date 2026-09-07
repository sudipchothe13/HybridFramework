
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

    private static final Map<String, ExtentReports> extentMap =
            new ConcurrentHashMap<>();

    private static final ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    private static String reportFolder;

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

            File folder = new File(reportFolder);

            if (!folder.exists()) {
                folder.mkdirs();
            }
        }

        return reportFolder;
    }

    public static ExtentReports getExtentReports(String browser) {

        String browserName = formatBrowserName(browser);

        return extentMap.computeIfAbsent(
                browserName,
                b -> createExtentReport(b)
        );
    }

    private static ExtentReports createExtentReport(String browser) {

        try {

            String reportPath =
                    getReportFolder()
                    + "/"
                    + browser
                    + "_SparkReport.html";

            System.out.println(
                    "Creating Extent Report: " + reportPath
            );

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);

            spark.config().setReportName(
                    "UI Automation Execution"
            );

            spark.config().setDocumentTitle(
                    "Automation Test Report"
            );

            spark.config().setTheme(
                    Theme.STANDARD
            );

            spark.config().setTimeStampFormat(
                    "dd-MMM-yyyy HH:mm:ss"
            );

            ExtentReports extent =
                    new ExtentReports();

            extent.attachReporter(spark);

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

    public static ExtentTest createTest(
            String scenarioName,
            String browser) {

        ExtentReports extent =
                getExtentReports(browser);

        ExtentTest t =
                extent.createTest(scenarioName);

        t.assignCategory(browser);

        test.set(t);

        return t;
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void removeTest() {
        test.remove();
    }

    public static synchronized void flushReports() {

        System.out.println(
                "========== FLUSHING EXTENT REPORTS =========="
        );

        for (Map.Entry<String, ExtentReports> entry :
                extentMap.entrySet()) {

            System.out.println(
                    "Flushing: " + entry.getKey()
            );

            entry.getValue().flush();
        }

        System.out.println(
                "========== EXTENT REPORTS FLUSHED =========="
        );
    }

    private static String formatBrowserName(String browser) {

        if (browser == null ||
                browser.trim().isEmpty()) {

            return "Unknown";
        }

        browser = browser.trim();

        return browser.substring(0, 1).toUpperCase()
                + browser.substring(1).toLowerCase();
    }
}


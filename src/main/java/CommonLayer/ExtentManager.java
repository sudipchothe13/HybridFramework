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
                + File.separator
                + "Reports"
                + File.separator
                + "ExtentReports"
                + File.separator
                + "Execution_"
                + timestamp;

        File folder = new File(reportFolder);

        if (!folder.exists() && !folder.mkdirs()) {

            throw new RuntimeException(
                    "Unable to create Extent report directory: "
                    + reportFolder
            );
        }
    }

    return reportFolder;
}

public static ExtentReports getExtentReports(String browser) {

    String browserName = formatBrowserName(browser);

    return extentMap.computeIfAbsent(
            browserName,
            ExtentManager::createExtentReport
    );
}

private static ExtentReports createExtentReport(String browser) {

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

    ExtentTest extentTest =
            extent.createTest(scenarioName);

    extentTest.assignCategory(browser);

    test.set(extentTest);

    return extentTest;
}

public static ExtentTest getTest() {

    return test.get();
}

public static void removeTest() {

    test.remove();
}

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

    for (Map.Entry<String, ExtentReports> entry :
            extentMap.entrySet()) {

        System.out.println(
                "Flushing : " + entry.getKey()
        );

        try {

            entry.getValue().flush();

        } catch (Exception e) {

            System.err.println(
                    "Failed to flush report for "
                    + entry.getKey()
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

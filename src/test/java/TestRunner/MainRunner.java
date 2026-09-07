
package TestRunner;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import BaseLayer.BaseClass;
import CommonLayer.BrowserManager;
import CommonLayer.ExtentManager;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features",

    glue = {
        "stepDefinitions",
        "HooksGUI"
    },

    tags = "@UI",

    plugin = {
        "pretty",
        "summary",
        "rerun:target/rerun-ui.txt"
    }
)
public class MainRunner extends AbstractTestNGCucumberTests {

    // ============================================================
    // PARALLEL CUCUMBER SCENARIO EXECUTION
    // ============================================================

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {

        return super.scenarios();
    }

    // ============================================================
    // SET BROWSER
    // ============================================================

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(
            @Optional("") String browser) {

        // --------------------------------------------------------
        // First priority:
        // Browser supplied through TestNG XML
        // --------------------------------------------------------

        if (browser == null ||
                browser.trim().isEmpty()) {

            browser =
                    System.getProperty(
                            "browser",
                            ""
                    );
        }

        // --------------------------------------------------------
        // Validate browser
        // --------------------------------------------------------

        if (browser == null ||
                browser.trim().isEmpty()) {

            throw new IllegalStateException(
                    "Browser is not configured. "
                    + "Use -Dbrowser for Single Browser mode "
                    + "or configure browser parameter in TestNG XML."
            );
        }

        // --------------------------------------------------------
        // Normalize browser name
        // --------------------------------------------------------

        browser =
                browser.trim().toLowerCase();

        // --------------------------------------------------------
        // Set browser in framework
        // --------------------------------------------------------

        BrowserManager.setBrowser(browser);

        BaseClass.setBrowser(browser);

        // --------------------------------------------------------
        // Format browser name
        // --------------------------------------------------------

        String formattedBrowser =
                browser.substring(0, 1).toUpperCase()
                + browser.substring(1).toLowerCase();

        // --------------------------------------------------------
        // Set Log4j Thread Context
        // --------------------------------------------------------

        ThreadContext.put(
                "browser",
                formattedBrowser
        );

        // --------------------------------------------------------
        // Console information
        // --------------------------------------------------------

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "TESTNG THREAD : "
                + Thread.currentThread().getName()
        );

        System.out.println(
                "BROWSER       : "
                + formattedBrowser
        );

        System.out.println(
                "=========================================="
        );
    }

    // ============================================================
    // TEST ENVIRONMENT SETUP
    // ============================================================

    @BeforeSuite(alwaysRun = true)
    public void setupTestEnvironment() {

        File logDir =
                new File("Logs");

        if (!logDir.exists()) {

            boolean created =
                    logDir.mkdirs();

            if (!created) {

                System.out.println(
                        "WARNING: Unable to create Logs directory."
                );
            }
        }

        flushLogs(logDir);
    }

    // ============================================================
    // FLUSH EXTENT REPORTS
    // ============================================================

    @AfterSuite(alwaysRun = true)
    public void flushExtentReports() {

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "FLUSHING CUSTOM EXTENT REPORTS"
        );

        System.out.println(
                "=========================================="
        );

        ExtentManager.flushReports();

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "CUSTOM EXTENT REPORTS FLUSHED"
        );

        System.out.println(
                "=========================================="
        );
    }

    // ============================================================
    // DELETE OLD LOG FILES
    // ============================================================

    private void flushLogs(
            File logDir) {

        String[] logFiles = {

            "Chrome.log",
            "Firefox.log",
            "Edge.log",
            "RestAssured.log",
            "Default.log"
        };

        for (String fileName : logFiles) {

            File file =
                    new File(
                            logDir,
                            fileName
                    );

            if (file.exists()) {

                boolean deleted =
                        file.delete();

                if (!deleted) {

                    System.out.println(
                            "WARNING: Unable to delete log file: "
                            + file.getAbsolutePath()
                    );
                }
            }
        }
    }

    // ============================================================
    // DISABLE SELENIUM VERBOSE LOGGING
    // ============================================================

    static {

        Logger.getLogger(
                "org.openqa.selenium"
        ).setLevel(Level.OFF);

        Logger.getLogger(
                "org.openqa.selenium.remote"
        ).setLevel(Level.OFF);

        Logger.getLogger(
                "org.openqa.selenium.devtools"
        ).setLevel(Level.OFF);

        Logger.getLogger(
                Logger.GLOBAL_LOGGER_NAME
        ).setLevel(Level.OFF);
    }
}


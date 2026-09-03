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
public class MainRunner
        extends AbstractTestNGCucumberTests {

    // =====================================================
    // PARALLEL EXECUTION
    // =====================================================

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {

        return super.scenarios();
    }

    // =====================================================
    // BROWSER
    // =====================================================

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(
            @Optional("") String browser) {

        // =================================================
        // GET BROWSER FROM TESTNG PARAMETER
        // OR JENKINS SYSTEM PROPERTY
        // =================================================

        if (browser == null || browser.isEmpty()) {

            browser = System.getProperty(
                    "browser",
                    "chrome"
            );
        }

        // =================================================
        // SET BROWSER IN BROWSER MANAGER
        // =================================================

        BrowserManager.setBrowser(browser);

        // =================================================
        // SET BROWSER IN BASE CLASS
        // =================================================

        BaseClass.setBrowser(
                browser.toLowerCase()
        );

        // =================================================
        // SET LOG4J THREAD CONTEXT
        // =================================================

        ThreadContext.put(
                "browser",
                browser.substring(0, 1).toUpperCase()
                        + browser.substring(1).toLowerCase()
        );
    }

    // =====================================================
    // BEFORE SUITE
    // =====================================================

    @BeforeSuite(alwaysRun = true)
    public void setupTestEnvironment() {

        // =================================================
        // CREATE LOG FOLDER
        // =================================================

        File logDir =
                new File("Logs");

        if (!logDir.exists()) {

            logDir.mkdirs();
        }

        // =================================================
        // DELETE OLD LOG FILES
        // =================================================

        flushLogs(logDir);

        // =================================================
        // DEFAULT BROWSER FOR LOGGING
        // =================================================

        ThreadContext.put(
                "browser",
                "Chrome"
        );
    }

    // =====================================================
    // AFTER SUITE
    // =====================================================

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
    }

    // =====================================================
    // DELETE OLD LOG FILES
    // =====================================================

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

                file.delete();
            }
        }
    }

    // =====================================================
    // SUPPRESS SELENIUM / TESTNG CONSOLE NOISE
    // =====================================================

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
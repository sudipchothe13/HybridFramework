package HooksGUI;

import org.openqa.selenium.WebDriver;

import BaseLayer.BaseClass;
import CommonLayer.DriverFactory;
import CommonLayer.ExtentManager;
import CommonLayer.WordLogger;
import CommonLayer.ScreenshotUtils;
import UtilsLayer.Log;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class UIHooks {

    private static ThreadLocal<Scenario> scenarioThreadLocal =
            new ThreadLocal<>();

    // =====================================================
    // GET CURRENT SCENARIO
    // =====================================================

    public static Scenario getScenario() {

        return scenarioThreadLocal.get();
    }

    // =====================================================
    // BEFORE SCENARIO
    // =====================================================

    @Before(order = 1)
    public void beforeScenario(Scenario scenario) {

        // -------------------------------------------------
        // Browser is already set by TestNG Runner
        // -------------------------------------------------

        String browser = BaseClass.getBrowser();

        // -------------------------------------------------
        // Initialize WebDriver
        // -------------------------------------------------

        WebDriver driver =
                DriverFactory.initDriver(browser);

        BaseClass.setDriver(driver);

        // -------------------------------------------------
        // Store Scenario in current Thread
        // -------------------------------------------------

        scenarioThreadLocal.set(scenario);

        // =================================================
        // WORD LOGGER
        // =================================================

        WordLogger.startScenario(
                scenario.getName(),
                browser
        );

        // =================================================
        // EXTENT REPORT
        // =================================================
        //
        // IMPORTANT:
        // This creates/uses a separate Extent report
        // for Chrome / Edge / Firefox.
        //
        // Example:
        //
        // Chrome  -> Chrome_SparkReport.html
        // Edge    -> Edge_SparkReport.html
        // Firefox -> Firefox_SparkReport.html
        //
        // =================================================

        ExtentManager.createTest(
                scenario.getName(),
                browser
        );

        // =================================================
        // BROWSER LAUNCH SCREENSHOT
        // =================================================

        ScreenshotUtils.capture(
                driver,
                "🖥️ Browser launched successfully: "
                + browser.toUpperCase()
        );

        // =================================================
        // START LOG
        // =================================================

        Log.info(
                "========== UI Scenario START =========="
        );

        Log.info(
                "Scenario : "
                + scenario.getName()
        );

        Log.info(
                "Browser  : "
                + browser.toUpperCase()
        );

        Log.info(
                "======================================="
        );
    }

    // =====================================================
    // AFTER SCENARIO
    // =====================================================

    @After(order = 1)
    public void afterScenario(Scenario scenario) {

        WebDriver driver =
                BaseClass.getDriver();

        // =================================================
        // FINAL SCREENSHOT
        // =================================================

        if (driver != null) {

            ScreenshotUtils.capture(
                    driver,
                    scenario.isFailed()
                            ? "Scenario FAILED"
                            : "Scenario PASSED"
            );

            // =================================================
            // QUIT DRIVER
            // =================================================

            driver.quit();
        }

        // =================================================
        // END LOG
        // =================================================

        Log.info(
                "========== UI Scenario END =========="
        );

        // =================================================
        // WORD REPORT
        // =================================================

        WordLogger.endScenario();

        // =================================================
        // DRIVER CLEANUP
        // =================================================

        BaseClass.unloadDriver();

        BaseClass.unloadBrowser();
    }

    // =====================================================
    // CLEAR THREAD LOCALS
    // =====================================================

    @After(order = 0)
    public void afterScenarioClear() {

        // Remove Scenario ThreadLocal
        scenarioThreadLocal.remove();

        // Remove ExtentTest ThreadLocal
        ExtentManager.removeTest();
    }
}
//--------
//package HooksGUI;
//
//import org.openqa.selenium.WebDriver;
//
//import BaseLayer.BaseClass;
//import CommonLayer.DriverFactory;
//import CommonLayer.ExtentManager;
//import CommonLayer.WordLogger;
//import CommonLayer.ScreenshotUtils;
//import UtilsLayer.Log;
//
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//
//public class UIHooks {
//
//    private static ThreadLocal<Scenario> scenarioThreadLocal =
//            new ThreadLocal<>();
//
//    // ================== GET CURRENT SCENARIO ==================
//
//    public static Scenario getScenario() {
//        return scenarioThreadLocal.get();
//    }
//
//    // ================== BEFORE SCENARIO ==================
//
//    @Before(order = 1)
//    public void beforeScenario(Scenario scenario) {
//
//        // Browser already set by TestNG Runner
//        String browser = BaseClass.getBrowser();
//
//        // Initialize WebDriver
//        WebDriver driver =
//                DriverFactory.initDriver(browser);
//
//        BaseClass.setDriver(driver);
//
//        // Store scenario for current thread
//        scenarioThreadLocal.set(scenario);
//
//        // ================= WORD LOGGER =================
//
//        WordLogger.startScenario(
//                scenario.getName(),
//                browser
//        );
//
//        // ================= EXTENT REPORT =================
//
//        ExtentManager.createTest(
//                scenario.getName(),
//                browser
//        );
//
//        // ================= BROWSER SCREENSHOT =================
//
//        ScreenshotUtils.capture(
//                driver,
//                "🖥️ Browser launched successfully: "
//                + browser.toUpperCase()
//        );
//
//        // ================= START LOG =================
//
//        Log.info(
//                "========== UI Scenario START =========="
//        );
//
//        Log.info(
//                "Scenario : " + scenario.getName()
//        );
//
//        Log.info(
//                "Browser  : " + browser.toUpperCase()
//        );
//
//        Log.info(
//                "======================================="
//        );
//    }
//
//    // ================== AFTER SCENARIO ==================
//
//    @After(order = 1)
//    public void afterScenario(Scenario scenario) {
//
//        WebDriver driver =
//                BaseClass.getDriver();
//
//        if (driver != null) {
//
//            ScreenshotUtils.capture(
//                    driver,
//                    scenario.isFailed()
//                            ? "Scenario FAILED"
//                            : "Scenario PASSED"
//            );
//
//            driver.quit();
//        }
//
//        // ================= END LOG =================
//
//        Log.info(
//                "========== UI Scenario END =========="
//        );
//
//        // ================= WORD REPORT =================
//
//        WordLogger.endScenario();
//
//        // ================= DRIVER CLEANUP =================
//
//        BaseClass.unloadDriver();
//        BaseClass.unloadBrowser();
//    }
//
//    // ================== CLEAR THREAD LOCALS ==================
//
//    @After(order = 0)
//    public void afterScenarioClear() {
//
//        scenarioThreadLocal.remove();
//
//        // Also make sure Extent ThreadLocal is cleared
//        ExtentManager.removeTest();
//    }
//}
//--------
//package HooksGUI;
//
//import org.openqa.selenium.WebDriver;
//
//import BaseLayer.BaseClass;
//import CommonLayer.DriverFactory;
//import CommonLayer.ExtentManager;
//import CommonLayer.WordLogger;
//import CommonLayer.ScreenshotUtils;
//import UtilsLayer.Log;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//
//public class UIHooks {
//
//    private static ThreadLocal<Scenario> scenarioThreadLocal = new ThreadLocal<>();
//
//    // ================== GET CURRENT SCENARIO ==================
//    public static Scenario getScenario() {
//        return scenarioThreadLocal.get();
//    }
//
//    // ================== BEFORE SCENARIO ==================
//    @Before(order = 1)
//    public void beforeScenario(Scenario scenario) {
//
//        // Browser already set by TestNG Runner
//        String browser = BaseClass.getBrowser();
//
//        // Initialize WebDriver
//        WebDriver driver = DriverFactory.initDriver(browser);
//        BaseClass.setDriver(driver);
//
//        scenarioThreadLocal.set(scenario);
//
//        WordLogger.startScenario(scenario.getName(), browser);
//        ExtentManager.createTest(scenario.getName());
//
//        ScreenshotUtils.capture(
//                driver,
//                "🖥️ Browser launched successfully: " + browser.toUpperCase()
//        );
//
//        // ===== REQUIRED LOG FORMAT (START) =====
//        Log.info("========== UI Scenario START ==========");
//        Log.info("Scenario : " + scenario.getName());
//        Log.info("Browser  : " + browser.toUpperCase());
//        Log.info("=======================================");
//    }
//
//    // ================== AFTER SCENARIO ==================
//    @After(order = 1)
//    public void afterScenario(Scenario scenario) {
//
//        WebDriver driver = BaseClass.getDriver();
//
//        if (driver != null) {
//            ScreenshotUtils.capture(
//                    driver,
//                    scenario.isFailed() ? "Scenario FAILED" : "Scenario PASSED"
//            );
//            driver.quit();
//        }
//
//        // ===== REQUIRED LOG FORMAT (END) =====
//        Log.info("========== UI Scenario END ==========");
//
//        WordLogger.endScenario();
//        BaseClass.unloadDriver();
//        BaseClass.unloadBrowser();
//    }
//
//    // ================== CLEAR THREAD LOCALS ==================
//    @After(order = 0)
//    public void afterScenarioClear() {
//        scenarioThreadLocal.remove();
//    }
//}

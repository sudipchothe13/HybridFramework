package CommonLayer;

import java.util.Base64;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.cucumber.java.Scenario;

public class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    // =====================================================
    // CAPTURE SCREENSHOT
    // =====================================================

    public static void capture(
            WebDriver driver,
            String message,
            Scenario scenario) {

        if (driver == null) {
            return;
        }

        try {

            // =================================================
            // 1. CAPTURE SCREENSHOT
            // =================================================

            byte[] screenshotBytes =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            String base64 =
                    Base64.getEncoder()
                            .encodeToString(screenshotBytes);

            // =================================================
            // 2. WORD LOGGER
            // =================================================
            // Existing WordLogger remains unchanged
            // =================================================

            WordLogger.writeBase64Screenshot(
                    base64,
                    message
            );

            // =================================================
            // 3. CUSTOM EXTENT REPORT
            // =================================================

            if (ExtentManager.getTest() != null) {

                Status status =
                        (scenario != null && scenario.isFailed())
                                ? Status.FAIL
                                : Status.INFO;

                ExtentManager.getTest().log(
                        status,
                        "✔ " + message,
                        MediaEntityBuilder
                                .createScreenCaptureFromBase64String(
                                        base64
                                )
                                .build()
                );
            }

        } catch (Exception e) {

            // =================================================
            // EXTENT WARNING
            // =================================================

            if (ExtentManager.getTest() != null) {

                ExtentManager.getTest().log(
                        Status.WARNING,
                        "⚠ Screenshot capture failed: "
                                + e.getMessage()
                );
            }
        }
    }

    // =====================================================
    // OVERLOADED METHOD
    // =====================================================

    public static void capture(
            WebDriver driver,
            String message) {

        Scenario scenario =
                HooksGUI.UIHooks.getScenario();

        capture(
                driver,
                message,
                scenario
        );
    }
}
//------
//package CommonLayer;
//
//import java.util.Base64;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.WebDriver;
//import com.aventstack.extentreports.MediaEntityBuilder;
//import com.aventstack.extentreports.Status;
//import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
//import io.cucumber.java.Scenario;
//
//public class ScreenshotUtils {
//
//    private ScreenshotUtils() {
//    }
//
//    /**
//     * Capture screenshot for WordLogger + Extent
//     * ✔ Explicit business log (NOT a feature step)
//     */
//    public static void capture(WebDriver driver, String message, Scenario scenario) {
//
//        if (driver == null) return;
//
//        try {
//            // 1️⃣ Capture screenshot
//            byte[] screenshotBytes =
//                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//
//            String base64 = Base64.getEncoder().encodeToString(screenshotBytes);
//
//            // 2️⃣ WordLogger (UNCHANGED)
//            WordLogger.writeBase64Screenshot(base64, message);
//
//            // 3️⃣ Extent (SINGLE, CLEAN LOG)
//            Status status =
//                    (scenario != null && scenario.isFailed()) ? Status.FAIL : Status.INFO;
//
//            ExtentCucumberAdapter.getCurrentStep().log(
//                    status,
//                    "✔ " + message,
//                    MediaEntityBuilder
//                            .createScreenCaptureFromBase64String(base64)
//                            .build()
//            );
//
//            // ❌ REMOVED scenario.log(message) → this caused duplication
//
//        } catch (Exception e) {
//            if (scenario != null && scenario.isFailed()) {
//                ExtentCucumberAdapter.getCurrentStep()
//                        .log(Status.WARNING, "⚠ Screenshot capture failed: " + e.getMessage());
//            }
//        }
//    }
//
//    /**
//     * Overloaded method
//     */
//    public static void capture(WebDriver driver, String message) {
//        Scenario scenario = HooksGUI.UIHooks.getScenario();
//        capture(driver, message, scenario);
//    }   // ✅ added
//
//}       // ✅ added

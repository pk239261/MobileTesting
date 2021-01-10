package SDETProject.KhanAcademy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class Capability {

	protected static String appPackage;
	protected static String appActivity;
	protected static String deviceName;
	protected static String chromedriverexecutable;
	public AppiumDriverLocalService service;

	// I am creating this method to start my appium through nodejs//this is given by
	// appium server
	public AppiumDriverLocalService startServer() {
		boolean flag = checkifserverisRunning(4723);
		if (!flag) {
			// service = AppiumDriverLocalService.buildDefaultService();
			service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
					.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
					.withAppiumJS(new File(
							"C:\\Users\\MerlinR\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
					.withIPAddress("0.0.0.0").usingPort(4723));
			service.start();
		}
		return service;
	}

	// socket pgm to check whether the port is running
	public static boolean checkifserverisRunning(int port) {
		boolean isserverrunning = false;
		ServerSocket serversocket;
		try {
			serversocket = new ServerSocket(port);
			serversocket.close();
		} catch (Exception e) {
			isserverrunning = true;
		} finally {
			serversocket = null;
		}
		return isserverrunning;
	}

	public static void startEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\main\\java\\Emulator2.bat");
        Thread.sleep(10000);
	}
	
	public static AndroidDriver<AndroidElement> capability(String appPackage, String appActivity, String deviceName,
			String chromedriverexecutable) throws IOException, InterruptedException {

		FileReader fis = new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\global.properties");
		Properties pro = new Properties();
		pro.load(fis);
		appPackage = pro.getProperty("appPackage");
		appActivity = pro.getProperty("appActivity");
		deviceName = pro.getProperty("deviceName");
		if (deviceName.contains("Merlin2")) {
			startEmulator();
		}
		chromedriverexecutable = pro.getProperty("chromedriverexecutable");

		// this line is getting the path of the application
		// File fp = new File("src\\General-Store.apk");
		DesiredCapabilities cap = new DesiredCapabilities();
		// cap.setCapability(MobileCapabilityType.APP, fp.getAbsolutePath());
		cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
		cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appActivity);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriverexecutable);
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"),
				cap);
		return driver;

	}
	
}

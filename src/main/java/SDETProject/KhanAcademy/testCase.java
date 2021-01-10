package SDETProject.KhanAcademy;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import junit.framework.Assert;


public class testCase extends Capability {
	AndroidDriver<AndroidElement> driver;

	@BeforeClass
	public void bc() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(5000);
		service = startServer();
		driver = capability(appPackage, appActivity, deviceName, chromedriverexecutable);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	}

	@AfterClass
	public void ac() {
		service.stop();
	}

	@AfterMethod
	public void am() throws InterruptedException {

		// wait for load home page
		Thread.sleep(2000);

		// verify home page landing after each tc executed successfully
		Assert.assertEquals((driver.findElementsByClassName("android.widget.TextView")).get(3).getText(), "Join class");
	}

	// test case 1 login
	@Test(priority = 0, enabled = true)
	public void login() throws Exception {

		// wait for Ready to start learning? text
		if (driver.findElement(MobileBy.xpath("//*[@text='Dismiss']")).isDisplayed()) {

			// wait for covid warning to load
			Thread.sleep(2000);

			// click dismiss on next warning
			driver.findElement(By.xpath("//*[@text='Dismiss']")).click();

			// wait for covid warning to load
			Thread.sleep(2000);

			// click dismiss on covid warning
			driver.findElement(By.xpath("//*[@text='Dismiss']")).click();

		} else {
			throw new Exception("Unable to load app");
		}
		// click on signin
		driver.findElement(By.xpath("//*[@text='Sign in']")).click();

		// click on sign in
		driver.findElement(By.xpath("//*[@text='Sign in']")).click();

		// enter email id
		driver.findElement(By.xpath("//*[@text='Enter an e-mail address or username']"))
				.sendKeys("pk@yahoo.co.in");

		// enter password
		driver.findElement(By.xpath("//*[@text='Password']")).sendKeys("learning123");

		// wait for sign in button to get enabled
		// Thread.sleep(2000);

		// click on sign in
		driver.findElementByAccessibilityId("Sign in").click();

		// wait for load home page
		Thread.sleep(8000);

	}

	// test case 2 Join class
	@Test(priority = 1, enabled = true)
	public void joinClass() throws InterruptedException {

		// click on join class
		driver.findElement(By.xpath("//*[@text='Join class']")).click();

		// enter valid class code
		driver.findElementByAccessibilityId("e.g. ABC123 or teacher@example.com").sendKeys("pk@gmail.com");

		// click on add
		driver.findElement(By.xpath("//*[@text='ADD']")).click();
		driver.findElement(By.xpath("//*[@text='ADD']")).click();

		// verify valid scenario popup
		Assert.assertEquals("Teacher added", driver.findElement(By.xpath("//*[@text='Teacher added']")).getText());
		// click dismiss on the popup
		driver.findElement(By.xpath("//*[@text='DISMISS']")).click();

		// click on join class
		driver.findElement(By.xpath("//*[@text='Join class']")).click();

		// enter invalid class code
		driver.findElementByAccessibilityId("e.g. ABC123 or teacher@example.com").sendKeys("pkrapid");

		// click on add
		driver.findElement(By.xpath("//*[@text='ADD']")).click();
		driver.findElement(By.xpath("//*[@text='ADD']")).click();

		// verify invalid scenario popup
		Assert.assertEquals("Error", driver.findElement(By.xpath("//*[@text='Error']")).getText());

		// click ok on the popup
		driver.findElement(By.xpath("//*[@text='OK']")).click();

		// click on cross
		driver.findElementByAccessibilityId("Dismiss").click();

	}

	// test case 3 add and remove teachers
	@Test(priority = 2, enabled = true)
	public void manageTeachers() throws InterruptedException {

		driver.findElementByAccessibilityId("Settings").click();

		driver.findElement(By.xpath("//*[@text='Manage teachers']")).click();

		// click on remove symbal
		driver.findElementByAccessibilityId("Remove teacher").click();

		// click on REMOVE
		driver.findElement(By.xpath("//*[@text='REMOVE']")).click();

		// verify No teachers added text
		Assert.assertEquals("No teachers", driver.findElement(By.xpath("//*[@text='No teachers']")).getText());

		driver.findElement(By.xpath("//*[@text='Add teacher']")).click();

		// enter valid teacher
		driver.findElementByAccessibilityId("e.g. ABC123 or teacher@example.com").sendKeys("pk@gmail.com");

		// click on add
		driver.findElement(By.xpath("//*[@text='ADD']")).click();
		driver.findElement(By.xpath("//*[@text='ADD']")).click();

		// verify valid scenario popup
		Assert.assertEquals("Teacher added", driver.findElement(By.xpath("//*[@text='Teacher added']")).getText());

		// click dismiss on the popup
		driver.findElement(By.xpath("//*[@text='DISMISS']")).click();

		// click on cross button
		driver.findElementByAccessibilityId("Dismiss").click();

		driver.pressKey(new KeyEvent(AndroidKey.BACK));

	}

	// test case 4 terms of service
	@Test(priority = 3, enabled = false)
	public void termsOfService() throws InterruptedException {
		driver.findElementByAccessibilityId("Settings").click();

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Terms of service\"))").click();

		Thread.sleep(10000);
		// to move from native app to web in hybride apps use context
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); 
		}

		// switch to web app
		try {
			driver.context("WEBVIEW_org.khanacademy.android");
			System.out.println(" old switch");
		} catch (Exception e) {
			driver.context("WEBVIEW_chrome");
			System.out.println(" new switch");
		}
		Thread.sleep(10000);

		assertTrue(driver.findElement(By.xpath("//*[@text='Khan Academy Terms of Service']")).isDisplayed());

		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(5000);
		driver.context("NATIVE_APP");
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

	}

	// test case 5 enable auto download
	@Test(priority = 4, enabled = true)
	public void enableAutoDownload() throws InterruptedException {

		// click on book marks
		driver.findElementByAccessibilityId("Bookmarks tab").click();
		Thread.sleep(2000);

		// click on download settings
		driver.findElement(By.xpath("//*[@text='Download settings']")).click();

		// enable auto download
		driver.findElementsByClassName("android.widget.Switch").get(0).click();
		Thread.sleep(2000);

		// verify after enabling it
		Assert.assertEquals("ON", driver.findElementsByClassName("android.widget.Switch").get(0).getText());

		// click on back
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

		// goto home
		driver.findElement(MobileBy.id("org.khanacademy.android:id/tab_bar_button_home")).click();

	}

}

package patterns;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSingleton {

	private static WebDriver driver;

	private WebDriverSingleton() {

	}

	public static WebDriver getInstance() {
		if (driver == null) {
			driver = new ChromeDriver();
		}
		return driver;
	}

	public static void closeWebBrowser() {
		if (null != driver) {
			driver.quit();
		}
		driver = null;
	}
}

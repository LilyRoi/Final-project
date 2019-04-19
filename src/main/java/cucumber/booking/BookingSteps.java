package cucumber.booking;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.booking.BookingPage;
import patterns.WebDriverSingleton;

public class BookingSteps

{
	private static final String URL = "https://www.booking.com";
	private BookingPage bookingPage;
	private WebDriver webDriver;
	
	private static final Logger logger = Logger.getLogger(BookingSteps.class);

	public BookingSteps() {		
		webDriver = WebDriverSingleton.getInstance();
		bookingPage = new BookingPage(webDriver);
		logger.info("BookingSteps is created");
	}

	@Given("^I am on main booking page$")
	public void loadMainPage() {
		logger.debug("LoadMainPage");
		webDriver.get(URL);
		webDriver.manage().window().maximize();
	}

	@When("^I select dates and search by Moscow$")
	public void selectDatesAndSearch() {
		logger.info("SelectDatesAndSearch");
		bookingPage.calendar();
		bookingPage.arrivalDate();
		bookingPage.departureDate();
		bookingPage.searchByCity("Moscow");
	}

	@Then("I see there are hotels for required dates")
	public void checkHotelsList() {
		logger.info("checkHotelsList");
		Assert.assertTrue(bookingPage.getHotelList().size() > 0);
	}

	@When("^I select dates and search by Moscow and sort by rating and get first hotel$")
	public void selectDatesAndSearchAndSort() throws Exception {
		logger.fatal("SelectDatesAndSearchAndSort");
		bookingPage.calendar();		
		bookingPage.arrivalDate();
		takeScreenshot("arrivalDate");
		bookingPage.departureDate();
		takeScreenshot("departureDate");
		bookingPage.searchByCity("Moscow");
		bookingPage.sortRaiting();
		takeScreenshot("sortRaiting");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		bookingPage.getHotelList().get(0).click();
	}

	@Then("I see that rating >=9")
	public void checkHotelRaiting() {
		logger.debug("Check rating");
		Assert.assertTrue(bookingPage.getHotelRating() >= 9.0);
	}
	
	@After()
	public void takeScreenshot(Scenario scenario) throws IOException {
		takeScreenshot(scenario.getName());
		WebDriverSingleton.closeWebBrowser();
	}
	
	private void takeScreenshot(String fileName)throws IOException{
		logger.error("Take Screenshot");
		File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\"+fileName +".png"));		
		
	}
		
	}
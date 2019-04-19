package pages.booking;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingPage {
	protected WebDriver driver;
	protected WebDriverWait wait;

	@FindBy(xpath = "//*[@id='ss']")
	private WebElement searchField;

	@FindBy(xpath = "//span[@class='sb-submit-helper']")
	private WebElement searchButton;

	@FindBy(xpath = "//*[@class='xp__dates-inner']")
	private WebElement calendar;

	@FindBy(xpath = "//*[@id=\"ss\"]")
	private WebElement searchCity;

	@FindBy(xpath = ".//td[@data-date=\"2019-04-10\"]")
	private WebElement arrivalDate;

	@FindBy(xpath = ".//td[@data-date=\"2019-04-20\"]")
	private WebElement departureDate;

	@FindBy(xpath = ".//span[@class[contains(.,'sr-hotel__name')]]")
	private List<WebElement> hotelsList;

	@FindBy(xpath = "//*[@id=\"filter_review\"]/div[2]/a[1]")
	private WebElement sortByRaiting;

	@FindBy(xpath = ".//div[@class='bui-review-score__badge']")
	private WebElement ratingHotel;

	public BookingPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 45, 500);
		PageFactory.initElements(driver, this);
	}

	public WebDriver setPropertyWindow() {
		driver.manage().window().maximize();
		return driver;
	}

	public void open(String url) {
		driver.get(url);
	}

	public void searchByCity(String city) {
		searchField.sendKeys(new String[] { city });
		searchField.submit();
	}

	public void clickSearch() {
		searchButton.submit();
	}

	public void calendar() {
		calendar.click();
	}

	public void arrivalDate() {
		arrivalDate.click();
	}

	public void departureDate() {
		departureDate.click();
	}

	public List<WebElement> getHotelList() {
		return hotelsList;
	}

	public void sortRaiting() {
		sortByRaiting.click();
	}

	public Double getHotelRating() {
		return Double.valueOf(ratingHotel.getText().replace(",", "."));

	}
}
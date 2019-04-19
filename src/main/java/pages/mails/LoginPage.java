package pages.mails;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	@FindBy(xpath = ".//*[@id='mailbox:login']")
	WebElement loginField;

	@FindBy(xpath = ".//*[@id='mailbox:password']")
	WebElement passwordFeild;

	@FindBy(xpath = ".//*[@id='mailbox:submit']/input")
	WebElement enterButton;

	@FindBy(xpath = ".//*[@id='PH_logoutLink']")
	WebElement exitLink;

	private WebDriver driver;
	private WebDriverWait wait;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
		PageFactory.initElements(driver, this);
	}

	public void enterLoginAndPassword(String login, String password) {
		loginField.clear();
		loginField.sendKeys(login);
		passwordFeild.clear();
		passwordFeild.sendKeys(password);
	}

	public void clickEnterButton() {
		enterButton.click();
		wait.until(ExpectedConditions.titleContains("Входящие"));
	}

	public boolean isExitLinkPresent() {
		return exitLink.isDisplayed();
	}
}

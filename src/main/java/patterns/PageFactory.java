package patterns;

import org.openqa.selenium.WebDriver;

import pages.mails.LoginPage;
import pages.mails.MailsPage;

public interface PageFactory {
	LoginPage createLoginPage(WebDriver driver);
	MailsPage createMailsPage(WebDriver driver);

}

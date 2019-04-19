package patterns;

import org.openqa.selenium.WebDriver;

import pages.mails.LoginPage;
import pages.mails.MailsPage;

public class MailRuPageFactory implements PageFactory {	

	@Override
	public LoginPage createLoginPage(WebDriver driver) {
		return new LoginPage(driver);
		
	}

	@Override
	public MailsPage createMailsPage(WebDriver driver) {
		return new MailsPage(driver);
	}

}

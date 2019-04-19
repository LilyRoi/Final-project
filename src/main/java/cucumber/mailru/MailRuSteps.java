package cucumber.mailru;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.mails.LoginPage;
import pages.mails.MailsPage;
import patterns.MailRuPageFactory;
import patterns.WebDriverSingleton;
import sql.Credentials;
import sql.JDBC;

public class MailRuSteps {
	
	private static final String URL = "https://www.mail.ru";	
	private WebDriver webDriver;
	
	private LoginPage loginPage;
	private MailsPage mailsPage;
	
	private static Credentials credentials = JDBC.getCredentials();	

	public MailRuSteps() {		
		webDriver = WebDriverSingleton.getInstance();		
		MailRuPageFactory factory = new MailRuPageFactory();
		loginPage = factory.createLoginPage(webDriver);
		mailsPage = factory.createMailsPage(webDriver);		
	}

	@Given("^I am logged in mail.ru$")
	public void loadMainPage() {
		webDriver.get(URL);
		webDriver.manage().window().maximize();		
					
		loginPage.enterLoginAndPassword(credentials.getLogin(),
				credentials.getPassword());
		loginPage.clickEnterButton();
	}
	
	@Then("I see that exit link is presented")
	public void checkExitLinkPresentation() {
		Assert.assertTrue(loginPage.isExitLinkPresent());
	}
	
	@Then("I see that of WriteEmail button is presented")
	public void checkWriteEmailButtonPresentation() {
		Assert.assertTrue(mailsPage.isWriteEmailButtonPresent());
	}	
	
	
	@When("^I send emails$")
	public void testSendMessages() throws Exception {		
		String[] sendToEmails = { "test1@test.com;", "test2@test.com;",
				"test3@test.com" };
		String topic = "Topic";
		String text = "Test text for email.";
		mailsPage.sendMails(Arrays.asList(sendToEmails), topic, text);		
	}
	
	@Then("I see message that mail was send")
	public void checkThatEmailWasSend() {
		String expectedMessage = "Ваше письмо отправлено. Перейти во Входящие";
		Assert.assertEquals(expectedMessage, mailsPage.getMessageSentTitle());
	}
	
	@When("I flagged 3 checkboxes")
	public void flagCheckboxes() throws Exception {
		Assert.assertTrue(mailsPage.checkboxList.size() > 3);
		int numberMarked = 3;
		mailsPage.markFlagOfFirst(numberMarked);
	}

	@Then("I see 3 flagged checkboxes")
	public void checkThatCheckboxesAreFlagged() throws Exception {
		Assert.assertEquals(mailsPage.flagsList.size(), 3);
	}
	
	@When("I remove all flags")
	public void testDeselectAllFlags() throws Exception {		
		mailsPage.removeAllFlag();
	}
	
	@Then("I see that all flags are deselected")
	public void checkThatAllFlagsAreDeselected() throws Exception {
		Assert.assertFalse(mailsPage.flagsList.size() > 0);
	}
	
	@When("I move mail to spam")
	public void moveToSpamFolder() throws Exception {
		mailsPage.moveMailToSpam();		
	}
	
	@Then("I see alert")
	public void testMoveToSpamFolder() throws Exception {		
		Assert.assertTrue(mailsPage.isAddedToSpamAlertPresent());
	}
	
	@When("I go to spam")
	public void goToSpamFolder() throws Exception {		
		mailsPage.goToSpam();		
	}
	
	@When("I return mail from spam")
	public void returnFromSpamFolder() throws Exception {
		mailsPage.returnMailFromSpam();
	}
	
	@Then("Alert 'Move from Spam' presented")
	public void testReturnFromSpamFolder() throws Exception {
		Assert.assertTrue(mailsPage.isMoveFromSpamAlertPresent());
	}
	
	@After()
	public void tearDown(Scenario scenario) throws IOException {
		File scrFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);	
		String name = scenario.getName();		
		FileUtils.copyFile(scrFile, new File("c:\\tmp\\mailru\\"+name +".png"));		
		WebDriverSingleton.closeWebBrowser();
	}


}

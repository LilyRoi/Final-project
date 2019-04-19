package tests.junit;

import java.util.Arrays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.mails.LoginPage;
import pages.mails.MailsPage;
import sql.Credentials;
import sql.JDBC;

public class TestMailRu {

	private WebDriver driver;
	private LoginPage loginPage;
	private MailsPage mailsPage;
	
	private static Credentials credentials = JDBC.getCredentials();	
	

	@Before
	public void before() {
		driver = new ChromeDriver();
		loginPage = new LoginPage(driver);
		mailsPage = new MailsPage(driver);
		driver.get("https://www.mail.ru");				
		loginPage.enterLoginAndPassword(credentials.getLogin(),
				credentials.getPassword());
		loginPage.clickEnterButton();
	}

	@Test
	public void test() throws Exception {	
		//Done
		Assert.assertTrue(loginPage.isExitLinkPresent());
	}
	
	@Test
	public void testSendMessages() throws Exception {
		//Done
		String[] sendToEmails = { "test1@test.com;", "test2@test.com;",
				"test3@test.com" };
		String topic = "Topic";
		String text = "Test text for email.";
		mailsPage.sendMails(Arrays.asList(sendToEmails), topic, text);
		String expectedMessage = "Ваше письмо отправлено. Перейти во Входящие";
		Assert.assertEquals(expectedMessage, mailsPage.getMessageSentTitle());
	}
	
	@Test
	public void testFlagging3Mails() throws Exception {		
		Assert.assertTrue(mailsPage.checkboxList.size() > 3);
		int numberMarked = 3;
		mailsPage.markFlagOfFirst(numberMarked);
		Assert.assertEquals(mailsPage.flagsList.size(), numberMarked);
	}

	@Test
	public void testDeselectAllFlags() throws Exception {		
		mailsPage.removeAllFlag();
		Assert.assertFalse(mailsPage.flagsList.size() > 0);
	}
	
	@Test
	public void testMoveToSpamFolder() throws Exception {
		mailsPage.moveMailToSpam();
		Assert.assertTrue(mailsPage.isAddedToSpamAlertPresent());
	}

	@Test
	public void testReturnFromSpamFolder() throws Exception {		
		mailsPage.goToSpam();
		mailsPage.returnMailFromSpam();
		Assert.assertTrue(mailsPage.isMoveFromSpamAlertPresent());
	}


	@After
	public void tearDown() {
		//driver.quit();
	}
}

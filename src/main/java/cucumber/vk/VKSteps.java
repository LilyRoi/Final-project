package cucumber.vk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import parser.java.model.VKparameters;
import parser.java.runner.SaxRunnerVK;

public class VKSteps {

	HttpClient client = HttpClientBuilder.create().build();
	String version = "5.92";
	static String postId = null;

	@When("^Add post to wall$")
	public void testSendMessages() throws Exception {
		VKparameters parameters = SaxRunnerVK.getVKParameters(0);
		URIBuilder builder = new URIBuilder(
				"https://api.vk.com/method/wall.post?");
		builder.setParameter("access_token", parameters.getAccess_token())
				.setParameter("owner_id", parameters.getOwner_id())
				.setParameter("message", parameters.getMessage())
				.setParameter("v", version);
		HttpGet request = new HttpGet(builder.build());
		HttpResponse response = client.execute(request);
		String result = EntityUtils.toString(response.getEntity());
		postId = getPostId(result);
		System.out.println(postId);
	}

	@Then("Wall contains post with message")
	public void checkThatPostContainsMessage() throws Exception {
		String actualPost = getPost();
		Assert.assertTrue(actualPost.contains("Post 1"));
	}

	@When("^Edit post on wall$")
	public void testEditPost() throws Exception {
		VKparameters vkparameters = SaxRunnerVK.getVKParameters(1);
		URIBuilder builder2 = new URIBuilder(
				"https://api.vk.com/method/wall.edit?");
		builder2.setParameter("access_token", vkparameters.getAccess_token())
				.setParameter("owner_id", "538134391")
				.setParameter("post_id", postId)
				.setParameter("message", vkparameters.getMessage())
				.setParameter("v", version);
		HttpGet request2 = new HttpGet(builder2.build());
		HttpResponse response2 = client.execute(request2);
		System.out.println(EntityUtils.toString(response2.getEntity()));

	}

	@Then("Wall contains edited post")
	public void checkThatPostContainsEditedMessage() throws Exception {
		String actualPost = getPost();
		Assert.assertTrue(actualPost.contains("Post 2"));
	}

	@When("^Delete Post on the wall")
	public void deletePost() throws Exception {
		VKparameters parameters = SaxRunnerVK.getVKParameters(0);
		URIBuilder builder3 = new URIBuilder(
				"https://api.vk.com/method/wall.delete?");
		builder3.setParameter("access_token", parameters.getAccess_token())
				.setParameter("owner_id", parameters.getOwner_id())
				.setParameter("post_id", postId).setParameter("v", version);
		HttpGet request3 = new HttpGet(builder3.build());
		HttpResponse response3 = client.execute(request3);
		System.out.println(EntityUtils.toString(response3.getEntity()));
	}

	@Then("Wall does not contain post")
	public void checkThatPostWasDeleted() throws Exception {
		String actualPost = getPost();
		Assert.assertFalse(actualPost.contains("Post 2"));
	}

	private String getPost() throws Exception {
		System.out.println("PostId = " + postId);
		VKparameters parameters = SaxRunnerVK.getVKParameters(0);
		URIBuilder builder3 = new URIBuilder(
				"https://api.vk.com/method/wall.getById?");
		builder3.setParameter("access_token", parameters.getAccess_token())
				.setParameter("posts", parameters.getOwner_id() + "_" + postId)
				.setParameter("v", version);
		HttpGet request3 = new HttpGet(builder3.build());
		HttpResponse response3 = client.execute(request3);
		String post = EntityUtils.toString(response3.getEntity());
		return post;
	}

	private static String getPostId(String response) {
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(response);
		while (m.find()) {
			return m.group().toString();
		}
		return response;
	}
}

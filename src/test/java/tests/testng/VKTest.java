package tests.testng;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VKTest {

	HttpClient client = HttpClientBuilder.create().build();
	String accessToken = "4c403a1ad1443f463e9813388a06f372e7dfed86ed0652bee8fb82bd7b1f2b274ed71fe27f061d7b413dd";
	String ownerId = "538134391";
	String version = "5.92";
	String postId = null;

	@Test(priority = 1)
	public void testAddPost() throws Exception {
		URIBuilder builder = new URIBuilder(
				"https://api.vk.com/method/wall.post?");
		builder.setParameter("access_token", accessToken)
				.setParameter("owner_id", ownerId)
				.setParameter("message", "Hello from Mary!")
				.setParameter("v", version);
		HttpGet request = new HttpGet(builder.build());
		HttpResponse response = client.execute(request);
		String result = EntityUtils.toString(response.getEntity());
		postId = getPostId(result);

		String actualPost = getPost();
		Assert.assertTrue(actualPost.contains("Hello from Mary!"));
	}

	@Test(priority = 2)
	public void testEditPost() throws Exception {
		URIBuilder builder2 = new URIBuilder(
				"https://api.vk.com/method/wall.edit?");
		builder2.setParameter("access_token", accessToken)
				.setParameter("owner_id", ownerId)
				.setParameter("post_id", postId)
				.setParameter("message", "Hello from Mary Edited!")
				.setParameter("v", version);
		HttpGet request2 = new HttpGet(builder2.build());
		HttpResponse response2 = client.execute(request2);
		System.out.println(EntityUtils.toString(response2.getEntity()));

		String actualPost = getPost();
		Assert.assertTrue(actualPost.contains("Hello from Mary Edited!"));

	}

	@Test(priority = 3)
	public void testDeletePost() throws Exception {
		URIBuilder builder3 = new URIBuilder(
				"https://api.vk.com/method/wall.delete?");
		builder3.setParameter("access_token", accessToken)
				.setParameter("owner_id", ownerId)
				.setParameter("post_id", postId).setParameter("v", version);
		HttpGet request3 = new HttpGet(builder3.build());
		HttpResponse response3 = client.execute(request3);
		System.out.println(EntityUtils.toString(response3.getEntity()));

		String actualPost = getPost();
		Assert.assertFalse(actualPost.contains("Hello from Mary Edited!"));
	}

	private String getPost() throws Exception {
		URIBuilder builder3 = new URIBuilder(
				"https://api.vk.com/method/wall.getById?");
		builder3.setParameter("access_token", accessToken)
				.setParameter("posts", ownerId + "_" + postId)
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

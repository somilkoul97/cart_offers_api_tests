package com.zomato.tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zomato.models.OfferRequest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OfferTest {

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "http://localhost:1080";
	}

	@Test
	public void testApplyFlatXOfferForP1() {
		Response response = applyOffer(1, 1, 200);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("cart_value"), 190);
	}

	@Test
	public void checkFlatXForOneSegment() throws Exception {
		List<String> segments = new ArrayList<>();
		segments.add("p1");
		OfferRequest offerRequest = new OfferRequest(1, "FLATX", 10, segments);

		boolean result = addOffer(offerRequest);
		Assert.assertTrue(result, "Offer should be successfully added");
	}

	@Test
	public void testApplyFlatXPercentOfferForP2() {
		Response response = applyOffer(2, 1, 200);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("cart_value"), 180);
	}

	@Test
	public void testOfferHigherThanCartValue() {
		Response response = applyOffer(1, 1, 5);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("cart_value"), 0);
	}

	@Test
	public void testZeroCartValue() {
		Response response = applyOffer(1, 1, 0);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("cart_value"), 0);
	}

	@Test
	public void testMultipleOffersSequentially() {
		Response response = applyOffer(1, 1, 300);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("cart_value"), 240);
	}

	@Test
	public void testMissingOfferForValidSegment() {
		Response response = applyOffer(4, 2, 200);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("cart_value"), 200);
	}

	@Test
	public void testDecimalValueRounding() {
		Response response = applyOffer(5, 1, 199);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getFloat("cart_value"), 174.13f);
	}

	@Test
	public void testHighCartValueLargeDiscount() {
		Response response = applyOffer(6, 1, 10000);
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getInt("cart_value"), 9500);
	}
	
	@Test
	public void testInvalidSegmentUser() {
	    Response response = applyOffer(999, 1, 200);
	    Assert.assertEquals(response.getStatusCode(), 404);
	}

	private Response applyOffer(int userId, int restaurantId, int cartValue) {
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("user_id", userId);
		requestBody.put("restaurant_id", restaurantId);
		requestBody.put("cart_value", cartValue);

		return RestAssured.given().header("Content-Type", "application/json").body(requestBody)
				.post("/api/v1/cart/apply_offer");
	}

	// ✅ Add the method here
	public boolean addOffer(OfferRequest offerRequest) throws Exception {
		String urlString = "http://localhost:1080/api/v1/offer";
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");

		ObjectMapper mapper = new ObjectMapper();
		String POST_PARAMS = mapper.writeValueAsString(offerRequest);

		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();

		int responseCode = con.getResponseCode();
		//System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//System.out.println("Response Body: " + response.toString());
			return true;
		} else {
			System.out.println("POST request did not work.");
			return false;
		}
	}
}

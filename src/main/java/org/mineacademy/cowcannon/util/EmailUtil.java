package org.mineacademy.cowcannon.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EmailUtil {

	public final static String SENDGRID_KEY = "YOUR_SENDGRID_KEY_HERE";
	private final static String ACTIVECAMPAIGN_API_URL = "YOUR_ACTIVECAMPAIGN_API_URL_HERE";
	private final static String ACTIVECAMPAIGN_API_KEY = "YOUR_ACTIVECAMPAIGN_API_KEY_HERE";

	/**
	 * Add a new contact to ActiveCampaign.
	 * <p>
	 * See https://www.activecampaign.com/api/example.php?call=contact_add for more info.
	 *
	 * @param email
	 * @param firstName
	 * @param lastName
	 * @param tags
	 * @param listId
	 * @return
	 */
	public static String addContact(String email, String firstName, String lastName, String tags, int listId) {
		final Map<String, String> params = new HashMap<>();
		params.put("api_action", "contact_add");
		params.put("api_output", "json");

		final Map<String, String> post = new HashMap<>();
		post.put("email", email);
		post.put("first_name", firstName);
		post.put("last_name", lastName);
		post.put("tags", tags);
		post.put("p[" + listId + "]", java.lang.String.valueOf(listId));
		post.put("status[" + listId + "]", "1");
		post.put("instantresponders[" + listId + "]", "1");

		try {
			final String queryString = encodeParams(params);
			final String postData = encodeParams(post);

			final URL url = new URL(ACTIVECAMPAIGN_API_URL + "/admin/api.php?" + queryString);
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");
			connection.setRequestProperty("API-TOKEN", ACTIVECAMPAIGN_API_KEY);
			connection.setDoOutput(true);

			try (OutputStream output = connection.getOutputStream()) {
				final byte[] bytes = postData.getBytes("UTF-8");

				output.write(bytes, 0, bytes.length);
			}

			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
				final String response = reader.lines().collect(Collectors.joining("\n"));

				return response;
			}

		} catch (final Throwable t) {
			t.printStackTrace();

			return "{}";
		}
	}

	private static String encodeParams(Map<String, String> params) throws UnsupportedEncodingException {
		String result = "";

		for (final Map.Entry<String, String> entry : params.entrySet())
			result += URLEncoder.encode(entry.getKey(), "UTF-8") + '=' + URLEncoder.encode(entry.getValue(), "UTF-8") + '&';

		if (result.endsWith("&"))
			result = result.substring(0, result.length() - 1);

		return result;
	}
}

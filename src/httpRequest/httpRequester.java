package httpRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/* This class is used to make http requests
 * Read more:
 * https://www.baeldung.com/java-http-request
 * */

public class httpRequester {
	
	public String getFeedRss(String urlFeed){
		String feedRssXml = null;
		try{
			URL url = new URL(urlFeed);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK){
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;

				while((line = reader.readLine()) != null){
					response.append(line);
				}
				reader.close();
				feedRssXml = response.toString();
			}
			con.disconnect();
		}
		catch(IOException e){
			e.printStackTrace();
		}

		return feedRssXml;
	}

	public String getFeedReddit(String urlFeed) {
		String feedReeditJson = null;
		try {
            // Create the URL object with the Reddit API endpoint
            URL url = new URL(urlFeed);

            // Open the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Set the User-Agent header to avoid 429 (Too Many Requests) response
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Get the response code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

				// return response.toString();
				feedReeditJson = response.toString();
            } else {
                System.out.println("Request failed. Response Code: " + responseCode);
            }

            // Close the connection
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
		return feedReeditJson;
	}

}
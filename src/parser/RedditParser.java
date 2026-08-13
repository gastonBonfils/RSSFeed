package parser;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;
import feed.Feed;
import feed.Article;
/*
 * Esta clase implementa el parser de feed de tipo reddit (json)
 * pero no es necesario su implemntacion 
 * */

public class RedditParser extends GeneralParser<Feed> {
    public Feed parse(String redditFeedJson) {
        /*
         * La idea del parser es tomar la string del requester y la convertimos a JSON
         * asi se hace mas facil parsear el archivo pues ya esta en formato JSON
         */
        String jsonString = redditFeedJson; // Replace with your JSON string
        Feed feed = new Feed(redditFeedJson);
        
        try {
            JSONObject json = new JSONObject(jsonString);
            JSONArray posts = json.getJSONObject("data").getJSONArray("children");
            
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.getJSONObject(i).getJSONObject("data");
                String title = post.getString("title");
                String text = post.optString("selftext", "");
                String link = post.getString("url");
                long createdUtc = post.getLong("created_utc");

                // Convert unix timestamp (seconds) to date format
                Date date = new java.util.Date(createdUtc * 1000L);
                
                Article article = new Article(title, text, date, link);
                feed.addArticle(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return feed;
    }
}


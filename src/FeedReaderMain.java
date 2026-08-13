import parser.*;
import subscription.*;
import httpRequest.*;
import feed.*;
import java.util.*;
import heuristic.*;

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}

	public static void main(String[] args) {
		System.out.println("************* FeedReader version 1.0 *************");
		if (args.length < 2) {

			// We parse the subscriptions.json file
			SubscriptionParser parser = new SubscriptionParser();
			Subscription subscriptions = parser.parse("config/subscriptions.json");
			
			List<Feed> feeds = new ArrayList<Feed>();

			SingleSubscription sSub;
			httpRequester requester = new httpRequester();
			String rawFeed = null;
			String urlLink;
			Heuristic heuristic = new QuickHeuristic();

			GeneralParser<Feed> feedParser;  

			// We iterate over all subscriptions
			for (int i = 0; i < subscriptions.getSubscriptionsList().size(); i++) {
				sSub = subscriptions.getSingleSubscription(i);

				// We check if the urlType is supported
				if (sSub.getUrlType().equals("rss")) {
					feedParser = new RssParser();
				} else if (sSub.getUrlType().equals("reddit")) {
					feedParser = new RedditParser();
				} else {
					System.out.println("Error: urlType not supported");
					continue;
				}
				
				// We obtain the feed from the url
				for (int j = 0; j < sSub.getUlrParamsSize(); j++) {
					urlLink = sSub.getFeedToRequest(j);
					rawFeed = (sSub.getUrlType().equals("rss")) ? requester.getFeedRss(urlLink) : requester.getFeedReddit(urlLink);
					if (rawFeed == null) {
						System.out.println("Error: request failed");
						continue;
					}

					Feed feed = feedParser.parse(rawFeed);
									
					// We compute the named entities if the user wants it
					if (args.length == 1 && args[0].equals("-ne")) {
						System.out.println("Computing named entities...");
						for(Feed f : feeds){
							for (Article article : feed.getArticleList()){
								article.computeNamedEntities(heuristic);	
								article.matchCategory(heuristic);
								article.matchTheme(heuristic);
								article.printNamedEntities();
							}
						}
					}

					feeds.add(feed);
				}
			}
			for (int i = 0; i < feeds.size(); i++) {
				feeds.get(i).prettyPrint();
			}


		} else {
			printHelp();
		}
	}
}

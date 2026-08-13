# RSS Feed

## How to compile

To compile and run the project use the command
`$ make run`
in the project's root folder.

<!-- To compile and run the project with named entities use the command
`$ make heuristic` -->

## Project design

The skeleton of the assignment was well separated by modules, so it was easy to divide up the tasks. We were able to start directly with the main function, and from there design ideas emerged for each specific module.  
Every time we added a module it was easy to test, since running the already-prepared main gave us direct output.

### Parser implementation

The `GeneralParser` class was implemented as a polymorphic abstract class defined with a single abstract function `Parse`. This class was then extended to implement: - `SubscriptionParser`, which parses a JSON file with the user's subscriptions and returns an object of class `Subscription` - `RssParser`, which parses an `xml` string to generate the articles and return them in a feed as an object of class `Feed` - `RedditParser`, which works similarly to `SubscriptionParser` since Reddit's feed is in `JSON` format. It returns a `Feed` object with the parsed posts

### Requester implementation

We defined the `httpRequester` with one method per type of feed to parse. - `getFeedRss`, which given a url makes a request to the page and returns the xml to be parsed. - `getFeedReddit`, which also takes a url and returns a JSON String to be parsed. This requester was harder to implement since Reddit was giving us error messages due to security issues.

<!-- ### Named entities

This module was the most complicated since the assignment instructions weren't entirely clear; we spent a lot of time without really knowing which direction to take. We started by first defining the variable we would use to represent the Heuristic in `FeedReaderMain.java` like this: `Heuristic heuristic = new QuickHeuristic();` This way we can quickly change the type of heuristic used without having to make major changes to the code.
Then we defined the class and interface hierarchies requested in the assignment. In the `namedEntity` directory the class hierarchies are defined. And in the `temaInter` directory the interface hierarchies are defined.
Once that was done, in the `NamedEntity` class we added a `String tema` field, so that each named entity has a theme in addition to a category.
Following on from this, we implemented two methods to assign theme and category to each entity, which are found in `Article.java`:
`public void matchCategory(Heuristic h)` and `public void matchTheme(Heuristic h)`. The first method accesses the Category dictionary that was defined in the skeleton in `Heuristic.java`, assigns a category to the entity, and initializes an object based on its category. We limited ourselves to defining only the objects that appeared in the dictionary as an example. The second method accesses a dictionary we created below the originally defined one and assigns a theme to the entity. It then initializes objects that have both category and theme defined, simulating multiple inheritance. We made a couple of examples in the `multiple` directory. We also modified the dictionaries so the examples would work correctly.
In some cases we implemented inheritance when we saw that some attributes were repeated across the class hierarchy. -->

package parser;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import feed.Article;
import feed.Feed;
import java.util.Date;


//Feed es lista. Article es single

public class RssParser extends GeneralParser<Feed> {

        public Feed parse(String xmlFile) {
            // Implementar el parseo de XML para RSS utilizando Java DOM
            Feed articles = new Feed(xmlFile);
    
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(new ByteArrayInputStream(xmlFile.getBytes()));
                doc.getDocumentElement().normalize();
    
                NodeList nList = doc.getElementsByTagName("item");
    
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                        String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                        String link = eElement.getElementsByTagName("link").item(0).getTextContent();
                        String pubDate = eElement.getElementsByTagName("pubDate").item(0).getTextContent();
                        SimpleDateFormat formato = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                        Date date = formato.parse(pubDate);
                        //Crea un objeto Article y lo agrega a la lista articles
                        Article article = new Article(title, description, date, link);
                        articles.addArticle(article);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return articles;
        }

    }


package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import heuristic.*;
import entities.*;
import multiple.*;

// This class is a representation of an article

public class Article {
	private String title;
	private String text;
	private Date publicationDate;
	private String link;
	
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	
	
	public Article(String title, String text, Date publicationDate, String link) {
		super();
		this.title = title;
		this.text = text;
		this.publicationDate = publicationDate;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ "]";
	}
	
	
	
	public NamedEntity getNamedEntity(String namedEntity){
		for (NamedEntity n: namedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){				
				return n;
			}
		}
		return null;
	}
	
	public void computeNamedEntities(Heuristic h){
		String text = this.getTitle() + " " +  this.getText();  
			
		String charsToRemove = ".,;:()'!?\n";
		for (char c : charsToRemove.toCharArray()) {
			text = text.replace(String.valueOf(c), "");
		}
			
		for (String s: text.split(" ")) {
			if (h.isEntity(s)){
				NamedEntity ne = this.getNamedEntity(s);
				if (ne == null) {
					String category = h.getCategory(s);
					this.namedEntityList.add(new NamedEntity(s, category,1, null));
				}else {
					ne.incFrequency();
				}
			}
		} 
	}


	public void matchTheme(Heuristic h) {
		for (NamedEntity ne : namedEntityList) {
			String theme = h.getTheme(ne.getName());
			if(theme != null){
				ne.setTheme(h.getTheme(ne.getName()));
				switch(theme){
					case "Politics":
						LastNamePolitics lnp = new LastNamePolitics(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), null, null, null);
						namedEntityList.set(namedEntityList.indexOf(ne), lnp);
						break;
					case "Football":
						LastNameFootball lnf = new LastNameFootball(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), null, null, null);
						namedEntityList.set(namedEntityList.indexOf(ne), lnf);
						break;
					case "Tenis":
						LastNameTenis lnt = new LastNameTenis(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), null, null, null);
						namedEntityList.set(namedEntityList.indexOf(ne), lnt);
						break;
					case "International":
						CountryInternat ci = new CountryInternat(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), 1, null);
						namedEntityList.set(namedEntityList.indexOf(ne), ci);
						break;
					case "Cinema":
						OrgCinema oc = new OrgCinema(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), null, null, null, 1);
						namedEntityList.set(namedEntityList.indexOf(ne), oc);
						break;
					case "OthersP":
						OrgOthersP oop = new OrgOthersP(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), null, null, null, 1);
						namedEntityList.set(namedEntityList.indexOf(ne), oop);
						break;
					default:
						break;
				}
			}
		}
	}

	public void printNamedEntities() {
		for (NamedEntity namedEntity : namedEntityList) {
			System.out.println(namedEntity);
		}
	}


	public void matchCategory(Heuristic h){
        for (NamedEntity ne : namedEntityList) {
            String category = h.getCategory(ne.getName());
			if(category != null){
			ne.setCategory(h.getCategory(ne.getName()));
            switch(category){
                case "Person":
                    PersonEntity np = new PersonEntity(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), null);
                    namedEntityList.set(namedEntityList.indexOf(ne), np);
                    break;
                case "Organization":
                    OrgEntity no = new OrgEntity(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), null, null, null, 1);
                    namedEntityList.set(namedEntityList.indexOf(ne), no);
                    break;
                case "Country":
                    CountryEntity npa = new CountryEntity(ne.getName(), ne.getCategory(), ne.getFrequency(), ne.getTheme(), 1, null);
                    namedEntityList.set(namedEntityList.indexOf(ne), npa);
                    break;
                default:
                    break;
            }
		}
            
        }
    }

	public void prettyPrint() {
		System.out.println("**********************************************************************************************");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Publication Date: " + this.getPublicationDate());
		System.out.println("Link: " + this.getLink());
		System.out.println("Text: " + this.getText());
		System.out.println("**********************************************************************************************");
		
	}
	
	public static void main(String[] args) {
		  Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  );
		 
		  a.prettyPrint();
	}
	
	
}




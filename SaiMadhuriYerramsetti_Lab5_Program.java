import java.sql.*;
import java.util.*;

public class SaiMadhuriYerramsetti_Lab5_Program {

   /* connect to database */
	public static void main(String[] args) throws Exception {
		
		Connection c = null;
		Statement s = null;
		Scanner input = new Scanner(System.in);
		String language = "";
		String year = "";
		String genre_title = "";
		String author_name = "";
		String publisher_name = "";
		String award_name = "";
		int input_option = 0;
		List<String> years = new ArrayList<>(Arrays.asList("1997", "1999", "2016", "2003", "2018", "2017", "1995", "2015", "2009", "2016", "1993", "2001", "2012", "2011"));
		List<String> genre_titles = new ArrayList<>(Arrays.asList("Teenage", "Action", "Romance", "Vampire", "Adult", "Violence", "Comedy", "Supernatural", "Tragedy", "Adventure", "Horror"));
		List<String> author_names = new ArrayList<>(Arrays.asList("Eiichiro", "Masashi", "Masahi", "Takeshi", "Zi", "Ji", "Jin", "Woo", "Shin Hye", "Yuna"));
		List<String> publisher_names = new ArrayList<>(Arrays.asList("Kodansha", "Sharp Point", "Daran", "Kadokawa", "MediaWorks", "WitiComics", "MagGarden", "Shonen Jump", "Tokodo", "Sansai"));
		List<String> award_names = new ArrayList<>(Arrays.asList("Akatsuka", "Internatonal Manga", "Kondansha Manga", "Newtype", "Seiyun", "Seiyuu", "Tezuka", "Tokyo", "Japan Catroonists", "Crunchyroll"));
		
		try {
		
			// Make a connection to db
			try {       
			    Class.forName("org.sqlite.JDBC");
			    c = DriverManager.getConnection("jdbc:sqlite:C:/sqlite/ComicRecommendation.db");
			    if(c == null)
			        throw new Exception("Connection to the database is null", null);
			    c.setAutoCommit(false);
			} 
			catch (Exception e){
			    System.err.println("Problem Encountered!");
			    e.printStackTrace();
			    throw e;
			} 
			System.out.println("Opened database successfully.");
		    System.out.println("Welcome to Comic recommendation application!");
			
			while(input_option != 11) {
				//Sql Queries
				String query1 = "SELECT COUNT(*) AS Count FROM Comic WHERE PublishedDate LIKE ?"; 
			    String query2a = "SELECT Comic.Id, Comic.Title, ComicUser.Rating FROM Comic, ComicUser, User WHERE Comic.Id = ComicId AND Rating = (SELECT MAX(Rating) FROM ComicUser) AND User.Id = UserId";
			    String query2b = "SELECT Comic.Id, Comic.Title, ComicUser.Rating FROM Comic, ComicUser, User WHERE Comic.Id = ComicId AND Rating = (SELECT MIN(Rating) FROM ComicUser) AND User.Id = UserId";
			    String query3 = " SELECT * FROM Comic WHERE Language=? AND Translated = TRUE";
			    String query4 = "SELECT Comic.Id, Comic.Title, Genre.GenreTitle FROM Comic, ComicGenre, Genre WHERE Comic.Id = ComicId AND GenreTitle = ? AND Genre.Id = GenreId";
			    String query5 = "SELECT Comic.Id, Comic.Title, Genre.GenreTitle FROM Comic, ComicGenre, Genre WHERE Comic.Id = ComicId AND (GenreTitle <> 'Adult'  AND GenreTitle <> 'Violence') AND Genre.Id = GenreId";
			    String query6 = "SELECT Comic.Id, Comic.Title, Genre.GenreTitle FROM Comic, ComicGenre, Genre WHERE (Comic.Id = ComicId AND Comic.Animated = 'Yes') AND GenreTitle = ? AND    Genre.Id = GenreId";
			    String query7 = "SELECT Comic.Id, Comic.title FROM Author, ComicAuthor, Comic WHERE Author.BestComicWrittenId = ComicId AND    AuthorId = (SELECT Author.Id FROM Author, Person WHERE  Author.Id = AuthorId AND Person.FirstName = ?) AND Comic.Id = ComicId";
			    String query8 = "SELECT  Comic.Id, Comic.Title, Genre.GenreTitle FROM Comic, ComicGenre, Genre WHERE (Comic.Id = ComicId AND Comic.Status = 'Completed') AND GenreTitle = ? AND Genre.Id = GenreId";
			    String query9 = "SELECT Comic.Id, Comic.Title, Comic.Language, Publisher.Name FROM Comic, ComicPublisher, Publisher WHERE  Comic.Id = ComicId AND Comic.Language = 'Japanese' AND Publisher.Id = PublisherId AND Publisher.Name = ?";
			    String query10 = "SELECT Comic.Id, Comic.Title FROM Comic, ComicArtist, Artist WHERE Comic.Id = ComicId AND ArtistId IN (SELECT Artist.Id FROM Artist, ArtistAward, Award WHERE  Artist.Id = ArtistId  AND Award.AwardTitle = ? AND Award.Id = AwardId) AND Artist.Id = ArtistId";
			    
				// Getting String input
		    	System.out.println(">> Enter 1 to check number of comics published in a particular year");
		    	System.out.println(">> Enter 2 to check highest or lowest rated comics");
		    	System.out.println(">> Enter 3 to check which comics of a particular language are translated to English");
		    	System.out.println(">> Enter 4 to obtain comics with a particular genre");
		    	System.out.println(">> Enter 5 to obtain comics that are appropriate for all age groups");
		    	System.out.println(">> Enter 6 to check which comics of a particular genre are adopted for producing animated series");
		    	System.out.println(">> Enter 7 to obtain best comic written by an author");
		    	System.out.println(">> Enter 8 to obtain completed comics of a particular genre");
		    	System.out.println(">> Enter 9 to check Japanese comics published by a particular publishing company");
		    	System.out.println(">> Enter 10 to obtain comics drawn by an award winning artists");
		    	System.out.println(">> Enter 11 to exit the application");	    	
		    	
			    s = c.createStatement();
		    	
			    //Get results after execution of queries based of user input
		    	int query_option = input.nextInt();
		    	input_option = query_option;

		    	if(query_option>=1 & query_option<=11) {
		    		System.out.println("You entered " + query_option);
		    		if(query_option==1) {
		    			System.out.println(">> Please enter the published year of the comics that you want to check, out of following:");
		    			System.out.println("1997\n1999\n2016\n2003\n2018\n2017\n1995\n2015\n2009\n1993\n2001\n2012\n2011");
		    			String query1_option = input.next();
		    			if(years.contains(query1_option)) {
		    				year = query1_option + "%";
		    				PreparedStatement ps1 = c.prepareStatement(query1);
		    				ps1.setString(1, year);
		    				ResultSet rs1 = ps1.executeQuery();	
		    				if(!rs1.isBeforeFirst()) {
		    					System.out.println(">> There are no records for this input, please enter another input");
		    				}
		    			    while (rs1.next()) {		    	
		    			    	int count = rs1.getInt("Count");
		    			    	System.out.println("_".repeat(60));
		    			    	System.out.println("The count of comics is " + count);
		    			    	System.out.println("_".repeat(60));
		    			    }
		    			    ps1.close();
		    				
		    			} else {
		    				System.out.println(">> Please enter a valid year");
		    			}
		    		}else if(query_option==2) {
		    			System.out.println(">> Please enter 1 if you want highest rated comics or\n >> 2 if you want lowest rated comics");
		    			int query2_option = input.nextInt();
		    			if(query2_option==1) {
		    				ResultSet rs2a = s.executeQuery(query2a);
		    				if(!rs2a.isBeforeFirst()) {
		    					System.out.println(">> There are no records for this input, please enter another input");
		    				}
		    				System.out.println("_".repeat(40));
	    		            System.out.println("Id" + "\t" + "Title" + "\t" + "Rating");
	    		            System.out.println("_".repeat(40));
		    				while (rs2a.next()) {		    	
		    			    	int id = rs2a.getInt("Id");
		    		            String title = rs2a.getString("Title");
		    		            int rating = rs2a.getInt("Rating");
		    		            System.out.println("_".repeat(40));
		    		            System.out.println(id + "\t" + title + "\t" + rating);
		    		            System.out.println("_".repeat(40));
		    				}
		    			} else if(query2_option==2) {
		    				ResultSet rs2b = s.executeQuery(query2b);
		    				if(!rs2b.isBeforeFirst()) {
		    					System.out.println(">> There are no records for this input, please enter another input");
		    				}
		    				System.out.println("_".repeat(40));
	    		            System.out.println("Id" + "\t" + "Title" + "\t" + "Rating");
	    		            System.out.println("_".repeat(40));
		    				while (rs2b.next()) {		    	
		    			    	int id = rs2b.getInt("Id");
		    		            String title = rs2b.getString("Title");
		    		            int rating = rs2b.getInt("Rating");
		    		            System.out.println("_".repeat(40));
		    		            System.out.println(id + "\t" + title + "\t" + rating);
		    		            System.out.println("_".repeat(40));
		    				}
		    			} else {
		    				System.out.println(">> Please select a valid option");
		    			}
		    		}else if(query_option==3){
		    			System.out.println(">> Please enter 1 if you want English translated Japanese comics or");
		    			System.out.println(">> Please enter 2 if you want English translated Chinese comics or");
		    			System.out.println(">> Please enter 3 if you want English translated Korean comics");
		    			int query3_option = input.nextInt();
		    			if(query3_option==1) {
		    				language = "Japanese";
		    				System.out.println("You entered " + query3_option  + ":" + language);
		    			}else if(query3_option==2) {
		    				language = "Chinese";
		    				System.out.println("You entered " + query3_option  + ":" + language);
		    			}else if(query3_option==3) {
		    				language = "Korean";
		    				System.out.println("You entered " + query3_option  + ":" + language);
		    			}else {
		    				System.out.println(">> Please enter a valid option");
		    			}
		    			if(language!="") {
		    			    PreparedStatement ps3 = c.prepareStatement(query3);
		    				ps3.setString(1, language);
		    				ResultSet rs3 = ps3.executeQuery();	
		    				if(!rs3.isBeforeFirst()) {
		    					System.out.println(">> There are no records for this input, please enter another input");
		    				}
		    				System.out.println("_".repeat(120));
	    		            System.out.println("Id" + "\t" + "Title" + "\t" + "PublishedDate" + "\t" + "Volumes" +
	    		                               "\t" + "Animated" + "\t" + "Translated" + "\t" + "ComicLanguage" +
	    		                               "\t" + "Status" + "\t" + "Type" + "\t" + "Sequelid");
	    		            System.out.println("_".repeat(120));
		    			    while (rs3.next()) {		    	
		    			    	int id = rs3.getInt("Id");
		    		            String title = rs3.getString("Title");
		    		            String publishedDate = rs3.getString("PublishedDate");
		    		            int volumes = rs3.getInt("Volumes");
		    		            String animated = rs3.getString("Animated");
		    		            boolean translated = rs3.getBoolean("Translated");
		    		            String comic_language = rs3.getString("Language");
		    		            String status = rs3.getString("Status");
		    		            String type = rs3.getString("Type");
		    		            int sequelid = rs3.getInt("SequelId");
		    		            System.out.println("_".repeat(120));
		    		            System.out.println(id + "\t" + title + "\t" + publishedDate + "\t" + volumes +
		    		                               "\t" + animated + "\t" + translated + "\t" + comic_language +
		    		                               "\t" + status + "\t" + type + "\t" + sequelid);
		    		            System.out.println("_".repeat(120));
		    		        }
		    			    ps3.close();
		    			}
		    		}else if(query_option==4 | query_option==6 | query_option==8){
		    			System.out.println(">> Please enter a genre of the comics that you wish to obtain from the below options:");
		    			System.out.println("Teenage\nAction\nRomance\nVampire\nAdult\nViolence\nComedy\nSupernatural\nTragedy\nAdventure\nHorror");
		    			String genre_query_option = input.next();
		    			if(genre_titles.contains(genre_query_option)) {
		    				genre_title = genre_query_option;
		    				if(query_option==4) {
		    					PreparedStatement ps4 = c.prepareStatement(query4);
		    					ps4.setString(1, genre_title);
		    					ResultSet rs4 = ps4.executeQuery();	
		    					if(!rs4.isBeforeFirst()) {
			    					System.out.println(">> There are no records for this input, please enter another input");
			    				}
		    					System.out.println("_".repeat(40));
		    		            System.out.println("Id" + "\t" + "Title" + "\t" + "GenreTitle");
		    		            System.out.println("_".repeat(40));
		    					while (rs4.next()) {		    	
		    						int id = rs4.getInt("Id");
		    						String title = rs4.getString("Title");
		    						String genretitle = rs4.getString("GenreTitle");
		    						System.out.println("_".repeat(40));
		    						System.out.println(id + "\t" + title + "\t" + genretitle);
		    						System.out.println("_".repeat(40));
		    					}	
		    					ps4.close();
		    					//c.close();
		    				} else if(query_option==6) {
		    					PreparedStatement ps6 = c.prepareStatement(query6);
		    					ps6.setString(1, genre_title);
		    					ResultSet rs6 = ps6.executeQuery();	 
		    					if(!rs6.isBeforeFirst()) {
			    					System.out.println(">> There are no records for this input, please enter another input");
			    				}
		    					System.out.println("_".repeat(40));
		    		            System.out.println("Id" + "\t" + "Title" + "\t" + "GenreTitle");
		    		            System.out.println("_".repeat(40));
		    					while (rs6.next()) {		    	
		    						int id = rs6.getInt("Id");
		    						String title = rs6.getString("Title");
		    						String genretitle = rs6.getString("GenreTitle");
		    						System.out.println("_".repeat(40));
		    						System.out.println(id + "\t" + title + "\t" + genretitle);
		    						System.out.println("_".repeat(40));
		    					}	
		    				} else {
		    					PreparedStatement ps8 = c.prepareStatement(query8);
		    					ps8.setString(1, genre_title);
		    					ResultSet rs8 = ps8.executeQuery();	 
		    					if(!rs8.isBeforeFirst()) {
			    					System.out.println(">> There are no records for this input, please enter another input");
			    				}
		    					System.out.println("_".repeat(40));
		    		            System.out.println("Id" + "\t" + "Title" + "\t" + "GenreTitle");
		    		            System.out.println("_".repeat(40));
		    					while (rs8.next()) {	
		    						int id = rs8.getInt("Id");
		    						String title = rs8.getString("Title");
		    						String genretitle = rs8.getString("GenreTitle");
		    						System.out.println("_".repeat(40));
		    						System.out.println(id + "\t" + title + "\t" + genretitle);
		    						System.out.println("_".repeat(40));
		    					}	    					
		    				}
		    			} else {
		    				System.out.println(">> Please enter a valid genre");
		    			}
		    		}else if(query_option==5){
		    			ResultSet rs5 = s.executeQuery(query5); 
		    			System.out.println("_".repeat(60));
    		            System.out.println("Id" + "\t" + "Title" + "\t" + "GenreTitle");
    		            System.out.println("_".repeat(60));
	    				while (rs5.next()) {		    	
	    					int id = rs5.getInt("Id");
							String title = rs5.getString("Title");
							String genretitle = rs5.getString("GenreTitle");
							System.out.println("_".repeat(60));
							System.out.println(id + "\t" + title + "\t" + genretitle);
							System.out.println("_".repeat(60));
	    				}	    						
		    		}else if(query_option==7){
		    			System.out.println(">> Please Enter the author name of the best comic that you wish to obtain from the below options:");
		    			System.out.println("Eiichiro\nMasashi\nMasahi\nTakeshi\nZi\nJi\nJin\nWoo\nShin Hye\nYuna");
		    			String query7_option = input.next();
		    			if(author_names.contains(query7_option)) {
		    				author_name = query7_option;
		    				PreparedStatement ps7 = c.prepareStatement(query7);
		    				ps7.setString(1, author_name);
		    				ResultSet rs7 = ps7.executeQuery();	 
		    				if(!rs7.isBeforeFirst()) {
		    					System.out.println(">> There are no records for this input, please enter another input");
		    				}
		    				System.out.println("_".repeat(30));
	    		            System.out.println("Id" + "\t" + "Title");
	    		            System.out.println("_".repeat(30));
		    				while (rs7.next()) {	
		    					int id = rs7.getInt("Id");
		    					String title = rs7.getString("Title");
		    					System.out.println("_".repeat(30));
		    					System.out.println(id + "\t" + title);
		    					System.out.println("_".repeat(30));
		    				}
		    			} else {
		    				System.out.println(">> Please enter a valid author name");
		    			}
		    		}else if(query_option==9){
		    			System.out.println(">> Please Enter the publisher name of the comics that you wish to obtain from the below options:");
		    			System.out.println("Kodansha\nSharp Point\nDaran\nKadokawa\nMediaWorks\nWitiComics\nMagGarden\nShonen Jump\nTokodo\nSansai");
		    			String query9_option = input.next();
		    			if(publisher_names.contains(query9_option)) {
		    				publisher_name = query9_option;
		    				PreparedStatement ps9 = c.prepareStatement(query9);
		    				ps9.setString(1, publisher_name);
		    				ResultSet rs9 = ps9.executeQuery();	 
		    				if(!rs9.isBeforeFirst()) {
		    					System.out.println(">> There are no records for this input, please enter another input");
		    				}
		    				System.out.println("_".repeat(60));
	    		            System.out.println("Id" + "\t" + "Title" + "\t" + "Language" + "\t" + "PublisherName");
	    		            System.out.println("_".repeat(60));
		    				while (rs9.next()) {	
		    					int id = rs9.getInt("Id");
		    					String title = rs9.getString("Title");
		    					String Language = rs9.getString("Language");
		    					String publishername = rs9.getString("Name");
		    					System.out.println("_".repeat(60));
		    					System.out.println(id + "\t" + title + "\t" + Language + "\t" + publishername);
		    					System.out.println("_".repeat(60));
		    				}	
		    			} else {
		    				System.out.println("Please enter a valid publisher name");
		    			}	    			
		    		}else if(query_option==10){
		    			System.out.println(">> Please enter the award name of the artist from the below options:");
		    			System.out.println("Akatsuka\nInternatonal Manga\nKondansha Manga\nNewtype\nSeiyun\nSeiyuu\nTezuka\nTokyo\nJapan Catroonists\nCrunchyroll");
		    			String query10_option = input.next();
		    			if(award_names.contains(query10_option)) {
		    				award_name = query10_option;
		    				PreparedStatement ps10 = c.prepareStatement(query10);
		    				ps10.setString(1, award_name);
		    				ResultSet rs10 = ps10.executeQuery();	 
		    				if(!rs10.isBeforeFirst()) {
		    					System.out.println(">> There are no records for this input, please enter another input");
		    				}
		    				System.out.println("_".repeat(40));
	    		            System.out.println("Id" + "\t" + "Title");
	    		            System.out.println("_".repeat(40));
		    				while (rs10.next()) {	
		    					int id = rs10.getInt("Id");
		    					String title = rs10.getString("Title");
		    					System.out.println("_".repeat(40));
		    					System.out.println(id + "\t" + title);
		    					System.out.println("_".repeat(40));
		    				}	
		    			} else {
		    				System.out.println(">> Please enter a valid award name");
		    			}
		    		}else if(query_option==11) {
		    			System.out.println(">> Thank you for using the comic recommendation application!");
		    		}
		    		
		    	} else {
		    		System.out.println(">> Please enter a valid option");
		    	}	
			}
			c.close();
		  		    		    
		}catch(Exception e){
		    e.printStackTrace();
		    throw e;
		}
		System.out.println("DB closed successfully.");
	}
}

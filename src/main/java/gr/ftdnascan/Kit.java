package gr.ftdnascan;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

public class Kit {
	
	public String rawGroup; // 2. ...>M417>CTS4385>Y2894>L664>(S3478?)>S3479>S3485>S3477>M2747>YP1131>YP1129*-A-x (Big Y needed)
	public int section; // 2
	public String haplogroup; // >M417>CTS4385>Y2894>L664>(S3478?)>S3479>S3485>S3477>M2747>YP1131>YP1129*
	public String subgroup; // A-x
	public String mutation; // YP1129*
	public String recommendation; // Big Y needed
	
	public String kitnum; // 169505
	public String color; // ADFF2F
	public String ancestor; // Muisiner/Misener, Leonard b. 1744
	public String country; // Germany
	public String country2; // Germany
	public String strs; // 13,24,14,10,12-12,12,...
	public int strcount; // 12/25/37/64/111
	
	public Kit(String color, String rawGroup, String kitnum, String ancestor, String country, String strs, int strcount) {
		super();
		this.color = color;
		this.rawGroup = rawGroup;
		this.kitnum = kitnum;
		this.ancestor = ancestor;
		this.country = country;
		this.strs = strs;
		this.strcount = strcount;
		
		this.section = 0;
		this.haplogroup = "";
		this.subgroup = "";
		this.mutation = "";
		this.recommendation = "";
		this.country2 = getCountry2(country); 
		
		parseGroup();
	}

	private void parseGroup() {

		// fix errors //
		String ftdnaGroupFixed = fixFtdnaGroup(rawGroup); 
		
		// extract section //
		if( 0 < rawGroup.length() && rawGroup.contains(".") ) {
			int indexOfGroupPoint = rawGroup.indexOf(".");
			String section_str = rawGroup.substring(0, indexOfGroupPoint);
			try { 
				section = Integer.parseInt( section_str );
			} catch (NumberFormatException e) {  
				section = 0;  
			}
		}
		
		// extract comment //
		if( 0 < rawGroup.length() && rawGroup.contains(">") && rawGroup.contains("(")) {
			int indexOfLastOpen = rawGroup.lastIndexOf(">");
			String sinceLastOpen = rawGroup.substring(indexOfLastOpen);
			recommendation = FtdnaPage.extractBetween(sinceLastOpen, "(", ")");
		}
		
		// extract group with subgroup //
		String groupAndSubgroup = ""; 
		StringTokenizer st = new StringTokenizer(ftdnaGroupFixed, " \t\n\r,."); // legal symbols: ?()-
		while (st.hasMoreTokens()) {
		    String token = st.nextToken();
		    if( 3 < token.length() && token.contains(">") ) {
		    	groupAndSubgroup = token;
		    	break;
		    }
		}
		
		// fix //
		groupAndSubgroup = groupAndSubgroup.replace("/", ">"); // needed for 72455 127747 454141: >Z93>Z94>Z2124>Z2125>S23592>S23201>YP1359/YFS102022
		groupAndSubgroup = groupAndSubgroup.replace("(", ""); // needed for N128746: YP326>(CLD56?)>CLD12*
		groupAndSubgroup = groupAndSubgroup.replace(")", ""); // needed for N128746: YP326>(CLD56?)>CLD12*
		groupAndSubgroup = groupAndSubgroup.replace("?", ""); // needed for N128746: YP326>(CLD56?)>CLD12*, M7267: >Z645>Z93(?)>Z94(?)
		groupAndSubgroup = groupAndSubgroup.replace(" ", ""); // trim
		
		// extract group, mutation and subgroup //
		if( 0 < groupAndSubgroup.length() && groupAndSubgroup.contains(">") ) {
			int indexOfLastAngle = groupAndSubgroup.lastIndexOf(">");
			String mutationAndSubgroup = groupAndSubgroup.substring(indexOfLastAngle + 1);
			int indexOfDash = mutationAndSubgroup.indexOf("-");
			if( 0 <= indexOfDash ) {
				haplogroup = groupAndSubgroup.substring(0, indexOfLastAngle + indexOfDash + 1);
				mutation = mutationAndSubgroup.substring(0, indexOfDash);
				subgroup = mutationAndSubgroup.substring(indexOfDash + 1);
			} else {
				haplogroup = groupAndSubgroup;
				mutation = mutationAndSubgroup;
				subgroup = "";
			}
		}
		
		// fix haplogroup //
		haplogroup = fixGaplogroup( haplogroup ); 
	}
	
	private String fixFtdnaGroup( String ftdnaGroup ) {
		String ftdnaGroupFixed = ftdnaGroup;
		ftdnaGroupFixed = ftdnaGroupFixed.replace("> ", ">"); // fix error in E21006: M420>M459> YP1272>YP1276-x
		ftdnaGroupFixed = ftdnaGroupFixed.replace("> -", "-"); // fix error in 344533: >M417>CTS4385>Y2894>L664>(S3478?)>S2894>YP285>YP282-C>YP441>YP1014> -A
		ftdnaGroupFixed = ftdnaGroupFixed.replace(" -", "-"); // fix error in 558105: >M417>CTS4385>Y2894>L664>(S3478?)>S2894>S2880 -y
		ftdnaGroupFixed = ftdnaGroupFixed.replace("(?)", ""); // fix error in N128746: YP326>(CLD56?)>CLD12*
		return ftdnaGroupFixed;
	}
	
	private String getCountry2( String country ) {
		String country2 = country.trim();
		
		country2 = country2.replace("Czech Republic", "Czechoslovakia");
		country2 = country2.replace("Slovakia", "Czechoslovakia");
		
		country2 = country2.replace("England", "UK");
		country2 = country2.replace("United Kingdom", "UK");
		country2 = country2.replace("Wales", "UK");
		
		country2 = country2.replace("Lithuania", "Baltic");
		country2 = country2.replace("Latvia", "Baltic");
		country2 = country2.replace("Estonia", "Baltic");
		
		country2 = country2.replace("Serbia", "Yugoslavia");
		country2 = country2.replace("Bosnia and Herzegovina", "Yugoslavia");
		country2 = country2.replace("Slovenia", "Yugoslavia");
		country2 = country2.replace("Croatia", "Yugoslavia");
		country2 = country2.replace("Macedonia", "Yugoslavia");
		country2 = country2.replace("Montenegro", "Yugoslavia");
		
		country2 = country2.replace("Northern Ireland", "Ireland");
		
		country2 = country2.replace("Armenia", "Postsoviet");
		country2 = country2.replace("Azerbaijan", "Postsoviet");
		country2 = country2.replace("Georgia", "Postsoviet");
		country2 = country2.replace("Israel", "Postsoviet");
		country2 = country2.replace("Kazakhstan", "Postsoviet");
		country2 = country2.replace("Kyrgyzstan", "Postsoviet");
		country2 = country2.replace("Uzbekistan", "Postsoviet");
		country2 = country2.replace("Moldova", "Postsoviet");
		
		country2 = country2.replace("Russian Federation", "Russia");
		
		country2 = country2.replace("Unknown Origin", "Unknown");
		if( country2.isEmpty() ) country2 = "Unknown";
		if( country2.length() <= 1 ) country2 = "Unknown";
		
		country2 = country2.replace("United States", "USA");
		country2 = country2.replace("Canada", "USA");
		
		country2 = country2.replace("Denmark", "Scandinavia");
		country2 = country2.replace("Norway", "Scandinavia");
		country2 = country2.replace("Sweden", "Scandinavia");
		country2 = country2.replace("Iceland", "Scandinavia");

		country2 = country2.replace("Algeria", "Arabia");
		country2 = country2.replace("Egypt", "Arabia");
		country2 = country2.replace("Palestinian Territory", "Arabia");
		country2 = country2.replace("Oman", "Arabia");
		country2 = country2.replace("Yemen", "Arabia");
		country2 = country2.replace("Tunisia", "Arabia");
		country2 = country2.replace("Lebanon", "Arabia");
		country2 = country2.replace("Syrian Arab Republic", "Arabia");
		country2 = country2.replace("Bahrain", "Arabia");
		country2 = country2.replace("Iraq", "Arabia");
		country2 = country2.replace("Qatar", "Arabia");
		country2 = country2.replace("Kuwait", "Arabia");
		country2 = country2.replace("United Arab Emirates", "Arabia");
		country2 = country2.replace("Saudi Arabia", "Arabia");
		
		country2 = country2.replace("Iran", "IndoIran");
		country2 = country2.replace("Afghanistan", "IndoIran");
		country2 = country2.replace("Pakistan", "IndoIran");
		country2 = country2.replace("India", "IndoIran");
		country2 = country2.replace("Sri Lanka", "IndoIran");
		country2 = country2.replace("Tajikistan", "IndoIran");
		
		country2 = country2.replace("Cyprus", "Greece");
		
		return country2;
	}
	
	private String fixGaplogroup( String rawGroup ) {
		
		String fixedGroup = rawGroup.trim();
		
		// fix haplogroup //
		fixedGroup = fixedGroup.replace("-A", "");
		fixedGroup = fixedGroup.replace("-C", "");
		fixedGroup = fixedGroup.replace("*", "");		
		
		// Root mutations:
		//
		// M420
		// M420>M459>M198>M417
		// M420>M459>M198>M417>Z645
		// M420>M459>M198>M417>Z645>Z283
		// M420>M459>M198>M417>Z645>Z283>Z282
		// M420>M459>M198>M417>Z645>Z283>Z282>Y2395
		// M420>M459>M198>M417>Z645>Z283>Z282>Y2395>Z284
		// M420>M459>M198>M417>Z645>Z283>Z282>Z280
		// M420>M459>M198>M417>Z645>Z93
		
		if( fixedGroup.startsWith(">M417") )
			fixedGroup = "M420>M459>M198" + fixedGroup;
		
		if( fixedGroup.startsWith(">Z645") )
			fixedGroup = "M420>M459>M198>M417" + fixedGroup;
		
		if( fixedGroup.startsWith(">Z283") )
			fixedGroup = "M420>M459>M198>M417>Z645" + fixedGroup;
		
		if( fixedGroup.startsWith(">Z282") )
			fixedGroup = "M420>M459>M198>M417>Z645>Z283" + fixedGroup;
		
		if( fixedGroup.startsWith(">Y2395") )
			fixedGroup = "M420>M459>M198>M417>Z645>Z283>Z282" + fixedGroup;
		
		if( fixedGroup.startsWith(">Z284") )
			fixedGroup = "M420>M459>M198>M417>Z645>Z283>Z282>Y2395" + fixedGroup;
		
		if( fixedGroup.startsWith(">Z280") )
			fixedGroup = "M420>M459>M198>M417>Z645>Z283>Z282" + fixedGroup;
		
		if( fixedGroup.startsWith(">Z93") )
			fixedGroup = "M420>M459>M198>M417>Z645" + fixedGroup;
		
		return fixedGroup;
	}
	
	public void print_info() {
		System.out.println( kitnum + "\t" + country2 + "\t" + mutation + "\t" + subgroup + "\t" + haplogroup + "\t\t\t" + rawGroup );
		// System.out.println( getCsvLine() );
	}
	
	public String getCsvLine() {
		String csvLine =
			kitnum 
			+ "," + Integer.toString(section)
			+ "," + color
			+ "," + haplogroup.replace(",", ".")
			+ "," + subgroup.replace(",", ".")
			+ "," + mutation.replace(",", ".")
			+ "," + ancestor.replace(",", ".")
			+ "," + country
			+ "," + country2			
			+ "," + Integer.toString(strcount)
			+ "," + strs
			+ "," + recommendation.replace(",", ".")
			+ "," + rawGroup.replace(",", ".")
			;

		return csvLine;
	}
	
	public static String getCsvHeader() {
		String csvLine =
			"kitnum" 
			+ "," + "section"
			+ "," + "color"
			+ "," + "group"
			+ "," + "subgroup"
			+ "," + "mutation"
			+ "," + "ancestor"
			+ "," + "country"
			+ "," + "country2"			
			+ "," + "strcount"
			+ "," + "strs"
			+ "," + "comment"
			+ "," + "rawgroup"
			;
		
		return csvLine;
	}
	
/*
CREATE TABLE kits
(
kitnum varchar2(10) PRIMARY KEY, -- E169505 (max-6)
section number, -- 2
color varchar2(10), -- ADFF2F
haplogroup varchar2(200), -- >M417>CTS4385>Y2894>L664>(S3478?)>S3479>S3485>S3477>M2747>YP1131>YP1129* (max-108)
subgroup varchar2(10), -- A*-x1 (max-5)
mutation varchar2(20), -- YP1129* (max-9)
strcount number, -- 111
ancestor varchar2(100), -- Muisiner/Misener, Leonard b. 1744 (max-50)
country varchar2(100), -- Germany (max-31)
country2 varchar2(100), -- Germany (max-31)
rawgroup varchar2(300), -- 2. ...>M417>CTS4385>Y2894>L664>(S3478?)>S3479>S3485>S3477>M2747>YP1131>YP1129*-A-x (Big Y needed) (max-160)
recommendation varchar2(200), -- Big Y needed (max-99)
strs varchar2(500) -- 14|14|11|8|... (max-345)
);
 */
	// Create INSERT statement in format: INSERT INTO test1 VALUES (100, 18) //
	public String getSqlInserStatement() {

		String statement = "INSERT INTO kits VALUES ("
			+       "'" + kitnum + "'"
			+ "," + section // int
			+ "," + "'" + color + "'"
			+ "," + "'" + haplogroup + "'"
			+ "," + "'" + subgroup + "'"
			+ "," + "'" + mutation + "'"
			+ "," + strcount // int
			+ "," + "'" + ancestor.replace("'", "`") + "'"
			+ "," + "'" + country + "'"
			+ "," + "'" + country2 + "'"			
			+ "," + "'" + rawGroup.replace("'", "`") + "'"
			+ "," + "'" + recommendation.replace("'", "`") + "'"
			+ "," + "'" + strs + "'"
			+ ")";

		return statement;
	}
	
	public static void writeToCsv(List<Kit> kits) {
		
		File file = null;
		long startTime = System.currentTimeMillis();
		
	    try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			String folder = classLoader.getResource("out").getFile();

	    	file = new File(folder + "\\ftdna_results.csv");
			System.out.println( "Writing " + kits.size() + " kits to " + file.getAbsolutePath() + "...");

	        if(!file.exists())
	            file.createNewFile();
	    } catch(IOException e) {
	    	System.out.println( "ERROR: " + e.getMessage() );
	        throw new RuntimeException(e);
	    } catch(Exception e) {
			System.out.println( "ERROR: " + e.getMessage() );
			return;
		}
	    
	    try ( final PrintWriter out = new PrintWriter(file.getAbsoluteFile()) ) {

	      	out.println(Kit.getCsvHeader());
	        	
	       	for(Kit kit: kits) {
	       		out.println(kit.getCsvLine());
	       	}
	    } catch(IOException e) {
	    	System.out.println( "ERROR: " + e.getMessage() );
	        throw new RuntimeException(e);
	    }

		System.out.println("Wrote " + kits.size() + " kits in total, time: " + (System.currentTimeMillis() - startTime));
	}	
}

package gr.ftdnascan;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;

public class FtdnaPage {
	
	private String path;
	private List<String> lines;
	
	public FtdnaPage(String path) {
		this.path = path;
		
		try {
			lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
			System.out.println( "Loaded " + lines.size() + " lines from " + path );
			//for(String line: lines) { System.out.println(line); }		
		} catch (IOException e) { 
			System.out.println( "ERROR: " + e.getMessage() );
		}
	}
	
	public List<Kit> Parse() {
		
		List<Kit> kits = new ArrayList<Kit>();
		
		for( int linenum = 0; linenum < lines.size(); linenum++ ) {
			String line = lines.get(linenum);
			
			// process group //
			if(line.contains( "<td class=\"LeftAlign\" colspan=\"106\"" )) {
				String color = extractColor( line );
				String ftdnaGroup = extractTableData( line );
				
				// process kits //
				for( ; linenum < lines.size(); linenum++ ) {
					line = lines.get(linenum);
					
					if(line.contains( "<tr class=\"AspNet-GridView-" )) {

						if( lines.get(linenum + 1).trim().isEmpty() ) {
							
							String kitnum = extractTableData( lines.get(linenum + 2) );
							String ancestor = extractTableData( lines.get(linenum + 4) );
							String country = extractTableData( lines.get(linenum + 5) );
							
							if( 3 <= kitnum.length() ) {
								
								StringBuilder strBuilder = new StringBuilder("");
								int strcount = 0;
								
								// process strs //
								for( strcount = 1; linenum + 8 + strcount < lines.size(); strcount++ ) {
									String strline = lines.get(linenum + 8 + strcount).trim();
									if( strline.equalsIgnoreCase("<td>&nbsp;</td>") || strline.equalsIgnoreCase("</tr>") || strline.isEmpty() ) {
										break;
									}
									String str_from_line = extractTableData( strline );
									if( 1 < strcount )
										strBuilder.append("|");
									strBuilder.append(str_from_line);
								}
								
								String strs = strBuilder.toString();								
								
								Kit kit = new Kit( color, ftdnaGroup, kitnum, ancestor, country, strs, strcount - 1 );
								
								if( 0 < kit.haplogroup.length() )
									kits.add(kit);
							}
						} else {
							break; // if next line is not empty then this is probably a next group //
						}
					}
				}
			}
		}
		return kits; 
	}
	
	private String extractColor( String src ) {
		return extractBetween( src, "background-color:#", ";" ); // ... style="background-color:#ADFF2F;">2...
	}
	
	private String extractTableData( String src ) {
		String item = extractBetween( src, ">", "</td>" ); // <td class="LeftAlign">B10992</td>
		item = decodeHtmlText( item );
		// item = item.replace(",", "."); // for exporting to csv // moved to getCsvLine
		item = item.trim();
		return item;
	}
	
	static public String extractBetween( String src, String before, String after ) {
		int indexOfBefore = src.indexOf(before);
		if( 0 <= indexOfBefore ) {
			int start = indexOfBefore + before.length();
			int indexOfAfter = src.indexOf(after, start);
			if( 0 <= indexOfAfter ) {
				String result = src.substring(start, indexOfAfter);
				return result;
			}
		}
		return "";
	}
	
	public static String decodeHtmlText( String str ) {
		String result = Jsoup.parse( str ).text();
		return result;
	}
}
	
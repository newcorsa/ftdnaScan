package gr.ftdnascan;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.*;

public class FtdnaScan {

	public static void main(String[] args) {
		
		Kit testkit = new Kit( "", "9. ..>Z645>Z93(?)>Z94(?)-x1 (Big Y. Z93 SNP test or R1a-Z93 SNP pack needed) [L657-]", "", "", "", "", 1 );
		testkit.print_info();	
		
		System.out.println("Start scanning");

//		ArrayList<File> fileList = new ArrayList<>(Arrays.asList(curFiles));
//		for(File file: fileList) {}
		
		List<Kit> kits = new ArrayList();
		
		kits.add(testkit); //TEST//
		
		File workDir = new File("C:\\work2\\ftdna\\in");
		File[] curFiles = workDir.listFiles();
		
		if( curFiles != null )
		{
			for(int i = 0; i < 1 && i < curFiles.length; i++) {
				if(curFiles[i].isFile()) {
					System.out.println(curFiles[i].getPath());
				
					String filename = curFiles[i].getPath();
				
					FtdnaPage ftdnaPage = new FtdnaPage(filename);
					List<Kit> pageKits = ftdnaPage.Parse();
					System.out.println( "Loaded " + pageKits.size() + " kits from " + filename );
				
					kits.addAll(pageKits);
				}
			}
		}
		
		System.out.println( "Loaded " + kits.size() + " kits in total" );
		
		for(Kit kit: kits) { 
			if( kit.ancestor.contains("Ch.") )
				kit.print_info(); 
			}
		
		//Kit.writeToCsv(kits);
		
		//--------------- Calculate Country/Haplogroup Statistics --------------//
		CountryStatistics russiaSt = new CountryStatistics( "Russia", kits );
		russiaSt.print_info();
		
		// FtdnaStore store = new FtdnaStore();
		// store.store(kits); 
		
		//////////////////////////////////////
		
		List<Kit> kits3 = new ArrayList<>();
		kits.stream()
			.filter((k) -> k.country2.equalsIgnoreCase("Russia"))
			.forEach( (k) -> kits3.add(k) );
		
		System.out.println( "Test1 " + kits.size() + " kits in total" );
		Map<String, HaplogroupInfo> mapInfos = HaplogroupInfo.loadFromKits( kits );
		System.out.println( "Test2 " + kits.size() + " kits in total" );
		
		mapInfos.forEach( (s,h) -> h.print_info() );
		
		// kits3.forEach((k)->k.print_info()); // haplogroup = YP5820

		/*
		Predicate<Kit> yp5820 = (Kit k) -> k.haplogroup.contains("YP5820");
		
		Consumer<Kit> prnt = (k) -> k.print_info();
		
		Consumer<Kit> prnt2 = (k) -> { if( k.haplogroup.contains("YP5820") ) k.print_info(); };
		
		kits3.forEach(prnt2);
		
		kits3.forEach( (k) -> { if( k.haplogroup.contains("YP5820") ) k.print_info(); } );
		*/
/*		
		kits.stream()
			.sorted((k,l) -> k.mutation.compareTo(l.mutation))
			.filter((k) -> k.country2.equalsIgnoreCase("Russia"))
			.forEach((k) -> k.print_info());
*/		

		;
	}
	

}

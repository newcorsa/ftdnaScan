package gr.ftdnascan;

import java.util.ArrayList;
import java.util.List;

public class CountryStatistics {
	
	private final String country;
	private final List<Kit> kits;
	
	public CountryStatistics( String country, List<Kit> kits ) {
		this.country = country;
		this.kits = new ArrayList<>();
		
		kits.stream()
			.sorted((k,l) -> k.haplogroup.compareTo(l.haplogroup))
			.filter((k) -> k.country2.equalsIgnoreCase("Russia"))
			.forEach((k) -> this.kits.add(k));
	}
	
	public void print_info() {
		System.out.println( "Statistics for " + country + ":");
		
		kits.forEach((k) -> k.print_info());
	}

}

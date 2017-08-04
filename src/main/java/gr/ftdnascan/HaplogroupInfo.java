package gr.ftdnascan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class HaplogroupInfo {
	
	public final String name;
	public String path;
	public String parent;
	public Set<String> ancestors = new HashSet<>();
	public Set<String> siblings = new HashSet<>();
	public Set<String> children = new HashSet<>();
	public Set<String> descendants = new HashSet<>();
	
	public HaplogroupInfo(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	public void print_info() {
		System.out.println( "HAPLOGROUP: " + name + ", PARENT: " + parent + ", PATH: " + path );
		System.out.println( "	ancestors: " + HaplogroupInfo.toString(ancestors) );
		System.out.println( "	siblings: " + HaplogroupInfo.toString(siblings) );
		System.out.println( "	children: " + HaplogroupInfo.toString(children) );
		System.out.println( "	descendants: " + HaplogroupInfo.toString(descendants) );
		System.out.println( "	" );
	}
	
	public static String toString(Set<String> set) {
		StringBuilder result = new StringBuilder();
		set.stream()
			.sorted((k,l) -> k.compareTo(l))
			.forEach( (s) -> result.append(s + " ") );
		return result.toString();
	}
	
	public static Map<String, HaplogroupInfo> loadFromKits(List<Kit> kits) {
		
		Map<String, HaplogroupInfo> mapInfos = new HashMap<>();
		
		kits.forEach((k) -> HaplogroupInfo.addKit(mapInfos, k));
		
		HaplogroupInfo.findSiblings(mapInfos);
		
		return mapInfos;
	}

	public static void addKit(Map<String, HaplogroupInfo> mapInfos, Kit kit) {
		
		System.out.println( "Adding " + kit.kitnum + ": " + kit.haplogroup + " ..." );
		
		StringTokenizer st = new StringTokenizer(kit.haplogroup, "> \t\n\r,."); // legal symbols: ?()-
		while (st.hasMoreTokens()) {
		    String token = st.nextToken();
		    if( 0 < token.length() ) {
		    	HaplogroupInfo.addHaplogroupWithKit(mapInfos, kit, token);
		    }
		}
	}

	public static void addHaplogroupWithKit(Map<String, HaplogroupInfo> mapInfos, Kit kit, String name) {
		
		System.out.println( "Adding " + name + " ..." );
		
		HaplogroupInfo hi = mapInfos.get(name);
		
		if( hi == null ) {
			hi = new HaplogroupInfo(name, kit.haplogroup);
			mapInfos.put(name, hi);
		} else {
			if( hi.path.length() < kit.haplogroup.length() ) {
				hi.path = kit.haplogroup;
			}
		}
	
		boolean nameFound = false;
		String previous0 = "";
		String previous = new String("");
		
		StringTokenizer st = new StringTokenizer(kit.haplogroup, "> \t\n\r,."); // legal symbols: ?()-
		while (st.hasMoreTokens()) {
		    String token = st.nextToken();
		    if( 0 < token.length() ) {
		    	if( token.equalsIgnoreCase(name) ) {
		    		nameFound = true;
		    		hi.parent = previous;
		    	} else {
		    		if( !nameFound ) {
		    			hi.ancestors.add(token);
		    		} else {
		    			hi.descendants.add(token);
		    		}
		    		if(previous.equalsIgnoreCase(name)) {
		    			hi.children.add(token);
		    		}
		    	}
		    	previous = token;
		    }
		}
	}
	
	public static void findSiblings(Map<String, HaplogroupInfo> mapInfos) {
		for( HaplogroupInfo hi : mapInfos.values() ) {
			HaplogroupInfo parent_hi = mapInfos.get( hi.parent );
			if( parent_hi != null) {
				for( String sibling_name : parent_hi.children ) {
					if( !sibling_name.equalsIgnoreCase(hi.name) ) {
						hi.siblings.add(sibling_name);
					}
				}
			}
		}
	}
	
}

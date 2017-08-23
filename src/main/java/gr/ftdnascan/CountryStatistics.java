package gr.ftdnascan;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.List;
import com.diffplug.common.base.TreeNode;

public class CountryStatistics {
	
	private final String country;
	private final List<Kit> kits;

	private static final Logger LOGGER = LogManager.getLogger();

	public CountryStatistics( String country, List<Kit> kits ) {
		this.country = country;
		this.kits = new ArrayList<>();
		
		kits.stream()
			.sorted((k,l) -> k.haplogroup.compareTo(l.haplogroup))
			.filter((k) -> k.country2.equalsIgnoreCase("Russia"))
			.forEach((k) -> this.kits.add(k));

		LOGGER.info("Statistics for " + country + ": " + kits.size());

		durianTreeTest();
	}
	
	public void print_info() {
		System.out.println( "Statistics for " + country + ":");
		
		kits.forEach((k) -> k.print_info());
	}

	public void durianTreeTest() {
		System.out.println( "durianTreeTest:");

		TreeNode<String> tn = new TreeNode<String>(null, "Test");
		System.out.println(tn.toString());

		TreeNode<String> root = TreeNode.createTestData(
				"root",
				" bigNode1",
				" bigNode2",
				"  child1",
				"  child2",
				" bigNode3"
		);

		TreeNode<String> ch2 = root.findByContent("child2");
		TreeNode<String> bn1 = root.getChildren().get(0);

		System.out.println(root.toStringDeep());
	}
}

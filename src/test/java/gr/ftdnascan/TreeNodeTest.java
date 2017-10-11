package gr.ftdnascan;

import com.diffplug.common.base.Throwing;
import com.diffplug.common.base.TreeNode;

import com.diffplug.common.base.TreeNodeKnowledgeable;
import com.diffplug.common.base.TreeNodeable;
import org.junit.*;

import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test
    public void createTestData() throws Exception {

        TreeNode<String> treeNode = TreeNode.createTestData(
            "root",
            " bigNode1",
            " bigNode2",
            "  child1",
            "  child2",
            " bigNode3");

        System.out.println( treeNode.toStringDeep() );

        assertEquals(3, treeNode.getChildren().size() );
    }

    @Test
    public void treeNodeableInterface() throws Exception {
        TreeNode<String> root = new TreeNode<String>(null, "root");
        TreeNode<String> child1 = new TreeNode<String>(root, "child1");
        TreeNode<String> child11 = new TreeNode<String>(child1, "child11");
        System.out.println(child1.toStringDeep());

        TreeNodeable<String> nodeable = root;
        System.out.println("Root content: " + nodeable.getContent());

        class ConsumerTest implements Consumer<TreeNode> {
            @Override public void accept(TreeNode tn) {
                System.out.println("Consume: " + tn.toStringDeep());
            }
        };

        ConsumerTest test1 = new ConsumerTest();
        test1.accept(root);
    }

    @Test
    public void treeNodeKnowledgeableInterface() throws Exception {

        class KnowledgeableContent implements TreeNodeKnowledgeable {
            public String text;
            public TreeNode<String> ref = null;

            public KnowledgeableContent(String s) {text = s;}

            @Override public String toString() {return text;}

            @Override public void setTreeNodeReference(Object ref) {
                try {
                    this.ref = (TreeNode<String>) ref;
                }
                catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }
            }
        };

        KnowledgeableContent с = new KnowledgeableContent("1st test");

        TreeNode<KnowledgeableContent> root = new TreeNode<KnowledgeableContent>(null, new KnowledgeableContent("root"));
        TreeNode<KnowledgeableContent> child1 = new TreeNode<KnowledgeableContent>(root, new KnowledgeableContent("child1"));
        TreeNode<KnowledgeableContent> child11 = new TreeNode<KnowledgeableContent>(child1, new KnowledgeableContent("child1_1"));
        System.out.println(child1.toStringDeep());
    }

    @Test
    public void testElement() throws Exception {

        Haplogroup haplo = new Haplogroup( "n", "c", 5, "r", "o" );

        System.out.println(haplo.getParent());
    }

    @Test
    public void find() throws Exception {

        TreeNode<String> treeNode = TreeNode.createTestData(
                "root",
                " bigNode1",
                " bigNode2",
                "  child1",
                "  child2",
                "   child22",
                " bigNode3");

        TreeNode<String> treeNodeChild2 = treeNode.findByContent("child2");

        System.out.println( treeNodeChild2.toStringDeep() );
    }
}
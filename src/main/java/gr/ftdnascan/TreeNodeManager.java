package gr.ftdnascan;

import com.diffplug.common.base.TreeNode;

import java.util.List;

public class TreeNodeManager {

    public static final TreeNodeManager instance = new TreeNodeManager();
    public static final TreeNode<Haplogroup> root = new TreeNode<Haplogroup>(null, new Haplogroup());

    public Haplogroup addHaplogroup( String color, String original ) {

        int section = FtdnaSequenceBuilder.extractSection(original);
        String recommendation = FtdnaSequenceBuilder.extractRecommendation(original);
        List<String> sequence = FtdnaSequenceBuilder.buildFromRawText(original);

        Haplogroup haplo = null;
        Haplogroup parent = null;

        for( String name : sequence ) {

            if( name.equals(sequence.get(sequence.size() - 1)) )
                haplo = new Haplogroup(name, color, section, recommendation, original);
            else
                haplo = new Haplogroup(name);

            addHaplogroup(haplo, parent);

            parent = haplo;
        }

        //System.out.println(root.toStringDeep());
        return haplo;
    }

    public void addHaplogroup(Haplogroup haplo, Haplogroup parent) {

        TreeNode<Haplogroup> foundNode = root.findBelowByContent(haplo);

        if( foundNode == null ) {

            TreeNode<Haplogroup> foundParentNode = root.findBelowByContent(parent);

            if( foundParentNode == null ) {

                if( parent == null ) {
                    TreeNode<Haplogroup> newNode = new TreeNode<Haplogroup>(root, haplo);
                } else {
                    TreeNode<Haplogroup> newParentNode = new TreeNode<Haplogroup>(root, parent);
                    TreeNode<Haplogroup> newNode = new TreeNode<Haplogroup>(newParentNode, haplo);
                }
            } else {
                TreeNode<Haplogroup> newNode = new TreeNode<Haplogroup>(foundParentNode, haplo);
            }
        } else {

            haplo.setTreeNodeReference(foundNode);
            foundNode.setContent(haplo); // change existence

            // check existing parent
            if(parent != null) {
                Haplogroup foundParent = foundNode.getParent().getContent();
                if(!parent.equals(foundParent))
                    throw new RuntimeException("Parent mismatch");
            }
        }
    }
}

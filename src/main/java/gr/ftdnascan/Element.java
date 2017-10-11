package gr.ftdnascan;

import com.diffplug.common.base.TreeNode;
import com.diffplug.common.base.TreeNodeKnowledgeable;
import com.diffplug.common.base.TreeNodeable;

import java.util.List;
import java.util.stream.Collectors;

public class Element<T> implements TreeNodeable<T>, TreeNodeKnowledgeable {

    private TreeNodeable<T> treeNode = null;

    public TreeNodeable<T> getTreeNode() { return treeNode; }

    @Override
    public void setTreeNodeReference( Object ref ) {
        try {
            treeNode = (TreeNodeable<T>) ref;
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    @Override
    public T getContent() {
        return treeNode.getContent();
    }

    @Override
    public TreeNodeable<T> getParent() {
        return treeNode.getParent();
    }

    @Override
    public List<TreeNodeable<T>> getNodeableChildren() { return treeNode.getNodeableChildren(); }
}

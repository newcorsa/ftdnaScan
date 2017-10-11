package com.diffplug.common.base;

import java.util.List;
import java.util.function.Consumer;

public interface TreeNodeable<T> {

    /** Returns the object which is encapsulated by this TreeNode. */
    T getContent();

    /** Returns the (possibly-null) parent of this TreeNode. */
    TreeNodeable<T> getParent();

    /** Returns the children of this TreeNode. */
    List<TreeNodeable<T>> getNodeableChildren();

    default int depth() {
        TreeNodeable<T> node = this;
        for( int n = 0;; n++ ) {
            node = node.getParent();
            if( node == null )
                return n;
        }
    }

    default void visit(Consumer<T> consumer) {
        consumer.accept(getContent());
        getNodeableChildren().forEach(n -> n.visit(consumer));
    }
}

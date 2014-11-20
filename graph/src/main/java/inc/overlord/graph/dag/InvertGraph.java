/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Assumes a DAG, and inverts it. Leaves become roots, roots become
 * leaves.
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class InvertGraph<D, W extends Node<D, W>> implements Consumer<W> {
    IdentityHashMap<W, W> reverseNodes = new IdentityHashMap<>();
    Set<W> reverseRoots = new HashSet<>();
    /**
     * {@inheritDoc}
     * @param node
     */
    @Override
    public void accept(W node) {
        W reverseNode = reverseNodes.get(node);
        if (reverseNode == null) {
            reverseNode = cloneWithoutConnections(node);
        }
        if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            for (W child: node.getChildren()) {
                W reverseChild = reverseNodes.get(child);
                if (reverseChild == null) {
                    reverseChild = cloneWithoutConnections(child);
                }
                reverseChild.addChild(reverseNode);
            }
        }
        else {
            reverseRoots.add(reverseNode);
        }
    }

    private W cloneWithoutConnections(W node) throws RuntimeException {
        try {
            W reverseNode = (W) node.clone();
            reverseNode.setChildren(new HashSet<>());
            reverseNodes.put(node, reverseNode);
            return reverseNode;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}

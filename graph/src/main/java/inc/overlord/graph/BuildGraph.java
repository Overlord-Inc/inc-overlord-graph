/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph;

import inc.overlord.common.Transformer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class BuildGraph<Data, Key, NodeWrapper extends KeyedNode<Data, Key, NodeWrapper>> implements Transformer<Set<NodeWrapper>, Set<NodeWrapper>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<NodeWrapper> transform(Set<NodeWrapper> nodes) {
        // all items are mapped from their key to the instance
        Map<Key, NodeWrapper> keyToNodeMap = new HashMap<Key, NodeWrapper>();
        // keep track of all references, it will be used at the end
        Map<Key, Set<NodeWrapper>> references = new HashMap<Key, Set<NodeWrapper>>(nodes.size());
        for (NodeWrapper node: nodes) {
            Key key = node.getKey();
            keyToNodeMap.put(key, node);
            // any previous references get replaced with the actual
            // instances, any further references will be looked up
            // in the keyToNodeMap
            Set<NodeWrapper> previousParents = references.get(key);
            for (NodeWrapper parent: previousParents) {
                parent.children.add(node);
            }
            // for each child, get it from the keyToNodeMap, or presume
            // that when the node traversal gets to the node, it will
            // have the back reference from the references
            Set<Key> childKeys = node.getChildKeys();
            Set<NodeWrapper> children = new HashSet<NodeWrapper>(childKeys.size());
            for (Key childKey: node.getChildKeys()) {
                NodeWrapper childNode = keyToNodeMap.get(childKey);
                if (childNode != null) {
                    children.add(childNode);
                }
                Set<NodeWrapper> parents = references.get(key);
                if (parents == null) {
                    parents = new HashSet<NodeWrapper>();
                    references.put(key, parents);
                }
                parents.add(node);
            }
            node.children = children;
        }
        // anything with no references to it is a root, logically,
        // removing everything that has a reference from the total set of
        // keys will leave only keys that have no references to them
        Set<Key> keys = new HashSet<Key>(keyToNodeMap.keySet());
        keys.removeAll(references.keySet());
        Set<NodeWrapper> roots = new HashSet<NodeWrapper>(keys.size());
        for (Key rootKey: keys) {
            roots.add(keyToNodeMap.get(rootKey));
        }
        return roots;
    }
}

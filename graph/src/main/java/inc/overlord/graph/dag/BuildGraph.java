/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Takes a set of serialized nodes with the keys to their child nodes. These
 * keys are looked up, and used to complete the graph. This can be used to
 * deserialize a set of in memory objects (as long as they all have keys).
 * 
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class BuildGraph<D, K extends Serializable, N extends KeyedNode<D, K, N>> implements Function<Set<N>, Set<N>> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Set<N> apply(Set<N> nodes) {
    // all items are mapped from their key to the instance
    Map<K, N> keyToNodeMap = new HashMap<>();
    // keep track of all references, it will be used at the end
    Map<K, Set<N>> references = new HashMap<>(nodes.size());
    for (N node : nodes) {
      K key = node.getKey();
      keyToNodeMap.put(key, node);
      // any previous references get replaced with the actual
      // instances, any further references will be looked up
      // in the keyToNodeMap
      Set<N> previousParents = references.get(key);
      if (previousParents != null) {
        for (N parent : previousParents) {
          parent.children.add(node);
        }
      }
      // invert the references, go from referred key to referring node,
      // this will be resolved in one sweep later
      Set<K> childKeys = node.getChildKeys();
      if (childKeys != null) {
        Set<N> children = new HashSet<>(childKeys.size());
        for (K childKey : node.getChildKeys()) {
          Set<N> parents = references.get(childKey);
          if (parents == null) {
            parents = new HashSet<>();
            references.put(childKey, parents);
          }
          parents.add(node);
        }
        node.children = children;
      }
    }
    // resolve all references
    references.forEach((key, nodeSet) -> {
      N referenced = keyToNodeMap.get(key);
      nodeSet.forEach((referringNode) -> {
        referringNode.addChild(referenced);
      });
    });
    // anything with no references to it is a root, logically,
    // removing everything that has a reference from the total set of
    // keys will leave only keys that have no references to them
    Set<K> keys = new HashSet<>(keyToNodeMap.keySet());
    keys.removeAll(references.keySet());
    Set<N> roots = new HashSet<>(keys.size());
    for (K rootKey : keys) {
      roots.add(keyToNodeMap.get(rootKey));
    }
    return roots;
  }
}

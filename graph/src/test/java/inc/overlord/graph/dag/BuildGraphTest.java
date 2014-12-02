package inc.overlord.graph.dag;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class BuildGraphTest {
  BuildGraph<String, String, StringNode> buildGraph;

  @Test
  public void testDiamond() {
    Set<StringNode> nodes = new HashSet<>();
    StringNode nodeA = new StringNode("A");
    StringNode nodeB = new StringNode("B");
    StringNode nodeC = new StringNode("C");
    StringNode nodeD = new StringNode("D");
    nodeA.setChildKeys(new HashSet<>(asList("B", "C")));
    Set<String> dChildKey = singleton("D");
    nodeB.setChildKeys(dChildKey);
    nodeC.setChildKeys(dChildKey);
    nodes.addAll(asList(nodeA, nodeB, nodeC, nodeD));
    buildGraph = new BuildGraph<String, String, StringNode>();
    Set<StringNode> graph = buildGraph.apply(nodes);
    assertEquals(1, graph.size());
    StringNode root = graph.iterator().next();
    assertSame(nodeA, root);
    assertTrue(root.getChildren().contains(nodeB));
    assertTrue(root.getChildren().contains(nodeC));
    assertTrue(nodeB.getChildren().contains(nodeD));
    assertTrue(nodeC.getChildren().contains(nodeD));
  }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class BreadthFirstTraversalTest {
  @Mock
  Consumer<StringNode> consumer;
  BreadthFirstTraversal<String, StringNode> traversal;

  @Before
  public void initialize() {
    initMocks(this);
  }

  @Test
  public void testTrivial() {
    StringNode node = new StringNode("1");
    Set<StringNode> graph = Collections.singleton(node);
    traversal = new BreadthFirstTraversal<>(graph);
    traversal.forEachRemaining(consumer);
    verify(consumer).accept(eq(node));
  }

  /**
   * Test of traverse method, of class BreadthFirstTraversal.
   */
  @Test
  public void testTraverse() {
    StringNode node1 = new StringNode("1"), node2 = new StringNode("2");
    StringNode node3 = new StringNode("3"), node4 = new StringNode("4");
    node1.addChild(node2);
    node1.addChild(node3);
    node2.addChild(node4);
    node3.addChild(node4);
    Set<StringNode> roots = Collections.singleton(node1);
    traversal = new BreadthFirstTraversal<>(roots);
    traversal.forEachRemaining(consumer);
    verify(consumer).accept(eq(node1));
    verify(consumer).accept(eq(node2));
    verify(consumer).accept(eq(node3));
    verify(consumer).accept(eq(node4));
  }

}

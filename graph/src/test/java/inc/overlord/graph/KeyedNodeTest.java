/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph;

import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 */
public class KeyedNodeTest {
    
    /**
     * Test of getKey method, of class KeyedNode.
     */
    @Test
    public void testGetKey() {
        String data = "data";
        StringNode node = new StringNode(data);
        assertEquals(data, node.getKey());
    }

    /**
     * Test of getChildKeys method, of class KeyedNode.
     */
    @Test
    public void testGetChildKeys() {
        String data1 = "data1", data2 = "data2", data3 = "data3";
        StringNode node1 = new StringNode(data1), node2 = new StringNode(data2), node3 = new StringNode(data3);
        Set<String> childKeys = node1.getChildKeys();
        assertNotNull(childKeys);
        assertTrue(childKeys.isEmpty());
        node1.addChild(node2);
        node1.addChild(node3);
        childKeys = node1.getChildKeys();
        assertNotNull(childKeys);
        assertFalse(childKeys.isEmpty());
        assertTrue(childKeys.contains(data2));
        assertTrue(childKeys.contains(data3));
        assertFalse(childKeys.contains(data1));
    }

}
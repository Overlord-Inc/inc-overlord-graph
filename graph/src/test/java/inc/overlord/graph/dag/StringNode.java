/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.overlord.graph.dag;

import inc.overlord.graph.dag.KeyedNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *
 * @author Anand Chelian <achelian@yahoo.com>
 */
@Data @EqualsAndHashCode(callSuper = true) @ToString(callSuper = true)
public class StringNode extends KeyedNode<String, String, StringNode> {

    StringNode(String value) {
        super(value);
    }

    @Override
    public String getKey() {
        return getData();
    }
}


public class AST {

    ASTNode root;

    public ASTNode getRoot() {
        return root;
    }

    public void setRoot(ASTNode root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "AST [root=" + root + "]";
    }

    public ASTNode jumpToNode(ASTNode node, String id) {
        if (String.valueOf(node.number).equals(id)) {
            return node;
        }
        for (ASTNode child : node.children) {
            ASTNode ret = jumpToNode(child, id);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

}

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
}

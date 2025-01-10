
import java.util.ArrayList;
import java.util.List;

public class ASTNode {

    ASTNode parent;
    String value;
    _TokenType type;
    Integer number;
    String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    List<ASTNode> children = new ArrayList<>();

    public ASTNode(String value, _TokenType type, Integer number) {
        this.value = value;
        this.type = type;
        this.number = number;
    }

    public ASTNode getParent() {
        return parent;
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public _TokenType getType() {
        return type;
    }

    public void setType(_TokenType type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void setChildren(List<ASTNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        String ret = "";

        ret += "<" + value + " id=\"" + number + "\" type=\"" + type + "\"";

        if (!children.isEmpty()) {
            ret += " children=\"";
            for (ASTNode astNode : children) {
                ret += astNode.getNumber() + ",";
            }
            ret += "\"";
        }
        ret += ">\n";

        if (!"".equals(content)) {
            ret += content + "\n";
        }

        if (!children.isEmpty()) {
            for (ASTNode astNode : children) {
                ret += astNode.toString();
            }
        }

        ret += "</" + value + ">\n";
        return ret;
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }
}


public class Variable {
    String name;
    _TokenType type;
    Integer id;

    public Variable(String name, _TokenType type, Integer id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public Variable() {
    }

    public String toString() {
        return "Variable: " + name + " Type: " + type;
    }

    public String toXML() {
        return "<Variable name=\"" + name + "\" type=\"" + type + "\" />";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public _TokenType getType() {
        return type;
    }

    public void setType(_TokenType type) {
        this.type = type;
    }
}

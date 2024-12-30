
public class Symbol {
    String name;
    _TokenType type;
    Integer id;

    public Symbol(String name, _TokenType type, Integer id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public Symbol() {
    }

    public String toString() {
        return "Symbol: " + name + " Type: " + type;
    }

    public String toXML() {
        return "<Symbol name=\"" + name + "\" type=\"" + type + "\" />";
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

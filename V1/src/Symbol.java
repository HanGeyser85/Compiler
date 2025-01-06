
public class Symbol {
    String name;
    _TokenType type;
    Integer id;
    Boolean isFromParent;
    Boolean isVisibleToChild;
    Boolean isUsed;
    Boolean isInitialized;
     
    public Symbol() {
    }

    public Symbol(Integer id, String name, _TokenType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return "Symbol: " + name + " Type: " + type + " IsFromParent: " + isFromParent;
    }
    
    public String toXML() {
        return "<Symbol name=\"" + name + "\" type=\"" + type + "\" isFromParent=\"" + isFromParent + "\" />";
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

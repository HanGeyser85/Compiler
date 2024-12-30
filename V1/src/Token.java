public class Token {
    private String value;
    private _TokenType type;
    private Token next;

    public Token(String value, _TokenType type, Token next) {
        this.value = value;
        this.type = type;
        this.next = next;
    }

    public Token(String value, _TokenType type) {
        this.value = value;
        this.type = type;
    }

    public Token getNext() {
        return next;
    }

    public void setNext(Token next) {
        this.next = next;
    }

    public String getValue() {
        return value;
    }

    public _TokenType getType() {
        return type;
    }

    public String toString() {
        return "Value: " + value + " Type: " + type.name();
    }
}

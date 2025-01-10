
import java.util.ArrayList;
import java.util.List;

public class Variable {

    String name;
    _TokenType type;
    Integer id;
    Boolean hasValue;
    Boolean notUsed;
    Boolean initialized;
    List<Integer> usedAtIds = new ArrayList<>();

    public Boolean getHasValue() {
        return hasValue;
    }

    public void setHasValue(Boolean hasValue) {
        this.hasValue = hasValue;
    }

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Variable(String name, _TokenType type, Integer id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public Variable() {
    }

    @Override
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

    public Boolean getInitialized() {
        return initialized;
    }

    public void setInitialized(Boolean notInitialized) {
        this.initialized = notInitialized;
    }

    public List<Integer> getUsedAtIds() {
        return usedAtIds;
    }

    public void setUsedAtIds(List<Integer> usedAtIds) {
        this.usedAtIds = usedAtIds;
    }
}

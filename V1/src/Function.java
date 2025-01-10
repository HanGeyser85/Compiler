
import java.util.ArrayList;
import java.util.List;

public class Function {

    String name;
    Integer id;
    Boolean isCalled;
    List<Integer> CalledAtIds = new ArrayList<>();

    public Function(Integer id, Boolean isCalled, String name) {
        this.id = id;
        this.isCalled = isCalled;
        this.name = name;
    }

    public Function() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsCalled() {
        return isCalled;
    }

    public void setIsCalled(Boolean isCalled) {
        this.isCalled = isCalled;
    }

    @Override
    public String toString() {
        return "Variable: " + name;
    }

    public String toXML() {
        return "<Variable name=\"" + name + "\" />";
    }

    public List<Integer> getCalledAtIds() {
        return CalledAtIds;
    }

    public void setCalledAtIds(List<Integer> CalledAtIds) {
        this.CalledAtIds = CalledAtIds;
    }

}

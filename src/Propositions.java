import java.util.HashMap;

public class Propositions {

    protected HashMap content;

	// constructeur

    public Propositions() {
        this.content = new HashMap();
    }

	// autres

    public void set(Proposition p) {
        this.content.put(p.get_type(), p.get_value());
    }

    public void set(String type, String value) {
        this.content.put(type, value);
    }

    public boolean contains(Proposition p) {
        return this.content.containsKey(p.get_type()) && this.content.containsValue(p.get_value());
    }
}
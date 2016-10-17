import java.util.HashMap;

public class Propositions {

    protected HashMap _content;

	// constructeur

    public Propositions() {
        _content = new HashMap;
    }

	// autres

    public void set(Proposition p) {
        _content.put(p.get_type(), p.get_value());
    }

    public vois set(String type, String value) {
        _content.put(type, value);
    }

    public boolean contains(Proposition p) {
        return _content.containsKey(p.get_type()) && _content.contains(p.get_value());
    }
}
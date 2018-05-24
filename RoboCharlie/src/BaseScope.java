import java.util.HashMap;
import java.util.Map;

public class BaseScope implements Scope{
	
	public Map<String, Symbol> baseScope = new HashMap<String, Symbol> ();

	@Override
	public String getScopeName() {
		return null;
	}

	@Override
	public Scope getEnclosingScope() {
		return null;
	}

	@Override
	public void define(Symbol sym) {
		baseScope.put(sym.name, sym);
	}

	@Override
	public Symbol resolve(String name) {
		return null;
	}

}

import java.util.HashMap;
import java.util.Map;

public class MethodSymbol extends Symbol implements Scope {
	
	public Map<String, Symbol> SymOfFunctions = new HashMap<String, Symbol>();

	public MethodSymbol(String name, Type type) {
		super(name, type);
		// TODO Auto-generated constructor stub
	}
	
	public MethodSymbol(String name) {
		super(name);
	}

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
		SymOfFunctions.put(sym.name, sym);
	}

	@Override
	public Symbol resolve(String name) {
		return SymOfFunctions.get(name);
	}

}

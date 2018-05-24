/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
import java.util.*;
public class SymbolTable implements Scope { // single-scope symtab
	
    public Map<String, Symbol> symbols = new HashMap<String, Symbol>();
    public Map<String, Integer> symContent = new HashMap<String, Integer>();
    
    public SymbolTable() {
    	initTypeSystem(); 
    }
    protected void initTypeSystem() {
        define(new BuiltInTypeSymbol("int"));
        define(new BuiltInTypeSymbol("float"));
    }
    // Satisfy Scope interface
    public String getScopeName() { return "global"; }
    
    public Scope getEnclosingScope() { return null; }
    
    public void define(Symbol sym) {
    	symbols.put(sym.name, sym); 
    	symContent.put(sym.name,new Integer("0"));
    }
    
    public Symbol resolve(String name) { return symbols.get(name); }
    public String toString() { return getScopeName()+":"+symbols; }
}
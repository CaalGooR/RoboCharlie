/*
 * Esta clase se encarga de analizar la sintaxis de la lista de Tokens 
 * generados que provienen de la clase PseudoLexer y así mismo traducir a lenguaje C,
 * ultilizando como base las reglas gramaticales de PsudoCodigo
 * */

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import PseudoLexer.PseudoLexer;
import java.io.BufferedWriter;

public class PseudoParser extends Parser {
	
	private VariableSymbol vSymbol;
	private SymbolTable symbolTable = new SymbolTable() ;
	private ArrayList<String> variables = new ArrayList<String>();
	
	public PseudoParser(PseudoLexer input) { 
		super(input); 
		programa();
	}

	private void programa(){    
		try{
			File file = new File ("src/codeIntermedio.rob");
			BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
			out.write(interCode);
			out.close();
		}
		catch(Exception e) {
			System.out.println("Error "+e);
		}
		
		match("INICIOPROGRAMA");
		declaraciones();
		System.out.println(symbolTable.symContent);
		//enunciados();
	}
	
	private void enunciados() {
		
		String y = lookahead.type.toString();
		while(!(lookahead.type.toString().equals("FINPROGRAMA"))){
			enunciado();
		}
		consume();
	}

	private void enunciado() {
		if(lookahead.type.toString().equals("VARIABLE")){
			asignacion();
		}
		else if(lookahead.type.toString().equals("LEER")) {
			leer();
		}
		else if(lookahead.type.toString().equals("ESCRIBIR")) {
			escribir();
		}
		else if(lookahead.type.toString().equals("SI")) {
			si();
		}
		else if(lookahead.type.toString().equals("MIENTRAS")) {
			mientras();
		}
		
	}
	
	private void asignacion() {
		existe(lookahead.data);
		match("VARIABLE");
		match("IGUAL");
		operacion();
	}
	
	
	private int operacion() {
		
		int resultado = valor();
		if(lookahead.type.toString().equals("OPARITMETICO")) {
			String operador = lookahead.data;
			consume();
			
			if (operador.equals("+"))
				return resultado + valor();
			
			else if (operador.equals("-"))
				return resultado - valor();
			
			else if (operador.equals("*"))
				return resultado * valor();
			
			else if (operador.equals("/"))
				return resultado / valor();
          
		}
		else
			throw new Error("Error en operacion cerca de: "+lookahead.data);
		
		return 0;
	}
	
	private int valor() {
		int valor;
		if (lookahead.type.toString().equals("NUMERO")){
			valor = Integer.parseInt(lookahead.data);
			consume();
			return valor;
		}
		else if (lookahead.type.toString().equals("VARIABLE")){
			valor = existe(lookahead.data);
			consume();
			return valor;
		}
		return 0;
	}
	
	private void leer() {
		match("LEER");
		match("CADENA");
		match("COMA");
		existe(lookahead.data);
		match("VARIABLE"); 
	}
	
	private void escribir() {
		match("ESCRIBIR");
		match("CADENA");
		if(lookahead.type.toString().equals("COMA")) {
			consume();
			existe(lookahead.data);
			match("VARIABLE");
		}
	}
	
	private void si() {
		match("SI");
		match("PARENTESISIZQ");
		comparacion();
		match("PARENTESISDER");
		match("ENTONCES");
		while(!(lookahead.type.toString().equals("FINSI"))){
			enunciado();	
		}
		consume();
	}
	
	private void mientras() {
		match("MIENTRAS");
		match("PARENTESISIZQ");
		comparacion();
		match("PARENTESISDER");
		while(!(lookahead.type.toString().equals("FINMIENTRAS"))){
			enunciado();
		}
		consume();
	}
	
	private void comparacion() {
		valor();
		match("OPERACIONAL");
		valor();
	}
	
	private void declaraciones() {
		if (lookahead.type.toString().equals("TIPO")){
			String typeAux = lookahead.data;
			consume();
			match("DOSPUNTOS");
			String currentToken = lookahead.data;
			lista_variables(typeAux);
			if (lookahead.type.toString().equals("PUNTOCOMA")) {
				consume();
				declaraciones();
			}
			else if(lookahead.type.toString().equals("IGUAL")) {
				consume();
				Integer r = operacion();
				if (r.intValue() != 0) {
					symbolTable.symContent.replace(currentToken,r);
				}
				if (lookahead.type.toString().equals("PUNTOCOMA")) {
					consume();
					declaraciones();
				}
			}
		}
	}
	
	private void lista_variables(String type) {
		
		String token = lookahead.data;
		Symbol variable = symbolTable.resolve(token);
		Symbol tipo = symbolTable.resolve(type);
		if (lookahead.type.toString().equals("VARIABLE") && variable == null && tipo != null) {
			symbolTable.define(new VariableSymbol(token,tipo));
		}	
		else
			throw new Error("Variable previamente declarada o tipo in existente");
		
		match("VARIABLE");
			
	}
	
//	private void agregaSymbol() {
//		int i = 0;
//		Symbol tipo = symbolTable.resolve(lookahead.data);
//		if (tipo != null){
//			for (String s : variables) {
//				vSymbol = new VariableSymbol(variables.get(i++),tipo);
//				symbolTable.define(vSymbol);
//			}
//		}
//	}
	
	private int existe(String token) {
		
		Symbol tipo = symbolTable.resolve(lookahead.data);
		if (tipo == null) 
			throw new Error("Variable existente "+lookahead.data);
		
		return symbolTable.symContent.get(tipo.name).intValue();
			
	}
	
}
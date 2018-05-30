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
	
	private int auxValue;
	private VariableSymbol vSymbol;
	private SymbolTable symbolTable = new SymbolTable() ;
	private ArrayList<String> variables = new ArrayList<String>();
	
	public PseudoParser(PseudoLexer input) { 
		super(input); 
		programa();
	}

	private void programa(){    
		match("INICIOPROGRAMA");
		interCode = "inicio \n";
		declaraciones();
		enunciados();
		escribirIntermedio(interCode);
	}
	
	private void enunciados() {
		
		while(!(lookahead.type.toString().equals("FINPROGRAMA"))){
			enunciado();
		}
		consume();
	}

	private void enunciado() {
		if(lookahead.type.toString().equals("VARIABLE"))
			asignacion();
		else if(lookahead.type.toString().equals("MOVIMIENTO"))
			mover();
		else if (lookahead.type.toString().equals("FOR"))
			ciclo();
		else if(lookahead.type.toString().equals("IF"))
			condicionalIF();
		
	}

	private void mover() {
		match("MOVIMIENTO");
		match("GUIONBAJO");
		tipo_movimiento();
		if (lookahead.type.toString().equals("MOVIMIENTO"))
			mover();
	}

	private void tipo_movimiento() {
		auxValue = 0;
		interCode += "mov_"+lookahead.data+"(";
		match("MOVIMIENTOS");
		match("PARENTESISIZQ");
		auxValue = valor();
		interCode += lookahead.data;
		consume();
		if (lookahead.type.toString().equals("OPERITMETICO")) {
			auxValue = operacion(auxValue);
			interCode += ") \n";
		}
		match("PARENTESISDER");
	}

	private void asignacion() {
		int auxValor = 0;
		int r;
		String currentVariable = lookahead.data;
		existe(currentVariable);
		match("VARIABLE");
		match("IGUAL");
		auxValor = valor();
		consume();
		if (lookahead.type.toString().equals("OPARITMETICO")) {
			interCode += currentVariable +" = "+auxValor+" ";
			r = operacion(auxValor);
			symbolTable.symContent.replace(currentVariable,r);
		}
		else {
			symbolTable.symContent.replace(currentVariable,auxValor);
			interCode +="\t"+currentVariable+" = "+ auxValor+ "\n";
		}
	}
	
	
	private int operacion(int x) {
		int resultado = x;
		auxValue = 0;
		if(lookahead.type.toString().equals("OPARITMETICO")) {
			
			String operador = lookahead.data; // Optenemos el operador 
			consume();
			
			
			auxValue = valor();	// Optenemos el valor
			consume();
			
			
			if (operador.equals("+") && !lookahead.type.toString().equals("OPARITMETICO")) {
				interCode +="+ "+auxValue+"\n";
				return resultado + auxValue;
			}
			else if (operador.equals("-") && !lookahead.type.toString().equals("OPARITMETICO")){
				interCode +="- "+auxValue+"\n";
				return resultado - auxValue;
			}
			else if (operador.equals("*") && !lookahead.type.toString().equals("OPARITMETICO")) {
				interCode +="* "+auxValue+"\n";
				return resultado * auxValue;
			}
			else if (operador.equals("/") && !lookahead.type.toString().equals("OPARITMETICO")) {
				interCode +="/ "+auxValue+"\n";
				return resultado / auxValue;
			}
			
			else {
				if (operador.equals("+")) {
					interCode += "+ "+auxValue+" ";
					return operacion(resultado) + auxValue;
				}
				
				else if (operador.equals("-")) {
					interCode +="- "+auxValue+" ";
					return operacion(resultado) - auxValue;
				}
				
				else if (operador.equals("*")) {
					interCode +="* "+auxValue+" ";
					return operacion(resultado) * auxValue;
				}
				
				else if (operador.equals("/")) {
					interCode +="/ "+auxValue+" ";
					return operacion(resultado) / auxValue;
				}
			}
		}
		else
			throw new Error("Error en operacion cerca de: "+lookahead.data);
		return 0;
	}
	
	private int valor() {
		int valor;
		if (lookahead.type.toString().equals("NUMERO")){
			valor = Integer.parseInt(lookahead.data);
			return valor;
		}
		else if (lookahead.type.toString().equals("VARIABLE")){
			valor = existe(lookahead.data);
			return valor;
		}
		return -1;
	}
	
	private void declaraciones() {
		int auxValue = 0;
		int r = 0;
		if (lookahead.type.toString().equals("TIPO")){
			String typeAux = lookahead.data;
			consume();
			match("DOSPUNTOS");
			String currentToken = lookahead.data;
			lista_variables(typeAux);
			match("VARIABLE");
			
			if (lookahead.type.toString().equals("PUNTOCOMA")) {
				consume();
				symbolTable.symContent.replace(currentToken,0);
				declaraciones();
			}
			else if(lookahead.type.toString().equals("IGUAL")) {
				consume();
				if (lookahead.type.toString().equals("NUMERO") || 
						lookahead.type.toString().equals("VARIABLE")) {
					
					auxValue = valor();
					consume();
					
					if (lookahead.type.toString().equals("PUNTOCOMA")) {
						symbolTable.symContent.replace(currentToken,auxValue);
						consume();
						declaraciones();
					}
					else {
						r = operacion(auxValue);
						symbolTable.symContent.replace(currentToken,r);
						if (lookahead.type.toString().equals("PUNTOCOMA")) {
							consume();
							declaraciones();
						}
						else
							throw new Error("Falta ';' serca de :"+lookahead.data);
					}
				}
				else
					throw new Error("Error cerca de :"+lookahead.data);
				
			}else
				throw new Error("Error cerca de :"+lookahead.data);
			
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
	}
	
	private int existe(String token) {
		
		Symbol tipo = symbolTable.resolve(lookahead.data);
		if (tipo == null) 
			throw new Error("Variable no existe ["+lookahead.data+"]");
		
		return symbolTable.symContent.get(tipo.name).intValue();		
	}
	
	public void ciclo() {
		match("FOR");
		match("PARENTESISIZQ");
		asignacion();
		match("PUNTOCOMA");
		comparacion();
		match("PUNTOCOMA");
		asignacion();
		match("PARENTESISDER");
		while(!(lookahead.type.toString().equals("ENDF")))
			enunciado();
		match("ENDF");
	}

	private void comparacion() {
	 int n1,n2;
	 n1 = valor();
	 consume();
	 match("OPERACIONAL");
	 n2 = valor();
	 consume();
	}
	
	public void condicionalIF() {
		match("IF");
		match("PARENTESISIZQ");
		comparacion();
		match("PARENTESISDER");
		while (!(lookahead.type.toString().equals("ENDIF")))
			enunciado();
		match("ENDIF");
	}
	
}
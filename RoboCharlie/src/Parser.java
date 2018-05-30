import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import Lexer.Lexer;
import PseudoLexer.*;
import PseudoLexer.PseudoLexer.Token;

public class Parser {
	
	public Lexer input;
	PseudoLexer pseudoInput;
	public Token lookahead;
	public String interCode = "";
	
	public Parser(Lexer in){
		input = in;
		consume();
	}
	
	public Parser(PseudoLexer in){
		pseudoInput = in;
		consume();
	}

	public void match(String x) {
		
		String siguiente = lookahead.type.toString();

		if(siguiente.equals(x)) {
			consume();
		}
		else throw new Error("Expecting");
	}
	public void consume() { lookahead = pseudoInput.nextToken(); }
	
	public void escribirIntermedio(String interCode) {
		try{
			File file = new File ("src/codeIntermedio.rob");
			BufferedWriter out = new BufferedWriter(new FileWriter(file)); 
			out.write(interCode);
			out.close();
		}
		catch(Exception e) {
			System.out.println("Error "+e);
		}
	}
}

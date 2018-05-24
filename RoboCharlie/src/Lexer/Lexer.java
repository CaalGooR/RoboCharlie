package Lexer;

public abstract class Lexer {
	protected static final char EOF = (char)-1;
	protected static final int EOF_TYPE = 1;
	String input;
	int index = 0;
	protected char c;
	
	public Lexer(String d) {
		this.input = d;
		c = input.charAt(index);
	}
	public void consume() {
		index++;
		if(index >= input.length())
			c = EOF;
		else
			c = input.charAt(index);
	}
	
	public void match(char x) {
		if(c == x)
			consume();
		else 
			throw new Error("Expecting: " + x + "; Found: " + c);
	}
}

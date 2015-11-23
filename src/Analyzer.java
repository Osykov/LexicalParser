import java.util.ArrayList;
import java.util.regex.*;

public class Analyzer {
	private ArrayList<Pattern> patterns;
	private int[] textByTokens;
	private String text;
	
	public Analyzer(String text) {
		patterns = getPatterns();
		this.text = text;
		textByTokens = new int[text.length()];
		for (int i = 0; i < textByTokens.length; i++) {
			textByTokens[i] = -1;
		}
	}
	
	public void analyze() {
		int start = 0;
		while(true) {
			int token = -1;
			int first_entry = Integer.MAX_VALUE;
			for (int i = 0; i < patterns.size(); i++) {
				Matcher m = patterns.get(i).matcher(text);
				if (m.find(start) && m.start() < first_entry) {
					first_entry = m.start();
					token = i;
				}
			}
			if (token == -1)
				break;
			Matcher m = patterns.get(token).matcher(text);
			m.find(start);
			setToken(m.start(), m.end(), token);
			start = m.end();
		}
	}
	
	private void setToken(int start, int end, int token) {
		for (int i = start; i < end; i++) {
			if (textByTokens[i] == -1)
				textByTokens[i] = token;
		}
	}
	
	private ArrayList<Pattern> getPatterns() {
		ArrayList<Pattern> patterns = new ArrayList<>();
		patterns.add(Pattern.compile("/\\*((.|\\n)+?)\\*/|//.*?$", Pattern.MULTILINE | Pattern.DOTALL)); //0 comment
		patterns.add(Pattern.compile("#.*"));                                                            //1 directive
		patterns.add(Pattern.compile("('([^'\\\\\\n)]|\\\\.)*?')|(\"([^'\\\\\\n)]|\\\\.)*?\")"));        //2 char sequence
		String reservedW= "(" + getPatternFromStrings(getReservedWords()) + ")\\s";
		patterns.add(Pattern.compile(reservedW));                                                        //3 reserved words
		patterns.add(Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*"));                                         //4 identifier
		patterns.add(Pattern.compile("([+-]?[0-9]+\\.[0-9]*|[+-]?[0-9]*\\.[0-9]+)(e[1-9][0-9]*)?"));     //5 float
		patterns.add(Pattern.compile("([+-]?(0x[0-9a-fA-F]+|0[0-7]*|[1-9][0-9]*))(e[1-9][0-9]*)?"));     //6 integer (hex, dec, 8)
		patterns.add(getPatternFromStrings(getOperators()));											 //7 operator
		patterns.add(getPatternFromStrings(getSeparators()));											 //8 separator
		return patterns;
	}
	
	private Pattern getPatternFromStrings(String[] arr) {
		StringBuilder pattern = new StringBuilder("");
		
		for (int i = 0; i < arr.length; i++) {
			pattern.append(arr[i]+"|");
		}
		pattern.delete(pattern.length() - 1, pattern.length());
		return Pattern.compile(pattern.toString());
	}
	
	private String[] getReservedWords() {
		String[] reservedWords = { "auto", "else", "long", "switch",
				 "break", "enum", "register", "typedef",
	             "case", "extern", "return", "union",
	             "char", "float", "short", "unsigned",
	             "const", "for", "signed", "void",
	             "continue", "goto", "sizeof", "volatile",
				 "default", "if", "static", "while",
				 "do", "int", "struct", "_Packed",
				 "double" };
		return reservedWords;
	}
	
	private String[] getOperators() {
		String[] operators = { ">>=", "<<=", "==", "!=", "\\+\\+", "--", ">=", "<=",
				 "&&", "\\|\\|", "<<", ">>", "\\+=", "-=", "\\*=", "/=", "%=", "&=", "\\^=",
				 "\\|=", "\\?", "\\+", "-", "\\*", "/", "%", "\\|", ">", "<", "!", "&", "\\^",
				 "~", "=" };
		return operators;
	}
	
	private String[] getSeparators() {
		String[] separators = { ";", ",", ".", ":", "\\(", "\\)", "\\[", "\\]", "\\{", "\\}" };
		return separators;
	}
	
	
	
	public String getHtmlString() {
		StringBuilder htmlText = new StringBuilder("<body style=\"background:lightgrey;\">");
		for (int i = 0; i < text.length(); i++) {
			if (textByTokens[i] != -1) {
				htmlText.append("<span style=\"color:");
				switch (textByTokens[i]) {
				case 0:
					htmlText.append("grey");
					break;
				case 1:
					htmlText.append("BurlyWood");
					break;
				case 2:
					htmlText.append("orange");
					break;
				case 3: 
					htmlText.append("blue");
					break;
				case 4: 
					htmlText.append("green");
					break;
				case 5: case 6:
					htmlText.append("violet");
					break;
				case 7: 
					htmlText.append("red");
					break;
				case 8:
					htmlText.append("black");
					break;
				}
				htmlText.append("\"><b>" + text.charAt(i) + "</b></span>");
			}
			else if (text.charAt(i) == '\n') {
				htmlText.append("<br>");
			}
		}
		htmlText.append("</body>");
		return htmlText.toString();
	}
}

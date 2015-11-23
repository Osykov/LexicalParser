public class Test {
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java input output");
		}
		
		String sourceText = new Reader(args[0]).read();
		
		Analyzer analyzer = new Analyzer(sourceText);
		analyzer.analyze();
		
		new Writer().writeToHtmlFile(args[1], analyzer.getHtmlString());
	}
	
}

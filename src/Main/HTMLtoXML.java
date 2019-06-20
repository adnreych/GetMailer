package Main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLtoXML {
	private MakeHtml html;
	private Pattern begin = Pattern.compile("<.*>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	private Pattern end = Pattern.compile("<\\/html>.*", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public HTMLtoXML(MakeHtml html) {
		this.html = html;
	}

	public String htmlToXml() {
		StringBuilder source = html.getHtml();
		Matcher matcher = begin.matcher(source);
		String s = matcher.replaceFirst("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		return s;
	}

	private String cleanFooter() {
		Matcher matcher = end.matcher(htmlToXml());
		String s = matcher.replaceAll("</html>");
		return s;
	}

	public static void main(String[] args) throws IOException {
		HTMLtoXML htx = new HTMLtoXML(new MakeHtml(new StringBuilder("https://glloss.ru")));
		// System.out.println(htx.htmlToXml());

		FileWriter writer = new FileWriter("test1", false);
		writer.write(htx.cleanFooter().toString());
		writer.flush();
		writer.close();

	}

}

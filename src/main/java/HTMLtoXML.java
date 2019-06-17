import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLtoXML {
	private MakeHtml html;
	private Pattern begin = Pattern.compile("<.*>", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	private Pattern end = Pattern.compile("<\\/html>.*", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

	HTMLtoXML(MakeHtml html) {
		this.html = html;
	}

	String htmlToXml() {
		StringBuilder source = html.getHtml();
		Matcher matcher = begin.matcher(source);
		String s = matcher.replaceFirst("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		matcher = end.matcher(s);
		s = matcher.replaceAll("<\\/html>");

		return s;
	}

}

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GetMail2 {
	private ArrayList<StringBuilder> query;

	public ArrayList<StringBuilder> getQuery() {
		return query;
	}

	public void setQuery(ArrayList<StringBuilder> query) {
		this.query = query;
	}

	GetJson gj = new GetJson();

	// Pattern pattern = Pattern.compile("(?<=mailto:)[[_0-9a-zA-Z]*@\\.]+",
	// Pattern.MULTILINE);
	Pattern pattern = Pattern.compile("(?<=mailto:)[//w*@\\.]+", Pattern.MULTILINE);

	private ArrayList<String> siteDomains = new ArrayList<String>();

	void fillSiteDomains() {
		for (StringBuilder q : query) {
			String s = gj.getUrl(q);
			siteDomains.add(s.substring(1, s.length() - 1)); // delete "" in JSON request
		}
	}

	private LinkedHashSet<String> emails = new LinkedHashSet<>();

	void getMail() {

	}

	void convertXML() throws ParserConfigurationException, SAXException, IOException {
		for (String domain : siteDomains) {
			SourceHtml sh = new SourceHtml(new StringBuilder(domain));
			StringBuilder sourceDomain = sh.getSourceHtml();
			ByteArrayInputStream input = new ByteArrayInputStream(sourceDomain.toString().getBytes("UTF-8"));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(input);
			NodeList tags = document.getDocumentElement().getElementsByTagName("p");
			for (int i = 0; i < tags.getLength(); i++) {
				System.out.println(tags.item(i).getTextContent());
			}
		}
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		ArrayList<StringBuilder> input = new ArrayList<StringBuilder>();
		input.add(new StringBuilder("glloss label"));
		// input.add(new StringBuilder("экмус"));
		// input.add(new StringBuilder("экоумвельт"));
		// input.add(new StringBuilder("30-06"));
		GetMail2 gm = new GetMail2();
		gm.setQuery(input);
		gm.fillSiteDomains();
		gm.convertXML();
	}

}

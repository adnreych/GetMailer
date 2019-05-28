import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMail {
	private ArrayList<StringBuilder> query;

	public ArrayList<StringBuilder> getQuery() {
		return query;
	}

	public void setQuery(ArrayList<StringBuilder> query) {
		this.query = query;
	}

	GetJson gj = new GetJson();

	Pattern pattern = Pattern.compile("[\\w-]+@[\\w-]+\\.[a-z]+", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

	public ArrayList<String> siteDomains = new ArrayList<String>();

	void fillSiteDomains() {
		for (StringBuilder q : query) {
			String s = gj.getUrl(q);
			siteDomains.add(s.substring(1, s.length() - 1)); // delete "" in JSON request
		}
	}

	private LinkedHashSet<String> emails = new LinkedHashSet<>();

	void getMail() {
		for (String domain : siteDomains) {
			SourceHtml siteSource = new SourceHtml(new StringBuilder(domain));
			StringBuilder source = siteSource.getSourceHtml();
			Matcher matcher = pattern.matcher(source);
			while (matcher.find()) {
				try {
					emails.add(source.substring(matcher.start(), matcher.end()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(emails);
	}

}

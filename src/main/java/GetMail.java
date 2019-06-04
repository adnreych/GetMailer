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

	private ArrayList<String> siteDomains = new ArrayList<String>();
	private ArrayList<String> notFound = new ArrayList<String>();

	void fillSiteDomains() {
		for (StringBuilder q : query) {
			String s = gj.getUrl(q);
			if (!s.equals(""))
				siteDomains.add(s.substring(1, s.length() - 1)); // delete "" in JSON request
			else
				notFound.add("По запросу " + q + " сайт не найден");

		}
	}

	private LinkedHashSet<String> emails = new LinkedHashSet<>();
	private ArrayList<String> orgsEmails = new ArrayList<>();

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
			orgsEmails.add(domain + " " + emails);
			emails.clear();
		}
		System.out.println(orgsEmails);
		System.out.println(notFound);
	}

	public static void main(String[] args) {
		ArrayList<StringBuilder> input = new ArrayList<StringBuilder>();
		input.add(new StringBuilder("rush agency"));
		input.add(new StringBuilder("irobot-crimea"));
		input.add(new StringBuilder("экмус"));
		input.add(new StringBuilder("экоумвельт"));
		input.add(new StringBuilder("течпорт"));

		GetMail gm = new GetMail();
		gm.setQuery(input);
		gm.fillSiteDomains();
		gm.getMail();
	}

}

package Main;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMail {
	private SearchUrl gj = new SearchUrl();
	private Pattern pattern = Pattern.compile("[\\w-]+@[\\w-]+\\.[a-z]+", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	private Pattern innerPattern = Pattern.compile("[A-Za-z\\.]+(?=\">Контакты<)",
			Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
	private ArrayList<StringBuilder> query;
	private ArrayList<String> siteDomains = new ArrayList<String>();
	private ArrayList<String> notFound = new ArrayList<String>();
	private LinkedHashSet<String> emails = new LinkedHashSet<>();
	private ArrayList<String> orgsEmails = new ArrayList<>();

	GetMail(ArrayList<StringBuilder> query) {
		this.query = query;
	}

	private void fillSiteDomains() {
		for (StringBuilder q : query) {
			String s = gj.getUrl(q);
			if (!s.equals(""))
				siteDomains.add(s.substring(1, s.length() - 1)); // delete "" in JSON request
			else
				notFound.add("По запросу " + q + " сайт не найден");
		}
	}

	private String getInnerPage(StringBuilder domain) {
		String inner = "Страница Контакты не найдена";
		MakeHtml siteSource = new MakeHtml(new StringBuilder(domain));
		StringBuilder source = siteSource.getHtml();
		Matcher innerMatcher = innerPattern.matcher(source.toString());
		while (innerMatcher.find())
			inner = source.substring(innerMatcher.start(), innerMatcher.end());
		return inner;
	}

	private void searchMails(String domain) {
		MakeHtml siteSource = new MakeHtml(new StringBuilder(domain));
		StringBuilder source = siteSource.getHtml();
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			try {
				emails.add(source.substring(matcher.start(), matcher.end()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		orgsEmails.add(domain + " " + emails + "\n");
	}

	void getMail() {
		fillSiteDomains();
		for (String domain : siteDomains) {
			searchMails(domain);
			if (emails.isEmpty()) {
				String innerPage = domain + getInnerPage(new StringBuilder(domain));
				searchMails(innerPage);
			}
			emails.clear();
		}
		System.out.println(orgsEmails);
		System.out.println(notFound);
	}

	public static void main(String[] args) {
		ArrayList<StringBuilder> input = new ArrayList<StringBuilder>();
		input.add(new StringBuilder("эльдорадо"));
		GetMail gm = new GetMail(input);
		gm.getMail();

	}
}

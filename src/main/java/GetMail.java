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

	Pattern pattern = Pattern.compile("\\w+-@\\w+-*\\.[a-z]+", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

	public ArrayList<String> siteDomains = new ArrayList<String>();

	void fillSiteDomains() {
		for (StringBuilder q : query) {
			String s = gj.getUrl(q);
			siteDomains.add(s.substring(1, s.length() - 1)); // delete "" in JSON request
		}
	} // оптимизировать регулярку
		// проверить что быстрее делается - обработка по одному значению
		// последовательно, или
		// сначала собираем URL-адреса, а потом ищем почты

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

	public static void main(String[] args) {
		ArrayList<StringBuilder> input = new ArrayList<StringBuilder>();
		input.add(new StringBuilder("rush agency")); // не обрабатывает, возможно потому что не все жрет
		input.add(new StringBuilder("irobot-crimea")); // не обрабатывает, т.к. api не находит сайт
		input.add(new StringBuilder("экмус"));
		input.add(new StringBuilder("экоумвельт"));
		input.add(new StringBuilder("течпорт"));
		// input.add(new StringBuilder("30-06")); //не обрабатывает
		GetMail gm = new GetMail();
		gm.setQuery(input);
		gm.fillSiteDomains();
		// System.out.println(gm.siteDomains);
		gm.getMail();
	}

}

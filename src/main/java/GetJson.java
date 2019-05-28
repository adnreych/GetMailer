import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetJson {

	private StringBuilder part1 = new StringBuilder("https://search-maps.yandex.ru/v1/?text=");
	private StringBuilder part2 = new StringBuilder(
			"&type=biz&lang=ru_RU&results=1&apikey=024a4de3-f595-4d24-a2ab-b553cb0a3d02");

	String getUrl(StringBuilder adress) {

		StringBuilder concatUrl = new StringBuilder().append(part1).append(adress).append(part2);
		SourceHtml jsonStream = new SourceHtml(concatUrl);

		String s = jsonStream.getSourceHtml().toString();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode newNode = mapper.readTree(s);
			return newNode.findPath("url").toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "IOException";
		} // bad catch

	}

	public static void main(String[] args) {
		GetJson gj = new GetJson();
		StringBuilder test = new StringBuilder("30-06");
		System.out.println(gj.getUrl(test));
	}

}

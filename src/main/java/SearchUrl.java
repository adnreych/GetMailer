import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchUrl {

	private StringBuilder part1 = new StringBuilder("https://search-maps.yandex.ru/v1/?text=");
	private StringBuilder part2 = new StringBuilder(
			"&type=biz&lang=ru_RU&results=1&apikey=024a4de3-f595-4d24-a2ab-b553cb0a3d02");

	String getUrl(StringBuilder adress) {

		StringBuilder concatUrl = new StringBuilder().append(part1).append(adress).append(part2);
		MakeHtml jsonStream = new MakeHtml(concatUrl);

		String s = jsonStream.getHtml().toString();
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode newNode = mapper.readTree(s);
			return newNode.findPath("url").toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

}

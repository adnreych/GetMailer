import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.SSLHandshakeException;

public class MakeHtml {

	public StringBuilder html = new StringBuilder();
	public URL url;
	public HttpURLConnection urlConn;
	public BufferedReader reader;

	public StringBuilder getHtml() {
		this.makeHtml();
		return html;
	}

	MakeHtml(StringBuilder s) {
		try {
			this.url = new URL(s.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void makeHtml() {
		try {
			if (this.isRedirected()) {
				this.protocolHandler();
			}
			try {
				reader = new BufferedReader(new InputStreamReader(url.openStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					StringBuilder ln = new StringBuilder(line);
					html.append(ln + "\n");
				}
				reader.close();
			} catch (SSLHandshakeException e) {
				// e.printStackTrace();
				SSLHandshakeHandler();
				makeHtml();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void SSLHandshakeHandler() {
		try {
			if (url.getHost().contains("www."))
				this.url = new URL("http://" + url.getHost().substring(4));
			else
				this.url = new URL("http://www" + url.getHost());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean isRedirected() throws IOException {
		urlConn = (HttpURLConnection) url.openConnection();
		if (urlConn.getResponseCode() == 301)
			return true;
		else
			return false;
	}

	void protocolHandler() throws MalformedURLException {
		if (url.getProtocol().equals("http"))
			this.url = new URL("https://" + url.getHost() + "/");
		else if (url.getProtocol().equals("https"))
			this.url = new URL("http://" + url.getHost() + "/");
	}

	public static void main(String[] args) {

	}
}

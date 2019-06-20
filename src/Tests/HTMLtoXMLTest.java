package Tests;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import Main.HTMLtoXML;
import Main.MakeHtml;

public class HTMLtoXMLTest {
	private ArrayList<HTMLtoXML> htmlToXml = new ArrayList<HTMLtoXML>();

	@Before
	public void constructorsHTMLtoXML(ArrayList<MakeHtml> html) {
		for (MakeHtml url : html) {
			htmlToXml.add(new HTMLtoXML(url));
		}
	}

	@Test
	public void testAssertThatFilesHaveXmlHeader() {
		for (HTMLtoXML s : htmlToXml) {
			String[] htmlToXmlParts = s.htmlToXml().split("\n");
			assertThat(htmlToXmlParts[0], is("<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\"?>"));
		}
	}
}

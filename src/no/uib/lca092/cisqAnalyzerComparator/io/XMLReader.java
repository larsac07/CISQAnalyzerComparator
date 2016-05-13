package no.uib.lca092.cisqAnalyzerComparator.io;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class XMLReader {

	public XMLReader() {
		// TODO Auto-generated constructor stub
	}

	public Document getXMLDoc(File xmlFile) {
		try {
			Document doc = Jsoup.parse(xmlFile, "utf-8");
			return doc;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

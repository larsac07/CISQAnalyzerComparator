package no.uib.lca092.cisqAnalyzerComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import no.uib.lca092.cisqAnalyzerComparator.compare.XMLComparator;
import no.uib.lca092.cisqAnalyzerComparator.io.IOUtils;
import no.uib.lca092.cisqAnalyzerComparator.io.XMLReader;

public abstract class Main {

	private final static String LOG_FILES = "Found the following files:";
	private final static String LOG_PARSING = "Parsing to XML:";

	public static void main(String[] args) {
		File folder = new File("/home/lars/Development/java_workspace/SQuIDS/compare");

		String fileEnding = "result.xml";

		List<String> excludes = new ArrayList<>();
		excludes.add(".*result\\.xml");

		List<String> includes = new ArrayList<>();
		includes.add(".*checkstyle.*");
		includes.add(".*Checkstyle.*");
		includes.add(".*RSSOwl 1\\.2\\.4.*");
		includes.add(".*RSSOwl Source.*");
		includes.add(".*rssowl.*");
		includes.add(".*JabRef.*");
		includes.add(".*Log4j.*");
		includes.add(".*log4j.*");

		List<File> files = IOUtils.getFiles(folder, fileEnding, excludes, includes);
		System.out.println(LOG_FILES);
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}

		List<Document> docs = new ArrayList<>();
		XMLReader reader = new XMLReader();
		System.out.println(LOG_PARSING);
		for (File xmlFile : files) {
			System.out.println(xmlFile.getAbsolutePath());
			Document doc = reader.getXMLDoc(xmlFile);
			docs.add(doc);
		}

		XMLComparator comp = new XMLComparator();
		comp.compare(docs);
	}

}

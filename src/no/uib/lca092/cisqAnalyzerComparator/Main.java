package no.uib.lca092.cisqAnalyzerComparator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import no.uib.lca092.cisqAnalyzerComparator.io.IOUtils;
import no.uib.lca092.cisqAnalyzerComparator.io.XMLReader;

public abstract class Main {

	public static void main(String[] args) {
		File folder = new File("/home/lars/Development/java_workspace/SQuIDS/compare");
		String fileEnding = "result.xml";
		List<String> excludes = new ArrayList<>();
		excludes.add(".*result\\.xml");
		List<String> includes = new ArrayList<>();
		includes.add(".*checkstyle.*");
		includes.add(".*Checkstyle.*");
		includes.add(".*RSSOwl.*");
		includes.add(".*rssowl.*");
		includes.add(".*SQuIDS/TV-Browser.*");
		includes.add(".*TV-Browser 2\\.2\\.5.*");
		includes.add(".*JabRef.*");
		includes.add(".*Log4j.*");
		List<File> files = IOUtils.getFiles(folder, fileEnding, excludes, includes);
		System.out.println(files.size() + " files");
		for (File file : files) {
			System.out.println(file.getAbsolutePath());
		}
		XMLReader reader = new XMLReader();
		for (File xmlFile : files) {
			System.out.println(xmlFile.getAbsolutePath());
			Document doc = reader.getXMLDoc(xmlFile);
			Elements items = doc.getElementsByTag("item");
			for (Element item : items) {
				System.out.println("-----------------------------------");
				System.out.println(item.getElementsByTag("metric").get(0));
				System.out.println(item.getElementsByTag("file").get(0));
				System.out.println(item.getElementsByTag("line").get(0));
				System.out.println(item.getElementsByTag("detail").get(0));
				System.out.println("-----------------------------------");
				System.out.println();
			}
		}
	}

}

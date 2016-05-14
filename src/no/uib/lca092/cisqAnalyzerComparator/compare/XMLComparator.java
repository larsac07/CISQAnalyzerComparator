package no.uib.lca092.cisqAnalyzerComparator.compare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class XMLComparator {

	public void compare(List<Document> list) {
		Map<String, Element> squidsIssuesMap = new HashMap<>();
		Map<String, Element> museIssuesMap = new HashMap<>();

		// Divide documents into two separate maps of metrics and items (issues)
		System.out.println("Dividing documents");
		for (Document doc : list) {
			Elements items = doc.select("item");
			for (Element item : items) {
				String metric = getMetricID(item.select("metric").text());
				if (includedMetric(metric)) {
					String file = getFileID(item.select("file").text());
					String line = item.select("line").text();
					String issueID = metric + ":" + file + ":" + line;
					Map<String, Element> issuesMap;
					if (item.select("tool").text().equals("SQuIDS")) {
						issuesMap = squidsIssuesMap;
					} else {
						issuesMap = museIssuesMap;
					}
					issuesMap.put(issueID, item);
				}
			}
		}

		// Sort metrics
		System.out.println("Sorting metrics");
		Set<String> squidsIssues = sortSet(squidsIssuesMap.keySet());
		Set<String> museIssues = sortSet(museIssuesMap.keySet());

		// Compare lists
		System.out.println("Comparing lists");

		Set<String> metricAndFiles = new LinkedHashSet<>();
		for (String issueID : squidsIssues) {
			metricAndFiles.add(issueID.substring(0, issueID.lastIndexOf(":")));
		}
		for (String issueID : museIssues) {
			metricAndFiles.add(issueID.substring(0, issueID.lastIndexOf(":")));
		}
		metricAndFiles = sortSet(metricAndFiles);

		squidsIssues.removeAll(museIssues);
		museIssues.removeAll(squidsIssues);

		String currentMetricAndFile = "";
		for (String metricAndFile : metricAndFiles) {
			if (!currentMetricAndFile.equals(metricAndFile)) {
				currentMetricAndFile = metricAndFile;
				System.out.println();
				System.out.println("Metric and File " + metricAndFile + ":");
			}
			System.out.println("SQuIDS:");
			for (String issueID : squidsIssues) {
				if (issueID.substring(0, issueID.lastIndexOf(":")).equals(metricAndFile)) {
					System.out.println(" - " + issueID);
				}
			}
			System.out.println("MUSE:");
			for (String issueID : museIssues) {
				if (issueID.substring(0, issueID.lastIndexOf(":")).equals(metricAndFile)) {
					System.out.println(" - " + issueID);
				}
			}
		}
	}

	private boolean includedMetric(String metric) {
		List<String> excludedMetrics = new ArrayList<>();
		excludedMetrics.add("M01");
		excludedMetrics.add("M02");
		excludedMetrics.add("M03");
		excludedMetrics.add("M04");
		excludedMetrics.add("M05");
		// excludedMetrics.add("M06");
		excludedMetrics.add("M07");
		excludedMetrics.add("M08");
		excludedMetrics.add("M09");
		excludedMetrics.add("M10");
		excludedMetrics.add("M11");
		excludedMetrics.add("M12");
		excludedMetrics.add("M13");
		excludedMetrics.add("M14");
		excludedMetrics.add("M15");
		// excludedMetrics.add("M16");
		excludedMetrics.add("M17");
		excludedMetrics.add("M18");
		excludedMetrics.add("M19");
		excludedMetrics.add("M20");
		excludedMetrics.add("M21");
		for (String excludedMetric : excludedMetrics) {
			if (metric.equals(excludedMetric)) {
				return false;
			}
		}
		return true;
	}

	protected <E> Set<E> sortSet(Set<E> set) {
		Set<E> sortedSet = new TreeSet<>(set);
		return sortedSet;
	}

	protected String getFileID(String text) {
		return text.replaceAll(".*java_workspace/.*/src/|.*PROJECT_ROOT_JAVA/.*/src/", "src/")
				.replaceAll("checkstyle/com/puppycrawl", "com/puppycrawl").replaceAll("java/net", "net")
				.replaceAll("main/java/org", "org");
	}

	protected String getMetricID(String text) {
		// "CISQMM05MethodUnreachable" or
		// "CisqMaintainability05UnreachableFunctions"
		// = "M05"

		return text.replaceAll("(?i)CISQ([A-Za-z])[^0-9]+([0-9]{2}).*", "$1$2");
	}

}

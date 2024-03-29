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

		// Remove common items
		removeCommonItems(squidsIssues, museIssues);

		String currentMetricAndFile = "";
		final String NL = System.lineSeparator();
		int squidsUniques = 0;
		int museUniques = 0;
		String squidsFiles = "";
		String museFiles = "";
		for (String metricAndFile : metricAndFiles) {
			String output = "";
			boolean print = false;
			if (!currentMetricAndFile.equals(metricAndFile)) {
				currentMetricAndFile = metricAndFile;
				output += NL;
				output += "Metric and File " + metricAndFile + ":" + NL;
			}
			output += "SQuIDS:" + NL;
			for (String issueID : squidsIssues) {
				if (issueID.substring(0, issueID.lastIndexOf(":")).equals(metricAndFile)) {
					output += " - " + issueID + NL;
					print = true;
					squidsUniques++;
					Element squidsIssue = squidsIssuesMap.get(issueID);
					squidsFiles += squidsIssue.select("file").text() + ":" + squidsIssue.select("line").text() + " ";
				}
			}
			output += "MUSE:" + NL;
			for (String issueID : museIssues) {
				if (issueID.substring(0, issueID.lastIndexOf(":")).equals(metricAndFile)) {
					output += " - " + issueID + NL;
					print = true;
					museUniques++;
					Element museIssue = museIssuesMap.get(issueID);
					museFiles += museIssue.select("file").text() + ":" + museIssue.select("line").text() + " ";
				}
			}
			if (print) {
				System.out.println(output);
			}
		}
		System.out.println("SQuIDS unique problems found: " + squidsUniques);
		System.out.println("MUSE unique problems found: " + museUniques);
		System.out.println();
		System.out.println("SQuIDS files: ");
		// System.out.println(squidsFiles);
		System.out.println(squidsFiles.replaceAll("([0-9]) (/)", "$1\n$2"));
		System.out.println();
		System.out.println("MUSE files: ");
		// System.out.println(adaptToLocalPaths(museFiles));
		System.out.println(adaptToLocalPaths(museFiles).replaceAll("([0-9]) (/)", "$1\n$2"));
	}

	private <E> void removeCommonItems(Set<E> set1, Set<E> set2) {
		Set<E> union = new TreeSet<>();
		union.addAll(set1);
		union.addAll(set2);
		for (E e : union) {
			if (set1.contains(e) && set2.contains(e)) {
				set1.remove(e);
				set2.remove(e);
			}
		}
	}

	private String adaptToLocalPaths(String paths) {
		paths = paths.replaceAll("/media/Daten/Studium/Masterarbeit/Benchmarking/PROJECT_ROOT_JAVA",
				"/home/lars/Development/java_workspace");
		paths = paths.replaceAll("JabRef 2.3.1/src", "jabref-2.3.1/src/java");
		paths = paths.replaceAll("RSSOwl 1.2.4/src", "rssowl_1_2_4/src/java");
		paths = paths.replaceAll("Log4j 1.2.15/src", "log4j/src/main/java");
		paths = paths.replaceAll("Checkstyle 4.4/src", "checkstyle/src/checkstyle");
		return paths;
	}

	private boolean includedMetric(String metric) {
		List<String> includedMetrics = new ArrayList<>();
		// includedMetrics.add("M01");
		// includedMetrics.add("M02");
		// includedMetrics.add("M03");
		// includedMetrics.add("M04");
		// includedMetrics.add("M05");
		// includedMetrics.add("M06");
		// includedMetrics.add("M07");
		// includedMetrics.add("M08");
		// includedMetrics.add("M09");
		// includedMetrics.add("M10");
		// includedMetrics.add("M11");
		// includedMetrics.add("M12");
		// includedMetrics.add("M13");
		// includedMetrics.add("M14");
		// includedMetrics.add("M15");
		// includedMetrics.add("M16");
		includedMetrics.add("M17");
		// includedMetrics.add("M18");
		// includedMetrics.add("M19");
		// includedMetrics.add("M20");
		// includedMetrics.add("M21");
		for (String includedMetric : includedMetrics) {
			if (metric.equals(includedMetric)) {
				return true;
			}
		}
		return false;
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

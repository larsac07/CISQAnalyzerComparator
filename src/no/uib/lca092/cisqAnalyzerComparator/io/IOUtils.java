package no.uib.lca092.cisqAnalyzerComparator.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.UnmappableCharacterException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public abstract class IOUtils {

	public static String fileToString(File file) {
		return fileToString(file.toPath());
	}

	public static String fileToString(Path file) {
		String fileString = "";
		List<String> lines = fileToStringLines(file);
		String nl = System.lineSeparator();
		for (String line : lines) {
			fileString += line + nl;
		}

		return fileString;
	}

	public static List<String> fileToStringLines(File file) {
		return fileToStringLines(file.toPath());
	}

	public static List<String> fileToStringLines(Path file) {
		List<String> lines = null;
		try {
			lines = readFileCharsetSafe(file);
		} catch (FileNotFoundException e) {
			System.err.println(e.getClass().getName() + ": Could not find file " + file.toAbsolutePath().toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getClass().getName() + ": Could not find file " + file.toAbsolutePath().toString());
			e.printStackTrace();
		}
		return lines;
	}

	private static List<String> readFileCharsetSafe(Path file) throws IOException {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(file);
			return lines;
		} catch (MalformedInputException | UnmappableCharacterException e) {
			for (String charsetKey : Charset.availableCharsets().keySet()) {
				try {
					Charset charset = Charset.availableCharsets().get(charsetKey);
					lines = Files.readAllLines(file, charset);
				} catch (MalformedInputException | UnmappableCharacterException e2) {

				}
			}
		}
		return lines;
	}

	public static List<File> getFiles(File folder, String fileEnding) {
		return getFiles(folder, fileEnding, null, null);
	}

	public static List<File> getFiles(File folder, String fileEnding, List<String> exclude, List<String> include) {
		return getFiles(folder, fileEnding, exclude, include, null);
	}

	/**
	 * Recursive method to get a flat list of all sub-files to the folder path
	 * provided, or the resource itself if it is a file
	 *
	 * @param folder
	 *            - the resource to search for files in
	 * @param fileEnding
	 *            - the file-ending to search for
	 * @param files
	 *            - the list of files to add to. If null, it will be generated
	 *            anew
	 * @param filters
	 *            - the files and folders to ignore (regex)
	 * @return a flat list of all sub-files to the folder path provided, or the
	 *         folder itself if it is a file
	 */
	private static List<File> getFiles(File folder, String fileEnding, List<String> exclude, List<String> include,
			List<File> files) {
		if (files == null) {
			files = new LinkedList<>();
		}
		String path = folder.getPath().toString();
		if (include(path, exclude, include)) {
			if (folder.isDirectory()) {
				File[] subFiles = folder.listFiles();
				for (File subFile : subFiles) {
					getFiles(subFile, fileEnding, exclude, include, files);
				}
			} else if (folder.isFile()) {
				if (folder.getName().endsWith(fileEnding)) {
					files.add(folder);
				}
			}
		}
		return files;
	}

	public static boolean include(String string, List<String> excludes, List<String> includes) {
		if (excludes == null || excludes.isEmpty()) {
			return true;
		}
		for (String exclude : excludes) {
			if (string.matches(exclude)) {
				if (includes != null) {
					for (String include : includes) {
						if (string.matches(include)) {
							return true;
						}
					}
					return false;
				} else {
					return false;
				}
			}
		}
		return true;
	}
}
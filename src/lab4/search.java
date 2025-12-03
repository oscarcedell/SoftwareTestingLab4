package lab4;

import java.io.File;
import java.util.Scanner;

public class search {

	public static void main(String[] args) {
		
		int idx = 0;
		
		boolean caseInsensitive = false;
		boolean invertedMatch = false;
		
		while (idx < args.length && args[idx].startsWith("-")) {
			// slice "-"
			String option = args[idx].substring(1);

			// If option -i, case-insensitivity, is included:
			caseInsensitive = option.contains("i");
			
			// If option -v, select non-matching lines, is included:
			invertedMatch = option.contains("v");
			
			idx++;
		}

		// If missing arguments
		if (args.length - idx != 2) {
			System.out.println("Use the file searcher with: -options <pattern> <file>");
			return;
		}

		try {
			// Get search pattern
			String pattern = args[idx];
			
			// Read file
			File file = new File(args[idx + 1]);

			// Scan file
			Scanner scan = new Scanner(file);

			// Iterate all lines
			while (scan.hasNextLine()) {
				String line = scan.nextLine();

				boolean lineContainsWord = false;

				// Split the line into array of words
				String[] words = line.split("[,\\s\\.\\;\\:\\!\\?\\'\\\"]");

				// Iterate all words of the line
				for (String word : words) {

					if (caseInsensitive) {
						// Case-insensitive matches
						if (word.equalsIgnoreCase(pattern)) {
							lineContainsWord = true;
							if (!invertedMatch) {
								System.out.println(line);
							}
							break;
						}
					} else {
						// Exact matches
						if (word.equals(pattern)) {
							lineContainsWord = true;
							if (!invertedMatch) {
								System.out.println(line);
							}
							break;
						}
					}
				}
				
				// Print (non-empty) line with no prior match and if invertedMatch option
				if (invertedMatch && !lineContainsWord) {
					if (!line.isBlank()) {
						System.out.println(line);
					}
				}
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

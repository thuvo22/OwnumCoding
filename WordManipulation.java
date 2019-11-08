import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author tv284
 * The application reads a file and do three tasks:
 * Give a total word count.
 * Identify the top 10 words used and display them in sorted order.
 * Pull the last sentence on the file that contains the most used word.
 */
public class WordManipulation {
	public static final int k = 10;
	public static String str = "";
	public static ArrayList<String> validWord = new ArrayList<>();
	public static ArrayList<String> validSentence = new ArrayList<>();
	public static ArrayList<String> topTen = new ArrayList<>();

	public static void topTen(ArrayList<String> validWord, int k) {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		for (String s : validWord) {
			hm.put(s, hm.getOrDefault(s, 0) + 1);
		}
		PriorityQueue<String> q = new PriorityQueue<>(new Comparator<String>() {
			public int compare(String a, String b) {
				if (hm.get(a) == hm.get(b)) {
					return a.compareTo(b);
				}
				return hm.get(b) - hm.get(a);
			}
		});

		for (String key : hm.keySet()) {
			if (key != null)
				q.add(key);
		}

		while (topTen.size() < k) {
			topTen.add(q.poll());
		}

		System.out.println("Top 10 most frequent words descendent:");
		for (String s : topTen) {
			System.out.println(s);
		}
	}

	/**
	 * The method checks if the first string contains the second string.
	 * @param s1  The first string
	 * @param s2 The second string 
	 * @return
	 */
	public static boolean isContain(String s1, String s2) {
		String pattern = "\\b" + s2 + "\\b";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s1);
		return m.find();
	}

	/**
	 * The method is used to find the last sentence contains the most frequent words
	 * @param validSentence
	 * @param topTen
	 */
	public static void findLastSentenceContainsMostUsedWord(ArrayList<String> validSentence, ArrayList<String> topTen) {
		int cnt = 0;
		int maxcnt = 0;

		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		for (int si = 0; si < validSentence.size(); si++) {
			for (int wi = 0; wi < topTen.size(); wi++) {
				if (isContain(validSentence.get(si), topTen.get(wi))) {
					cnt++;
				}
			}
			if (maxcnt <= cnt)
				maxcnt = cnt;
			hm.put(maxcnt, si);
			cnt = 0;
		}

		System.out.println("The last sentence on the file that contains the most used word:" + '\n'
				+ validSentence.get(hm.get(maxcnt) - 2));
	}

	/**
	 * The method finds the total words in the file.
	 * @param str the string that was converted from the file.
	 */
	public static void findTotalWords(String str) {
		String[] sentences = str.split("\\.");
		for (String s : sentences) {
			validSentence.add(s);
		}
		String[] words = str.split("[\\s+]");

		for (String s : words) {
			String strg = removeLastChar(s);
			if (!strg.equals(""))
				validWord.add(strg);
		}
		System.out.println("The total number of words: " + validWord.size());
	}

	/**
	 * The method used to process the string
	 * and remove unnecessary punctuation.
	 * @param s words.
	 * @return  processed word.
	 */
	public static String removeLastChar(String s) {
		s = s.toLowerCase();
		if (s == null || s.length() == 0)
			return "";
		if (s.length() == 1)
			return s;
		if (!Character.isLetter(s.charAt(s.length() - 1)) || (s.charAt(s.length() - 1)) == ' ') {
			return (s.substring(0, s.length() - 1));
		}
		if (s.charAt(0) == '"') {
			return (s.substring(1, s.length()));
		}
		return s;
	}

	public static void main(String[] args) {
		// edit the path of your text file here
		String path = "C:\\Users\\tv284\\Desktop\\Practice\\Ownum\\src\\passage.txt";
		try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
			String line = reader.readLine();

			while (line != null) {
				str += (line + " ");
				line = reader.readLine();
			}
			findTotalWords(str);
			System.out.println();
			topTen(validWord, k);
			System.out.println();
			findLastSentenceContainsMostUsedWord(validSentence, topTen);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

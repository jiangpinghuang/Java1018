package hjp.edu.java.nlp.corpus;

/*
 * RCV.java
 *
 *  Created on: Dec 10, 2016
 *      Author: hjp
 *      E-mail: hjp@whu.edu.cn
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RCV {

	public static void listFile(String dirPath) {
		File file = new File(dirPath);

		if (!file.exists()) {
			System.out.println(dirPath + " is not existed!");
			return;
		} else {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					listFile(files[i].getAbsolutePath());
				} else {
					if (files[i].getAbsolutePath().endsWith(".xml")) {
						System.out.println(files[i].getAbsolutePath());
						readFile(files[i].getAbsolutePath());
					}
				}
			}
		}

	}

	public static void readFile(String filePath) {
		File file = new File(filePath);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println("Line: " + line);
				if (line.contains("<p>")) {
					line = line.replace("<p>", "");
					line = line.replace("</p>", "");
					line = line.replace("&quot;", "");
					line = line.replace("-- ", "");
					line = line.trim();
					System.out.println("Demo: " + line + '\n');
					if (line.contains(". ")) {
						System.out.println(". : " + line);
						String[] sents = line.split("\\. ");
						for (int i = 0; i < sents.length; i++) {
							if (i == (sents.length - 1)) {
								System.out.println("sent:" + sents[i] + "\n");
								writeFile(sents[i]);
							} else {
								System.out.println("sent:" + sents[i] + ".\n");
								writeFile(sents[i] + ".");
							}
						}
					} else
						writeFile(line);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void writeFile(String content) {
		if (!content.contains("  ")) {
			String filePath = "/your/rcv/file/path";
			content = content.substring(0, content.length() - 1);
			content = content + " .";
			content = content.replaceAll(", ", " , ");
			content = content.replaceAll("!", " !");
			content = content.replaceAll("/", " / ");
			content = content.replaceAll("\"", " \" ");
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
				writer.write(content + "\r\n");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		String dirPath = "/your/rcv/file/dir/";
		listFile(dirPath);

	}

}

package hjp.edu.java.phd.scrn;

/*
 * SSTChunker.java
 *
 * Description: Chunker for Stanford Sentiment corpus using OpenNLP packages.
 *  Created on: July 15, 2017
 *      Author: hjp
 *      E-mail: hjp@whu.edu.cn
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import hjp.edu.java.nlp.chunker.OpenNLPChunker;

public class SSTChunker {

	public static void Chunker(String inFile, String outFile) {
		File file = new File(inFile);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				String strline = "";
				String[] sents = line.split("\t");
				String[] tokens = OpenNLPChunker.Tokener(sents[1]);
				String[] tags = OpenNLPChunker.POSTagger(tokens);
				String[] chunkers = OpenNLPChunker.Chunker(tokens, tags);

				strline = sents[0] + "\t" + sents[1] + "\t" + Sequence(tokens) + "\t" + Sequence(tags) + "\t"
						+ Sequence(chunkers);
				System.out.println(strline);
				writeFile(outFile, strline);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String Sequence(String[] tokens) {
		String str = "";

		for (String tok : tokens) {
			if (str.length() == 0) {
				str = tok;
			} else {
				str = str + " " + tok;
			}
		}

		return str;
	}

	public static void writeFile(String filePath, String content) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(content + "\r\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();

		String tar_valid = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/ssc/tar_valid.txt";
		String valid = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/ssc/valid.txt";

		String tar_train = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/ssc/tar_train.txt";
		String train = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/ssc/train.txt";

		String tar_test = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/ssc/tar_test.txt";
		String test = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/ssc/test.txt";

		Chunker(tar_valid, valid);
		Chunker(tar_train, train);
		Chunker(tar_test, test);

		long end = System.currentTimeMillis();
		System.out.println("It costs " + (end - start) / 1000 + " seconds!");
	}

}

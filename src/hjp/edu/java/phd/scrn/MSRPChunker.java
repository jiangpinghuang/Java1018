package hjp.edu.java.phd.scrn;

/*
 * MSRPChunker.java
 *
 * Description: Chunker for MSR paraphrase identification text using OpenNLP packages.
 *  Created on: July 03, 2017
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

public class MSRPChunker {

	public static void Chunker(String inFile, String outFile) {
		File file = new File(inFile);
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("0") || line.startsWith("1")) {
					String strline = "";
					System.out.println(line);
					String[] sents = line.split("\t");

					String[] ltokens = OpenNLPChunker.Tokener(sents[3]);
					String[] rtokens = OpenNLPChunker.Tokener(sents[4]);

					String[] ltags = OpenNLPChunker.POSTagger(ltokens);
					String[] rtags = OpenNLPChunker.POSTagger(rtokens);

					String[] lchunkers = OpenNLPChunker.Chunker(ltokens, ltags);
					String[] rchunkers = OpenNLPChunker.Chunker(rtokens, rtags);

					strline = sents[0] + "\t" + sents[3] + "\t" + Sequence(ltokens) + Sequence(ltags)
							+ Sequence(lchunkers) + sents[4] + "\t" + Sequence(rtokens) + Sequence(rtags)
							+ Sequence(rchunkers);
					System.out.println(strline);
					writeFile(outFile, strline);
				}
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
		str = str + "\t";

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

		String inTrain = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/msc/train.txt";
		String inTest = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/msc/test.txt";

		String outTrain = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/tmp/msc_train.txt";
		String outTest = "/Users/hjp/MacBook/Workspace/Workshop/Corpus/tmp/msc_test.txt";

		String text = "The European Commission, the EU's antitrust enforcer, is expected to issue its decision next spring ? unless a settlement is reached.";
		System.out.println(text);

		String[] tokens = OpenNLPChunker.Tokener(text);
		for (String tok : tokens) {
			System.out.print(tok + " ");
		}
		System.out.println();
		String[] tags = OpenNLPChunker.POSTagger(tokens);
		for (String tag : tags) {
			System.out.print(tag + " ");
		}
		System.out.println();
		String[] cks = OpenNLPChunker.Chunker(tokens, tags);
		for (String ck : cks) {
			System.out.print(ck + " ");
		}
		System.out.println();

		Chunker(inTrain, outTrain);
		Chunker(inTest, outTest);

		long end = System.currentTimeMillis();
		System.out.println("It costs " + (end - start) / 1000 + " seconds!");
	}

}

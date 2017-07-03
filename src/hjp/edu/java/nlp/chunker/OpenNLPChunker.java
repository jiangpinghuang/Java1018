package hjp.edu.java.nlp.chunker;

/*
 * OpenNLPChunker.java
 *
 * Description: Chunker for natural language text using OpenNLP packages.
 *  Created on: Dec 22, 2012
 *      Author: hjp
 *      E-mail: hjp@whu.edu.cn
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class OpenNLPChunker {

	public static String binDir = "/Users/hjp/MacBook/Workspace/Tempshop/Library/OpenNLP";

	public static String[] Tokener(String sent) {
		InputStream modelIn = null;
		TokenizerModel model = null;

		try {
			modelIn = new FileInputStream(binDir + "/bin/en-token.bin");
			model = new TokenizerModel(modelIn);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Tokenizer tokenizer = new TokenizerME(model);
		String tokens[] = tokenizer.tokenize(sent);

		return tokens;
	}

	public static String[] POSTagger(String[] tok) {
		InputStream modelIn = null;
		POSModel model = null;

		try {
			modelIn = new FileInputStream(binDir + "/bin/en-pos-perceptron.bin");
			model = new POSModel(modelIn);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		POSTaggerME tagger = new POSTaggerME(model);
		String tags[] = tagger.tag(tok);

		return tags;
	}

	public static String[] Chunker(String[] tok, String[] tag) throws IOException {
		InputStream modelIn = null;
		ChunkerModel model = null;

		try {
			modelIn = new FileInputStream(binDir + "/bin/en-chunker.bin");
			model = new ChunkerModel(modelIn);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		ChunkerME chunker = new ChunkerME(model);
		String chk[] = chunker.chunk(tok, tag);

		return chk;
	}

	public static void main(String[] args) throws IOException {
		String sent = "The European Commission, the EU's antitrust enforcer, is expected to issue its decision next spring ? "
				+ "unless a settlement is reached.";

		System.out.println("Original sentence: \n" + sent);
		String[] tokens = Tokener(sent);
		System.out.println("\nSentence token:");
		for (String tok : tokens) {
			System.out.print(tok + " ");
		}

		String[] tags = POSTagger(tokens);
		System.out.println("\n\nSentence POS:");
		for (String tag : tags) {
			System.out.print(tag + " ");
		}

		String[] chunkers = Chunker(tokens, tags);
		System.out.println("\n\nSentence chunk:");
		for (String chk : chunkers) {
			System.out.print(chk + " ");
		}

		System.out.println("\n\nToken\tPos\tChunker:");
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(tokens[i] + "\t" + tags[i] + "\t" + chunkers[i]);
		}
	}

}

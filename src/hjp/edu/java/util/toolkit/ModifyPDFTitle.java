package hjp.edu.java.util.toolkit;

/*
 * ModifyPDFTitle.java
 *
 *  Created on: Dec 10, 2012
 *      Author: hjp
 *      E-mail: hjp@whu.edu.cn
 */

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

public class ModifyPDFTitle {

	public static String confOrJour = "(conf/jour)";

	public static void main(String[] args) {
		String pdfDir = "/your/pdf/dir/";
		listFile(pdfDir);

	}

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
					if (files[i].getAbsolutePath().endsWith(".pdf") && !files[i].isHidden())
						modifyPDFName(files[i].getAbsolutePath());
				}
			}
		}

	}

	public static void modifyPDFName(String filePath) {
		System.out.println(filePath);
		File file = new File(filePath);
		PDDocument doc = null;

		try {
			doc = PDDocument.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PDDocumentInformation info = doc.getDocumentInformation();
		String title = info.getTitle();
		if (title != null) {
			title = title.replace("*", " ").replace(":", " ").replace("  ", " ").replace("  ", " ").replace("  ", " ");
			try {
				doc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			title = title + confOrJour + ".pdf";
			renameFile(filePath, title);
		}

	}

	public static void renameFile(String oldName, String newName) {
		File oldFile = new File(oldName);

		if (!oldFile.exists()) {
			System.out.println(oldName + " is not existed!");
		} else {
			String path = oldFile.getParent();
			File newFile = new File(path + File.separator + newName);
			if (oldFile.renameTo(newFile)) {
				System.out.println("Rename " + newName + " success!");
			} else {
				System.out.println("Rename " + newName + " fail!");
			}
		}

	}

}

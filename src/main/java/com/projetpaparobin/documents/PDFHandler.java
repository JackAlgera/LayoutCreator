package com.projetpaparobin.documents;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFHandler {

	private static PDFHandler instance;
	private static Splitter splitter = new Splitter();

	private PDFHandler() {
	}
	
	public static PDFHandler getInstance() {
		if(instance == null) {
			instance = new PDFHandler();
		}
		
		return instance;
	}
	
	public BufferedImage getImageFromPDF(String path, int pageNum) throws IOException {
		PDDocument document = PDDocument.load(new File(path));
		PDFRenderer pdfRenderer = new PDFRenderer(document);			
		BufferedImage image = pdfRenderer.renderImage(pageNum - 1);
		document.close();					
		return image;		
	}
	
	public void getPDFContents(String path) {
		File file = new File(path);
		
		try {
			PDDocument document = PDDocument.load(file);
			String text = new PDFTextStripper().getText(document);
//			System.out.println(text);
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

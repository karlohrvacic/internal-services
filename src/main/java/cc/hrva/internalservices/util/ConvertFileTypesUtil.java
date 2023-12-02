package cc.hrva.internalservices.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;


public class ConvertFileTypesUtil {

    public static void convertDocxToPdf(String documentFilePath, String pdfOutputFilePath) throws DocumentException, IOException {
        InputStream docxInputStream = new FileInputStream(documentFilePath);
        try (XWPFDocument document = new XWPFDocument(docxInputStream);
             OutputStream pdfOutputStream = new FileOutputStream(pdfOutputFilePath)) {
            Document pdfDocument = new Document();
            PdfWriter.getInstance(pdfDocument, pdfOutputStream);
            pdfDocument.open();

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                pdfDocument.add(new Paragraph(paragraph.getText()));
            }
            pdfDocument.close();
        }
    }

}

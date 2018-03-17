package net.restapp.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Create PDF file. Use by {@link net.restapp.servise.EmailService}
 */
@Slf4j
public class PDF {

    /**
     * Create PDF file from text in PDFFiles folder
     *
     * @param pdfName - file name
     * @param text    - text witch convert to PDF
     * @return - PDF file
     */
    public static File createPDF(String pdfName, String text) {
        //todo: create PDFFiles directory if it don't exist
        //Обязательно должна быть папка "PDFFiles" в файле с проектом!!!

        Document document = new Document();

        try {
            //Создаем файл pdfName с расширением pdf в папке PDFFiles
            PdfWriter.getInstance(document, new FileOutputStream("PDFFiles" + File.separator + pdfName + ".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException x) {
            x.printStackTrace();
        }

        //Настройки документа
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        //Chunk chunk = new Chunk(text, font);
        Paragraph paragraph = new Paragraph();
        paragraph.add(text);
        //chunk.append("Try this one");

        try {
            //document.add(chunk);
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();

        File file = new File("PDFFiles" + File.separator + pdfName + ".pdf");

        if (file.delete()) {
            log.info(file.getName() + " is deleted!");
        } else {
            log.error("Delete operation is failed.");
        }

        return file;
    }

}



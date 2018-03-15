package net.restapp.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PDF {

    //метод создает PDF файл в папке PDFFiles и возвращяет этот файл
    //Обязательно должна быть папка "PDFFiles" в файле с проектом!!!

    public static File createPDF(String pdfName, String text) {

        Document document = new Document();

        try {
            //Создаем файл pdfName с расширением pdf в папке PDFFiles
            PdfWriter.getInstance(document, new FileOutputStream("PDFFiles"+ File.separator+pdfName+".pdf"));
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

        File file = new File("PDFFiles"+ File.separator+pdfName+".pdf");

        if(file.delete()){
            System.out.println(file.getName() + " is deleted!");
        }else{
            System.out.println("Delete operation is failed.");
        }

        return file;
    }

}



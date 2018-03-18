package net.restapp.Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

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

        String tmp_name_dir = System.getProperty("user.dir") + File.separator + "PDFFiles";
        String fileName = tmp_name_dir + File.separator + pdfName + ".pdf";
        File dir = new File(tmp_name_dir);
        if (!dir.exists()){
            dir.mkdirs();
        } else {
            Arrays.stream(Objects.requireNonNull(new File(tmp_name_dir).listFiles())).forEach(File::delete);
        }
        try {
            String body = "<html><body> "+text+" </body></html>";
            OutputStream file = new FileOutputStream(new File(fileName));
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
            InputStream is = new ByteArrayInputStream(body.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new File(fileName);
    }

}



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.utilities;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 *
 * @author JP
 */
public class UtilPdf {

    /**
     *
     * @param pdfIn
     * @param textTop
     * @param textCenter
     * @param textBottom
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static byte[] marcaDeAgua(byte[] pdfIn, String textTop, String textCenter, String textBottom) throws IOException, DocumentException {
//        System.out.println("marca de agua " + pdfIn.toString() + " " + textBottom + " " + textCenter + " " + textCenter);
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PdfReader reader = null;
        try {
            reader = new PdfReader(pdfIn);
        } catch (Exception e) {
            System.out.println("marca de agua " + e.getCause());
        }
        int pages = reader.getNumberOfPages();
        int i = 0;
        PdfStamper pdfStamper = new PdfStamper(reader, salida);
        Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new GrayColor(0.75f));

        PdfContentByte underContent;

        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

        PdfGState gs = new PdfGState();
        gs.setFillOpacity(0.8f);

        while (i < pages) {
            i++;
            underContent = pdfStamper.getUnderContent(i);
            underContent.setGState(gs);
            underContent.beginText();
            underContent.setFontAndSize(bf, 52);
            underContent.setColorFill(Color.lightGray);
                  if (textTop != null) {
                underContent.showTextAligned(com.itextpdf.text.Element.ALIGN_CENTER, textTop, 297.5f, 800, 0);
            }
            if (textCenter != null) {
                underContent.showTextAligned(com.itextpdf.text.Element.ALIGN_CENTER, textCenter, 297.5f, 421, 50);
            }

            if (textBottom != null) {
                underContent.showTextAligned(com.itextpdf.text.Element.ALIGN_CENTER, textBottom, 297.5f, 5, 0);
            }
        }

        pdfStamper.close();

        return salida.toByteArray();
    }

    /**
     *
     * @param pdfIn
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static byte[] marcaDeAgua(byte[] pdfIn) throws IOException, DocumentException {
        return marcaDeAgua(pdfIn, "SOLO USO INTERNO", false, true, false);
    }

    /**
     *
     * @param pdfIn
     * @param text
     * @param top
     * @param center
     * @param bottom
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static byte[] marcaDeAgua(byte[] pdfIn, String text, boolean top, boolean center, boolean bottom)
            throws IOException, DocumentException {
        String textTop = null, textCenter = null, textBottom = null;

        if (top) {
            textTop = text;
        }
        if (center) {
            textCenter = text;
        }
        if (bottom) {
            textBottom = text;
        }

        return marcaDeAgua(pdfIn, textTop, textCenter, textBottom);
    }

    /**
     * Inner class to add a watermark to every page.
     */
    class Watermark extends PdfPageEventHelper {

        Font FONT = new Font(Font.HELVETICA, 52, Font.BOLD, new GrayColor(0.75f));

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
//            ColumnText.showTextAligned(writer.getDirectContentUnder(),
//                    com.itextpdf.text.Element.ALIGN_CENTER, new Phrase("FOOBAR FILM FESTIVAL", FONT),
//                    297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
        }
    }
}

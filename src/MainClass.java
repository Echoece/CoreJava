import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MainClass {
    public static void main(String[] args) throws IOException {
        List<InvoiceItem> itemList = Arrays.asList(
                new InvoiceItem("Sound box", "Bitz standard quality" , 1, 12900.00f),
                new InvoiceItem("Samsung", "model - A32" , 2, 29000.00f)
        );
        PDDocument document = new PDDocument();

        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document,page);
        contentStream.setNonStrokingColor(Color.BLACK);

        // customer details (top-left side)
        float baseYPosition =page.getCropBox().getHeight()-54;
        int lineheight= 11;

        writeText(contentStream,"BDX",PDType1Font.HELVETICA_BOLD,12, 55,baseYPosition , RenderingMode.FILL);
        writeText(contentStream, "Company ID : 123456789",PDType1Font.HELVETICA,9, 55, baseYPosition-lineheight,RenderingMode.FILL);
        writeText(contentStream, "Tax ID : 986432",PDType1Font.HELVETICA,9, 55, baseYPosition- (2*lineheight),RenderingMode.FILL);
        writeText(contentStream, "19/2A Debi Das Ghat Lane, Chawk Bazar",PDType1Font.HELVETICA,9, 55, baseYPosition - (3*lineheight),RenderingMode.FILL);
        writeText(contentStream, "Dhaka Dhaka 1211",PDType1Font.HELVETICA,9, 55, baseYPosition - (4*lineheight),RenderingMode.FILL);
        writeText(contentStream, "Bangladesh",PDType1Font.HELVETICA,9, 55, baseYPosition - (5*lineheight),RenderingMode.FILL);

        // Invoice details (top-right side)
        writeText(contentStream,"INVOICE",PDType1Font.HELVETICA, 24,450, baseYPosition, RenderingMode.FILL);
        String invoiceNumber = "000001"; // issue with leading zero while parsing string
        writeText(contentStream, "# INV-" + invoiceNumber, PDType1Font.HELVETICA, 10,450, baseYPosition-(2*lineheight), RenderingMode.FILL);

        writeText(contentStream, "Balance Due", PDType1Font.HELVETICA_BOLD, 8,450, baseYPosition-(5*lineheight), RenderingMode.FILL);
        String currency = "BDT ";
        String amount ="47530.00"; // issue with leading zero while parsing string
        writeText(contentStream, currency+ amount, PDType1Font.HELVETICA_BOLD, 12,450, baseYPosition-(6*lineheight), RenderingMode.FILL);

        // Bill to (below the customer details on the top-left side)
        writeText(contentStream, "Bill To",PDType1Font.HELVETICA,11, 55,baseYPosition - (9*lineheight),RenderingMode.FILL);
        writeText(contentStream, "John Doe",PDType1Font.HELVETICA_BOLD,9, 55,baseYPosition - (10*lineheight),RenderingMode.FILL);
        writeText(contentStream, "19/2A Gulshan-2",PDType1Font.HELVETICA,9,55,baseYPosition - (11*lineheight),RenderingMode.FILL);
        writeText(contentStream, "Dhaka",PDType1Font.HELVETICA,9,55,baseYPosition - (12*lineheight),RenderingMode.FILL);
        writeText(contentStream, "1211 Dhaka bibhag",PDType1Font.HELVETICA,9,55,baseYPosition - (13*lineheight),RenderingMode.FILL);
        writeText(contentStream, "Bangladesh",PDType1Font.HELVETICA,9,55,baseYPosition - (14*lineheight),RenderingMode.FILL);
        //subject
        writeText(contentStream, "Subject :",PDType1Font.HELVETICA,9,55,baseYPosition - (18*lineheight),RenderingMode.FILL);
        writeText(contentStream, "Invoice of purchasing mobile",PDType1Font.HELVETICA,9,55,baseYPosition - (19*lineheight),RenderingMode.FILL);


        // invoice dates and terms (below invoice details on the top-right side)
        writeText(contentStream, "Invoice Date :       " + LocalDate.now(),PDType1Font.HELVETICA,11,400,baseYPosition - (11*lineheight),RenderingMode.FILL);
        writeText(contentStream, "Terms :                 " + "Due on Receipt",PDType1Font.HELVETICA,11,400,baseYPosition - (13*lineheight),RenderingMode.FILL);
        writeText(contentStream, "Due Date :            " + LocalDate.now(),PDType1Font.HELVETICA,11,400,baseYPosition - (15*lineheight),RenderingMode.FILL);

        // invoice item menu header
        contentStream.addRect(55,baseYPosition-(22*lineheight),530,20);
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.fill();
        writeText(contentStream, "#",PDType1Font.HELVETICA, Color.white,10,60,baseYPosition - (22*lineheight)+7,RenderingMode.FILL);
        writeText(contentStream, "Item & Description",PDType1Font.HELVETICA, Color.white,10,100,baseYPosition - (22*lineheight)+7,RenderingMode.FILL);
        writeText(contentStream, "Qty",PDType1Font.HELVETICA, Color.white,10,400,baseYPosition - (22*lineheight)+7,RenderingMode.FILL);
        writeText(contentStream, "Rate",PDType1Font.HELVETICA, Color.white,10,470,baseYPosition - (22*lineheight)+7,RenderingMode.FILL);
        writeText(contentStream, "Amount",PDType1Font.HELVETICA, Color.white,10,540,baseYPosition - (22*lineheight)+7,RenderingMode.FILL);

        AtomicInteger i= new AtomicInteger(1);
        AtomicInteger itemlistBaseY = new AtomicInteger(23);
        itemList.forEach(
                item -> {
                    try {
                        float yPos= baseYPosition - (itemlistBaseY.get() *lineheight)-10;

                        writeText(contentStream, Integer.toString(i.get()),PDType1Font.HELVETICA, Color.black,9,60,yPos,RenderingMode.FILL);
                        writeText(contentStream, item.productName,PDType1Font.HELVETICA_BOLD, Color.black,9,100,yPos+10,RenderingMode.FILL);
                        writeText(contentStream, item.productDetails,PDType1Font.HELVETICA, Color.black,9,100,yPos,RenderingMode.FILL);
                        writeText(contentStream, Integer.toString(item.quantity),PDType1Font.HELVETICA, Color.black,9,400,yPos,RenderingMode.FILL);
                        writeText(contentStream, "pcs",PDType1Font.HELVETICA, Color.black,9,400,yPos+10,RenderingMode.FILL);
                        writeText(contentStream, Float.toString(item.unitprice),PDType1Font.HELVETICA, Color.black,9,470,yPos,RenderingMode.FILL);
                        writeText(contentStream, Float.toString(item.unitprice * item.quantity),PDType1Font.HELVETICA, Color.black,9,540,yPos,RenderingMode.FILL);

                        i.getAndAdd(1);
                        itemlistBaseY.getAndAdd(4);

//                        contentStream.moveTo(60,itemlistBaseY.get());
//                        contentStream.lineTo(600, 540);
//                        contentStream.stroke();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        System.out.println(page.getCropBox());

        contentStream.close();
        document.save("test.pdf");
        document.close();
    }

    private static void writeText(PDPageContentStream contentStream, String text, PDFont font,
                           int size, float xPos, float yPos, RenderingMode renderMode) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, size);
        contentStream.newLineAtOffset(xPos, yPos);
        //contentStream.setRenderingMode(renderMode);
        contentStream.showText(text);
        contentStream.endText();
    }
    private static void writeText(PDPageContentStream contentStream, String text, PDFont font, Color color,
                                  int size, float xPos, float yPos, RenderingMode renderMode) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, size);
        contentStream.newLineAtOffset(xPos, yPos);
        contentStream.setNonStrokingColor(color);
        //contentStream.setRenderingMode(renderMode);
        contentStream.showText(text);
        contentStream.endText();
    }


}

package com.e_commerce._util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;


import java.io.ByteArrayOutputStream;
@Service
public class Bill {

//    public class BillGenerator {

        public static byte[] generateBillByteArray(@RequestParam String name) {

            // Prepare output stream for the generated PDF
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

//        Double totalIgst= 0.0, totalCgst= 0.0, totalSgst= 0.0, totalAmount= 0.0, totalGstAmount= 0.0, invoiceAmount= 0.0;

            PdfWriter pdfWriter;
            try {
                pdfWriter = new PdfWriter(outputStream);
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                Document document = new Document(pdfDocument);

                pdfDocument.setDefaultPageSize(PageSize.A4);

                //Heading Table
                float colWidthForHeading[]= {600};
                Table tableForHeading= new Table(colWidthForHeading);

                tableForHeading.addCell(new Cell().add("E-Cart Pvt Ltd")
                        .setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setFontSize(30f).setFontColor(Color.GRAY)
                        .setBold()
                );
                tableForHeading.addCell(new Cell().add("INVOICE")
                        .setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setFontSize(15f).setFontColor(Color.DARK_GRAY)
                        .setBold()
                );
                //Heading Table Ends
                document.add(tableForHeading);


                float [] pointColumnWidths = {40f, 70f, 70f, 70f};
                Table headerTable = new Table(UnitValue.createPercentArray(pointColumnWidths));
                headerTable.setFixedLayout();
                headerTable.setBorder(Border.NO_BORDER);

                String imageFile = "C:\\Users\\brahm\\OneDrive\\Pictures\\logo.png";
                ImageData data = ImageDataFactory.create(imageFile);

                headerTable.addCell(new Cell(3,1).add(new Image(data).setAutoScale(true))
                        .setTextAlignment(TextAlignment.CENTER).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER)
                        .setBorder(Border.NO_BORDER)
                );

                headerTable.addCell(new Cell().add(" Contact us: 1800 208 9898 " )
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                headerTable.addCell(new Cell().add(" email: vaibhavgaur801945@gmail.comdasd ")
                        .setFontSize(11)
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                headerTable.addCell(new Cell().add("Tax Invoice: # 031091")
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );

                headerTable.addCell(new Cell(2,1).add("Informatique Solutions, ")
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
                );
                headerTable.addCell(new Cell(1, 2).add("Address: informatique Solutions, Gaziabad, sector 62, Noida( Madhya Pradesh) ")
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                headerTable.addCell(new Cell(1,2).add("Noida( Madhya Pradesh)- 203103")
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
                );
                document.add(headerTable);


                float [] forOrderAddressDetails = {55, 60, 60};
                Table orderAddressDetailsTable= new Table(forOrderAddressDetails);
//            orderAddressDetailsTable.setBorder(Border.NO_BORDER);


                orderAddressDetailsTable.addCell(new Cell().add("Order Id: FER322RW2R23RH")
                        .setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Billing Address")
                        .setBold().setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Shipping Address")
                        .setBold().setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );

                orderAddressDetailsTable.addCell(new Cell().add("Order Date: 15/13/13 ")
                        .setBold().setBorder(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell(3, 1).add("Hitesh Villa  room no. 117, Hostel H block  " +
                                "Noida, 201301, Uttar Pradesh ")
                        .setBold().setBorder(Border.NO_BORDER)
                );
//            orderAddressDetailsTable.addCell(new Cell().add("Shipping Address").setBold());
                orderAddressDetailsTable.addCell(new Cell(3, 1).add("Hitesh Villa  room no. 117, Hostel H block " +
                                "Noida, 201301, Uttar Pradesh ")
                        .setBold().setBorder(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Invoice Date: 10/03/24")
                        .setBold().setBorder(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("VAT/TIN: 1563453242654")
                        .setBold().setBorder(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("")
                        .setBold().setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Phone Number: 7894561230")
                        .setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Phone Number: 7894561230")
                        .setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
                );
                document.add(orderAddressDetailsTable);



                float [] forOrderDetails = {50, 100, 100, 40, 80, 80, 100};
                Table orderDetailsTable= new Table(forOrderDetails);
                orderDetailsTable.setBorder(Border.NO_BORDER);

                orderDetailsTable.addCell(new Cell().add("S.No. ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Product ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Title ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Qty ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Actual Price ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Discount Price ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Total ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );



                orderDetailsTable.addCell(new Cell().add("1").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Dummy Product ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Intex Hp Rtx 4090 Titan with liquid cooling and vortex fan").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("1 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00,000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00, 000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00,000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );

                orderDetailsTable.addCell(new Cell().add("1").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Dummy Product ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Intex Hp Rtx 4090 Titan with liquid cooling and vortex fan").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("1 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00,000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00, 000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00,000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );

                orderDetailsTable.addCell(new Cell().add("1").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Dummy Product ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Intex Hp Rtx 4090 Titan with liquid cooling and vortex fan").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("1 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00,000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00, 000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("10,00,00,000 ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );


                //row for tatal and total price
                orderDetailsTable.addCell(new Cell(1, 4).add("Total title ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT)
                );
                orderDetailsTable.addCell(new Cell().add("Total Price value").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Total Dicounted Price value").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );
                orderDetailsTable.addCell(new Cell().add("Grand Total Price value").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                );

                orderDetailsTable.addCell(new Cell(1, 5).add("Grand Total Final price Title ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setMarginRight(30f)
                        .setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE)
                );
                orderDetailsTable.addCell(new Cell(1, 2).add("Grand Total Final price Value ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.MIDDLE)
                );



                document.add(orderDetailsTable);





                System.out.println("PDF generated successfully.");

                // Close the document
                document.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return outputStream.toByteArray();
        }
    }

//            tableForExtraDetails.addCell(new Cell().add("For Metaphor Infrastructure Pvt. Ltd. \n")
//                    .setMarginTop(0f).setMarginBottom(5f).setFontSize(10f).setMarginLeft(5f).setBorderLeft(Border.NO_BORDER)
//                    .setBorderBottom(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER)
//            );
//            tableForExtraDetails.addCell(new Cell().add("")
//                    .setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
//            );
//            tableForExtraDetails.addCell(new Cell().add("Signature of Authorised Signatory \n")
//                    .setMarginTop(8f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f).setBorderLeft(Border.NO_BORDER)
//                    .setBorderTop(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER)
//            );



////tableForAmount.addCell(new Cell().add("Amount in words \n Sixteen Thousand Two Hundred Ninty One Rupees")
//tableForAmount.addCell(new Cell().add("Amount in words ")
//		.setMarginTop(20f).setMarginBottom(0f).setFontSize(8f).setMarginLeft(5f)
//		.setBorderBottom(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
//		);
//
//tableForAmount.addCell(new Cell().add("Total")
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add("0.0")
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add("0.0")
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add("0.0")
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add(""+String.valueOf(totalAmount) )
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//
//
//tableForAmount.addCell(new Cell().add(" Sixteen Thousand Two Hundred Ninjasj ajs c;akjsc kj;ac kj ckjcj kjj ty One Rupees ")
//		.setMarginTop(1f).setMarginBottom(5f).setFontSize(8f).setMarginLeft(5f)
//		.setBorderTop(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
//		);
//tableForAmount.addCell(new Cell().add("GST Amount")
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add(""+String.valueOf(totalIgst))
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add(""+String.valueOf(totalCgst))
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add(""+String.valueOf(totalSgst))
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//tableForAmount.addCell(new Cell().add(""+( String.valueOf(totalGstAmount) ))
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(9f).setMarginLeft(5f)
//		);
//
//// Table For Amount Ends
//document.add(tableForAmount);
//
//// Table for Invoice Amount
//float colWidthForInvoiceAmount[]= {343f, 98f, 170f};
//Table tableForInvoiceAmount= new Table(colWidthForInvoiceAmount);
//tableForInvoiceAmount.setWidthPercent(100);
//
//tableForInvoiceAmount.addCell(new Cell().add(" ")
//		.setBorderTop(Border.NO_BORDER)
//		);
//
//tableForInvoiceAmount.addCell(new Cell().add("Invoice Amount : ")
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(10f).setMarginLeft(5f).setBorderRight(Border.NO_BORDER)
//
//		);
//tableForInvoiceAmount.addCell(new Cell().add(""+( String.valueOf(invoiceAmount) ))
//		.setMarginTop(5f).setMarginBottom(5f).setFontSize(10f).setMarginLeft(5f).setBorderLeft(Border.NO_BORDER)
//		.setTextAlignment(TextAlignment.CENTER)
//		);
//
//// Table for Invoice Amount Ends
//document.add(tableForInvoiceAmount);




package com.e_commerce._util;

import com.e_commerce.entity.OrderDetail;
import com.e_commerce.entity.UserOrders;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

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
import java.util.List;

import static com.itextpdf.kernel.pdf.PdfName.User;

@Service
@RequiredArgsConstructor
public class Bill {

    private final HelperUtils helperUtils;
    private final ImagePathProvider imagePathProvider;


        @SneakyThrows
        public  String generateBillByteArray(OrderDetail orderDetail)  {
//
//            String pdfPath= helperUtils.getPathForPdf();
////            +orderDetail.getOrderId()+".pdf";
//
//            File mainFilePath = new File(pdfPath);
//
//            if (!mainFilePath.exists()) {
//                System.out.println("path ban raha he-------------------------------------------------------------------");
//                mainFilePath.mkdirs();
//                System.out.println(pdfPath);
//
//            }
//
//            // Prepare output stream for the generated PDF
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            FileOutputStream fos= new FileOutputStream(helperUtils.getPathForPdf()+orderDetail.getOrderId()+".pdf");
//

//        Double totalIgst= 0.0, totalCgst= 0.0, totalSgst= 0.0, totalAmount= 0.0, totalGstAmount= 0.0, invoiceAmount= 0.0;
            Double totalActualPrice= 0.0, totalDiscountedPrice= 0.0;
            PdfWriter pdfWriter;
            try {
//                pdfWriter = new PdfWriter("D:\\sample.pdf");
//                pdfWriter = new PdfWriter(helperUtils.getCompletePdf()+orderDetail.getOrderId()+".pdf");
                pdfWriter = new PdfWriter(helperUtils.getPathForPdf()+orderDetail.getOrderId()+".pdf");
                PdfDocument pdfDocument = new PdfDocument(pdfWriter);
                Document document = new Document(pdfDocument);

                pdfDocument.setDefaultPageSize(PageSize.A4);

                //Heading Table
                float colWidthForHeading[]= {600};
                Table tableForHeading= new Table(colWidthForHeading);

                tableForHeading.addCell(new Cell().add("IAF Canteen")
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

                String imageFile = imagePathProvider.imagesPath;
//                        "C:\\Users\\brahm\\OneDrive\\Pictures\\logo.png";
                ImageData data = ImageDataFactory.create(imageFile);

                headerTable.addCell(new Cell(3,1).add(new Image(data).setAutoScale(true))
                        .setTextAlignment(TextAlignment.CENTER).setBorderRight(Border.NO_BORDER).setBorderLeft(Border.NO_BORDER)
                        .setBorder(Border.NO_BORDER)
                );

                headerTable.addCell(new Cell().add(" Contact us: 1800 208 9898 "  )
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                headerTable.addCell(new Cell().add(" email: iafcanteen@gmail.comdasd ")
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


                float [] forOrderAddressDetails = {60, 150, 60};
                Table orderAddressDetailsTable= new Table(forOrderAddressDetails);
//            orderAddressDetailsTable.setBorder(Border.NO_BORDER);


                orderAddressDetailsTable.addCell(new Cell().add(orderDetail.getOrderId())
                        .setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Billing Address")
//                        orderDetail.getSelectedStore()
                        .setBold().setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Shipping Address")
                        .setBold().setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderBottom(Border.NO_BORDER)
                );

                orderAddressDetailsTable.addCell(new Cell().add(""+orderDetail.getOrderDate())
                        .setBold().setBorder(Border.NO_BORDER)
                );


                orderAddressDetailsTable.addCell(new Cell(2, 1).add(orderDetail.getSelectedStore())
                        .setBold().setBorder(Border.NO_BORDER)
                );
//            orderAddressDetailsTable.addCell(new Cell().add("Shipping Address").setBold());
                orderAddressDetailsTable.addCell(new Cell(2, 1).add(orderDetail.getDeliveryAddress())
                        .setBold().setBorder(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("Invoice Date: 10/03/24")
                        .setBold().setBorder(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add("VAT/TIN: 1563453242654")
                        .setBold().setBorder(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add(orderDetail.getContactNo())
                        .setBold().setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
                );
                orderAddressDetailsTable.addCell(new Cell().add(orderDetail.getAlternateContactNumber())
                        .setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
                );
//                orderAddressDetailsTable.addCell(new Cell().add()
//                        .setBold().setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setBorderTop(Border.NO_BORDER)
//                );
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
                List<UserOrders> userOrdersList = orderDetail.getUserOrders();

                for (int i = 0; i < userOrdersList.size(); i++) {
                    UserOrders userOrders = userOrdersList.get(i);
                    totalActualPrice += userOrders.getProduct().getProductActualPrice();
                    totalDiscountedPrice += userOrders.getProduct().getProductDiscountedPrice();
                    orderDetailsTable.addCell(new Cell().add(""+(i+1)).setBold()
                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    );
                    orderDetailsTable.addCell(new Cell().add(userOrders.getProduct().getProductName()).setBold()
                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    );
                    orderDetailsTable.addCell(new Cell().add("dummy short product description, change later if it is added in product").setBold()
                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    );
//                    orderDetailsTable.addCell(new Cell().add(userOrders.getProduct().getProductShortDescription()).setBold()
//                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
//                    );
                    orderDetailsTable.addCell(new Cell().add(""+userOrders.getQuantity()).setBold()
                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    );
                    orderDetailsTable.addCell(new Cell().add(""+userOrders.getProduct().getProductActualPrice()).setBold()
                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    );
                    orderDetailsTable.addCell(new Cell().add(""+userOrders.getProduct().getProductDiscountedPrice()).setBold()
                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    );
                    orderDetailsTable.addCell(new Cell().add(""+userOrders.getProductTotalAmount()).setBold()
                            .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                    );
                }

                //row for total and total price

                orderDetailsTable.addCell(new Cell(1, 4).add("Total").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.RIGHT)
                );
                orderDetailsTable.addCell(new Cell().add(""+totalActualPrice).setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.CENTER)
                );
                orderDetailsTable.addCell(new Cell().add(""+totalDiscountedPrice).setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.CENTER)
                );
                orderDetailsTable.addCell(new Cell().add(""+orderDetail.getTotalOrderAmount()).setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                        .setTextAlignment(TextAlignment.CENTER)
                );

                orderDetailsTable.addCell(new Cell(1, 5).add("Grand Total: ").setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setMarginRight(30f)
                        .setTextAlignment(TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE)
                );
                orderDetailsTable.addCell(new Cell(1, 2).add(""+orderDetail.getTotalOrderAmount()).setBold()
                        .setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE).setTextAlignment(TextAlignment.CENTER)
                );

                document.add(orderDetailsTable);







                System.out.println("PDF generated successfully.");

                // Close the document
                document.close();

//                fos.write(outputStream.to);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return helperUtils.getPathForPdf()+orderDetail.getOrderId()+".pdf";
//            return outputStream.toByteArray();
        }
    }




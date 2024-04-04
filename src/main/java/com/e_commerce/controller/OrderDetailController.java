package com.e_commerce.controller;

import com.e_commerce.entity.OrderDetail;
import com.e_commerce.request.OrderInput;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.OrderDetailService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/placeOrder/{isSingleProductCheckout}")
    public ApiResponse<OrderDetail> placeOrder(
            @PathVariable Boolean isSingleProductCheckout,
            @RequestBody OrderInput orderInput,
            @RequestHeader("Authorization") String authToken) throws Exception {

        System.out.println("isSingleProductCheckout: "+isSingleProductCheckout);
        return  orderDetailService.placeOrder(orderInput, authToken, isSingleProductCheckout);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getOrderDetailsOfUser")
    public ApiResponse<List<OrderDetail>> getOrderDetailsOfUser(
            @RequestHeader("Authorization") String authToken
    ) throws Exception {
        return orderDetailService.getOrderDetailsOfUser(authToken);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllOrderDetailsOfAdmin/{status}")
    public ApiResponse<List<OrderDetail>> getAllOrderDetailsOfAdmin(@PathVariable String status) {
        System.out.println(status);
        return orderDetailService.getAllOrderDetailsOfAdmin(status);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/markOrderAsDelivered/{orderId}")
    public void markOrderAsDelivered(@PathVariable Integer orderId) throws Exception {
        orderDetailService.markOrderAsDelivered(orderId);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/markOrderAsNotDelivered/{orderId}")
    public void markOrderAsNotDelivered(@PathVariable Integer orderId) throws Exception {
        orderDetailService.markOrderAsNotDelivered(orderId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/ /{orderId}")
    public void cancelOrder(@PathVariable Integer orderId) throws Exception {
        orderDetailService.cancelOrder(orderId);
    }
    @GetMapping("/pdf/{orderId}")
    public ApiResponse<List<Object>> returnPdf(@PathVariable String orderId) throws Exception {
        return orderDetailService.getPdf(orderId);

//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment;filename=sample.pdf");
////        byte[] pdfBytes = (byte[])stringObjectMap.get("pdfBytes");
//
//        response.getOutputStream().write(pdfBytes);
////
//
//        String mimeType = URLConnection.guessContentTypeFromName(file.getName()); // for you it would be application/pdf
//        if (mimeType == null) mimeType = "application/octet-stream";
//        response.setContentType(mimeType);
//        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
//        response.setContentLength((int) file.length());
//        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//        FileCopyUtils.copy(inputStream, response.getOutputStream());
//    }
}
}

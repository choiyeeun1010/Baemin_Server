package com.example.demo.src.order;

import com.example.demo.src.order.OrderProvider;
import com.example.demo.src.order.OrderService;
import com.example.demo.src.store.model.GetStoreMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/order")
public class OrderController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;


    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/users/main/:userIdx
    public BaseResponse<List<GetOrder>> getOrder(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetOrder> getOrder = (List<GetOrder>) orderProvider.getOrder(userIdx);
            return new BaseResponse<>(getOrder);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}/{orderIdx}") // (GET) 127.0.0.1:9000/users/main/:userIdx
    public BaseResponse<List<GetOrderReceipt>> getOrderReceipt(@PathVariable("userIdx") int userIdx, @PathVariable("orderIdx") int orderIdx) {
        try {
            List<GetOrderReceipt> getOrderReceipt = (List<GetOrderReceipt>) orderProvider.getOrderReceipt(userIdx, orderIdx);
            return new BaseResponse<>(getOrderReceipt);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }
}
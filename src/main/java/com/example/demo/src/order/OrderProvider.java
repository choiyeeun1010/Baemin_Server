package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.src.order.model.*;
import com.example.demo.src.order.OrderDao;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class OrderProvider {

    private final OrderDao orderDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderProvider(OrderDao orderDao, JwtService jwtService) {
        this.orderDao = orderDao;
        this.jwtService = jwtService;
    }

    public List<GetOrder> getOrder(int userIdx) throws BaseException {
       // try {
            List<GetOrder> getOrder = orderDao.getOrder(userIdx);
            return getOrder;
      //  } catch (Exception exception) {
        //    throw new BaseException(DATABASE_ERROR);
       // }
    }
}

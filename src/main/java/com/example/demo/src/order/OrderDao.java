package com.example.demo.src.order;

import com.example.demo.src.order.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetOrder> getOrder(int userIdx){
        String getOrderQuery = "select o.orderIdx,\n" +
                "       s.storeName \'상점명\',\n" +
                "       m.menuName \'메뉴이름\',\n" +
                "       concat(sm.menuCount, '개') \'메뉴개수\',\n" +
                "       concat(format(o.orderPrice + dp.deliveryPrice, 0), '원') \'결제금액\'\n" +
                "from `Order` o, User u, Store s, StoreImage si, SelectMenu sm, Menu m, DeliveryPrice dp\n" +
                "where o.userIdx = u.userIdx\n" +
                "and o.storeIdx = s.storeIdx\n" +
                "and o.basketIdx = sm.basketIdx\n" +
                "and sm.menuIdx = m.menuIdx\n" +
                "and o.storeIdx = si.storeIdx\n" +
                "and dp.storeIdx = o.storeIdx and o.userIdx = ? ";
        int getOrderParams = userIdx;
        return this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrder(
                        rs.getInt("o.orderIdx"),
                        rs.getString("상점명"),
                        rs.getString("메뉴이름"),
                        rs.getString("메뉴개수"),
                        rs.getString("결제금액")),
                getOrderParams);
    }
}

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

    public List<GetOrderReceipt> getOrderReceipt(int userIdx, int orderIdx){
        String getOrderReceiptQuery = "select o.orderIdx,\n" +
                "       o.storeIdx,\n" +
                "       s.storeName \'상점명\',\n" +
                "       m.menuName \'메뉴이름\',\n" +
                "       concat(format(m.menuPrice, 0), '원') \'메뉴가격\',\n" +
                "       sm.menuCount \'메뉴개수\',\n" +
                "       o.createAt \'주문일시\',\n" +
                "       s.storeCallNum \'가게전화번호\',\n" +
                "       si.sideName \'사이드메뉴이름\',\n" +
                "       concat(format(si.sidePrice, 0), '원') \'사이드메뉴가격\',\n" +
                "       concat(format(dp.deliveryPrice, 0), '원') \'배달팁\',\n" +
                "       concat(format(o.orderPrice, 0), '원') \'주문금액\',\n" +
                "       concat(format(o.orderPrice + dp.deliveryPrice, 0), '원') \'총결제금액\',\n" +
                "       pm.methodName \'결제방법\',\n" +
                "       ua.address \'배달주소\',\n" +
                "       u.userPhone \'전화번호\',\n" +
                "       o.orderRequest \'가게사장님께\',\n" +
                "       o.riderRequest \'라이더님께\'\n" +
                "from `Order` o, Store s, Basket b,\n" +
                "     SelectMenu sm, Menu m, SideMenu si,\n" +
                "     User u, UserAddress ua,\n" +
                "     DeliveryPrice dp, PaymentMethod pm, StorePaymentMethod sp\n" +
                "where o.storeIdx = s.storeIdx\n" +
                "and u.userIdx = b.userIdx\n" +
                "and u.userIdx = o.userIdx\n" +
                "and o.basketIdx = sm.basketIdx\n" +
                "and sm.menuIdx = m.menuIdx\n" +
                "and sm.sideIdx = si.sideIdx\n" +
                "and dp.storeIdx = s.storeIdx\n" +
                "and sp.storeIdx = s.storeIdx\n" +
                "and sp.methodIdx = pm.methodIdx\n" +
                "and o.userAddressIdx = ua.userAddressIdx and o.userIdx = ? and o.orderIdx = ? ";
        int getOrderReceiptUserParams = userIdx;
        int getOrderReceiptOrderParams = orderIdx;
        return this.jdbcTemplate.query(getOrderReceiptQuery,
                (rs, rowNum) -> new GetOrderReceipt(
                        rs.getInt("o.orderIdx"),
                        rs.getInt("o.storeIdx"),
                        rs.getString("상점명"),
                        rs.getString("메뉴이름"),
                        rs.getString("메뉴가격"),
                        rs.getInt("메뉴개수"),
                        rs.getString("주문일시"),
                        rs.getString("가게전화번호"),
                        rs.getString("사이드메뉴이름"),
                        rs.getString("사이드메뉴가격"),
                        rs.getString("배달팁"),
                        rs.getString("주문금액"),
                        rs.getString("총결제금액"),
                        rs.getString("결제방법"),
                        rs.getString("배달주소"),
                        rs.getString("전화번호"),
                        rs.getString("가게사장님께"),
                        rs.getString("라이더님께")),
                getOrderReceiptUserParams, getOrderReceiptOrderParams);
    }
}

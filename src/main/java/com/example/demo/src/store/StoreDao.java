package com.example.demo.src.store;

import com.example.demo.src.store.model.*;
import com.example.demo.src.store.model.GetStoreMain;
import com.example.demo.src.user.model.GetUserMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoreDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetStoreMain> getStoreMain(int storeIdx){
        String getStoreMainQuery = "select s.storeIdx, \n" +
                "       s.storeName, \n" +
                "       s.storeCallNum, \n" +
                "       si.image, \n" +
                "       s.higienInformation,\n" +
                "       concat('최소주문금액 ', format(s.minamount, 0), '원'), \n" +
                "       concat('결제방법 ', pm.methodName), \n" +
                "       concat('배달시간 ', s.deliveryTime, ' 소요 예상'), \n" +
                "       concat('배달팁 ', format(dp.deliveryPrice, 0), '원'), \n" +
                "       round(ifnull(startGrade, 0), 1), \n" +
                "       ifnull(reviewNum, 0), \n" +
                "       ifnull(countComments, 0), \n" +
                "       concat('찜 ', ifnull(likeNum, 0)) \n" +
                "\n" +
                "from Store s \n" +
                "   join(\n" +
                "       select storeIdx, sum(scope)/count(reviewIdx) as startGrade,\n" +
                "              concat('최근 리뷰 ', count(reviewIdx)) as reviewNum\n" +
                "       from UserReview\n" +
                "       group by storeIdx\n" +
                "    ) as v on v.storeIdx = s.storeIdx\n" +
                "   join(\n" +
                "       select storeIdx, concat('최근 사장님 댓글 수 ', count(comentIdx)) as countComments\n" +
                "       from StoreManagerComent\n" +
                "       group by storeIdx\n" +
                "    ) as w on w.storeIdx = s.storeIdx\n" +
                "   join(\n" +
                "       select storeIdx, count(userIdx) as likeNum, likeState\n" +
                "       from LikeStore\n" +
                "       where likeState = 'T'\n" +
                "       group by storeIdx\n" +
                "    ) as x on x.storeIdx = s.storeIdx\n" +
                ", StoreImage si, DeliveryPrice dp, UserReview ur, StorePaymentMethod m, PaymentMethod pm\n" +
                "where s.storeIdx = si.storeIdx and s.storeIdx = dp.storeIdx and s.storeIdx = ur.storeIdx\n" +
                "and s.storeIdx = m.storeIdx and m.methodIdx = pm.methodIdx and s.storeIdx = ? ";
            int getStoreMainParams = storeIdx;

            return this.jdbcTemplate.query(getStoreMainQuery,
                (rs,rowNum) -> new GetStoreMain(
                        rs.getInt("storeIdx"),
                        rs.getString("storeName"),
                        rs.getString("storeCallNum"),
                        rs.getString("image"),
                        rs.getString("higienInformation"),
                        rs.getInt("minamount"),
                        rs.getString("methodName"),
                        rs.getString("deliveryTime"),
                        rs.getInt("deliveryPrice"),
                        rs.getFloat("scope"),
                        rs.getInt("reviewIdx"),
                        rs.getInt("comentIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("likeState"),
                        rs.getInt("methodIdx")
                ), getStoreMainParams);
    }

    public List<GetStoreList> getStoreList(){
        String getStoreListQuery = "select s.storeIdx,\n" +
                "       si.image,\n" +
                "       s.storeName as \"상점명\",\n" +
                "       concat(s.deliveryTime, '분') as \"배달시간\",\n" +
                "       concat('최소주문 ', format(s.minAmount, 0), '원') as \"최소주문금액\",\n" +
                "       concat('배달팁 ', format(dp.deliveryPrice, 0), '원') as \"배달비\",\n" +
                "       ifnull(v.startGrade, 0) as starGrade,\n" +
                "       ifnull(v.reviewCount, 0) as starCount,\n" +
                "       ifnull(mainMenu, '') as mainMenu\n" +
                "\n" +
                "from Store s\n" +
                "   left join(select storeIdx,\n" +
                "                    round(sum(scope) / count(storeIdx), 1) as 'startGrade',\n" +
                "                    count(storeIdx) as 'reviewCount'\n" +
                "       from UserReview\n" +
                "       group by storeIdx) as v\n" +
                "   on s.storeIdx = v.storeIdx\n" +
                "   left join(select storeIdx, group_concat(menuName separator ', ') as mainMenu\n" +
                "       from Menu\n" +
                "       where menuState = 'T' and Menu.menuCategoryIdx=1\n" +
                "       group by storeIdx) as w\n" +
                "   on s.storeIdx = w.storeIdx, StoreImage si, DeliveryPrice dp\n" +
                "where s.storeIdx = si.storeIdx and s.storeIdx = dp.storeIdx ";

        return this.jdbcTemplate.query(getStoreListQuery,
                (rs,rowNum) -> new GetStoreList(
                        rs.getInt("storeIdx"),
                        rs.getString("image"),
                        rs.getString("storeName"),
                        rs.getString("deliveryTime"),
                        rs.getInt("minAmount"),
                        rs.getInt("deliveryPrice"),
                        rs.getFloat("scope"),
                        rs.getString("menuName"),
                        rs.getString("menuState"),
                        rs.getInt("methodIdx")
                ));
    }
}

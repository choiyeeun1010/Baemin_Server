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
        String getStoreMainQuery = "select s.storeIdx, " +
                "       s.storeName, " +
                "       s.storeCallNum, " +
                "       si.image, " +
                "       s.higienInformation, " +
                "       concat('최소주문금액 ', format(s.minAmount, 0), '원'), " +
                "       concat('결제방법 ', pm.methodName), " +
                "       concat('배달시간 ', s.deliveryTime, ' 소요 예상'), " +
                "       concat('배달팁 ', format(dp.deliveryPrice, 0), '원'), " +
                "       round(ifnull(startGrade, 0), 1), " +
                "       ifnull(reviewNum, 0), " +
                "       ifnull(countComments, 0), " +
                "       concat('찜 ', ifnull(likeNum, 0)) " +
                "from Store s " +
                "   join( " +
                "       select storeIdx, sum(scope)/count(reviewIdx) as startGrade, " +
                "              concat('최근 리뷰 ', count(reviewIdx)) as reviewNum " +
                "       from UserReview " +
                "       group by storeIdx " +
                "    ) as v on v.storeIdx = s.storeIdx " +
                "   join( " +
                "       select storeIdx, concat('최근 사장님 댓글 수 ', count(comentIdx)) as countComments " +
                "       from StoreManagerComent " +
                "       group by storeIdx " +
                "    ) as w on w.storeIdx = s.storeIdx " +
                "   join( " +
                "       select storeIdx, count(userIdx) as likeNum, likeState " +
                "       from LikeStore " +
                "       where likeState = 'T' " +
                "       group by storeIdx " +
                "    ) as x on x.storeIdx = s.storeIdx " +
                ", StoreImage si, DeliveryPrice dp, UserReview ur, StorePaymentMethod m, PaymentMethod pm " +
                "where s.storeIdx = si.storeIdx and s.storeIdx = dp.storeIdx and s.storeIdx = ur.storeIdx " +
                "and s.storeIdx = m.storeIdx and m.methodIdx = pm.methodIdx and s.storeIdx = ? ";
            int getStoreMainParams = storeIdx;

            return this.jdbcTemplate.query(getStoreMainQuery,
                (rs,rowNum) -> new GetStoreMain(
                        rs.getInt("s.storeIdx"),
                        rs.getString("s.storeName"),
                        rs.getString("s.storeCallNum"),
                        rs.getString("si.image"),
                        rs.getString("s.higienInformation"),
                        rs.getInt("s.minAmount"),
                        rs.getString("pm.methodName"),
                        rs.getString("s.deliveryTime"),
                        rs.getInt("dp.deliveryPrice"),
                        rs.getFloat("scope"),
                        rs.getInt("reviewIdx"),
                        rs.getInt("comentIdx"),
                        rs.getInt("userIdx"),
                        rs.getString("likeState"),
                        rs.getInt("methodIdx")
                ), getStoreMainParams);
    }

    public List<GetStoreList> getStoreList(){
        String getStoreListQuery = "select s.storeIdx, " +
                "       si.image, " +
                "       s.storeName, " +
                "       concat(s.deliveryTime, '분') as \'배달시간\', " +
                "       concat('최소주문 ', format(s.minAmount, 0), '원') as \'최소주문금액\', " +
                "       concat('배달팁 ', format(dp.deliveryPrice, 0), '원') as \'배달비\', " +
                "       ifnull(v.startGrade, 0) as starGrade, " +
                "       ifnull(v.reviewCount, 0) as starCount, " +
                "       ifnull(mainMenu, '') as mainMenu " +
                "from Store s " +
                "   left join(select storeIdx, " +
                "                    round(sum(scope) / count(storeIdx), 1) as 'startGrade', " +
                "                    count(storeIdx) as 'reviewCount' " +
                "       from UserReview " +
                "       group by storeIdx) as v " +
                "   on s.storeIdx = v.storeIdx\n" +
                "   left join(select storeIdx, group_concat(menuName separator ', ') as mainMenu " +
                "       from Menu " +
                "       where menuState = 'T' and Menu.menuCategoryIdx=1 " +
                "       group by storeIdx) as w " +
                "   on s.storeIdx = w.storeIdx, StoreImage si, DeliveryPrice dp " +
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

    public List<GetStoreIntro> getStoreIntro(int storeIdx){
        String getStoreIntroQuery = "select s.storeIdx, s.storeName, si.image, s.storeIntro\n" +
                "from Store s, StoreImage si\n" +
                "where s.storeIdx = si.storeIdx and s.storeIdx = ? ";
        int getStoreIntroParams = storeIdx;
        return this.jdbcTemplate.query(getStoreIntroQuery,
                (rs, rowNum) -> new GetStoreIntro(
                        rs.getInt("storeIdx"),
                        rs.getString("storeName"),
                        rs.getString("image"),
                        rs.getString("storeIntro")),
                getStoreIntroParams);
    }
}

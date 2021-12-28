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
        String getStoreMainQuery = "select s.storeIdx,\n" +
                "       s.storeName as \'상점명\',\n" +
                "       s.storeCallNum as \'상점전화번호\',\n" +
                "       si.image as \'상점이미지\',\n" +
                "       s.higienInformation as \'위생정보\',\n" +
                "       concat('최소주문금액 ',format(minAmount, 0), '원') as \'최소주문금액\',\n" +
                "       concat('결제방법 ', pm.methodName) as \'결제방법\',\n" +
                "       concat('배달시간 ', s.deliveryTime, ' 소요 예상') as \'배달시간\',\n" +
                "       concat('배달팁 ', format(dp.deliveryPrice, 0), '원') as \'배달팁\',\n" +
                "       round(ifnull(startGrade, 0), 1) as \'별점\',\n" +
                "       ifnull(reviewNum, 0) as \'리뷰수\',\n" +
                "       ifnull(countComments, 0) as \'사장님댓글수\',\n" +
                "       concat('찜 ', ifnull(likeNum, 0)) as \'찜\'\n" +
                "\n" +
                "from Store s\n" +
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
                        rs.getString("상점명"),
                        rs.getString("상점전화번호"),
                        rs.getString("상점이미지"),
                        rs.getString("위생정보"),
                        rs.getString("최소주문금액"),
                        rs.getString("결제방법"),
                        rs.getString("배달시간"),
                        rs.getString("배달팁"),
                        rs.getFloat("별점"),
                        rs.getString("리뷰수"),
                        rs.getString("사장님댓글수"),
                        rs.getString("찜")
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
                        rs.getString("배달시간"),
                        rs.getString("최소주문금액"),
                        rs.getString("배달비"),
                        rs.getFloat("starGrade"),
                        rs.getInt("starCount"),
                        rs.getString("mainMenu")
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

    public List<GetStoreInfo> getStoreInfo(int storeIdx){
        String getStoreInfoQuery = "select s.storeIdx, s.storeName, si.image, s.storeIntro\n" +
                "from Store s, StoreImage si\n" +
                "where s.storeIdx = si.storeIdx and s.storeIdx = ? ";
        int getStoreInfoParams = storeIdx;
        return this.jdbcTemplate.query(getStoreInfoQuery,
                (rs, rowNum) -> new GetStoreInfo(
                        rs.getInt("storeIdx"),
                        rs.getString("storeName"),
                        rs.getString("openTime"),
                        rs.getString("closeTime"),
                        rs.getString("storeHoliday"),
                        rs.getString("storeCallNum"),
                        rs.getString("noticeContents"),
                        rs.getInt("orderPrice"),
                        rs.getInt("deliveryPrice"),
                        rs.getString("regionName"),
                        rs.getInt("orderIdx"),
                        rs.getInt("reviewIdx"),
                        rs.getInt("likeStoreIdx")
                        ),
                getStoreInfoParams);
    }
}

package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select userIdx, userID, userName, password, userEmail from User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("userEmail"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String userEmail){
        String getUsersByEmailQuery = "select * from User where userEmail =?";
        String getUsersByEmailParams = userEmail;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("userEmail")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from User where userIdx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("userEmail")),
                getUserParams);
    }

    public List<GetUserMain> getUserMain(int userIdx){
        String getUserMainQuery = "select u.userIdx, u.addressName, s.categoryIdx, s.categoryName, s.categoryImage from StoreCategory s, UserAddress u where u.userIdx = ?";
        int getUserMainParams = userIdx;
        return this.jdbcTemplate.query(getUserMainQuery,
                (rs, rowNum) -> new GetUserMain(
                        rs.getInt("u.userIdx"),
                        rs.getString("u.addressName"),
                        rs.getInt("s.categoryIdx"),
                        rs.getString("s.categoryName"),
                        rs.getString("s.categoryImage")),
                getUserMainParams);
    }

    public List<GetUserAddress> getUserAddress(int userIdx){
        String getUserAddressQuery = "select userIdx, userAddressIdx, addressName, address from UserAddress where userIdx = ?";
        int getUserAddressParams = userIdx;
        return this.jdbcTemplate.query(getUserAddressQuery,
                (rs, rowNum) -> new GetUserAddress(
                        rs.getInt("userIdx"),
                        rs.getInt("userAddressIdx"),
                        rs.getString("addressName"),
                        rs.getString("address")
                ), getUserAddressParams);
    }

    public List<GetUserSearch> getUserSearch(int userIdx){
        String getUserSearchQuery = "select userIdx, searchIdx, searchContents from Search where userIdx = ?";
        int getUserSearchParams = userIdx;
        return this.jdbcTemplate.query(getUserSearchQuery,
                (rs, rowNum) -> new GetUserSearch(
                        rs.getInt("userIdx"),
                        rs.getInt("searchIdx"),
                        rs.getString("searchContents")
                ), getUserSearchParams);
    }

    public List<GetSearchRanking> getSearchRanking(){
        String getSearchRankingQuery = "select searchContents as \'검색내용\', count(searchContents) as \'검색수\', concat(date_format(now(), '%m.%d %H:00'), ' 기준') as \'기준\'\n" +
                "from Search\n" +
                "where createAt < date_format(now(), '%Y-%m-%d %H:00:00')\n" +
                "group by searchContents\n" +
                "order by count(searchContents) desc";
        return this.jdbcTemplate.query(getSearchRankingQuery,
                (rs, rowNum) -> new GetSearchRanking(
                        rs.getString("검색내용"),
                        rs.getInt("검색수"),
                        rs.getString("기준")
                ));
    }

    public List<GetUserLike> getUserLike(int userIdx){
        String getUserLikeQuery = "select l.storeIdx,\n" +
                "       likeState '찜상태',\n" +
                "       storeName as \'상점명\',\n" +
                "       image \'상점이미지\',\n" +
                "       concat('최소주문 ', format(minAmount, 0), '원') \'최소주문금액\',\n" +
                "       concat('배달팁 ', format(deliveryPrice, 0), '원') \'배달팁\',\n" +
                "       starGrade \'별점\',\n" +
                "       reviewCount \'리뷰수\',\n" +
                "       mainMenu as \'메인메뉴\'\n" +
                "from LikeStore l\n" +
                "   join(select s.storeIdx,\n" +
                "               s.storeName,\n" +
                "               si.image,\n" +
                "               deliveryTime,\n" +
                "               s.minAmount,\n" +
                "               dp.deliveryPrice,\n" +
                "               ifnull(w.starGrade,0) as starGrade,\n" +
                "               ifnull(w.reviewCount, 0) as reviewCount,\n" +
                "               ifnull(mainMenu, '') as mainMenu\n" +
                "        from  StoreImage si, DeliveryPrice dp, Store s\n" +
                "           left join(select storeIdx,\n" +
                "                            round(sum(scope) / count(storeIdx), 1) as 'starGrade',\n" +
                "                            count(storeIdx) as 'reviewCount'\n" +
                "               from UserReview\n" +
                "               group by storeIdx) as w\n" +
                "           on s.storeIdx = w.storeIdx\n" +
                "           left join(select storeIdx, group_concat(menuName separator ', ') as mainMenu\n" +
                "               from Menu\n" +
                "               where menuCategoryIdx = '1'\n" +
                "               group by storeIdx) as x\n" +
                "           on s.storeIdx = x.storeIdx) as v\n" +
                "where likeState = 'T' and userIdx = ? ";
        int getUserLikeParams = userIdx;
        return this.jdbcTemplate.query(getUserLikeQuery,
                (rs, rowNum) -> new GetUserLike(
                        rs.getInt("l.storeIdx"),
                        rs.getString("찜상태"),
                        rs.getString("상점명"),
                        rs.getString("상점이미지"),
                        rs.getString("최소주문금액"),
                        rs.getString("배달팁"),
                        rs.getFloat("별점"),
                        rs.getInt("리뷰수"),
                        rs.getString("메인메뉴")
                ), getUserLikeParams);
    }

    public List<GetUserCoupon> getUserCoupon(int userIdx){
        String getUserCouponQuery = "select c.couponIdx,\n" +
                "       c.couponName \'쿠폰이름\',\n" +
                "       c.couponImage \'쿠폰이미지\',\n" +
                "       concat(format(c.couponPrice, 0), '원') \'쿠폰가격\',\n" +
                "       concat('최소주문금액 ', format(c.minAmount, 0), '원') \'최소주문금액\',\n" +
                "       concat('사용기간 ', c.startDate, ' - ', c.endDate) as \'사용기간\'\n" +
                "from Coupon c, User u\n" +
                "where c.userIdx = u.userIdx and couponState = 'T' and c.userIdx =  ? ";
        int getUserCouponParams = userIdx;
        return this.jdbcTemplate.query(getUserCouponQuery,
                (rs, rowNum) -> new GetUserCoupon(
                        rs.getInt("c.couponIdx"),
                        rs.getString("쿠폰이름"),
                        rs.getString("쿠폰이미지"),
                        rs.getString("쿠폰가격"),
                        rs.getString("최소주문금액"),
                        rs.getString("사용기간")
                ), getUserCouponParams);
    }

    public List<GetUserReview> getUserReview(int userIdx){
        String getUserReviewQuery = "select ur.reviewIdx,\n" +
                "       s.storeName \'상점명\',\n" +
                "       ri.image \'리뷰이미지\',\n" +
                "       ur.review \'리뷰내용\',\n" +
                "       m.menuName \'메뉴이름\',\n" +
                "       ur.scope \'별점\',\n" +
                "       ur.good \'추천여부\'\n" +
                "from UserReview ur, User u, ReviewImage ri, Store s, SelectMenu sm, Menu m\n" +
                "where ur.userIdx = u.userIdx\n" +
                "and ur.reviewIdx = ri.reviewIdx\n" +
                "and ur.storeIdx = s.storeIdx\n" +
                "and ur.selectIdx = sm.selectIdx\n" +
                "and sm.menuIdx = m.menuIdx and ur.userIdx = ?";
        int getUserReviewParams = userIdx;
        return this.jdbcTemplate.query(getUserReviewQuery,
                (rs, rowNum) -> new GetUserReview(
                        rs.getInt("ur.reviewIdx"),
                        rs.getString("상점명"),
                        rs.getString("리뷰이미지"),
                        rs.getString("리뷰내용"),
                        rs.getString("메뉴이름"),
                        rs.getFloat("별점"),
                        rs.getString("추천여부")
                ), getUserReviewParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into UserInfo (userName, userID, password, userEmail) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getUserName(), postUserReq.getId(), postUserReq.getPassword(), postUserReq.getEmail()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select userEmail from UserInfo where userEmail = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, userID, userName, password, userEmail from User where userID = ?";
        String getPwdParams = postLoginReq.getId();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userIdx"),
                        rs.getString("userID"),
                        rs.getString("userName"),
                        rs.getString("password"),
                        rs.getString("userEmail")),
                getPwdParams);

    }


}

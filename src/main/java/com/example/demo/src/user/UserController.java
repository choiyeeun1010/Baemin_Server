package com.example.demo.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /users
     * 회원 번호 및 이메일 검색 조회 API
     * [GET] /users? Email=
     * @return BaseResponse<List<GetUserRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String Email) {
        try{
            if(Email == null){
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users
            List<GetUserRes> getUsersRes = userProvider.getUsersByEmail(Email);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}") // (GET) 127.0.0.1:9000/users/:userIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetUserRes getUserRes = userProvider.getUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 메인화면 조회 API
     * [GET] /users/:userIdx/main
     * @return BaseResponse<GetUserMain>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}/main") // (GET) 127.0.0.1:9000/users/:userIdx/main
    public BaseResponse<List<GetUserMain>> getUserMain(@PathVariable("userIdx") int userIdx) {
        // Get Users Main
        try{
            List<GetUserMain> getUserMain = (List<GetUserMain>) userProvider.getUserMain(userIdx);
            return new BaseResponse<>(getUserMain);
        } catch(BaseException exception){
            return new BaseResponse

                    <>((exception.getStatus()));
        }

    }

    /**
     * 회원 주소 조회 API
     * [GET] /users/:userIdx/address
     * @return BaseResponse<GetUserAddress>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}/address") // (GET) 127.0.0.1:9000/users/:userIdx/address
    public BaseResponse<List<GetUserAddress>> getUserAddress(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetUserAddress> getUserAddress = userProvider.getUserAddress(userIdx);
            return new BaseResponse<>(getUserAddress);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원 검색내역 조회 API
     * [GET] /users/:userIdx/search
     * @return BaseResponse<GetUserSearch>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}/search") // (GET) 127.0.0.1:9000/users/:userIdx/search
    public BaseResponse<List<GetUserSearch>> getUserSearch(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetUserSearch> getUserSearch = userProvider.getUserSearch(userIdx);
            return new BaseResponse<>(getUserSearch);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 검색내역 조회(순위) API
     * [GET] /users/search
     * @return BaseResponse<GetSearchRanking>
     */
    @ResponseBody
    @GetMapping("/search") // (GET) 127.0.0.1:9000/users/search
    public BaseResponse<List<GetSearchRanking>> getSearchRanking() {
        // Get Users
        try{
            List<GetSearchRanking> getSearchRanking = userProvider.getSearchRanking();
            return new BaseResponse<>(getSearchRanking);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 찜한상점 조회 API
     * [GET] /users/:userIdx/like
     * @return BaseResponse<GetUserLike>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}/like") // (GET) 127.0.0.1:9000/users/:userIdx/like
    public BaseResponse<List<GetUserLike>> getUserLike(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetUserLike> getUserLike = userProvider.getUserLike(userIdx);
            return new BaseResponse<>(getUserLike);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 쿠폰 조회 API
     * [GET] /users/:userIdx/coupon
     * @return BaseResponse<GetUserCoupon>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}/coupon") // (GET) 127.0.0.1:9000/users/:userIdx/coupon
    public BaseResponse<List<GetUserCoupon>> getUserCoupon(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetUserCoupon> getUserCoupon = userProvider.getUserCoupon(userIdx);
            return new BaseResponse<>(getUserCoupon);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 리뷰 조회 API
     * [GET] /users/:userIdx/review
     * @return BaseResponse<GetUserReview>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{userIdx}/review") // (GET) 127.0.0.1:9000/users/:userIdx/review
    public BaseResponse<List<GetUserReview>> getUserReview(@PathVariable("userIdx") int userIdx) {
        // Get Users
        try{
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetUserReview> getUserReview = userProvider.getUserReview(userIdx);
            return new BaseResponse<>(getUserReview);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        try{
            if(postUserReq.getUserID() == null){
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            if(postUserReq.getUserID().length() < 3 || postUserReq.getUserID().length() > 15){
                return new BaseResponse<>(POST_USERS_INVALID_ID);
            }
            if(postUserReq.getUserName() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_NAME);
            }
            if(postUserReq.getUserName().length() < 2 || postUserReq.getUserName().length() > 10){
                return new BaseResponse<>(POST_USERS_INVALID_NAME);
            }
            if(postUserReq.getUserNickName() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
            }
            if(postUserReq.getUserNickName().length() < 2 || postUserReq.getUserNickName().length() > 10){
                return new BaseResponse<>(POST_USERS_INVALID_NICKNAME);
            }
            if(postUserReq.getEmail() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            //이메일 정규표현
            if(!isRegexEmail(postUserReq.getEmail())){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }
            if(postUserReq.getPassword() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            if(postUserReq.getPassword().length() < 4 || postUserReq.getPassword().length() > 15){
                return new BaseResponse<>(POST_USERS_INVALID_PASSWORD);
            }
            if(postUserReq.getUserPhone() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_PHONE);
            }
            if(postUserReq.getUserPhone().length() < 8 || postUserReq.getUserPhone().length() > 13){
                return new BaseResponse<>(POST_USERS_INVALID_PHONE);
            }
            if(postUserReq.getMailAgree() == null || postUserReq.getMailAgree().length() < 1){
                return new BaseResponse<>(POST_USERS_EMPTY_MAILAGREE);
            }
            if(postUserReq.getSmsAgree() == null || postUserReq.getSmsAgree().length() < 1){
                return new BaseResponse<>(POST_USERS_EMPTY_SMSAGREE);
            }

            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 주소 추가 API
     * [POST] /users/address
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/address")
    public BaseResponse<String> createUserAddress(@RequestBody PostUserAddress postUserAddress) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        try {
            if(postUserAddress.getAddress() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_ADDRESS);
            }
            if(postUserAddress.getAddress().length() < 5 || postUserAddress.getAddress().length() > 60){
                return new BaseResponse<>(POST_USERS_INVALID_ADDRESS);
            }
            int userIdx = postUserAddress.getUserIdx();
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.createUserAddress(postUserAddress);
            String result = postUserAddress.getAddress();
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 최근 검색어 추가 API
     * [POST] /users/search
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/search")
    public BaseResponse<String> createUserSearch(@RequestBody PostUserSearch postUserSearch) {
        // TODO: email 관련한 짧은 validation 예시입니다. 그 외 더 부가적으로 추가해주세요!
        try {
            if(postUserSearch.getSearchContents() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_CONTENTS);
            }
            if(postUserSearch.getSearchContents().length() < 1 || postUserSearch.getSearchContents().length() > 20){
                return new BaseResponse<>(POST_USERS_INVALID_CONTENTS);
            }
            int userIdx = postUserSearch.getUserIdx();
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            userService.createUserSearch(postUserSearch);
            String result = postUserSearch.getSearchContents();
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try{
            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
            if(postLoginReq.getId() == null || postLoginReq.getId().length() < 1){
                return new BaseResponse<>(USERS_EMPTY_USER_ID);
            }
            if(postLoginReq.getPassword() == null || postLoginReq.getPassword().length() < 1){
                return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
            }
            // 존재하지 않는 아이디 및 비밀번호라면
            // return new BaseResponse<>(3015);
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user){
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경
            PatchUserReq patchUserReq = new PatchUserReq(userIdx,user.getUserName());
            userService.modifyUserName(patchUserReq);

            String result = "";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 카카오 로그인 API
     * [POST] /users/kakao-signin
     * @return BaseResponse<PostUserSignInRes>
     */
    @ResponseBody
    @PostMapping("/kakao-login")
    public BaseResponse<PostLoginRes> postKakaoLogIn() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("KAKAO-ACCESS-TOKEN");

        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ACCESS_TOKEN);
        }

        try {
            PostLoginRes postLoginRes = userService.createKakaoSignIn(accessToken/*, deviceToken*/);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 네이버 로그인 API
     * [POST] /users/naver-signin
     * @return BaseResponse<PostUserSignInRes>
     */
    @ResponseBody
    @PostMapping("/users/naver-login")
    public BaseResponse<PostLoginRes> postNaverSignIn() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("NAVER-ACCESS-TOKEN");

        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_ACCESS_TOKEN);
        }

        try {
            PostLoginRes postLoginRes = userService.createNaverSignIn(accessToken);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

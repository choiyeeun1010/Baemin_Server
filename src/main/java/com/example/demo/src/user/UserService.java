package com.example.demo.src.user;



import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;


    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try{
            //암호화
            pwd = new SHA256().encrypt(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            int userIdx = userDao.createUser(postUserReq);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(jwt,userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createUserAddress(PostUserAddress postUserAddress) throws BaseException{
        try{
            int result = userDao.createUserAddress(postUserAddress);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createUserSearch(PostUserSearch postUserSearch) throws BaseException{
        try{
            int result = userDao.createUserSearch(postUserSearch);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }

        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
        try{
            int result = userDao.modifyUserName(patchUserReq);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /**
     * 카카오 로그인 API
     * @param accessToken
     * @return PostLogInRes
     * @throws BaseException
     */
    public PostLoginRes createKakaoSignIn(String accessToken/*, String deviceToken*/) throws BaseException {
        JSONObject jsonObject;

        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        String apiURL = "https://kapi.kakao.com/v2/user/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);

        HttpURLConnection con;
        try {
            URL url = new URL(apiURL);
            con = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new BaseException(WRONG_URL);
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_CONNECT);
        }

        String body;
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> rqheader : requestHeaders.entrySet()) {
                con.setRequestProperty(rqheader.getKey(), rqheader.getValue());
            }

            int responseCode = con.getResponseCode();
            InputStreamReader streamReader;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                streamReader = new InputStreamReader(con.getInputStream());
            } else { // 에러 발생
                streamReader = new InputStreamReader(con.getErrorStream());
            }

            BufferedReader lineReader = new BufferedReader(streamReader);
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            body = responseBody.toString();
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        } finally {
            con.disconnect();
        }

        if (body.length() == 0) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        }
        System.out.println(body);

        String socialId;
        String response;
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(body);
            socialId = "kakao_"+jsonObject.get("id").toString();
            response = jsonObject.get("kakao_account").toString();
        }
        catch (Exception e){
            System.out.println("1");
            throw new BaseException(FAILED_TO_PARSE);
        }

        String profilePhoto=null;
        String userName=null;
        String email=null;
        String phoneNumber=null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject responObj = (JSONObject) jsonParser.parse(response);
            if(responObj.get("email")!=null) {
                email = responObj.get("email").toString();
                System.out.println(email);

            }

            String profile = responObj.get("profile").toString();
            JSONObject profileObj = (JSONObject) jsonParser.parse(profile);
            userName = profileObj.get("nickname").toString();

            /*
            if(profileObj.get("profile_image")!=null) {
                profilePhoto = profileObj.get("profile_image").toString();
            }*/

        }
        catch (Exception e){
            System.out.println("2");

            throw new BaseException(FAILED_TO_PARSE);
        }


        User existUserInfo = null;
        //existUserInfo = userProvider.retrieveUserInfoBySocialId(socialId);

//        if (existUserInfo == null) {
//            UserInfo userInfo = new UserInfo(userName, null, null, deviceToken, null, null,null,socialId,email);
//
//            try {
//                userInfo = userInfoRepository.save(userInfo);
//            } catch (Exception exception) {
//                throw new BaseException(FAILED_TO_SAVE_USERINFO);
//            }
//
//            String jwt = jwtService.createJwt(userInfo.getUserIdx());
//
//            Integer useridx = userInfo.getUserIdx();
//            return new PostUserSignInRes(useridx, jwt);
//
//        }
       // else {
            GetSocial getSocial = userDao.getIdx(email);
            int userIdx = getSocial.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);
        //}
    }

    /**
     * 네이버 로그인 API
     * @param accessToken
     * @return PostLogInRes
     * @throws BaseException
     */
    public PostLoginRes createNaverSignIn(String accessToken/*, String deviceToken*/) throws BaseException {
        JSONObject jsonObject;
        String resultcode;

        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);

        HttpURLConnection con;
        try {
            URL url = new URL(apiURL);
            con = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new BaseException(WRONG_URL);
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_CONNECT);
        }

        String body;
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> rqheader : requestHeaders.entrySet()) {
                con.setRequestProperty(rqheader.getKey(), rqheader.getValue());
            }

            int responseCode = con.getResponseCode();
            InputStreamReader streamReader;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                streamReader = new InputStreamReader(con.getInputStream());
            } else { // 에러 발생
                streamReader = new InputStreamReader(con.getErrorStream());
            }

            BufferedReader lineReader = new BufferedReader(streamReader);
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            body = responseBody.toString();
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        } finally {
            con.disconnect();
        }

        if (body.length() == 0) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        }
        System.out.println(body);

        String socialId;
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(body);
            socialId = "naver_"+jsonObject.get("id").toString();
            resultcode = jsonObject.get("resultcode").toString();
            System.out.println(resultcode);
        }
        catch (Exception e){
            System.out.println("1");
            throw new BaseException(FAILED_TO_PARSE);
        }

        String response;
        if(resultcode.equals("00")){
            response = jsonObject.get("response").toString();
            System.out.println(response);
        }
        else{
            throw new BaseException(FORBIDDEN_ACCESS);
        }

        String profilePhoto=null;
        String userName=null;
        String email=null;
        String phoneNumber=null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject responObj = (JSONObject) jsonParser.parse(response);
            socialId = "naver_"+responObj.get("id").toString();

            if(responObj.get("email")!=null) {
                email = responObj.get("email").toString();
                System.out.println(email);

            }

            String profile = responObj.get("profile").toString();
            JSONObject profileObj = (JSONObject) jsonParser.parse(profile);
            userName = profileObj.get("nickname").toString();

            if(responObj.get("email")!=null) {
                email = responObj.get("email").toString();
            }
            if(responObj.get("mobile")!=null) {
                phoneNumber = responObj.get("mobile").toString();
            }
            /*
            if(profileObj.get("profile_image")!=null) {
                profilePhoto = profileObj.get("profile_image").toString();
            }*/

        }
        catch (Exception e){
            System.out.println("2");

            throw new BaseException(FAILED_TO_PARSE);
        }


        User existUserInfo = null;
        //existUserInfo = userProvider.retrieveUserInfoBySocialId(socialId);

//        if (existUserInfo == null) {
//            UserInfo userInfo = new UserInfo(userName, null, null, deviceToken, null, null,null,socialId,email);
//
//            try {
//                userInfo = userInfoRepository.save(userInfo);
//            } catch (Exception exception) {
//                throw new BaseException(FAILED_TO_SAVE_USERINFO);
//            }
//
//            String jwt = jwtService.createJwt(userInfo.getUserIdx());
//
//            Integer useridx = userInfo.getUserIdx();
//            return new PostUserSignInRes(useridx, jwt);
//
//        }
        // else {
        GetSocial getSocial = userDao.getIdx(email);
        int userIdx = getSocial.getUserIdx();
        String jwt = jwtService.createJwt(userIdx);
        return new PostLoginRes(userIdx, jwt);
        //}
    }
}

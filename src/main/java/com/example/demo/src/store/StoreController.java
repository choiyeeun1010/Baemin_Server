package com.example.demo.src.store;

import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.GetUserMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.store.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/stores")
public class StoreController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;
    @Autowired
    private final JwtService jwtService;


    public StoreController(StoreProvider storeProvider, StoreService storeService, JwtService jwtService) {
        this.storeProvider = storeProvider;
        this.storeService = storeService;
        this.jwtService = jwtService;
    }

    /**
     * 상점 메인화면 조회 API
     * [GET] /stores/:storeIdx
     * @return BaseResponse<GetStoreMain>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{storeIdx}") // (GET) 127.0.0.1:9000/stores/:storeIdx
    public BaseResponse<List<GetStoreMain>> getStoreMain(@PathVariable("storeIdx") int storeIdx) {
        try {
            List<GetStoreMain> getStoreMain = (List<GetStoreMain>) storeProvider.getStoreMain(storeIdx);
            return new BaseResponse<>(getStoreMain);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }

    /**
     * 전체 상점 조회 API
     * [GET] /stores/list
     * @return BaseResponse<GetStoreList>
     */
    @ResponseBody
    @GetMapping("/list") // (GET) 127.0.0.1:9000/stores/list
    public BaseResponse<List<GetStoreList>> getStoreList() {
        try {
            List<GetStoreList> getStoreList = (List<GetStoreList>) storeProvider.getStoreList();
            return new BaseResponse<>(getStoreList);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }

    /**
     * 상점 소개 조회 API
     * [GET] /stores/:storeIdx/intro
     * @return BaseResponse<GetStoreIntro>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{storeIdx}/intro") // (GET) 127.0.0.1:9000/stores/:storeIdx/intro
    public BaseResponse<List<GetStoreIntro>> getStoreIntro(@PathVariable("storeIdx") int storeIdx) {
        try {
            List<GetStoreIntro> getStoreIntro = (List<GetStoreIntro>) storeProvider.getStoreIntro(storeIdx);
            return new BaseResponse<>(getStoreIntro);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }

    /**
     * 상점 정보 조회 API
     * [GET] /stores/:storeIdx/info
     * @return BaseResponse<GetStoreInfo>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{storeIdx}/info") // (GET) 127.0.0.1:9000/stores/:storeIdx/info
    public BaseResponse<List<GetStoreInfo>> getStoreInfo(@PathVariable("storeIdx") int storeIdx) {
        try {
            List<GetStoreInfo> getStoreInfo = (List<GetStoreInfo>) storeProvider.getStoreInfo(storeIdx);
            return new BaseResponse<>(getStoreInfo);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }

    /**
     * 카테고리별 상점 조회 API
     * [GET] /stores/:categoryIdx/list
     * @return BaseResponse<GetStoreCategory>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{categoryIdx}/list") // (GET) 127.0.0.1:9000/stores/:categoryIdx/list
    public BaseResponse<List<GetStoreCategory>> getStoreCategory(@PathVariable("categoryIdx") int categoryIdx) {
        try {
            List<GetStoreCategory> getStoreCategory = (List<GetStoreCategory>) storeProvider.getStoreCategory(categoryIdx);
            return new BaseResponse<>(getStoreCategory);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }

    /**
     * 상점 메뉴 조회 API
     * [GET] /stores/:storeIdx/menu
     * @return BaseResponse<GetStoreMenu>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{storeIdx}/menu") // (GET) 127.0.0.1:9000/stores/:storeIdx/menu
    public BaseResponse<List<GetStoreMenu>> getStoreMenu(@PathVariable("storeIdx") int storeIdx) {
        try {
            List<GetStoreMenu> getStoreMenu = (List<GetStoreMenu>) storeProvider.getStoreMenu(storeIdx);
            return new BaseResponse<>(getStoreMenu);
        } catch (BaseException exception) {
            return new BaseResponse
                    <>((exception.getStatus()));
        }
    }
}

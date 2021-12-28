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
@RequestMapping("/store")
public class StoreController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;
    @Autowired
    private final JwtService jwtService;




    public StoreController(StoreProvider storeProvider, StoreService storeService, JwtService jwtService){
        this.storeProvider = storeProvider;
        this.storeService = storeService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{storeIdx}") // (GET) 127.0.0.1:9000/users/main/:userIdx
    public BaseResponse<List<GetStoreMain>> getStoreMain(@PathVariable("storeIdx") int storeIdx) {
        // Get Users Main
        try{
            List<GetStoreMain> getStoreMain = (List<GetStoreMain>) storeProvider.getStoreMain(storeIdx);
            return new BaseResponse<>(getStoreMain);
        } catch(BaseException exception){
            return new BaseResponse

                    <>((exception.getStatus()));
        }

    }


}

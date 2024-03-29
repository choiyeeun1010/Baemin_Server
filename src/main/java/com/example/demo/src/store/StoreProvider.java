package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.*;
import com.example.demo.src.store.StoreDao;
import com.example.demo.src.store.model.GetStoreMain;
import com.example.demo.src.user.model.GetUserMain;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class StoreProvider {

    private final StoreDao storeDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StoreProvider(StoreDao storeDao, JwtService jwtService) {
        this.storeDao = storeDao;
        this.jwtService = jwtService;
    }

    public List<GetStoreMain> getStoreMain(int storeIdx) throws BaseException {
        try {
            List<GetStoreMain> getStoreMain = storeDao.getStoreMain(storeIdx);
            return getStoreMain;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreList> getStoreList() throws BaseException {
        try {
            List<GetStoreList> getStoreList = storeDao.getStoreList();
            return getStoreList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreIntro> getStoreIntro(int storeIdx) throws BaseException {
        try {
            List<GetStoreIntro> getStoreIntro = storeDao.getStoreIntro(storeIdx);
            return getStoreIntro;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreInfo> getStoreInfo(int storeIdx) throws BaseException {
        try {
            List<GetStoreInfo> getStoreInfo = storeDao.getStoreInfo(storeIdx);
            return getStoreInfo;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreCategory> getStoreCategory(int categoryIdx) throws BaseException {
        try {
            List<GetStoreCategory> getStoreCategory = storeDao.getStoreCategory(categoryIdx);
            return getStoreCategory;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreMenu> getStoreMenu(int storeIdx) throws BaseException {
        try {
            List<GetStoreMenu> getStoreMenu = storeDao.getStoreMenu(storeIdx);
            return getStoreMenu;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}

package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    EMPTY_ACCESS_TOKEN(false,2004,"ACCESS TOKEN을 입력하세요."),


    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),


    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EXISTS_ID(false, 2018, "중복된 아이디입니다."),
    POST_USERS_INVALID_ID(false, 2019, "아이디 형식을 확인해주세요."),
    POST_USERS_EMPTY_NAME(false, 2020, "이름을 입력해주세요."),
    POST_USERS_INVALID_NAME(false, 2021, "이름 형식을 확인해주세요."),
    POST_USERS_EMPTY_NICKNAME(false, 2022, "닉네임을 입력해주세요."),
    POST_USERS_INVALID_NICKNAME(false, 2023, "닉네임 형식을 확인해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2024, "비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PASSWORD(false, 2025, "비밀번호 형식을 확인해주세요."),
    POST_USERS_EMPTY_PHONE(false, 2026, "전화번호를 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2027, "전화번호 형식을 확인해주세요."),
    POST_USERS_EXISTS_PHONE(false, 2028, "중복된 전화번호입니다."),
    POST_USERS_EMPTY_MAILAGREE(false, 2029, "메일수신동의를 확인해주세요."),
    POST_USERS_EMPTY_SMSAGREE(false, 2030, "sms 수신동의를 확인해주세요."),
    POST_USERS_EMPTY_USERIDX(false, 2031, "userIdx를 입력해주세요."),
    POST_USERS_INVALID_USERIDX(false, 2032, "userIdx 형식을 확인해주세요."),
    POST_USERS_EMPTY_ADDRESS(false, 2033, "주소를 입력해주세요."),
    POST_USERS_INVALID_ADDRESS(false, 2034, "주소 형식을 확인해주세요."),
    POST_USERS_EMPTY_CONTENTS(false, 2035, "검색어를 입력해주세요"),
    POST_USERS_INVALID_CONTENTS(false, 2036, "검색어 형식을 확인해주세요"),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),


    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),
    FAILED_TO_USERIDX(false, 3015, "존재하지 않는 userIdx입니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),


    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),


    // 5000 : 기타 오류
    WRONG_URL(false, 5001, "잘못된 URL 정보입니다."),
    FAILED_TO_CONNECT(false,5002,"URL 연결에 실패했습니다."),
    FAILED_TO_READ_RESPONSE(false,5003,"로그인 정보 조회에 실패했습니다."),
    FAILED_TO_PARSE(false,5004,"파싱에 실패했습니다."),
    FORBIDDEN_ACCESS(false, 5005, "접근 권한이 없습니다."),
    FAILED_TO_KAKAO_SIGN_UP(false, 5006, "카카오 회원가입에 실패하였습니다."),
    FAILED_TO_NAVER_SIGN_UP(false, 5007, "네이버 회원가입에 실패하였습니다."),
    FAILED_TO_KAKAO_SIGN_IN(false, 5008, "카카오 로그인에 실패하였습니다."),
    FAILED_TO_NAVER_SIGN_IN(false, 5009, "네이버 로그인에 실패하였습니다."),
    EXIST_USER(false, 5010, "존재하는 회원입니다. 로그인을 시도하세요"),
    FORBIDDEN_USER(false, 5011, "해당 회원에 접근할 수 없습니다."),

            ;
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

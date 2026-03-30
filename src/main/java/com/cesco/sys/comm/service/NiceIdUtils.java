package com.cesco.sys.comm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cesco.sys.comm.dto.NiceIdConfig;
import com.cesco.sys.comm.dto.NiceIdEncDataParamDto;
import com.cesco.sys.comm.dto.NiceIdResult;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

//@Slf4j
@Component("FwNiceIdUtils")
public class NiceIdUtils {

	@Autowired
	private SessionUtils sessionUtils;

	/**
	 * 휴대폰본인인증 Nice ID로 전달할 encdata를 생성하여 return한다.
	 *
	 * @param config:	Nice 환경설정
	 * @param param:	성공/실패 redirect url 정보
	 * @return 암호화된 문자열
	 */
	public String getEncData(NiceIdConfig config, NiceIdEncDataParamDto param) {
		/*
		 * NiceIdConfig 확인
		 */
		if (config == null) {
			throw new CommDuplicateException("nice-id 설정이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
		}
		if (config.getSiteCode().isBlank()) {
      throw new CommDuplicateException("nice-id:site-code 설정이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
		}
		if (config.getSitePassword().isBlank()) {
      throw new CommDuplicateException("nice-id:site-password 설정이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
		}

		/*
		 * 입력 확인
		 */
		if (param == null) {
      throw new CommDuplicateException("입력이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
		}
		if (param.getSuccessUrl().isBlank()) {
      throw new CommDuplicateException("successUrl을 입력하세요.",ErrorCode.INTER_SERVER_ERROR);
		}
		if (param.getFailUrl().isBlank()) {
      throw new CommDuplicateException("failUrl을 입력하세요.",ErrorCode.INTER_SERVER_ERROR);
		}
		// if (!param.getSuccessUrl().indexOf("http")) {
    //   throw new CommDuplicateException("successUrl은 http 또는 https로 시작해야 합니다.",ErrorCode.INTER_SERVER_ERROR);
		// }
		// if (!param.getFailUrl().indexOf("http")) {
    //   throw new CommDuplicateException("failUrl은 http 또는 https로 시작해야 합니다.",ErrorCode.INTER_SERVER_ERROR);
		// }

		// NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

	    //String sRequestNumber = niceCheck.getRequestNO(config.getSiteCode());
		String sRequestNumber = sessionUtils.niceIdSession.set(config.getSiteCode());
	   	String sAuthType = "M"; // 없으면 기본 선택화면, M(휴대폰), X(인증서공통), U(공동인증서), F(금융인증서), S(PASS인증서), C(신용카드)
		String customize = "";	// 없으면 기본 웹페이지 / Mobile : 모바일페이지

		/*
		 * CheckPlus(본인인증) 처리 후, 결과 데이타를 리턴 받기위해 다음예제와 같이 http부터 입력합니다.
		 * 리턴url은 인증 전 인증페이지를 호출하기 전 url과 동일해야 합니다. ex) 인증 전 url : http://www.~ 리턴 url : http://www.~
		 */
	    String sReturnUrl = param.getSuccessUrl();      // 성공시 이동될 URL
	    String sErrorUrl = param.getFailUrl();          // 실패시 이동될 URL

	    // 입력될 plain 데이타를 만든다.
	    String plainData = "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
	                        "8:SITECODE" + config.getSiteCode().getBytes().length + ":" + config.getSiteCode() +
	                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
	                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
	                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl +
	                        "9:CUSTOMIZE" + customize.getBytes().length + ":" + customize;

	    String encData = null;

	    // int iReturn = niceCheck.fnEncode(config.getSiteCode(), config.getSitePassword(), plainData);
	    // if (iReturn != 0 ) {
	    // 	String sMessage = null;
	    // 	if( iReturn == -1) {
	    // 		sMessage = "암호화 시스템 에러입니다.";
	    // 	}
	    // 	else if( iReturn == -2) {
	    // 		sMessage = "암호화 처리오류입니다.";
	    // 	}
	    // 	else if( iReturn == -3) {
	    // 		sMessage = "암호화 데이터 오류입니다.";
	    // 	}
	    // 	else if( iReturn == -9) {
	    // 		sMessage = "입력 데이터 오류입니다.";
	    // 	}
	    // 	else {
	    // 		sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
	    // 	}
      //   throw new CommDuplicateException(sMessage,ErrorCode.INTER_SERVER_ERROR);
	    // }

      //   encData = niceCheck.getCipherData();
        return encData;
	}

	/**
	 * NiceID로부터 받은 결과 데이터를 복호화하여 return한다.
	 *
	 * @param config:		Nice 환경설정
	 * @param encodeData:	NiceID로 부터 받은 암호화된 결과 데이터
	 * @return 복호화한 결과 데이터
	 */
	public NiceIdResult getResult(NiceIdConfig config, String encodeData) {
		/*
		 * NiceIdConfig 확인
		 */
		if (config == null) {
      throw new CommDuplicateException("nice-id 설정이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
		}
		if (config.getSiteCode().isBlank()) {
      throw new CommDuplicateException("nice-id:site-code 설정이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
		}
		if (config.getSitePassword().isBlank()) {
      throw new CommDuplicateException("nice-id:site-password 설정이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
		}

		/*
		 * 입력확인
		 */
		if (encodeData.isBlank()) {
      throw new CommDuplicateException("encodeData를 입력하세요.",ErrorCode.INTER_SERVER_ERROR);
		}

	    // NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

	    // String sEncodeData = requestReplace(encodeData, "encodeData");

	    // //String sCipherTime = "";			// 복호화한 시간
	    // String sMessage = "";
	    // String sPlainData = "";

	    // int iReturn = niceCheck.fnDecode(config.getSiteCode(), config.getSitePassword(), sEncodeData);

	    // if (iReturn != 0) {
		  //   if( iReturn == -1) {
		  //       sMessage = "복호화 시스템 오류입니다.";
		  //   }
		  //   else if( iReturn == -4) {
		  //       sMessage = "복호화 처리 오류입니다.";
		  //   }
		  //   else if( iReturn == -5) {
		  //       sMessage = "복호화 해쉬 오류입니다.";
		  //   }
		  //   else if( iReturn == -6) {
		  //       sMessage = "복호화 데이터 오류입니다.";
		  //   }
		  //   else if( iReturn == -9) {
		  //       sMessage = "입력 데이터 오류입니다.";
		  //   }
		  //   else if( iReturn == -12) {
		  //       sMessage = "사이트 패스워드 오류입니다.";
		  //   }
		  //   else {
		  //       sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
		  //   }

	    // 	throw new CommDuplicateException(sMessage,ErrorCode.INTER_SERVER_ERROR);
	    // }

      //   sPlainData = niceCheck.getPlainData();
      //   String sCipherTime = niceCheck.getCipherDateTime(); // 복호화한 시간

        // 데이타를 추출합니다.
    //     @SuppressWarnings("unchecked")
		// java.util.HashMap<String, String> mapresult = niceCheck.fnParse(sPlainData);

    //     String sRequestNumber  = mapresult.get("REQ_SEQ");
    //     String sResponseNumber = mapresult.get("RES_SEQ");
    //     String sAuthType       = mapresult.get("AUTH_TYPE");
    //     String sName         = mapresult.get("NAME");
		// //String sName           = mapresult.get("UTF8_NAME"); //charset utf8 사용시 주석 해제 후 사용
    //     String sBirthDate      = mapresult.get("BIRTHDATE");
    //     String sGender         = mapresult.get("GENDER");
    //     String sNationalInfo   = mapresult.get("NATIONALINFO");
    //     String sDupInfo        = mapresult.get("DI");
    //     String sConnInfo       = mapresult.get("CI");
    //     String sMobileNo       = mapresult.get("MOBILE_NO");
    //     String sMobileCo       = mapresult.get("MOBILE_CO");

    //     boolean sessionValue = sessionUtils.niceIdSession.check(sRequestNumber);
    //     if(!sessionValue) {
    //       throw new CommDuplicateException("휴대폰 본인인증 세션값 불일치 오류입니다.",ErrorCode.INTER_SERVER_ERROR);
    //     }

    //     NiceIdResult result = NiceIdResult.builder()
    //     		.cipherTime(sCipherTime)
    //     		.requestNumber(sRequestNumber)
    //     		.responseNumber(sResponseNumber)
    //     		.authType(sAuthType)
    //     		.name(sName)
    //     		.birthDate(sBirthDate)
    //     		.gender(sGender)
    //     		.nationalInfo(sNationalInfo)
    //     		.dupInfo(sDupInfo)
    //     		.connInfo(sConnInfo)
    //     		.mobileNo(sMobileNo)
    //     		.mobileCo(sMobileCo)
    //     		.build();
    //     return result;
    return new NiceIdResult();
	}

	private String requestReplace (String paramValue, String gubun) {

        String result = "";

        if (paramValue != null) {

        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");

        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            	paramValue = paramValue.replaceAll("=", "");
        	}

        	result = paramValue;

        }
        return result;
  }
}

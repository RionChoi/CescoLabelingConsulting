package com.cesco.sys.comm.service;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cesco.sys.comm.dto.CmmtSessionInfoDto;
import com.cesco.sys.comm.dto.NiceIdResult;
import com.cesco.sys.common.CommonUtils;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

@Component("FwSwssionUtils")
public class SessionUtils {

	@Autowired
	private HttpServletRequest request;

	/**
	 * 만료일시 30분 연장
	 *
	 * @param sessionId
	 */
	public void updateExpiredDt(String sessionId)
	{
		updateExpiredDt(sessionId, 30);
	}

	/**
	 * 만료일시를 입력된 분만큼 연장
	 *
	 * @param sessionId
	 * @param addMin
	 */
	public void updateExpiredDt(String sessionId, int addMin)
	{
		Date now = new Date();
    // todo......
		// Date expiredDt = service.addMinutes(now, addMin);
		// service.updateExpiredDt(sessionId, expiredDt);
	}

	/**
	 * 세션 만료
	 *
	 * @param sessionId
	 */
	public void expire(String sessionId) {
		// Date now = new Date();
		// service.updateExpiredDt(sessionId, now);
	}

	/**
	 * Nice 휴대폰 본인인증 환경정보 Session 처리
	 */
	public class NiceIdSession {
		private final int EXPIRED_MINUTE = 60;  // 만료시간을 60분으로 정의

		public String set(String siteCode) {
			String sessionId = CommonUtils.getNewId(siteCode + "-");
			Date now = new Date();
			// Date expiredDt = date.addMinutes(now, EXPIRED_MINUTE);
			CmmtSessionInfoDto session = CmmtSessionInfoDto.builder()
											.sessionId(sessionId)
											.sessionValue(Boolean.toString(false))
											.createdDt(now)
											.expiredDt(now)
											.build();
			// service.insert(session);

			return sessionId;
		}
	}

	/**
	 * 휴대폰 본인인증 인증결과 정보 Session 처리
	 */
	public class MobileCertSession
	{
		private final int EXPIRED_MINUTE = 60;  // 만료시간을 60분으로 정의

		/**
		 * 휴대폰본인인증 결과를 세션에 저장한다.
		 * @param result NiceId 결과
		 * @return Session ID
		 */
		public String set(NiceIdResult result) {
			String sessionId = CommonUtils.getNewId("niceid-");
			Date now = new Date();
			// Date expiredDt = date.addMinutes(now, EXPIRED_MINUTE);
			// String sessionValue = json.toString(result);
			// CmmtSessionInfoDto session = CmmtSessionInfoDto.builder()
			// 		.sessionId(sessionId)
			// 		.sessionValue(sessionValue)
			// 		.createdDt(now)
			// 		.expiredDt(expiredDt)
			// 		.build();
			// dao.insert(session);
			return sessionId;
		}

		/**
		 * Session에 저장된 휴대폰본인인증 결과(NiceIdResult)를 조회한다.
		 *
		 * @param sessionId 세션ID
		 * @return 휴대폰본인인증 결과
		 */
		public NiceIdResult get(String sessionId) {
			Date now = new Date();
			// CmmtSessionInfoDto session = dao.select(sessionId);
			// if (session == null) {
      //   throw new CommDuplicateException("휴대폰 본인인증 결과가 없습니다.",ErrorCode.INTER_SERVER_ERROR);
			// }

			// if (now.compareTo(session.getExpiredDt()) > 0) {
      //   throw new CommDuplicateException("휴대폰 본인인증 결과 저장기간이 만료되었습니다.",ErrorCode.INTER_SERVER_ERROR);
			// }

			// NiceIdResult result = session.getSessionJsonValue(NiceIdResult.class);
			// if (result == null) {
      //   throw new CommDuplicateException("휴대폰 본인인증 결과가 없습니다.",ErrorCode.INTER_SERVER_ERROR);
			// }

			// return result;
        return new NiceIdResult();
		}
	}

	public final NiceIdSession niceIdSession = new NiceIdSession();
	public final MobileCertSession mobileCertSession = new MobileCertSession();
}

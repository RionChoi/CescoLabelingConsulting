package com.cesco.sys.comm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cesco.co.notice.dto.NoticeAttachDto;
import com.cesco.co.notice.service.NoticeMgtService;
import com.cesco.fs.consultMgt.service.ConsultMgtService;
import com.cesco.sys.comm.dto.ChangePasswdParam;
import com.cesco.sys.comm.dto.CommCodeDTO;
import com.cesco.sys.comm.dto.ConsultingAttach;
import com.cesco.sys.comm.dto.FtpUploadReturn;
import com.cesco.sys.comm.dto.SendPhoneDto;
import com.cesco.sys.comm.dto.UserDTO;
import com.cesco.sys.comm.dto.UserReqDTO;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.comm.service.FileCommService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.common.Utils;
import com.cesco.sys.communityhandlers.CommDuplicateException;
import com.jcraft.jsch.SftpException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author choihaegun
 * @apiNote common
 */

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
// @CrossOrigin(origins = "http://127.0.0.1:3000", allowedHeaders = "*")
public class ApiController {
    private static final Logger logger = LogManager.getLogger(ApiController.class);
    
    @Autowired
    private CommService commService;
    @Autowired
    private FileCommService fileCommService;
    @Autowired
    ConsultMgtService consultMgtService;
    @Autowired
    NoticeMgtService noticeMgtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * @apiNote 로그인 화면 체크
     * @return UserDTO
     */
    @PostMapping("/login")
    public UserDTO callLogin(@RequestBody UserReqDTO req,HttpServletRequest request,HttpSession session) throws Exception {
        // 회원조회
        UserDTO userDto = commService.getUsersMail(req);
        if(userDto != null) {
            // if(!req.getSys_code().equals(userDto.getSys_id())){
            //     throw new CommDuplicateException("서비스 구분 확인 하세요.",ErrorCode.PASSWORDS_DO_NOT_MATCH);
            // }
            if(!passwordEncoder.matches(req.getPwd(), userDto.getPwd())){
                throw new CommDuplicateException("비밀번호 맞지 않습니다.",ErrorCode.PASSWORDS_DO_NOT_MATCH);
            }

            session.setAttribute("userId"    , userDto.getUser_id());         // 사용자 ID
            session.setAttribute("userNm"    , userDto.getUser_nm());         // 사용자 명
            session.setAttribute("cstcd"     , userDto.getCst_id());          // 고객 ID
            session.setAttribute("dept"      , userDto.getUser_dept_nm());    // 사용자 부서명
            session.setAttribute("deptCode"  , userDto.getCst_tp_cd());       // 부서 코드 
            session.setAttribute("cstNm"     , userDto.getCst_nm());          // 고객명
            session.setAttribute("email"     , userDto.getEmail());           // 사용자 이메일
            session.setAttribute("userTpCd"  , userDto.getUser_tp_cd());      // 사용자 구분코드
            session.setAttribute("sysCode"   ,  req.getSys_code());           // 시스텡 코드
            session.setAttribute("notice1Yn" , userDto.getNotice1_yn());      // 신규의뢰시 알림
            session.setAttribute("notice2Yn" , userDto.getNotice2_yn());      // 멘션 알림
            session.setAttribute("notice3Yn" , userDto.getNotice3_yn());      // 참여컨설팅 알림
            session.setAttribute("notice4Yn" , userDto.getNotice4_yn());      // 견젹서 및 입금확인 알림

            // 신규 로그인 확인
            try {
                // 이력조회
                List<UserDTO> userDtoLog = commService.getUserHis(req);
                // 신규 아닌 경우
                if (userDtoLog.size() > 0) { // pwd_init_yn = 'N'
                    commService.saveLoginHis(userDto);
                }

            } catch (IndexOutOfBoundsException  e) {
                throw new CommDuplicateException("",ErrorCode.LOGINLOG_DUPLICATION);
            }
        }else{
            session.invalidate();
            throw new CommDuplicateException("이메일 맞지 않습니다.",ErrorCode.LOGINLOG_DUPLICATION);
        }
        return userDto;
    }

    /**
     * @apiNote 사용자 조회
     * @return UserDTO
     */
    @GetMapping(value = "/users")
    public UserDTO getUser(@RequestParam("user_id") String user_id,@RequestParam("pwd") String pwd) throws Exception{
        UserReqDTO uto = new UserReqDTO();
        uto.setUser_id(user_id);
        uto.setPwd(pwd);
        // 시스템구분 따라 사용자 조회
        return commService.getUser(uto);
    }

    /**
     * @apiNote 비밀번호 변경 신규
     * @param   passwd,repasswd
     * @return UserDTO
     */
    @PutMapping(value = "/changePass")
    public void changePass(@RequestBody ChangePasswdParam param,HttpSession session) throws Exception{
        UserReqDTO uto = new UserReqDTO();
        logger.info("paramparamparamparamparam" + param);
        uto.setEmail(param.getEmail());
        uto.setPwd(param.getPwd());
        UserDTO utoInfo = commService.getUsersMail(uto);
        
        /*
		 * 비밀번호 확인
		 */
		if (!param.getPasswd1().equals(param.getPasswd2())) {
            throw new CommDuplicateException("",ErrorCode.PASSWORDS_DO_NOT_MATCH);
		}
        String encodePasswod = passwordEncoder.encode(param.getPasswd1());
        // 비밀번호 암호화
        param.setPasswd1(encodePasswod);
        // 비밀번호 변경
        int res =  commService.changePass(param);
        if (res > 0) {
            // 수정 완료
            try {
                commService.saveLoginHis(utoInfo);
                logger.info("비밀번호 수정 되었습니다.");
            } catch (Exception e) {
                throw new CommDuplicateException("",ErrorCode.PASSWORDS_CHANGE_FAIL);
            }
        } else {
            session.invalidate();
            throw new CommDuplicateException("비밀번호 수정 실페하였습니다.",ErrorCode.PASSWORDS_CHANGE_FAIL);
        }
    }

    /**
     * @apiNote 비밀번호 변경 초기화
     * @param   passwd,repasswd
     * @return UserDTO
     */
    @PutMapping(value = "/loginForgotPwEnter")
    public ResponseEntity<String> loginForgotPwEnter(@RequestBody ChangePasswdParam param,HttpSession session) throws Exception{
        /*
		 * 비밀번호 확인
		 */
		if (!param.getPasswd1().equals(param.getPasswd2())) {
            throw new CommDuplicateException("",ErrorCode.PASSWORDS_DO_NOT_MATCH);
		}

        // 비밀번호 암호화
        String encodePasswod = passwordEncoder.encode(param.getPwd());
        param.setPwd(encodePasswod);
        // 비밀번호 변경
        int res =  commService.loginForgotPwEnter(param);
        if (res > 0) {
            // 수정 완료
            try {
                return new ResponseEntity<>("비밀번호 초기화 되었습니다.",HttpStatus.OK);
            } catch (Exception e) {
                throw new CommDuplicateException("비밀번호 초기화 실페하였습니다.",ErrorCode.PASSWORDS_CHANGE_FAIL);
            }
        } else {
            throw new CommDuplicateException("비밀번호 초기화 실페하였습니다.",ErrorCode.PASSWORDS_CHANGE_FAIL);
        }
    }

    /**
     * @apiNote 공통 코드 조회
     * @param sys_cd , cmm_cd
     * @return UserDTO
     */
    @PostMapping(value="/commDtlCodeAllList")
    public List<CommCodeDTO> getCommDtlCodeAlllist(@RequestBody List<CommCodeDTO> commCodeList, HttpSession session) throws Exception{
        
        commCodeList = commService.getCommDtlCodeList(commCodeList, session);

        return commCodeList;
    } 

    /**
     * 휴대폰 인증 번호 발송
     * @return
     * @throws Exception
     */
    @PostMapping("/sendPhone")
    public ResponseEntity<String> uspFsSetCommCellPhone(@RequestBody SendPhoneDto param) throws Exception {
        UserReqDTO req = new UserReqDTO();
        req.setTel_no(param.getDest_phone());
        if (param.getDest_phone().isBlank()) {
            throw new CommDuplicateException("휴대폰 번호 입력하세요",ErrorCode.INTER_SERVER_ERROR);
        }
        // 회원조회
        UserDTO userDto = commService.getUsersPhone(req);
        if (userDto == null) {
            throw new CommDuplicateException("회원정보 없습니다. 확인 후 다시 시도하세요",ErrorCode.INTER_SERVER_ERROR);
        }
        // 인증 번호 생성
        Integer intval = Utils.generateAuthNo();

        param.setMsg_type("0");
        param.setSend_id(userDto.getUser_id());
        param.setMsg_body(intval.toString());
        // 인증 번호 발송 서시스 호출
        Integer res = commService.uspFsSetCommCellPhone(param);
        
        if (res > 0) {
            return new ResponseEntity<>(intval.toString(),HttpStatus.OK);
        } else {
            throw new CommDuplicateException("사용자 정보 없습니다. 확인 후 다시 시도 해주세요",ErrorCode.INTER_SERVER_ERROR);
        }
    }

    /**
     * @apiNote nice 인증토큰 데이터 전달
     * @param sys_cd , cmm_cd
     * @return UserDTO
     * @throws Exception
     */
	@PostMapping(value="/findUserId")
	public UserDTO init(@RequestBody UserReqDTO param) throws Exception {
        UserDTO box = commService.findUserId(param);
        if (box == null) {
            throw new CommDuplicateException("사용자 정보 없습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
        return box;
	}
    
    /**
     * @apiNote 파일 다운로드 
     * @param filename , viewid
     * @return ConsultingAttach / ConsultingAttachChatDto
     * @throws Exception
     */
    @GetMapping(value ="/filedownload/{viewid}/{filename}")
    public ResponseEntity<InputStreamResource> fileDownload(@PathVariable String filename,@PathVariable String viewid,HttpSession session) throws Exception{
        String sysCode = (String)session.getAttribute("sysCode");
        String downname = "";
        String url = "";

        // 컨설팅
        if(viewid.equals("cons") || viewid.equals("chat")) {
            // 파일 다운로드 정보 가져오기
            ConsultingAttach consultingAttach = consultMgtService.getAttachDownloadInfo(filename,viewid);
            if(consultingAttach == null){
                throw new CommDuplicateException("",ErrorCode.NOT_FOUND);
            }
            downname = consultingAttach.getOrg_file_nm();
            url = consultingAttach.getUrl();

        // 공지사항
        } else if(viewid.equals("noti")) {
            // 파일 다운로드 정보 가져오기
            NoticeAttachDto noticeAttachDto = noticeMgtService.getAttachDownloadInfo(filename,viewid);

            if(noticeAttachDto == null){
                throw new CommDuplicateException("",ErrorCode.NOT_FOUND);
            }

            downname = noticeAttachDto.getOrg_file_nm();
            url = noticeAttachDto.getUrl();
        }

        // 다운로드명
        downname = URLEncoder.encode(downname, "UTF-8");

        String mediaType = "application/octet-stream";
        // 헤더 셋팅
        HttpHeaders header = new HttpHeaders();        

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downname);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
            .headers(header)
            .contentType(MediaType.parseMediaType(mediaType))
            .body(commService.sftpDownLoad(url, filename, sysCode));    
    }

    /**
     * @apiNote zip 파일 다운로드
     * @param filename , viewid
     * @return ConsultingAttach / ConsultingAttachChatDto
     * @throws Exception
     */ 
    @GetMapping(value ="/downloadZipFile/{viewid}/{filename}")
    public void downloadZipFile(@PathVariable String viewid,@PathVariable String filename,HttpServletResponse response,HttpSession session,HttpServletRequest request) throws Exception {
        String sysCode = (String)session.getAttribute("sysCode");
        String[] fileNmList = filename.split("_");
        List<ConsultingAttach> fileInfoList  = new ArrayList<>();

        for (String fileNm : fileNmList) {
            ConsultingAttach consultingAttach = consultMgtService.getAttachDownloadInfo(fileNm,viewid);
            if (ObjectUtils.isEmpty(consultingAttach)) {
                throw new CommDuplicateException("파일 정보 없습니다.",ErrorCode.REQUIRED_VALUE_ERROR);
            }
            fileInfoList.add(consultingAttach);
        }
        
        logger.info("downloadZipFile :::::"+ fileInfoList);
        fileCommService.zipFileDownload(fileInfoList,sysCode,response,request);

    }


    @RequestMapping(value = "/file/download")
    public void fileDownload(HttpServletResponse response) throws Exception {

        File file = new File("/Users/choi/sftpfile/" + "uspt_wnpz_202211121830.sql");
        InputStream inputStream = new FileInputStream(file);
        assert inputStream != null;
    
        StreamUtils.copy(inputStream, response.getOutputStream());
    
        response.flushBuffer();
        inputStream.close();
        // 파일 가져오기 
        // fileCommService.sftpDownLoad("/Users/choi/sftpfile/", "uspt_wnpz_202211121830.sql", "FS",response);
    }


    /**
     * 표시컨설팅_채팅창 파일 등록
     * @return
     */ 
    @PostMapping("/fileUp")
    public ResponseEntity<FtpUploadReturn> fileUp(@RequestPart(value = "files", required = false) List<MultipartFile> files,HttpSession session) throws Exception {
        FtpUploadReturn ftpUploadReturn = new FtpUploadReturn();
        try {
           // 파일 저장
            if(files != null){            
                for(MultipartFile f : files){
                    // 파일 업로드
                    ftpUploadReturn = fileCommService.sftpUpLoad(f, "FS");
                }
            }
        } catch (Exception e) {
            throw new CommDuplicateException("",ErrorCode.UPLOAD_FAIL);
        }
        return new ResponseEntity<FtpUploadReturn>(ftpUploadReturn,HttpStatus.OK);
    }













    @GetMapping(value = "/down/file")
    // @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<InputStreamResource> fileDownload2(HttpSession session) throws Exception{
        // String sysCode = (String)session.getAttribute("sysCode");
        String downname = "uspt_wnpz_202211121830.sql";
        String url = "/Users/choi/sftpfile/";

        // 다운로드명
        downname = URLEncoder.encode(downname, "UTF-8");

        String mediaType = "application/octet-stream";
        // 헤더 셋팅
        HttpHeaders header = new HttpHeaders();        

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downname);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
            .headers(header)
            .contentType(MediaType.parseMediaType(mediaType))
            .body(commService.sftpDownLoad(url, downname, "FS"));    
    }

    /**
     * 세션 알림정보 변경
     * @param noticeMgtDto
     * @returnPostMapping
     */
    @PostMapping(value="/setUserChangeNoti")
    public ResponseEntity<String> setUserChangeNoti(@RequestBody UserDTO userDto, HttpSession session) throws Exception {

        // 세션 알림정보 변경
        int result = commService.setUserChangeNoti(userDto, session);

        if(result == 1) {
            // session 재 세팅
            session.setAttribute("notice1Yn" , userDto.getNotice1_yn());      // 신규의뢰시 알림
            session.setAttribute("notice2Yn" , userDto.getNotice2_yn());      // 멘션 알림
            session.setAttribute("notice3Yn" , userDto.getNotice3_yn());      // 참여컨설팅 알림
            session.setAttribute("notice4Yn" , userDto.getNotice4_yn());      // 견젹서 및 입금확인 알림

            return new ResponseEntity<>("변경 성공", HttpStatus.OK);  
        } else {
            return new ResponseEntity<>("변경 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

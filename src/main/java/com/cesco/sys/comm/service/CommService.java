package com.cesco.sys.comm.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.Vector;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import com.cesco.sys.comm.dto.ChangePasswdParam;
import com.cesco.sys.comm.dto.CommCodeDTO;
import com.cesco.sys.comm.dto.CommDtlCodeDTO;
import com.cesco.sys.comm.dto.ConsultingAttach;
import com.cesco.sys.comm.dto.FtpUploadReturn;
import com.cesco.sys.comm.dto.MailDto;
import com.cesco.sys.comm.dto.SendPhoneDto;
import com.cesco.sys.comm.dto.UserDTO;
import com.cesco.sys.comm.dto.UserReqDTO;
import com.cesco.sys.comm.mapper.CommMapper;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;
import com.cesco.sys.config.SftpProperties;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommService {
    private static final Logger looger = LogManager.getLogger(CommService.class);
    // 처음 로그인할 때 프롬프트를 설정합니다. 선택적 값은 다음과 같습니다：(ask | yes | no)
    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    
    @Autowired
    private SftpProperties config;

	// private Session session = null;
	// private Channel channel = null;
	// private ChannelSftp channelSftp = null;

    private final CommMapper commMapper;

    /**
     * SFTP 연결
     * @return
     * @throws Exception
     */
    private ChannelSftp createSftp() throws Exception {
        JSch jsch = new JSch();
        looger.info("Try to connect sftp[" + config.getUsername() + "@" + config.getHost() + "], use password[" + config.getPassword() + "]");

        Session session = createSession(jsch, config.getHost(), config.getUsername(), config.getPort());
        session.setPassword(config.getPassword());
        session.connect(config.getSessionConnectTimeout());

        looger.info("Session connected to {}.", config.getHost());

        Channel channel = session.openChannel(config.getProtocol());
        channel.connect(config.getChannelConnectedTimeout());

        looger.info("Channel created to {}.", config.getHost());

        return (ChannelSftp) channel;
    }
    /**
     * session 연결
     * @param jsch
     * @param host
     * @param username
     * @param port
     * @return
     * @throws Exception
     */
    private Session createSession(JSch jsch, String host, String username, Integer port) throws Exception {
        Session session = null;

        if (port <= 0) {
            session = jsch.getSession(username, host);
        } else {
            session = jsch.getSession(username, host, port);
        }

        if (session == null) {
            throw new CommDuplicateException("세션 존재 하지않습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }

        session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, config.getSessionStrictHostKeyChecking());
        return session;
    }

    /**
     * 사용자 조회
     * @return
     */
    public UserDTO getUser(UserReqDTO uto) throws Exception{
        looger.info("사용자 조회:::",uto);
        return commMapper.getUser(uto);
    }
    /**
     * 사용자 조회 이메일
     * @return
     */
    public UserDTO getUsersMail(UserReqDTO uto) throws Exception{
        looger.info("사용자 조회:::",uto);
        return commMapper.getUsersMail(uto);
    }
    /**
     * 사용자 조회 휴대폰
     * @return
     */
    public UserDTO getUsersPhone(UserReqDTO uto) throws Exception{
        looger.info("사용자 조회:::",uto);
        return commMapper.getUsersPhone(uto);
    }

    /**
     * 사용자 조회 이름/폰
     * @return
     */
    public UserDTO findUserId(UserReqDTO uto) throws Exception{
        looger.info("사용자 조회:::",uto);
        return commMapper.findUserId(uto);
    } 
    
    /**
     * 로그인 이력 조회
     * @return
     */
    public List<UserDTO> getUserHis(UserReqDTO uto) throws Exception{
        looger.info("로그인 이력 조회:::",uto);
        return commMapper.getUserHis(uto);
    }

    /**
     * 로그인 이력 저장
     * @return
     */
    public void saveLoginHis(UserDTO uto) throws Exception{
        looger.info("로그인 이력 저장:::",uto);
        commMapper.saveLoginHis(uto);
    }

    /**
     * 이메일 저장
     * @return
     */
    public int saveEmail(MailDto uto) throws Exception{
        looger.info("로그인 이력 저장:::",uto);
        return commMapper.saveEmail(uto);
    }

    /**
     * 휴대폰 인증 발송
     * @return
     */
    public int uspFsSetCommCellPhone(SendPhoneDto param) throws Exception{
        looger.info("로그인 이력 저장:::",param);
        return commMapper.uspFsSetCommCellPhone(param);
    }

    /**
     * 비밀번호 변경
     * @return
     */
    public int changePass(ChangePasswdParam uto) throws Exception{
        looger.info("비밀번호 변경:::",uto);
        return commMapper.changePass(uto);
    }
    
    /**
     * 비밀번호 초기화
     * @return
     */
    public int loginForgotPwEnter(ChangePasswdParam uto) throws Exception{
        looger.info("비밀번호 변경:::",uto);
        return commMapper.loginForgotPwEnter(uto);
    }
    
    /**
     * 파일 업로드 정보 저장
     * @return
     */
    public String saveUspFsSetAttach(ConsultingAttach uto) throws Exception{
        looger.info("비밀번호 변경:::",uto);
        return commMapper.saveUspFsSetAttach(uto);
    }

    /**
     * 공통 파일 업로드 정보 저장 일반
     * @param sys_id
     * @param userId
     * @param fileList
     * @return void
    */
    public void multipartFileUpload(String sys_id, String userId, List<MultipartFile> fileList) throws Exception{

        if(fileList != null && fileList.size() > 0) {
            if(fileList != null && fileList.size() > 0) {
                // int size = 100 * (1024 * 1024);
                for(MultipartFile fileInfo : fileList) {
                    try {
                        long fileSize = 0;
                        int maxFileSize = 1000 * (1024 * 1024);
                        fileSize = fileInfo.getSize();
                        
                        if (maxFileSize > 0 && fileSize > maxFileSize) {
                            throw new CommDuplicateException("저장가능한 파일크기(" + maxFileSize + ")를 초과하였습니다." ,ErrorCode.UPLOAD_FAIL);
                        }

                        Date nowDate = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                        System.out.println(simpleDateFormat.format(nowDate));

                        ConsultingAttach fileData = new ConsultingAttach();
                        String linkid = UUID.randomUUID().toString() + simpleDateFormat.format(nowDate);
                        
                        fileData.setFs_no(null);
                        fileData.setAtch_nm(fileInfo.getOriginalFilename());
                        fileData.setAtch_size(fileSize + "");
                        fileData.setOrg_file_nm(fileInfo.getOriginalFilename());
                        fileData.setUrl("fs/ftp/0afa4955-91ee-40ff-81e4-db08e76d8850.jpg");
                        fileData.setStatus("Y");
                        fileData.setSys_id(sys_id);
                        fileData.setRgstr_id(userId);
                        fileData.setSer_file_nm(linkid);
                        // fileData.setRgstr_id("navy");
    
                        // 파일 정보 저장
                        String res = saveUspFsSetAttach(fileData);
    
                        // 파일 저장
                        File nfile = new File(fileInfo.getOriginalFilename());
                        fileInfo.transferTo(nfile);
        
                        } catch (IllegalStateException | IOException e) {
                        looger.error("Fail to create directory '" + fileInfo + "'");
                        throw new CommDuplicateException("",ErrorCode.INTER_SERVER_ERROR);
                    }
                }
            }
        }
    } 

    /**
     * * 공통 파일 FTP다운로드 단일 파일
     * @param directory 저장된 디렉토리 경로
     * @param filename  저장된 파일 이름
     * @return InputStreamResource
     * @throws Exception
     */
	public InputStreamResource sftpDownLoad(String directory,String fileName, String sysid) throws Exception{
        
        InputStreamResource resource = null;
        if (sysid.isEmpty()) {
            throw new CommDuplicateException("시스템 구분 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        if (directory.isEmpty()) {
            throw new CommDuplicateException("파일경로 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        if (fileName.isEmpty()) {
            throw new CommDuplicateException("파일명이  없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        
        // sftp 연결
        ChannelSftp sftp = this.createSftp();
		try {
            //ftp.enterLocalActiveMode();// FTP 서버설정 엑티브
            // 서버 경로 진입
			sftp.cd(directory);
			File file = new File(fileName);
            // 서버 파일 수출
            resource = new InputStreamResource(sftp.get(file.getName()));

		}  catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            // this.disconnection();
            // disconnect(sftp);
            // sftp.quit();
            // session.disconnect();
        }

        return resource;
	}

	public InputStream sftpDownLoad1(String directory,String fileName, String sysid) throws Exception{
        // sftp 연결
        ChannelSftp sftp = this.createSftp();
        InputStream ins = null;
		try {
            //ftp.enterLocalActiveMode();// FTP 서버설정 엑티브
            // 서버 경로 진입
			sftp.cd(directory);
			File file = new File(fileName);
            ins = sftp.get(file.getName());

		}  catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            // this.disconnection();
            // resource.getDescription();
            // this.disconnect(sftp);
            // sftp.quit();
            // session.disconnect();
        }

        return ins;
	}


	public void sftpDownLoad3(String directory,String fileName, String sysid,HttpServletResponse res) throws Exception{
        // sftp 연결
        ChannelSftp sftp = this.createSftp();
        InputStream ins = null;
		try {
            //ftp.enterLocalActiveMode();// FTP 서버설정 엑티브
            // 서버 경로 진입
			sftp.cd(directory);
			File file = new File(fileName);
            ins = sftp.get(file.getName());

            assert ins != null;
            StreamUtils.copy(ins, res.getOutputStream());
        
            res.flushBuffer();
            ins.close();
            
		}  catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            // 커넥션 connection 종료
            this.disconnect(sftp);
        }
	}

    /**
     * * 공통 파일 FTP다운로드
     * @param fileInfoList 파일 정보
     * @param sysid  시스템 id
     * @return InputStreamResource
     * @throws Exception
     */
	public void zipFileDownload(List<ConsultingAttach> fileInfoList ,String sysid,HttpServletResponse response,HttpServletRequest request) throws Exception{
        if (sysid.isEmpty()) {
            throw new CommDuplicateException("시스템 구분 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        ZipArchiveOutputStream zous = this.getServletOutputStream(response);
        
        try {
            // sftp 연결
            ChannelSftp sftp = this.createSftp();
            //ftp.enterLocalActiveMode();// FTP 서버설정 엑티브
            // 서버 경로 진입
            sftp.cd(fileInfoList.get(0).getUrl());
            // response param 설정 및 ServletOutputStream 받아오기
            
            // zipDirFileToFile(mapList,request,response,channelSftp);
            for (ConsultingAttach map1 : fileInfoList) {
                String fileName1 = map1.getSer_file_nm();
                InputStream inputStream = sftp.get(map1.getSer_file_nm());
                this.setByteArrayOutputStream(fileName1, inputStream, zous);
            }
            zous.close();
        }  catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            if(zous != null){
                try{ zous.close(); } catch( IOException e){}
            }
            // this.disconnection();
        }
	}

    /**
     * 공통 파일 SFTP업로드
     * @param file  업로드 파일
     * @param dir   저장 디렉토리 구분자 => \\
     * @return filename : 파일명(ex> 699580db-5725-4577-b657-883b61d4bcdd-316284.pdf), 
     * directory : 저장 경로(ex> \fsdev\fs\)
     * @throws Exception
     */
	public FtpUploadReturn uploadFileFTP(MultipartFile fiels, String sysid, String tempFileName) throws Exception {

        if (sysid.isEmpty()) {
            throw new CommDuplicateException("시스템 구분 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        // 서버 파일 정보
        FtpUploadReturn ftpUploadReturn = new FtpUploadReturn();

        long fileSize = 0;
        int maxFileSize = 1000 * (1024 * 1024);
        fileSize = fiels.getSize();
        // 파일 사이즈 체크
        if (maxFileSize > 0 && fileSize > maxFileSize) {
            throw new CommDuplicateException("저장가능한 파일크기(" + maxFileSize + ")를 초과하였습니다." ,ErrorCode.UPLOAD_FAIL);
        }

        // sftp 연결
        ChannelSftp sftp = this.createSftp();
        //ftp.enterLocalActiveMode();// FTP 서버설정 엑티브
        // 날짜별 디렉토리 생성 막아둠
        Date date = new Date();
        SimpleDateFormat locFormat = new SimpleDateFormat("yyyyMMdd");
        String locNow = null;
        locNow = locFormat.format(date);

        // 디렉토리 생성 반드시 \\ <= 로 구분
        String directory = "";
        // 시스템 구분에 따라 경로 셋팅
        if ("FS".equals(sysid)) {
            directory =config.getRoot() + locNow;
        } else {
            directory = config.getRootHc() + locNow;
        }
        String arrDir[] = directory.split("/");
        // 서버 경로 확인
        if(StringUtils.isNotEmpty(directory)){
            String tempDir = "";
            for(String sebDir : arrDir){
                tempDir += "/" + sebDir;                        
                if(exists(tempDir)) {						 
                    sftp.cd(tempDir);
                }
                else {
                    sftp.mkdir(tempDir);
                    sftp.cd(tempDir);
                }
            }
            sftp.cd(tempDir);
        }    
        
        InputStream fis = null;
        try {
            // 랜덤 파일명 생성
            UUID uuid = UUID.randomUUID();
            String orgFilename = fiels.getOriginalFilename();
            String fileExt   = orgFilename.substring(orgFilename.lastIndexOf("."),orgFilename.length());
            // 서버 파일 명 생성
            String serverfilename = uuid.toString() + "-" + (int)(Math.random()*1000000) + fileExt;                
            
            fis = fiels.getInputStream(); // multipartFile.getInputStream()으로
            // 파일 전송
            sftp.put(fis, serverfilename);
            // 파일 정보 셋팅
            ftpUploadReturn.setDirectory(directory);
            ftpUploadReturn.setFilename(serverfilename);
        } catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            disconnect(sftp);
        }
        
		return ftpUploadReturn;
	}



    /**
     * 공통 파일 SFTP업로드
     * @param file  업로드 파일
     * @param dir   저장 디렉토리 구분자 => \\
     * @return filename : 파일명(ex> 699580db-5725-4577-b657-883b61d4bcdd-316284.pdf), 
     * directory : 저장 경로(ex> \fsdev\fs\)
     * @throws Exception
     */
	public FtpUploadReturn uploadFileFTP(MultipartFile fiels, String sysid) throws Exception {

        // 서버 파일 정보
        FtpUploadReturn ftpUploadReturn = new FtpUploadReturn();

        // sftp 연결
        ChannelSftp sftp = this.createSftp();

        sftp.cd("/Users/choi/upload");

        InputStream fis = null;
        try {
            // 랜덤 파일명 생성
            UUID uuid = UUID.randomUUID();

            String orgFilename = fiels.getOriginalFilename();
            String fileExt   = orgFilename.substring(orgFilename.lastIndexOf("."),orgFilename.length());
            // 서버 파일 명 생성
            String serverfilename = uuid.toString() + "-" + (int)(Math.random()*1000000) + fileExt;                
            
            fis = fiels.getInputStream(); // multipartFile.getInputStream()으로
            // 파일 전송
            sftp.put(fis, serverfilename);
            // 파일 정보 셋팅
            ftpUploadReturn.setDirectory(config.getRoot());
            ftpUploadReturn.setFilename(serverfilename);
        } catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            disconnect(sftp);
        }
        
		return ftpUploadReturn;
	}

    /**
     * hc base64 이미지 업로드
     * @param commCodeList
     * @return
     * @throws Exception
     */
    public FtpUploadReturn uploadImgFTP(String sysid,String tempFileName,byte[] base64img) throws Exception {
        if (sysid.isEmpty()) {
            throw new CommDuplicateException("시스템 구분 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        if (tempFileName.isEmpty()) {
            throw new CommDuplicateException("파일명이 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        if (base64img.length < 0) {
            throw new CommDuplicateException("파일이 존재 하지 않습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        // 서버 파일 정보
        FtpUploadReturn ftpUploadReturn = new FtpUploadReturn();

            // sftp 연결
            ChannelSftp sftp = this.createSftp();
			//ftp.enterLocalActiveMode();// FTP 서버설정 엑티브
            // 날짜별 디렉토리 생성 막아둠
            Date date = new Date();
            SimpleDateFormat locFormat = new SimpleDateFormat("yyyyMMdd");
            String locNow = null;
            locNow = locFormat.format(date);

            // 디렉토리 생성 반드시 \\ <= 로 구분
            String directory = "";
            // 시스템 구분에 따라 경로 셋팅
            if ("FS".equals(sysid)) {
                directory =config.getRoot() + locNow;
            } else {
                directory = config.getRootHc() + locNow;
            }
            String arrDir[] = directory.split("/");
            // 서버 경로 확인
            if(StringUtils.isNotEmpty(directory)){
                String tempDir = "";
                for(String sebDir : arrDir){
                    tempDir += "/" + sebDir;                        
                    if(exists(tempDir)) {						 
                        sftp.cd(tempDir);
                    }
                    else {
                        sftp.mkdir(tempDir);
                        sftp.cd(tempDir);
                    }
                }
                sftp.cd(tempDir);
            }    
			
			InputStream fis = null;
			try {
                // 랜덤 파일명 생성
                UUID uuid = UUID.randomUUID();
                String fileExt   = tempFileName.substring(tempFileName.lastIndexOf("."),tempFileName.length());
                // 서버 파일 명 생성
                String serverfilename = uuid.toString() + "-" + (int)(Math.random()*1000000) + fileExt;                
                
				fis = new ByteArrayInputStream(base64img);

                // 파일 전송
				sftp.put(fis, serverfilename);
                ftpUploadReturn.setDirectory(directory);
                ftpUploadReturn.setFilename(serverfilename);
			}  catch (SftpException e) {
                throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
            } finally {
                disconnect(sftp);
            }
		return ftpUploadReturn;
    }

    /**
     * 공통코드, 고객사, 담당자 list -> cmm_dtl_cd, cmm_dtl_nm으로 조회
     * @param commCodeList
     * @return
     * @throws Exception
     */
    public List<CommCodeDTO> getCommDtlCodeList(List<CommCodeDTO> commCodeList, HttpSession session) throws Exception {
	
        // 고객사
        for (CommCodeDTO commcode : commCodeList) {
            if(commcode.getCmm_cd().equals("cst")) {
                commcode.setP_parm1(session.getAttribute("sysCode").toString());
                commcode.setP_parm2(session.getAttribute("cstcd").toString());
                commcode.setP_parm3(session.getAttribute("userId").toString());
                commcode.setCstList(this.getComboCstList(commcode));            

            // 담당자
            } else if(commcode.getCmm_cd().equals("mng")) {
                commcode.setP_parm1(session.getAttribute("sysCode").toString());
                commcode.setP_parm2(session.getAttribute("cstcd").toString());
                commcode.setP_parm3(session.getAttribute("userId").toString());
                commcode.setMngList(this.getComboMngList(commcode));            

            // 부서
            } else if(commcode.getCmm_cd().equals("dept")) {
                commcode.setP_parm1(session.getAttribute("sysCode").toString());
                commcode.setP_parm2(session.getAttribute("cstcd").toString());
                commcode.setP_parm3(session.getAttribute("userId").toString());
                commcode.setDeptList(this.getComboDeptList(commcode));

            // 공통코드
            } else {
                // commcode.setSys_cd(session.getAttribute("sysCode").toString());
                commcode.setDtlCodeList(this.getCommDtlCode(commcode));            
            }
        }

        return commCodeList;
    }

    /**
     * 공통 코드 목록 조회
     * @return
     */
    public List<CommDtlCodeDTO> getCommDtlCode(CommCodeDTO commCode) throws Exception{
        looger.info("공통 코드 목록 조회:::",commCode);
        return commMapper.getCommDtlCode(commCode);
    }

    /**
     * 고객사 combo box 조회
     * @param commCode
     * @return
     * @throws Exception
     */
    public List<CommDtlCodeDTO> getComboCstList(CommCodeDTO commCode) throws Exception{
        List<CommDtlCodeDTO> ctsList = commMapper.getComboCstList(commCode);
        return ctsList;
    }

    /**
     * 관리자 combo box 조회
     * @param commCode
     * @return
     * @throws Exception
     */
    public List<CommDtlCodeDTO> getComboMngList(CommCodeDTO commCode) throws Exception{
        List<CommDtlCodeDTO> mngList = commMapper.getComboMngList(commCode);
        return mngList;
    }

    /**
     * 부서 combo box 조회
     * @param commcode
     * @return
     * @throws Exception
     */
    private List<CommDtlCodeDTO> getComboDeptList(CommCodeDTO commcode) throws Exception {
        List<CommDtlCodeDTO> mngList = commMapper.getComboDeptList(commcode);
        return mngList;
    }

    private  ZipArchiveOutputStream getServletOutputStream(HttpServletResponse response) throws Exception {

        String outputFileName = "문서" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";
        response.reset();
        response.setHeader("Content-Type", "application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        ServletOutputStream out = response.getOutputStream();

        ZipArchiveOutputStream zous = new ZipArchiveOutputStream(out);
        zous.setUseZip64(Zip64Mode.AsNeeded);
        return zous;
    }
    // 파일 셋팅
    private  void setByteArrayOutputStream(String fileName, InputStream inputStream, ZipArchiveOutputStream zous) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        byte[] bytes = baos.toByteArray();

        //파일명 설정
        ArchiveEntry entry = new ZipArchiveEntry(fileName);
        zous.putArchiveEntry(entry);
        zous.write(bytes);
        zous.closeArchiveEntry();
        baos.close();
    }

	private boolean exists(String path) {
        Vector<?> res = null;
        // try {
        //     // res = channelSftp.ls(path);
        // } catch (SftpException e) {
        //     if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
        //         return false;
        //     }
        // }
        return res != null && !res.isEmpty();
    }

    /**
     * 关闭连接
     * @param sftp
     */
    private void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    looger.info("sftp is closed already");
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 세션 알림정보 변경
     * @param userDto
     * @param session
     * @return
     * @throws Exception
     */
    public int setUserChangeNoti(UserDTO userDto, HttpSession session) throws Exception {
        userDto.setUser_id(session.getAttribute("userId").toString());
        int result = commMapper.setUserChangeNoti(userDto);
        return result;
    }

}

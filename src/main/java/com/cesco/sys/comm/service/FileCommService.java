package com.cesco.sys.comm.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import com.cesco.sys.comm.dto.ConsultingAttach;
import com.cesco.sys.comm.dto.FtpUploadReturn;
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
public class FileCommService {
    private static final Logger looger = LogManager.getLogger(FileCommService.class);
    // 처음 로그인할 때 프롬프트를 설정합니다. 선택적 값은 다음과 같습니다：(ask | yes | no)
    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    
    @Autowired
    private SftpProperties config;

	public void sftpDownLoad(String directory,String fileName, String sysid,HttpServletResponse res) throws Exception{
        // sftp 연결
        ChannelSftp sftp = this.createSftp();
        InputStream ins = null;
		try {
            // 서버 경로 진입
			sftp.cd(directory);
			File file = new File(fileName);
            ins = sftp.get(file.getName());

            assert ins != null;
            StreamUtils.copy(ins, res.getOutputStream());
            res.flushBuffer();
            // res.getOutputStream().flush();
            ins.close();
            
		}  catch (SftpException e) {
            switch (e.id) {
                case 0:
                    throw new CommDuplicateException("작업이 성공적으로 완료되었습니다.",ErrorCode.SSH_FX_OK);
                case 1:
                    throw new CommDuplicateException("파일 끝 상태를 나타냅니다. SSH_FX_READ의 경우파일에서 더 많은 데이터를 사용할 수 있으며 SSH_FX_READDIR의 경우디렉토리에 더 이상 파일이 포함되어 있지 않음을 나타냅니다.",ErrorCode.SSH_FX_EOF);
                case 2:
                    throw new CommDuplicateException("파일 존재 하지 않습니다.",ErrorCode.SSH_FX_NO_SUCH_FILE);
                case 3:
                    throw new CommDuplicateException("인증된 사용자가 충분하지 않은 경우 반환됩니다.작업을 수행할 수 있는 권한.",ErrorCode.SSH_FX_PERMISSION_DENIED);
                case 4:
                    throw new CommDuplicateException("파일반적인 포괄 오류 메시지입니다. 다음과 같은 경우 반환해야 합니다.더 이상 구체적인 오류 코드가 없는 오류가 발생합니다.한정된.",ErrorCode.SSH_FX_FAILURE);
                case 5:
                    throw new CommDuplicateException("형식이 잘못된 패킷이나 프로토콜이비호환성이 감지되었습니다.",ErrorCode.SSH_FX_BAD_MESSAGE);
                case 6:
                    throw new CommDuplicateException("클라이언트에 오류가 없음을 나타내는 의사 오류입니다.서버에 대한 연결(로컬에서만 생성할 수 있습니다.클라이언트이며 서버에서 반환하면 안 됩니다(MUST NOT).",ErrorCode.SSH_FX_NO_CONNECTION);
                case 7:
                    throw new CommDuplicateException("에 대한 연결을 나타내는 의사 오류입니다.서버가 손실되었습니다(서버에서 로컬로만 생성할 수 있음).클라이언트이며 서버에서 반환하면 안 됩니다(MUST NOT).",ErrorCode.SSH_FX_CONNECTION_LOST);         
                default:
                    throw new CommDuplicateException("작업을 수행하려고 시도했음을 나타냅니다.서버에 대해 지원되지 않음(로컬에서 생성될 수 있음)예를 들어 클라이언트 버전 번호 교환은필요한 기능이 서버에서 지원되지 않거나서버가 구현하지 않은 경우 서버에서 반환작업).",ErrorCode.SSH_FX_OP_UNSUPPORTED);
            }
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
        // 시스템 구분 확인
        if (sysid.isEmpty()) {
            throw new CommDuplicateException("시스템 구분 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        // 파일 저보 유무 확인
        if (fileInfoList.size() < 1) {
            throw new CommDuplicateException("파일 저보 없습니다." ,ErrorCode.INTER_SERVER_ERROR);
        }
        ZipArchiveOutputStream zous = this.getServletOutputStream(response);
        // sftp 연결
        ChannelSftp sftp = this.createSftp();
        // 서버 경로 진입
        sftp.cd(fileInfoList.get(0).getUrl());
        try {
            for (ConsultingAttach map1 : fileInfoList) {
                String fileName1 = map1.getSer_file_nm();
                InputStream inputStream = sftp.get(map1.getSer_file_nm());
                this.setByteArrayOutputStream(fileName1, inputStream, zous);
            }
            zous.close();
        }  catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } catch (FileNotFoundException e) {
            throw new CommDuplicateException("서버 파일 존재 하지 않습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            this.disconnect(sftp);
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
	public FtpUploadReturn sftpUpLoad(MultipartFile fiels, String sysid, String tempFileName) throws Exception {

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
                if(exists(tempDir,sftp)) {						 
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
	public FtpUploadReturn sftpUpLoad(MultipartFile fiels, String sysid) throws Exception {

        // 서버 파일 정보
        FtpUploadReturn ftpUploadReturn = new FtpUploadReturn();
        InputStream fis = null;

        // sftp 연결
        ChannelSftp sftp = this.createSftp();

        // 서버 파일 위치에 진입
        String directory = this.goDirectory(sysid,sftp);

        try {
            // 서버 파일 명 생성
            String serverfilename = mkServerFileName(fiels.getOriginalFilename());            
            // 파일 담기
            fis = fiels.getInputStream();
            // 파일 전송
            sftp.put(fis, serverfilename);
            // 파일 정보 셋팅
            ftpUploadReturn.setDirectory(directory);
            ftpUploadReturn.setFilename(serverfilename);
        } catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            // 세션 종료
            disconnect(sftp);
        }
        
		return ftpUploadReturn;
	}

    /**
     * hc 레포트 싸인 base64 이미지 업로드
     * @param  base64img / sysid / tempFileName
     * @return
     * @throws Exception
     */
    public FtpUploadReturn sftpReportImageUpLoad(String sysid,String tempFileName,byte[] base64img) throws Exception {
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
        InputStream fis = null;
        // sftp 연결
        ChannelSftp sftp = this.createSftp();
        // 서버 파일 위치에 진입
        String directory = this.goDirectory(sysid,sftp);

        try {
            String serverfilename = mkServerFileName(tempFileName);                
            
            fis = new ByteArrayInputStream(base64img);

            // 파일 전송
            sftp.put(fis, serverfilename);
            ftpUploadReturn.setDirectory(directory);
            ftpUploadReturn.setFilename(serverfilename);
        }  catch (SftpException e) {
            throw new CommDuplicateException("파일 전송 실페했습니다.",ErrorCode.UPLOAD_FAIL);
        } finally {
            // 세션 종료
            disconnect(sftp);
        }
		return ftpUploadReturn;
    }

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
     * session 생성
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
     * 서버 파일명 생성
     * @param  tempFileName
     * @return
     * @throws Exception
     */
    private  String mkServerFileName(String tempFileName) {
            // 랜덤 파일명 생성
            UUID uuid = UUID.randomUUID();
            String fileExt   = tempFileName.substring(tempFileName.lastIndexOf("."),tempFileName.length());
            // 서버 파일 명 생성
            String serverfilename = uuid.toString() + "-" + (int)(Math.random()*1000000) + fileExt;  
        return serverfilename;
    }

    /**
     * go to sftp server file directory ( 파일 경로 진입)
     * @param  sftp / sysid 
     * @return
     * @throws Exception
     */
    private  String goDirectory(String sysid,ChannelSftp sftp) throws Exception {
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
                if(exists(tempDir,sftp)) {						 
                    sftp.cd(tempDir);
                }
                else {
                    sftp.mkdir(tempDir);
                    sftp.cd(tempDir);
                }
            }
            sftp.cd(tempDir);
        }   
        return directory;
    }

    private  ZipArchiveOutputStream getServletOutputStream(HttpServletResponse response) throws Exception {

        String outputFileName = "컨설팅압축파일" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".zip";
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
    
    /**
     * 파일 압축 실행
     * @param  base64img / sysid / tempFileName
     * @return
     * @throws Exception
     */
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
    
    /**
     * 파일 여부 확인
     * @param  base64img / sysid / tempFileName
     * @return
     * @throws Exception
     */
	private boolean exists(String path,ChannelSftp channelSftp) {
        Vector<?> res = null;
        try {
            res = channelSftp.ls(path);
        } catch (SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                return false;
            }
        }
        return res != null && !res.isEmpty();
    }

    /**
     * sftp and session connection 종료
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
            throw new CommDuplicateException("서버 정상적으로 종료 되지 않아습니다.",ErrorCode.UPLOAD_FAIL);
        }
    }

}

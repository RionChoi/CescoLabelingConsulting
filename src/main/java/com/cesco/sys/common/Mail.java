package com.cesco.sys.common;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.cesco.sys.comm.dto.MailDto;
import com.cesco.sys.comm.service.CommService;
import com.cesco.sys.communityhandlers.CommDuplicateException;
import java.util.Properties;

public class Mail {
    
    @Autowired
    private final CommService service;

    public Mail(CommService service) {
        this.service = service;
    }

    public void sendMail(MailDto eMailDto, String subject){
        if (eMailDto.getSys_id().isBlank()) {            
            throw new CommDuplicateException("시스템 id  없습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
        if (eMailDto.getSend_tp_cd().isBlank()) {            
            throw new CommDuplicateException("알림발송유형이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
        if (eMailDto.getReceive_email().isBlank()) {
            throw new CommDuplicateException("전송 이메일 정보 없습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
        if (eMailDto.getSend_text().isBlank()) {
            throw new CommDuplicateException("전송 내용이 없습니다.",ErrorCode.INTER_SERVER_ERROR);
        }
        if (eMailDto.getSend_nm().isBlank()) {
            throw new CommDuplicateException("전송 담당자명 없습니다",ErrorCode.INTER_SERVER_ERROR);
        }

        try {
            // 메일 환경 변수 설정입니다.
            Properties props = new Properties();
            // 메일 프로토콜은 gmail를 이용할 것이기 때문에 smtp로 사용합니다.
            props.setProperty("mail.transport.protocol", "smtp");
            // 메일 호스트 주소를 설정합니다.
            props.setProperty("mail.host", "mail.cesco.co.kr");
            // ID, Password 설정이 필요합니다.
            props.put("mail.smtp.auth", "true");
            // port는 465입니다.
            props.put("mail.smtp.port", "25");
            // ssl를 사용할 경우 설정합니다.
            props.setProperty("mail.smtp.quitwait", "false");
            // id와 password를 설정하고 session을 생성합니다.
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("no_reply_notifications@cesco.co.kr", "!QAZ2ws3ed4rf");
                }
            });
            // 디버그 모드입니다.
            session.setDebug(true);
            // 메일 메시지를 만들기 위한 클래스를 생성합니다.
            MimeMessage message = new MimeMessage(session);
            // 송신자 설정
            message.setFrom(getAddress("no_reply_notifications@cesco.co.kr"));
            // 수신자 설정
            message.addRecipients(Message.RecipientType.TO, getAddresses(eMailDto.getReceive_email()));
            // 메일 제목을 설정합니다.
            message.setSubject(subject);
            // 메일 내용을 설정을 위한 클래스를 설정합니다.
            message.setContent(new MimeMultipart());

            // 메일 내용을 위한 Multipart클래스를 받아온다. (위 new MimeMultipart()로 넣은 클래스입니다.)
            Multipart mp = (Multipart) message.getContent();

            // 이메일 저장
            try {
                Integer res = service.saveEmail(eMailDto);
                if (res>0) {
                    String body = "";
                    switch (eMailDto.getSend_tp_cd()) {
                        case "01": // FSCCS10005 => 비밀번호 초기화 인증 메일
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getInit_code() + "]" + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getReceive_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "] " + eMailDto.getInit_code() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>";
                            break;
                        case "02": // FSCCS10069 => 컨설팅 의뢰 시 _고객접수 시
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "]" + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getReceive_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "] " + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>" +
                            " @세스코운영자</div>" +
                            " <div style='margin-top:10px;font-size:25px;color:#233975;'>" +
                            " [CPLB][" + eMailDto.getSend_nm() + "]님으로부터" + 
                            " <span style='color:#0052d9;'>" + eMailDto.getProd_nm() + 
                            " </span> 접수가 의뢰되었습니다.</div>";
                            break;
                        case "03": // FSCCS10070 => 컨설팅 의뢰 시_고객이 일부 제품 등록 후 ADMIN권한으로 최종승인  
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "]" + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getReceive_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "] " + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>" +
                            " @세스코운영자</div>" +
                            " <div style='margin-top:10px;font-size:25px;color:#233975;'>" +
                            " [CPLB][" + eMailDto.getSend_nm() + "]님으로부터" + 
                            " <span style='color:#0052d9;'>" + eMailDto.getProd_nm() + 
                            " </span> 접수가 의뢰되었습니다.</div>";
                            break;
                        case "04": // FSCCS10071 => 대화창에서의 기능_세스코가 업체로 확인요청 댓글 달았을 시 
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "]" + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getReceive_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "] " + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>" +
                            " @세스코운영자</div>" +
                            " <div style='margin-top:10px;font-size:25px;color:#233975;'>" +
                            " [CPLB][" + eMailDto.getSend_nm() + "]님으로부터" + 
                            " <span style='color:#0052d9;'>" + eMailDto.getProd_nm() + 
                            " </span> 접수가 의뢰되었습니다.</div>";
                            break;  
                        case "05": // FSCCS10072 => 대화창에서의 기능_업체가 세스코담당자에게 댓글 달았을 시
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ eMailDto.getSend_chat_st_nm() + "]" + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getSend_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ eMailDto.getSend_chat_st_nm() + "] " + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>@" + eMailDto.getReceive_nm() + 
                            "</div>" +
                            " <div style='margin-top:10px;font-size:25px;color:#233975;'>" +
                            " <span style='color:#0052d9;'>" + subject + "</span></div>";
                            break;      
                        case "06": // FSCCS10073 => 서비스내역서 확인요청 시 
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "]" + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getReceive_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "] " + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>" +
                            " @세스코운영자</div>" +
                            " <div style='margin-top:10px;font-size:25px;color:#233975;'>" +
                            " [CPLB][" + eMailDto.getSend_nm() + "]님으로부터" + 
                            " <span style='color:#0052d9;'>" + eMailDto.getProd_nm() + 
                            " </span> 접수가 의뢰되었습니다.</div>";
                            break;
                        case "07": // FSCCS10074 => 서비스내역서 확인 완료 시
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "]" + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getReceive_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "] " + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>" +
                            " @세스코운영자</div>" +
                            " <div style='margin-top:10px;font-size:25px;color:#233975;'>" +
                            " [CPLB][" + eMailDto.getSend_nm() + "]님으로부터" + 
                            " <span style='color:#0052d9;'>" + eMailDto.getProd_nm() + 
                            " </span> 접수가 의뢰되었습니다.</div>";
                            break;
                        case "08": // FSCCS10075 => 계산서 완료 후 고객으로부터 입금완료 시
                            body =                 
                            " <div style='margin-top:100px;padding-bottom:10px;border-bottom:3px solid #171725;font-size:30px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "]" + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:40px;font-size:16px;color:#233975;'>" + 
                            " 22/02/18 13:51:20 "+ eMailDto.getReceive_nm() + " 담당자 님으로부터 멘션 알림이 있습니다.</div>" +
                            " <div style='margin-top:15px;font-size:16px;color:#233975;'>" +
                            " [CPLB]["+ subject +"]["+ eMailDto.getReceive_nm() + "] " + eMailDto.getProd_nm() + "</div>" +
                            " <div style='margin-top:55px;font-size:16px;color:#233975;'>" +
                            " @세스코운영자</div>" +
                            " <div style='margin-top:10px;font-size:25px;color:#233975;'>" +
                            " [CPLB][" + eMailDto.getSend_nm() + "]님으로부터" + 
                            " <span style='color:#0052d9;'>" + eMailDto.getProd_nm() + 
                            " </span> 접수가 의뢰되었습니다.</div>";
                            break;            
                            default:
                            break;
                    }                
        
                    // html 형식으로 본문을 작성해서 바운더리에 넣습니다.
                    mp.addBodyPart(getContents(
                        "<!DOCTYPE html>" +
                        "<html lang='ko'>" +
                        "	<head>" +
                        "		<meta http-equiv='content-type' charset='UTF-8' />" +
                        "		<title>세스코 표시컨설팅 알림 메일</title>" +
                        "	</head>" +
                        "	<body>" +
                        " <table width='1000' border='0' cellspacing='0' cellpadding='0' style='background-color:#fff;font-family:Malgun Gothic,맑은 고딕,dotum,sans-serif;'>" +
                        "        <tbody>" +
                        "            <tr>" +
                        "                <td style='border:0px solid #d0d0d0;border-width:1px 1px 0;'>" +
                        "                    <table width='1000' border='0' cellspacing='0' cellpadding='0'>" +
                        "                        <tbody>" +
                        "                            <tr><td height='60' colspan='3'></td></tr>" +
                        "                            <tr>" +
                        "                                <td width='50'>&nbsp;</td>" +
                        "                                <td>" +
                        "                                    <div style='margin:0;overflow:hidden;'>" +
                        "                                        <img src='https://fsccsdev.cesco.co.kr/images/common/logo_2x.png' border='0' style='float:left;width:100px;margin-top:8px;' alt='CESCO 로고' />" +
                        "                                        <h1 style='margin:0;margin-left:20px;font-weight:400;font-style:normal;color:#233975;font-size:35px;float:left;letter-spacing:-1.8px;font-family:Malgun Gothic,맑은 고딕,dotum,sans-serif;'>세스코 표시컨설팅 알림 메일</h1>" +
                        "                                    </div>" +
                        "                                </td>" +
                        "                                <td width='50'>&nbsp;</td>" +
                        "                            </tr>" +
                        "                            <tr style='font-style:normal;font-size:20px;line-height:30px;letter-spacing:-0.07em;word-spacing:-0.03em;color:#000;font-family:Malgun Gothic,맑은 고딕,dotum,sans-serif;'>" +
                        "                                <td width='50'>&nbsp;</td>" +
                        "                                <td style='line-height:1.5;'>" + 
                                                            body +
                                                         "</td>" +
                        "                                <td width='50'>&nbsp;</td>" +
                        "                            </tr>" +
                        "                            <tr><td height='100' colspan='3'></td></tr>" +
                        "                        </tbody>" +
                        "                    </table>" +
                        "                </td>" +
                        "            </tr>" +
                        "            <!-- 푸터 -->" +
                        "            <tr>" +
                        "                <td>" +
                        "                    <table border='0' cellspacing='0' cellpadding='0' style='width:100%;background:#e9ecf1;'>" +
                        "                        <tbody>" +
                        "                            <td colspan='3' height='30'>&nbsp;</td>" +
                        "                            <tr>" +
                        "                                <td width='50'>&nbsp;</td>" +
                        "                                <td style='font-style:normal;font-size:14px;line-height:20px;letter-spacing:-0.05em;color:#707070;font-family:Malgun Gothic,맑은 고딕,dotum,sans-serif;'>" +
                        "                                    (주)세스코 (대표이사 부회장 전찬혁)&nbsp;&nbsp;|&nbsp;&nbsp;주소 : 서울시 강동구 상일로 10길 46 세스코 터치센터 (우 : 05288)<br />" +
                        "                                    사업자등록번호 : 212-81-05946&nbsp;&nbsp;|&nbsp;&nbsp;통신판매업신고번호 : 제 2008-서울강동-0240호<br />" +
                        "                                    고객센터 : 1588-1119&nbsp;(평일 08:30~18:00, 토요일 08:30~15:00, 일요일/공휴일 휴무)&nbsp;&nbsp;|&nbsp;&nbsp;Fax : 02-488-1720<br>" +
                        "                                    <span style='letter-spacing:-0.03em;'>ⓒ ALL RIGHTS RESERVED.</span>" +
                        "                                </td>" +
                        "                                <td width='50'>&nbsp;</td>" +
                        "                            </tr>" +
                        "                            <td colspan='3' height='30'>&nbsp;</td>" +
                        "                        </tbody>" +
                        "                    </table>" +
                        "                </td>" +
                        "            </tr>" +
                        "            <!-- //푸터 -->" +
                        "        </tbody>" +
                        "    </table>" +
                        "	</body>" +
                        "</html>"
                    ));
                    
                    // 메일을 보냅니다.
                    Transport.send(message);
                } else {
                    throw new CommDuplicateException("이메일 저장 실페하였습니다.",ErrorCode.INTER_SERVER_ERROR);
                }               
            } catch (Exception e) {
                throw new CommDuplicateException("이메일 저장 실페하였습니다.",ErrorCode.INTER_SERVER_ERROR);
            }



        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    // 메일의 본문 내용 설정
    private BodyPart getContents(String html) throws MessagingException {
        BodyPart mbp = new MimeBodyPart();

        // html 형식으로 설정
        mbp.setContent(html, "text/html; charset=utf-8");
        return mbp;
    }
    // String으로 된 메일 주소를 Address 클래스로 변환
    private Address getAddress(String address) throws AddressException {
        return new InternetAddress(address);
    }
    // String으로 된 복수의 메일 주소를 콤마(,)의 구분으로 Address array형태로 변환
    private Address[] getAddresses(String addresses) throws AddressException {
        String[] array = addresses.split(",");
        Address[] ret = new Address[array.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = getAddress(array[i]);
        }
        return ret;
    }

}

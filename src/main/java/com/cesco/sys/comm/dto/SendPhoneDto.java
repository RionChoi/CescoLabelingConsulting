package com.cesco.sys.comm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendPhoneDto {

  private String msg_type;          // MESSAGE 발송 타입 (sms 0 / mms 5 / 알림톡 6)
  private String cmid;              // 데이터 ID 값                             
  private String request_time;      // 요청시간                                 
  private String send_time;         // 보내는 날짜                               
  private String dest_phone;        // 받는사람 번호                              
  private String dest_name;         // 받는사람 이름                              
  private String send_id;           // 보내는 사람 사번                            
  private String dest_id;           // 받는 사람 사번                             
  private String send_phone;        // 보내는사람번호                              
  private String msg_body;          // 문자 내용                                
  private String subject;           // 문자 제목
  
}

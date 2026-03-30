package com.cesco.fs.consultMgt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsultSearchDTO {

    private String p_type;                      // 검색 타입 1
    private String sys_id;                      // 스템 코드 FS
    private String customer;                    // 고객사ID 10000001
    private String workplace;                   // 			
    private String food_type;                   // 식품유형 FS001
    private String product_name;                // 제품명
    private String manager;                     // 담당자 ID
    private String cesco_person_in_charge;      // 세스코 담당자	
    private String start_date;                  // 접수일 시작 '20221101'
    private String end_date;                    // 접수일 끝 '20221101'
    private String fs_status_cd;                // 상태 코드 FS004
    
}

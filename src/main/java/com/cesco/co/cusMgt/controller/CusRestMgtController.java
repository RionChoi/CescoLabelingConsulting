package com.cesco.co.cusMgt.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cesco.co.cusMgt.dto.CusBookDTO;
import com.cesco.co.cusMgt.dto.CusCertificationInfoDTO;
import com.cesco.co.cusMgt.dto.CusInfoDTO;
import com.cesco.co.cusMgt.dto.CusSearchDTO;
import com.cesco.co.cusMgt.service.CusMgtService;
import com.cesco.sys.common.ErrorCode;
import com.cesco.sys.communityhandlers.CommDuplicateException;

/**
 * @author parkminho
 * @apiNote cusMgt
 */

@RestController
@RequestMapping("/cus/api")
public class CusRestMgtController {

    @Autowired
    CusMgtService cusMgtService;

    // private static final Logger LOGGER = LogManager.getLogger(ApiController.class);

    // 고객사 목록 가져오기
    @GetMapping(value="/cusList")
    public List<CusInfoDTO> getCusList(
        CusSearchDTO cusSearch,
        HttpSession session) throws Exception {

        String userId = (String)session.getAttribute("userId");

        try{

            return cusMgtService.getCusList(cusSearch, userId);

        }catch(Exception e){

            throw new CommDuplicateException("목록 불러오기에 실패했습니다.",ErrorCode.INTER_SERVER_ERROR);

        }
        
    }

    // 즐겨찾기 등록[미사용]
    @PostMapping(value="/cusFavoritesIns")
    public ResponseEntity<String> setCusFavoritesIns(@RequestBody List<CusInfoDTO> cusInfoDTO, HttpSession session) throws Exception {
        
        // 로그인 ID
        String userId = (String)session.getAttribute("userId");

        List<CusBookDTO> bookList = new ArrayList<>();

        try{            

            for(CusInfoDTO c: cusInfoDTO){
                // 즐겨 찾기 등록이 안된것만 등록
                if(c.getBook_seq() == 0){                
                    bookList.add(new CusBookDTO("", 0, "01", userId, c.getCst_id(), "", "고객사즐겨찾기", "Y", userId, null, userId, null,""));
                }
            }

        }catch(Exception e){
            throw new CommDuplicateException("즐겨 찾기 추가 중 장애가 발생했습니다.",ErrorCode.INTER_SERVER_ERROR);
        }

        // 즐겨찾기 등록
        for(CusBookDTO b : bookList){
            cusMgtService.setCusBookIns(b);
        }

        return new ResponseEntity<>("즐겨찾기가 등록 되었습니다.",HttpStatus.OK);
        
    }

    // 즐겨찾기 해제(DELETE)[미사용]
    @DeleteMapping(value="/cusFavoritesDel")
    public ResponseEntity<String> setCusFavoritesDelDelete(@RequestParam("book_seq") List<Integer> bookSeqList, HttpSession session) throws Exception {
        // 로그인 ID
        String userId = (String)session.getAttribute("userId");
        
        for(Integer iBookSeq : bookSeqList){
            cusMgtService.setCusBookDelSeq(iBookSeq, userId);
        }
        
        return new ResponseEntity<>("즐겨찾기가 해제 되었습니다.", HttpStatus.OK);
    }
    
    // 고객사 등록
    @PostMapping(value="/cusInfoIns")
    public ResponseEntity<String> setCusInfoIns(@RequestBody CusInfoDTO cusInfoDTO, HttpSession session) throws Exception {    
        
        // 로그인 ID
        String userId = (String)session.getAttribute("userId"); 

        // 고객사 등록
        cusMgtService.setCusInfoIns(cusInfoDTO, userId);

        return new ResponseEntity<>("등록 되었습니다.", HttpStatus.OK);
    }

    // 고객사 수정
    @PutMapping(value="/cusInfoUpt")
    public ResponseEntity<String> setCusInfoUpt(@RequestBody CusInfoDTO cusInfoDTO, HttpSession session) throws Exception {
        
        // 로그인 ID
        String userId = (String)session.getAttribute("userId");
        
        cusMgtService.setCusInfoUpt(cusInfoDTO, userId);
        
        return new ResponseEntity<>("고객 정보가 수정 되었습니다.",HttpStatus.OK);
    }

    @PostMapping(value="/certificationInfo")
    public CusCertificationInfoDTO getCertificationInfo(@RequestBody Map<String, String> reqInfo) throws Exception{

        String cust_cd = reqInfo.get("cust_cd");

        if(cust_cd == null)
            throw new CommDuplicateException("고객사 코드가 없습니다.",ErrorCode.INTER_SERVER_ERROR);
        
        return cusMgtService.getCusCertificationInfo(cust_cd);
        
    }
    
}

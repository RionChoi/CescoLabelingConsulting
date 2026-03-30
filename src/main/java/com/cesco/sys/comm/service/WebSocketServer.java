package com.cesco.sys.comm.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cesco.fs.consulting.service.ConsultingService;
import org.apache.commons.lang3.StringUtils;


/**
 * @author navycui
 * @apiNote param userId 
 */

@Component
@ServerEndpoint("/chatServer/{sysId}/{fsNo}/{userId}")
public class WebSocketServer {
    
  private static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(WebSocketServer.class);

  /**현재 온라인 연결 수를 기록하는 데 사용되는 정적 변수입니다. 스레드로부터 안전하도록 설계되어야 합니다.*/
  private static int onlineCount = 0;
  
  /**스레드로부터 안전한 동시 패키지 세트는 각 클라이언트에 해당하는 MyWebSocket 개체를 저장하는 데 사용됩니다.*/
  private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
  /* 클라이언트에 해당하는 MyWebSocket 개체를 저장하는 그룹 **/
  private static ConcurrentHashMap<String,ConcurrentHashMap<String,WebSocketServer>> roomList = new ConcurrentHashMap<>();

  /**클라이언트에 데이터를 전송하는 데 사용해야 하는 클라이언트와의 연결 세션*/
  private Session session;
  /**system id :sysId*/
  private String sysId="";
  /**컨설팅번호 :fsNo*/
  private String fsNo="";
  /**사용자 ID 수신 :userId*/
  private String userId="";

  public  static ConsultingService constltingService;
  
  @Autowired
  public void setUserService (ConsultingService userService){
    WebSocketServer.constltingService = userService;
  }


  /**
   * 성공적으로 연결되었을 때 호출되는 메소드
   * @a
   * @param userId
   * @param sysId
   * @param fsNo
   * 
   **/
  @OnOpen
  public void onOpen(Session session,@PathParam("userId") String userId,@PathParam("fsNo") String fsNo,@PathParam("sysId") String sysId) throws Exception {
      this.session = session;
      this.sysId=sysId;
      this.fsNo=fsNo;
      this.userId=userId;
    
    // 방 이름에 따라 소켓 저장하고 각 방에서 사용자를 격리
    if (!roomList.containsKey(fsNo)) {
        // Set<Session> room = new HashSet<>();
        // 사용자 추가
        ConcurrentHashMap<String,WebSocketServer> room = new ConcurrentHashMap<>();
        room.put(userId, this);
        // room.add(session);
        // 방에 추가
        roomList.put(fsNo, room);
        
    } else {
        // 방이 이미 존재합니다. 해당 방에 사용자를 직접 추가하십시오.
        roomList.get(fsNo).put(userId, this);
        log.info("사용자roomList.get(fsNo):"+roomList.get(fsNo).get(userId));
        addOnlineCount();
    }

      log.info("사용자roomList.get(fsNo):"+roomList.get(fsNo).get(userId));
      log.info("사용자 연결:"+userId+",현재 온라인 사용자 수는:" + getOnlineCount());

      try {
          sendMessage(JSON.toJSONString("userId:"+ userId+ ",fsNo:"+ fsNo));
      } catch (IOException e) {
          log.error("사용자:"+userId+",네트워크 이상!!!!!!");
      }
    }
  
    /**
     * 연결 종료 시 호출되는 메소드
     */
    @OnClose
    public void onClose() {
        if(roomList.get(fsNo).containsKey(userId)){
            roomList.get(fsNo).remove(userId);
            // set에서 지우기
            subOnlineCount();
        }
        log.info("사용자로그아웃:"+userId+",현재 온라인 사용자 수는:" + getOnlineCount());
    }

  /**
   * 클라이언트 메시지 수신 시 호출되는 메소드
   *
   * @param message 클라이언트가 보낸 메시지*/
  @OnMessage
  public void onMessage(String message, Session session) {
      log.info("사용자 메세지:"+userId+",내용:"+message);
      //대량 메시지를 보낼 수 있습니다
      //메시지가 데이터베이스에 저장됩니다.
      if(StringUtils.isNotBlank(message)){
          try {
              //보낸 메시지 구문 분석
              JSONObject jsonObject = JSON.parseObject(message);
              //추가 발신자(교차 수정 방지)
              jsonObject.put("fromUserId",this.userId);
            
              JSONArray memberList=jsonObject.getJSONArray("memList");
              log.info("메세지 저장 진행 : " + jsonObject);
              // 전체 메신저 발송
              broadcast(message,memberList);

          }catch (Exception e){
              e.printStackTrace();
          }
      }
  }

  /**
   *
   * @param session
   * @param error
   */
  @OnError
  public void onError(Session session, Throwable error) {
      log.error("user error:"+this.userId+",원인:"+error.getMessage());
      error.printStackTrace();
  }
  /**
   * 활성서버 푸시 구현
   */
  public void sendMessage(String message) throws IOException {
      this.session.getBasicRemote().sendText(message);
  }


  /**
   * 맞춤 메시지 보내기
   * */
  public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
      log.info(userId + "에 메시지를 보낸다:"+"，내용 :"+message);
      // if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
      if(webSocketMap.containsKey(userId)){
          webSocketMap.get(userId).sendMessage(message);
      }else{
          log.error("사용자"+userId+",오프라인 상태 입니다.！");
      }
  }

    // 방 별 전송
    public void broadcast(String meg,JSONArray memberList) {
        // 방 이름에 따라 소켓 저장하고 각 방에서 사용자를 격리
        roomList.get(fsNo).forEach((key,value)->{
            if(roomList.get(fsNo).containsKey(key)){
                try {
                    roomList.get(fsNo).get(key).sendMessage(meg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            log.info("broadcast.get(fsNo):"+key+value);
            log.info("broadcast.get(fsNo):"+roomList.get(fsNo).keys());

        });
    }

  public static synchronized int getOnlineCount() {
      return onlineCount;
  }

  public static synchronized void addOnlineCount() {
      WebSocketServer.onlineCount++;
  }

  public static synchronized void subOnlineCount() {
      WebSocketServer.onlineCount--;
  }
}

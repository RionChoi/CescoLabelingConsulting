package com.cesco.sys.common;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404,"COMMON-ERR-404","PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","시스템에러 발생하였습니다."),
    UPLOAD_FAIL(500,"UPLOAD-FAIL-ERR-500","파일저장 실페했습니다."),
    DOWNLOAD_FAIL(500,"UPLOAD-FAIL-ERR-500","파일 다운로드 실페했습니다."),
    FILE_SIZE_FAIL(500,"UPLOAD-FAIL-ERR-500","파일 크기 초과 하였습니다."),
    SAVE_FILEINFO_DUPLICATION(500,"SAVE-FILEINFO-ERR-500","파일정보 저장 실페했습니다."),
    LOGINLOG_DUPLICATION(500,"LOGIN-LOG-ERR-500","로그인 이력조회 실페하였습니다."),
    LOGIN_INFO_NO_FOUND(403,"LOGIN-LOG-ERR-500","사용자 정보 죄회 되지 않아습니다."),
    PASSWORDS_DO_NOT_MATCH(500,"PASSWORD-NOT-MATCH-ERR-500","비밀번호 2개가 일치하지 않습니다."),
    PASSWORDS_CHANGE_FAIL(500,"PASSWORD-NOT-MATCH-ERR-500","비밀번호 수정 실페하였습니다."),
    CHANGE_PASSWORD_DUPLICATION(500,"CHANGE_PASSWORD-ERR-500","CHANGE_PASSWORD_DUPLICATION"),
    USER_ID_DUPLICATION(500,"USERID-ERR-500","로그인 정보 없습니다.로그인 후 호출하세요"),
    FSNO_DUPLICATION(500,"FSNO-ERR-500","FSNO_DUPLICATION"),
    SAVE_NEW_CONSULTING_DUPLICATION(500,"SAVE_NEW_CONSULTING-ERR-500","컨설팅 신규의뢰 등록 실페했습니다."),
    REQUIRED_VALUE_ERROR(500, "REQUIRED-VALUE-ERR", "필수 값 에러입니다."),
    SSH_FX_OK(500,"SSH-FX-200","작업이 성공적으로 완료되었습니다."),
    SSH_FX_EOF(500,"SSH-FX-500","파일 끝 상태를 나타냅니다. SSH_FX_READ의 경우파일에서 더 많은 데이터를 사용할 수 있으며 SSH_FX_READDIR의 경우디렉토리에 더 이상 파일이 포함되어 있지 않음을 나타냅니다."),
    SSH_FX_NO_SUCH_FILE(500,"SSH-FX-500","파일 찾을수 없습니다."),
    SSH_FX_PERMISSION_DENIED(500,"SSH-FX-500","인증된 사용자가 충분하지 않은 경우 반환됩니다.작업을 수행할 수 있는 권한."),
    SSH_FX_FAILURE(500,"SSH-FX-500","파일반적인 포괄 오류 메시지입니다. 다음과 같은 경우 반환해야 합니다.더 이상 구체적인 오류 코드가 없는 오류가 발생합니다.한정된."),
    SSH_FX_BAD_MESSAGE(500,"SSH-FX-500","형식이 잘못된 패킷이나 프로토콜이비호환성이 감지되었습니다."),
    SSH_FX_NO_CONNECTION(500,"SSH-FX-500","클라이언트에 오류가 없음을 나타내는 의사 오류입니다.서버에 대한 연결(로컬에서만 생성할 수 있습니다.클라이언트이며 서버에서 반환하면 안 됩니다(MUST NOT)."),
    SSH_FX_CONNECTION_LOST(500,"SSH-FX-500","에 대한 연결을 나타내는 의사 오류입니다.서버가 손실되었습니다(서버에서 로컬로만 생성할 수 있음).클라이언트이며 서버에서 반환하면 안 됩니다(MUST NOT)."),
    SSH_FX_OP_UNSUPPORTED(500,"SSH-FX-500","작업을 수행하려고 시도했음을 나타냅니다.서버에 대해 지원되지 않음(로컬에서 생성될 수 있음)예를 들어 클라이언트 버전 번호 교환은필요한 기능이 서버에서 지원되지 않거나서버가 구현하지 않은 경우 서버에서 반환작업).");

    private int status;
    private String errorCode;
    private String message;


    public int getStatus() {
        return status;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

}
package sw_workbook.spring.apiPayload.code.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sw_workbook.spring.apiPayload.code.BaseErrorCode;
import sw_workbook.spring.apiPayload.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}

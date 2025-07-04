package sw_workbook.spring.apiPayload.code.exception.handler;

import sw_workbook.spring.apiPayload.code.BaseErrorCode;
import sw_workbook.spring.apiPayload.code.exception.GeneralException;

public class TempHandler extends GeneralException{

    public TempHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}

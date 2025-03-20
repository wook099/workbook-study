package sw_workbook.spring.apiPayload.code.exception.handler;


import sw_workbook.spring.apiPayload.code.BaseErrorCode;
import sw_workbook.spring.apiPayload.code.exception.GeneralException;

public class FoodCategoryHandler extends GeneralException {
    public FoodCategoryHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
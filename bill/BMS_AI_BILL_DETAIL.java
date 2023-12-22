package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/14 17:09
 * @Description 财务票据明细
 */
//@Component
public class BMS_AI_BILL_DETAIL{


    public static void execute() {
        TableDefine define=TableDefine.newBillDetailTable("BMS_AI_BILL_DETAIL","财务票据明细");
        define.addNvarcharBaseDataField("FINBILL_TYPE","票据类型","MD_BMS_FINBILL_TYPE");
        define.addNvarcharField("FINBILL_NO","票据号",100,null);
        define.addDateField("INVOICE_DATE","开票日期");
        define.addNumericField2("INVOICE_AMOUNT","金额",14,2,null);
        define.addNvarcharField("MEMO","备注",200,null);
        CreateDataModelUtil.pushBillMasterTable(define);
    }

}

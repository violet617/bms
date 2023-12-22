package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/14 17:09
 * @Description 租赁收款单子表--收款明细
 */
//@Component
public class BMS_AI_RENT_RECEIVEBILL_M2{
    public static void execute() {
        TableDefine define=TableDefine.newBillDetailTable("BMS_AI_RENT_RECEIVEBILL_M2","租赁收款明细");
        define.addNvarcharBaseDataField("FEE_TYPE","收款项目","MD_BMS_FEE_TYPE");
        define.addNvarcharBaseDataField("ACCOUNT_SUBJECT","会计科目","MD_BMS_ACCOUNT_SUBJECT");
        define.addNvarcharBaseDataField("COST_TYPE","费用类型","MD_BMS_COST_TYPE");
        define.addIntegerField("ACC_PERIOD","账单时期",null);
        define.addIntegerField("PERIOD","费用时期",null);
        define.addDateField("SDATE","开始日期");
        define.addDateField("EDATE","结束日期");
        define.addNumericField2("RECEIVABLE","应收",14,2,"0");
        define.addNumericField2("ACT_AMOUNT","实收",14,2,"0");
        define.addNumericField2("TAX","税额",14,2,"0");
        define.addNvarcharBaseDataField("RATE_VALUE","税率","MD_BMS_RATE_VALUE");
        CreateDataModelUtil.pushBillMasterTable(define);
    }


}

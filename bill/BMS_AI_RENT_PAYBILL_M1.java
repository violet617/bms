package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/19 11:26
 * @Description 租赁退款单子表--退款明细
 */
//@Component
public class BMS_AI_RENT_PAYBILL_M1{

    public static void execute() {
        TableDefine tableDefine=TableDefine.newBillDetailTable("BMS_AI_RENT_PAYBILL_M1","租赁退款明细");
        tableDefine.addNvarcharBaseDataField("FEE_TYPE","收款项目","MD_BMS_FEE_TYPE");
        tableDefine.addNvarcharBaseDataField("RATE_VALUE","税率","MD_BMS_RATE_VALUE");
        tableDefine.addNumericField2("CAN_PAY_AMOUNT","可退金额",14,2,null);
        tableDefine.addNumericField2("PAY_MOUNT","实退金额",14,2,null);
        tableDefine.addNumericField2("TAX","税额",14,2,null);
        tableDefine.addIntegerField("PERIOD","费用时期",null);
        tableDefine.addIntegerField("ACC_PERIOD","账单时期",null);
        tableDefine.addDateField("SDATE","开始日期");
        tableDefine.addDateField("EDATE","结束日期");
        tableDefine.addUUIDField("ADDED_FEE_ID","费用ID");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }

}


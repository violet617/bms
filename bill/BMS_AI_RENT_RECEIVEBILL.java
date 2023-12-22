package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/14 16:55
 * @Description 租赁收款单主表
 */
//@Component
public class BMS_AI_RENT_RECEIVEBILL{

    public static void execute() {
        TableDefine define=TableDefine.newBillMasterTable("BMS_AI_RENT_RECEIVEBILL","租赁收款单主表");
        define.addIntegerField("WORKFLOWSTATE","工作流状态",null);
        define.addNvarcharBaseDataField("MALL","项目","MD_BMS_STXM");
        define.addNvarcharBaseDataField("RENT_CONTRACT","合同","MD_BMS_HT");
        define.addNvarcharBaseDataField("SKDW","收款单位","MD_BMS_CUSTOMER");
        define.addNvarcharBaseDataField("KHDW","客户单位","MD_BMS_CUSTOMER");
        define.addNvarcharBaseDataField("RENT_STORE","租赁资产","MD_BMS_STORE");
        define.addNvarcharBaseDataField("ACT_CUSTOMER","实际缴款单位","MD_BMS_CUSTOMER");
        define.addNvarcharBaseDataField("ACCOUNT_YEAR","会计年","EM_BMS_YEAR");
        define.addNvarcharBaseDataField("ACCOUNT_MONTH","会计月","EM_BMS_MONTH");
        define.addDateField("PAYMENT_DATE","缴费/结转日期");
        define.addNumericField2("AMOUNT","缴费金额",14,2,"0");
        define.addNvarcharBaseDataField("OUT_CONTRACT","财务入档号（转出）","MD_BMS_HT");
        CreateDataModelUtil.pushBillMasterTable(define);
    }


}

package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/19 11:26
 * @Description 租赁退款单主表
 */
//@Component
public class BMS_AI_RENT_PAYBILL{

   public static void execute() {
        TableDefine tableDefine=TableDefine.newBillMasterTable("BMS_AI_RENT_PAYBILL","租赁退款单主表");
        tableDefine.addIntegerField("WORKFLOWSTATE","工作流状态",null);
        tableDefine.addNvarcharBaseDataField("MALL","项目","MD_BMS_STXM");
        tableDefine.addNvarcharBaseDataField("RENT_CONTRACT","租赁合同","MD_BMS_HT");
        tableDefine.addNvarcharBaseDataField("KHDW","客户单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("RENT_STORE","租赁资产","MD_BMS_STORE");
        tableDefine.addNvarcharBaseDataField("SKDW","实际收款单位","MD_BMS_CUSTOMER");
        tableDefine.addDateField("PAY_DATE","付款日期");
        tableDefine.addNumericField2("AMOUNT","付款金额",14,2,null);
        tableDefine.addNvarcharBaseDataField("ACCOUNT_YEAR","会计年","EM_BMS_YEAR");
        tableDefine.addNvarcharBaseDataField("ACCOUNT_MONTH","会计月","EM_BMS_MONTH");
        tableDefine.addNvarcharBaseDataField("SETTLE_TYPE","结算方式","MD_BMS_RENT_SET_TYPE");
        tableDefine.addNvarcharBaseDataField("BANK_ACCOUNT","银行账户","MD_BMS_BANK_ACCOUNT");
        tableDefine.addTextField("MEMO","备注");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }



}

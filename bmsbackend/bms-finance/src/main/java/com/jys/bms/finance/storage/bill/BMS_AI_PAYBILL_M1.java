package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/20 15:01
 * @Description 结算付款单--结算明细
 */

//@Component
public class BMS_AI_PAYBILL_M1{

   public static void execute() {
        TableDefine tableDefine = TableDefine.newBillDetailTable("BMS_AI_PAYBILL_M1", "结算明细");
        tableDefine.addNvarcharField("ZHAIY","摘要",200,null);
        tableDefine.addNumericField2("AMOUNT","金额",14,2,null);
        tableDefine.addNumericField2("TAX","税额",14,2,null);
        tableDefine.addNumericField2("AMOUNT_UNTAX","除税金额",14,2,null);
        tableDefine.addBooleanField("IF_DEDUCT","是否抵扣",null);
        tableDefine.addUUIDField("COST_KEY","录用分录唯一标识");
        tableDefine.addNvarcharBaseDataField("COST_TYPE","费用类型","MD_BMS_COST_TYPE");
        tableDefine.addNvarcharBaseDataField("BUDGET_SUBJECT","预算科目","MD_BMS_BUDGET_SUBJECT");
        tableDefine.addNvarcharBaseDataField("ACCOUNT_SUBJECT","会计科目","MD_BMS_ACCOUNT_SUBJECT");
        tableDefine.addNvarcharBaseDataField("KHDW","客户单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("MALL","项目","MD_BMS_CONTRACT_PROJECT");
        tableDefine.addNvarcharBaseDataField("TENANT","商户","MD_BMS_RENTER");
        tableDefine.addNvarcharBaseDataField("HOME_ORG","承担单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("CUSTOM_PROJECT","自定义项目","MD_BMS_CUSTOM_PROJECT");
        tableDefine.addNvarcharBaseDataField("COST_CENTER","成本中心","MD_BMS_COST_CENTER");
        tableDefine.addNvarcharBaseDataField("PAYBILL_TYPE","付款单据类型","MD_BMS_PAYBILL_TYPE");
        tableDefine.addNvarcharBaseDataField("RATE","税率","MD_BMS_RATE_VALUE");
        tableDefine.addNvarcharBaseDataField("TENANT_TYPE","商户类型","MD_BMS_MERCHANT_TYPE");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

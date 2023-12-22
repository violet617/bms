package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/20 14:19
 * @Description 付款单
 */
//@Component
public class BMS_AI_PAYBILL{


    public static void execute() {
        TableDefine tableDefine=TableDefine.newBillMasterTable("BMS_AI_PAYBILL","付款单");
        tableDefine.addDateField("ACT_PAY_DATE","实际付款日期");
        tableDefine.addNvarcharField("APPLY_BILLCODE","申请单编号",100,null);
        tableDefine.addUUIDField("APPLY_BILLDEFINE","申请单定义");
        tableDefine.addUUIDField("APPLY_BILLID","申请单ID");
        tableDefine.addNvarcharField("INVOICE_NO","发票号",200,null);
        tableDefine.addNumericField2("INVOICE_AMOUNT","发票金额",14,2,null);
        tableDefine.addNvarcharField("COLLECTION_BANK","收款银行",50,null);
        tableDefine.addNvarcharField("COLLECTION_AMOUNT_NUM","收款账号",30,null);
        tableDefine.addNumericField2("BORROWING_AMOUNT","冲减借款金额",14,2,null);
        tableDefine.addNumericField2("APPLY_AMOUNT","申请金额",14,2,null);
        tableDefine.addNumericField2("ACTUAL_PAY_AMOUNT","实付金额",14,2,null);
        tableDefine.addNvarcharField("ZHAIY","摘要",200,null);
        tableDefine.addNvarcharField("STDCODE","编号",60,null);
        tableDefine.addNvarcharField("STDNAME","名称",200,null);
        tableDefine.addBooleanField("NO_VORCHER","不生成凭证",null);
        tableDefine.addNumericField2("NEED_INVOICE_AMOUNT","需要发票金额",14,2,null);
        tableDefine.addNumericField2("FPU_INVOICE_AMOUNT","已开发票金额",14,2,null);
        tableDefine.addNvarcharField("INVIOICE_MEMO","发票说明",200,null);
        tableDefine.addNvarcharField("SKDWS","收款单位文本",200,null);

        tableDefine.addNvarcharBaseDataField("FIN_SUBJECT","财务科目","MD_BMS_ACCOUNT_SUBJECT");
        tableDefine.addNvarcharBaseDataField("SETTLE_TYPE","结算方式","MD_BMS_SETTLE_TYPE");
        tableDefine.addNvarcharBaseDataField("SKDW","收款单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("BANK_ACCOUNT","银行账号","MD_BMS_BANK_ACCOUNT");
        tableDefine.addNvarcharBaseDataField("APPLYBILL_TYPE","申请单类型","MD_BMS_RP_APPLY_TYPE");
        tableDefine.addNvarcharBaseDataField("ACCOUNT_YEAR","会计年","EM_BMS_YEAR");
        tableDefine.addNvarcharBaseDataField("ACCOUNT_MONTH","会计月","EM_BMS_MONTH");
        tableDefine.addNvarcharBaseDataField("FKDW","付款单位","MD_BMS_CUSTOMER");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

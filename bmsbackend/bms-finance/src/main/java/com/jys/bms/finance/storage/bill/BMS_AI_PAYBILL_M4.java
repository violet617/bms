package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/20 16:45
 * @Description 结算付款--付款单（支付明细）
 */
//@Component
public class BMS_AI_PAYBILL_M4{

    public static void execute() {
        TableDefine tableDefine=TableDefine.newBillDetailTable("BMS_AI_PAYBILL_M4","付款单（支付明细）");
        tableDefine.addNumericField("ACT_AMOUNT","支付金额",null);
        tableDefine.addDateField("ACT_PAYDATE","实际支付日期");
        tableDefine.addNvarcharField("TRANS_NUM","银行交易序号",50,null);
        tableDefine.addNvarcharField("ZHAIY","摘要",200,null);
        tableDefine.addNvarcharField("COLLECTION_BANK","收款银行",100,null);
        tableDefine.addNvarcharField("RCVACCNO","收款账号",100,null);
        tableDefine.addUUIDField("COST_KEY","支付单ID");
        tableDefine.addTextField("REMARK","结算详情");
        tableDefine.addTextField("MEMO","备注");
        tableDefine.addNumericField("TRANSFER_AMOUNT","可转金额",null);
        tableDefine.addNvarcharField("LOAN_CODE","借款单编号",100,null);
        tableDefine.addUUIDField("LOAN_ID","借款单ID");
        tableDefine.addUUIDField("LOAN_DEFINE","借款单定义");

        tableDefine.addNvarcharBaseDataField("SETTLE_TYPE","结算方式","MD_BMS_SETTLE_TYPE");
        tableDefine.addNvarcharBaseDataField("BANK","支付银行","MD_BANK");
        tableDefine.addNvarcharBaseDataField("BANK_ACCOUNT","银行账户","MD_BMS_BANK_ACCOUNT");
        tableDefine.addNvarcharBaseDataField("ACCOUNT_SUBJECT","财务科目","MD_BMS_ACCOUNT_SUBJECT");
        tableDefine.addNvarcharBaseDataField("SKDW","收款单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("PROJECT","核算项目","MD_BMS_CONTRACT_PROJECT");
        tableDefine.addNvarcharBaseDataField("PAY_SCHEDULE","付款进度","MD_BMS_PAY_SCHEDULE");
        tableDefine.addNvarcharBaseDataField("LOAN_STAFF","借款人","MD_STAFF");
        tableDefine.addNvarcharBaseDataField("XJLLL","现金流量类","MD_BMS_XJLLL");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

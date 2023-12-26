package com.jys.bms.finance.storage.bill;

import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;

/**
 * @Author wangyunfan
 * @Date 2023/12/22 10:14
 * @Description 分录明细
 */
public class BMS_AI_VORCHER_M2 {
    public static void execute(){
        TableDefine tableDefine=TableDefine.newBillMasterTable("BMS_AI_VORCHER_M2","分录明细");
        tableDefine.addTextField("ZHAIY","摘要");
        tableDefine.addNumericField("AMOUNT","金额",null);
        tableDefine.addNvarcharField("TRANS_NUM","银行交易序列号",50,null);
        tableDefine.addUUIDField("ENTRYID","分录ID");
        tableDefine.addUUIDField("YSPZID_HZ","原始凭证ID-汇总");
        tableDefine.addNvarcharField("票据号","PJH",100,null);

        tableDefine.addNvarcharBaseDataField("ACCOUNT_SUBJECT","会计科目","MD_BMS_ACCOUNT_SUBJECT");
        tableDefine.addNvarcharBaseDataField("DEPT","部门","MD_DEPARTMENT");
        tableDefine.addNvarcharBaseDataField("STAFF","职员","MD_STAFF");
        tableDefine.addNvarcharBaseDataField("CUSTOMER","客户单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("PROJECT","项目","MD_BMS_HT_PROJECT");
        tableDefine.addNvarcharBaseDataField("TENANT","商户","MD_BMS_RENTER");
        tableDefine.addNvarcharBaseDataField("HOME_ORG","承担单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("CUSTOMER_PROJECT","自定义项目","MD_BMS_CUSTOM_PROJECT");
        tableDefine.addNvarcharBaseDataField("COST_CENTER","成本中心","MD_BMS_COST_CENTER");
        tableDefine.addNvarcharBaseDataField("BANK_ACCOUNT","银行账户","MD_BMS_BANK_ACCOUNT");
        tableDefine.addNvarcharBaseDataField("SETTLE_TYPE","结算方式","MD_BMS_SETTLE_TYPE");
        tableDefine.addNvarcharBaseDataField("XJLL","现金流量","MD_BMS_XJLLL");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

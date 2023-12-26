package com.jys.bms.finance.storage.bill;

import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;

/**
 * @Author wangyunfan
 * @Date 2023/12/22 10:14
 * @Description 收款月结明细
 */
public class BMS_AI_MS_SK_M1 {
    public static void execute(){
        TableDefine tableDefine=TableDefine.newBillMasterTable("BMS_AI_MS_SK_M1","收款月结明细");
        tableDefine.addNumericField2("TAX_AMOUNT","税额",14,2,null);
        tableDefine.addNumericField2("PRICE_AMOUNT","除税金额",14,2,null);
        tableDefine.addNumericField2("TAX_PRICE_AMOUNT","金额",14,2,null);
        tableDefine.addIntegerField("FP","财务时期",null);
        tableDefine.addIntegerField("YP","费用时期",null);
        tableDefine.addIntegerField("ZP","账单时期",null);
        tableDefine.addIntegerField("PERIOD","应收确认时期",null);
        tableDefine.addBooleanField("YJ","押金",null);
        tableDefine.addDateField("SDATE","开始日期");
        tableDefine.addDateField("EDATE","结束日期");
        tableDefine.addDateField("CDATE","生成日期");


        tableDefine.addNvarcharBaseDataField("SKDW","收款单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("KHDW","客户单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("RATE_VALUE","税率","MD_BMS_RATE_VALUE");
        tableDefine.addNvarcharBaseDataField("SSDW","所属单位","MD_ORG");
        tableDefine.addNvarcharBaseDataField("FEE_TYPE","收款项目","MD_BMS_FEE_TYPE");
        tableDefine.addNvarcharBaseDataField("RENTER","商户","MD_BMS_RENTER");
        tableDefine.addNvarcharBaseDataField("EXE_HT","执行合同","MD_BMS_HT");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }
}

package com.jys.bms.finance.storage.bill;

import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;

/**
 * @Author wangyunfan
 * @Date 2023/12/22 10:14
 * @Description 应收月结
 */
public class BMS_AI_MS_YS {
    public static void execute(){
        TableDefine tableDefine=TableDefine.newBillMasterTable("BMS_AI_MS_YS","应收月结");
        tableDefine.addNumericField2("TAX_AMOUNT","税额",14,2,null);
        tableDefine.addNumericField2("PRICE_AMOUNT","除税金额",14,2,null);
        tableDefine.addNumericField2("TAX_PRICE_AMOUNT","金额",14,2,null);
        tableDefine.addBooleanField("IS_NVALID","是否无效",null);
        tableDefine.addNvarcharBaseDataField("STAFF","职员","MD_STAFF");
        tableDefine.addNvarcharBaseDataField("DEPT","部门","MD_DEPARTMENT");
        tableDefine.addNvarcharBaseDataField("EM_YEAR","年度","EM_BMS_YEAR");
        tableDefine.addNvarcharBaseDataField("EM_MONTH","月份","EM_BMS_MONTH");
        tableDefine.addNvarcharBaseDataField("SKDW","收款单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("VORCHER_SHCEDULE","凭证生成进度","MD_BMS_VOR_SHCEDULE");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

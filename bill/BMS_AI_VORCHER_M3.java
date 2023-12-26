package com.jys.bms.finance.storage.bill;

import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;

/**
 * @Author wangyunfan
 * @Date 2023/12/22 10:14
 * @Description 原始凭证
 */
public class BMS_AI_VORCHER_M3 {
    public static void execute(){
        TableDefine tableDefine=TableDefine.newBillMasterTable("BMS_AI_VORCHER_M3","原始凭证");
        tableDefine.addNvarcharField("SRC_BILLCODE","源单据代码",100,null);
        tableDefine.addNvarcharField("SRC_BILLNAME","源单据名称",100,null);
        tableDefine.addNvarcharField("SRC_VORCHERCODE","源凭证代码",100,null);
        tableDefine.addNvarcharField("APPLYBILL_CODE","申请单编号",60,null);
        tableDefine.addUUIDField("SRC_DEFINE","源单据定义");
        tableDefine.addUUIDField("SRC_BILLID","源单据ID");
        tableDefine.addUUIDField("SRC_VORCHERID","源凭证ID");
        tableDefine.addNvarcharBaseDataField("APPLYBILL_TYPE","申请单类型","MD_BMS_RP_APPLY_TYPE");

        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

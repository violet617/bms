package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/20 14:49
 * @Description 结算付款（收款人）
 */
//@Component
public class BMS_AI_PAYBILL_M2{

    public static void execute() {
        TableDefine tableDefine=TableDefine.newBillDetailTable("BMS_AI_PAYBILL_M2","结算付款（收款人）");
        tableDefine.addNvarcharBaseDataField("SKFS","收款方式","MD_BMS_SETTEL_TYPE");
        tableDefine.addNvarcharBaseDataField("SKDW","收款单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharField("SKYH","收款银行",50,null);
        tableDefine.addNvarcharField("YHZH","银行账号",50,null);
        tableDefine.addNumericField2("AMOUNT","收款金额",14,2,null);
        tableDefine.addIntegerField("WORKFLOWSTATE","工作流状态",null);
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

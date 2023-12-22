package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/14 17:09
 * @Description 租赁收款单子表--项目汇总
 */
//@Component
public class BMS_AI_RENT_RECEIVEBILL_M4 {
    public static void execute() {
        TableDefine define=TableDefine.newBillDetailTable("BMS_AI_RENT_RECEIVEBILL_M4","租赁收款明细_项目汇总");
        define.addNvarcharBaseDataField("FEE_TYPE","收款项目","MD_BMS_FEE_TYPE");
        define.addNumericField2("ACT_AMOUNT","实收",14,2,null);
        define.addNumericField2("RATE_VALUE","税率",14,2,null);
        define.addNumericField2("TAX","税额",14,2,null);
        define.addNumericField2("NO_TAX_AMOUNT","除税金额",14,2,null);
        CreateDataModelUtil.pushBillMasterTable(define);
    }


}

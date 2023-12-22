package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/14 17:09
 * @Description 租赁收款单子表--暂收明细
 */
//@Component
public class BMS_AI_RENT_RECEIVEBILL_M3 {
    public static void execute() {
        TableDefine define=TableDefine.newBillDetailTable("BMS_AI_RENT_RECEIVEBILL_M3","租赁暂收明细");
        define.addNvarcharBaseDataField("FEE_TYPE","收款项目","MD_BMS_FEE_TYPE");
        define.addNumericField2("AMOUNT","金额",14,2,"0");
        CreateDataModelUtil.pushBillMasterTable(define);
    }


}

package com.jys.bms.finance.storage.bill;

import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

/**
 * @Author wangyunfan
 * @Date 2023/12/14 17:09
 * @Description 租赁收款单子表--缴费明细
 */
//@Component
public class BMS_AI_RENT_RECEIVEBILL_M1{
    public static void execute() {
        TableDefine define=TableDefine.newBillDetailTable("BMS_AI_RENT_RECEIVEBILL_M1","租赁缴费明细");
        define.addNvarcharBaseDataField("SETTLE_TYPE","缴费方式","MD_BMS_RENT_SET_TYPE");
        define.addNvarcharBaseDataField("FEE_TYPE","押金类型","MD_BMS_FEE_TYPE");
        define.addNvarcharBaseDataField("BANK_ACCOUNT","银行账户","MD_BMS_BANK_BANK_ACCOUNT");
        define.addTextField("REMARK","缴费详情");
        define.addNvarcharField("ZHAIY","摘要",200,"0");
        define.addNumericField2("AMOUNT","金额",14,2,"0");
        CreateDataModelUtil.pushBillMasterTable(define);
    }


}

package com.jys.bms.finance.storage.bill;

import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;

/**
 * @Author wangyunfan
 * @Date 2023/12/22 10:14
 * @Description 凭证
 */
public class BMS_AI_VORCHER {
    public static void execute(){
        TableDefine tableDefine=TableDefine.newBillMasterTable("BMS_AI_VORCHER","凭证");
        tableDefine.addBooleanField("CREATE_FIN_DOC", "是否生成财务凭证", null);
        tableDefine.addDateTimeField("CREATE_DOC_TIME", "生成时间");
        tableDefine.addDateField("BUINESS_DATE",  "业务日期");
        tableDefine.addNvarcharField("APPLYBILL_DATE","申请单编号",60,null);
        tableDefine.addNvarcharField("VOR_CODE","凭证编号",8,null);
        tableDefine.addNvarcharField("ZHDCR","最后导出人",50,null);
        tableDefine.addIntegerField("DCCS","导出次数",null);
        tableDefine.addDateTimeField("ZHDDCSJ", "最后导出时间");
        tableDefine.addTextField("ZHAIY","摘要");
        tableDefine.addTextField("CREATE_MEMO","生成日志");
        tableDefine.addBooleanField("IS_HZ","汇总凭证",null);
        tableDefine.addNvarcharField("HZ_VOR_CODE","汇总凭证编号",60,null);
        tableDefine.addUUIDField("HZ_COR_ID","汇总凭证ID");

        tableDefine.addNvarcharBaseDataField("VOR_TYPE","凭证类型","MD_BMS_VOR_TYPE");
        tableDefine.addNvarcharBaseDataField("ACCOUNT_YEAR","会计年","EM_BMS_YEAR");
        tableDefine.addNvarcharBaseDataField("ACCOUNT_MONTH","会计月","EM_BMS_MONTH");
        tableDefine.addNvarcharBaseDataField("APPLYBILL_TYPE","申请单类型","MD_BMS_RP_APPLY_TYPE");
        tableDefine.addNvarcharBaseDataField("SFYDC","是否已导出","MD_BMS_BOOLEAN");
        tableDefine.addNvarcharBaseDataField("VOR_VYVLE","凭证周期","MD_BMS_VOR_CYCLE");
        tableDefine.addNvarcharBaseDataField("VOR_DW","凭证单位","MD_BMS_CUSTOMER");
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }


}

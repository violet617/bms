package com.jys.bms.finance.storage.secondHandle;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.jiuqi.va.domain.basedata.OrgMngType.ISOLATED;
import static com.jiuqi.va.domain.basedata.OrgMngType.SHARE;

/**
 * @Author wangyunfan
 * @Date 2023/12/15 17:32
 * @Description 基础数据类型--商户
 */
public class TB_MD_BMS_RENTER {
    private static final String Base_Data_NAME = "MD_BMS_RENTER";

    private static final String Base_Data_TITLE = "商户";

    private static final String GROUP_NAME = "system";
    public static void init(String tenantName) {
        //获取 基础数据定义声明客户端 装配的bean
        BaseDataDefineClient client = ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        //获取基础数据定义交互
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
        //租户标识
        baseDataDefineDTO.setTenantName(tenantName);
        //设置基础数据标识
        baseDataDefineDTO.setName(Base_Data_NAME);
        //获取当前设置的基础数据标识 是否存在已经定义好的基础数据表
        PageVO<BaseDataDefineDO> result = client.list(baseDataDefineDTO);
        /**
         * 设置基础数据结构类型  有四种
         *   0 列表，
         *   1 分组列表，
         *   2 树形，
         *   3 级次树
         */
        baseDataDefineDTO.setStructtype(0);
        //设置基础数据名称
        baseDataDefineDTO.setTitle(Base_Data_TITLE);
        /**
         * 设置基础数据所属分组  分组有三种
         * 1. 公用分组 DEFINE_GROUP_PUBLIC ：public
         * 2. 系统分组 DEFINE_GROUP_SYSTEM ：system
         * 3. 其他分组 DEFINE_GROUP_OTHER ： other
         */
        baseDataDefineDTO.setGroupname(GROUP_NAME);
        /**
         * 设置共享隔离类型 四种
         *  OrgMngType.SHARE.getCode()      共享
         *  OrgMngType.ISOLATED.getCode()   隔离
         *  OrgMngType.DISPATCH.getCode()    隔离+向下共享
         *  OrgMngType.SharedAndIsolated.getCode()   共享+隔离
         */
        baseDataDefineDTO.setSharetype(ISOLATED.getCode());
        baseDataDefineDTO.setSharefieldname("UNITCODE"); //隔离维度 组织机构？
        // 加上这两个才会使隔离生效
        // 维度标记，1维度 0非维度， 维度表才可以启用多版本
        baseDataDefineDTO.setDimensionflag(1);
        // 版本启用标记 1 启用 0不启用
        baseDataDefineDTO.setVersionflag(1);
        /**
         * 固化标识
         *   固化：1   固化了也不能初始化新增数据
         *  非固化：0
         */
        baseDataDefineDTO.setSolidifyflag(0);
        /**
         * 只读标志
         *  只读：1   只读后不能初始化新增数据
         *  可用：0
         */
        baseDataDefineDTO.setReadonly(0);
        /**
         *  版本启用标志
         *   启用：1
         *   不启用：0  不启用也不能初始化新增数据
         */
        baseDataDefineDTO.setVersionflag(1);
        /**
         * 虚拟基础数据标记
         *   虚拟: 1
         *   正常: 0
         */
        baseDataDefineDTO.setDummyflag(0);

        if (result.getTotal() == 0) {
            baseDataDefineDTO.setId(UUID.randomUUID());
            client.add(baseDataDefineDTO);
        }else {
            baseDataDefineDTO.setModifytime(new Date());
            client.upate(baseDataDefineDTO);
            System.out.println("修改基础数据");
        }

        initBaseDataColumn();
        //增加测试数据
//        BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
//        baseDataBatchOptDTO.setTenantName(tenantName);
//        List<BaseDataDO> dataList = new ArrayList<BaseDataDO>();
//        dataList.add(initBaseData(tenantName,"01","收款项目01"));
//        dataList.add(initBaseData(tenantName,"02","收款项目02"));
//        dataList.add(initBaseData(tenantName,"03","收款项目03"));
//        baseDataBatchOptDTO.setDataList(dataList);
//
//        BaseDataClient dataClient = ApplicationContextRegister.getBean(BaseDataClient.class);
//        dataClient.batchAdd(baseDataBatchOptDTO);
    }

    /**
     * @description 初始化基础数据定义字段
     * @param
     * @return null
     * @Created by violet at 2023/12/15 14:16
     */
    private static void initBaseDataColumn(){
        TableDefine tableDefine=TableDefine.newBaseDataTable(Base_Data_NAME,Base_Data_TITLE);
        tableDefine.addNvarcharField("STDCODE","代码",100,null);
        tableDefine.addNvarcharField("STDNAME","名称",100,null);
        tableDefine.addNvarcharBaseDataField("BRAND","品牌","MD_BMS_BRAND");
        tableDefine.addNvarcharBaseDataField("RENT_STORE","租赁资产","MD_BMS_STORE");
        tableDefine.addNvarcharField("BRANDNAME","品牌名称",100,null);
        tableDefine.addNvarcharBaseDataField("REGION","经营区域","MD_BMS_REGION");
        tableDefine.addNvarcharBaseDataField("CUSTOMERUNIT","客户单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("TOB","业态（经营业态）","MD_BMS_TOB");
        tableDefine.addNvarcharBaseDataField("CONTRACT","合同","MD_BMS_HT");
        tableDefine.addNvarcharBaseDataField("RENTER_TYPE","商户类型","MD_BMS_POS_MERCHANT_TYPE");
        tableDefine.addNvarcharBaseDataField("FLOOR_LED","楼层经理","MD_STAFF");
        tableDefine.addNvarcharBaseDataField("SALES_DATA_SOURCE","销售额来源","MD_BMS_SALES_SOURCE");
        tableDefine.addIntegerField("WORKFLOWSTATE","工作流状态",null);
        tableDefine.addNvarcharBaseDataField("RENT_STATE","状态","MD_BMS_RENTERSTATE");
        tableDefine.addDateField("UC_PAY_TYPE_BEGIN_TIME","代收起使日期");
        tableDefine.addDateField("CONTRACT_SDATE","合同起始日");
        tableDefine.addDateField("CONTRACT_EDATE","合同截止日");
        tableDefine.addDateField("BILLDATE","单据时间");
        tableDefine.addDateField("CLOSEDATE","退铺时间");
        tableDefine.addDateField("REALCLOSEFATE","实际退铺日期");
        tableDefine.addDateField("STOP_DATE","终止日期");
        tableDefine.addNvarcharBaseDataField("UC_PAY_TYPE","代收介质","MD_BMS_POS_PAY_TYPE");
        tableDefine.addNvarcharBaseDataField("JSZQ","结算周期","MD_BMS_SETTLEMENT_PERIOD");
        tableDefine.addNvarcharBaseDataField("HSBM","核算部门","MD_DEPARTMENT");
        tableDefine.addNvarcharBaseDataField("ACQUIRINNG_BANK","收单行","MD_BMS_ACQUIRING_BANK");
        tableDefine.addNvarcharField("ABBREVIATION","缩写",100,null);
        tableDefine.addNvarcharField("BRANDNAME","品牌名称",100,null);
        tableDefine.addNvarcharField("ZLTB_SCBS","租赁填报-删除标识",100,null);
        tableDefine.addNvarcharField("ZLTB_SCBS_CW","租赁填报-删除标识-财务",100,null);
        tableDefine.addBooleanField("IS_UNITE_COLLECT","是否统收银",null);
        tableDefine.addBooleanField("IS_ALL_PAY_TYPE","是否代收全部",null);
        tableDefine.addBooleanField("IS_USE_MINI","是否使用商户通",null);
        tableDefine.addBooleanField("HAS_SALE","有销售",null);
        CreateDataModelUtil.pushBillMasterTable(tableDefine);
    }

    /**
     * 初始化基础数据
     *
     * @param tenantName
     * @param code
     * @param name
     * @return
     */
    private static BaseDataDO initBaseData(String tenantName, String code, String name) {
        BaseDataDO baseDataDO = new BaseDataDO();
        baseDataDO.setTenantName(tenantName);
        baseDataDO.setTableName(Base_Data_NAME);
        baseDataDO.setCode(code);
        baseDataDO.setName(name);
        return baseDataDO;
    }
}

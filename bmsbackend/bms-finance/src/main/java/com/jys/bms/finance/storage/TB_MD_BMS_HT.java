package com.jys.bms.finance.storage;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.jiuqi.va.domain.basedata.OrgMngType.ISOLATED;
import static com.jiuqi.va.domain.basedata.OrgMngType.SHARE;

/**
 * @Author wangyunfan
 * @Date 2023/12/14 9:22
 * @Description 基础数据项目--租赁合同
 */
public class TB_MD_BMS_HT{
    private static final String Base_Data_NAME = "MD_BMS_HT";

    private static final String Base_Data_TITLE = "租赁合同";

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
        //增加字段
        initBaseDataColumn();

        //增加测试数据
//        BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
//        baseDataBatchOptDTO.setTenantName(tenantName);
//
//        List<BaseDataDO> dataList = new ArrayList<BaseDataDO>();
//        dataList.add(initBaseData(tenantName,"01","合同01"));
//        dataList.add(initBaseData(tenantName,"02","合同02"));
//        dataList.add(initBaseData(tenantName,"03","合同03"));
//        dataList.add(initBaseData(tenantName,"04","合同04"));
//        dataList.add(initBaseData(tenantName,"05","合同05"));
//
//        baseDataBatchOptDTO.setDataList(dataList);
//        BaseDataClient dataClient = ApplicationContextRegister.getBean(BaseDataClient.class);
//        dataClient.batchAdd(baseDataBatchOptDTO);
//        System.out.println(r);
    }

    /**
     * @description 补充字段
     * @param
     * @return void
     * @Created by violet at 2023/12/15 15:10
     */
    private static void initBaseDataColumn(){
        TableDefine tableDefine=TableDefine.newBaseDataTable(Base_Data_NAME,Base_Data_TITLE);
        tableDefine.addNvarcharField("STDCODE","合同ID",100,null);
        tableDefine.addDateField("SING_DATE","签约日期");
        tableDefine.addNvarcharBaseDataField("MALL","项目","MD_BMS_STXM");
        tableDefine.addNvarcharBaseDataField("YYXM","运营项目","MD_BMS_YYXM");
        tableDefine.addNvarcharBaseDataField("KHDW","客户单位","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("RENT_STORE","租赁资产","MD_BMS_STORE");
        tableDefine.addNvarcharBaseDataField("BRAND","品牌","MD_BMS_BRAND");
        tableDefine.addNvarcharBaseDataField("TOB","业态","MD_BMS_TOB");
        tableDefine.addDateField("SDATE","开始日期");
        tableDefine.addDateField("EDATE","结束日期");
        tableDefine.addDateField("STOP_DATE","终止日期");
        tableDefine.addDateField("EXIT_DATE","退场日期");
        tableDefine.addNvarcharBaseDataField("RENTER","所属商户","MD_BMS_RENTER");
        tableDefine.addNvarcharBaseDataField("HT_STATE","合同状态","MD_BMS_HT_STATE");
        tableDefine.addNvarcharBaseDataField("HT_TYPE","合同类型","MD_BMS_HT_TYPE");
        tableDefine.addNvarcharBaseDataField("HT_CATEGORY","合同类别","MD_BMS_HT_CATEGORY");
        tableDefine.addNvarcharBaseDataField("HT_NATURE","合同性质","MD_BMS_HT_NATURE");
        tableDefine.addBooleanField("HAS_SALE","有销售",null);
        tableDefine.addNvarcharBaseDataField("FIRST_PARTY","甲方","MD_BMS_CUSTOMER");
        tableDefine.addNvarcharBaseDataField("SECOND_PARTY","乙方","MD_BMS_CUSTOMER");
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

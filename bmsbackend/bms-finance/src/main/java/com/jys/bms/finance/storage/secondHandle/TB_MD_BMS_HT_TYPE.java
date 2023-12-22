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

import static com.jiuqi.va.domain.basedata.OrgMngType.SHARE;

/**
 * @Author wangyunfan
 * @Date 2023/12/15 17:33
 * @Description 基础数据类型--合同类型
 */
public class TB_MD_BMS_HT_TYPE {
    private static final String Base_Data_NAME = "MD_BMS_HT_TYPE";

    private static final String Base_Data_TITLE = "合同类型";

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
        baseDataDefineDTO.setStructtype(1);
        baseDataDefineDTO.setGroupfieldname("SKXMFZ");
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
        baseDataDefineDTO.setSharetype(SHARE.getCode());
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
        BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
        baseDataBatchOptDTO.setTenantName(tenantName);
        List<BaseDataDO> dataList = new ArrayList<BaseDataDO>();
        dataList.add(initBaseData(tenantName,"01","收款项目01"));
        dataList.add(initBaseData(tenantName,"02","收款项目02"));
        dataList.add(initBaseData(tenantName,"03","收款项目03"));
        baseDataBatchOptDTO.setDataList(dataList);

        BaseDataClient dataClient = ApplicationContextRegister.getBean(BaseDataClient.class);
        dataClient.batchAdd(baseDataBatchOptDTO);
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
        tableDefine.addNvarcharField("HCODE","业务编号",100,null);
        tableDefine.addNvarcharBaseDataField("DCYT","地产业态","MD_BMS_AI_LAND_NATURE");
        tableDefine.addNvarcharBaseDataField("ZHLX","租户类型","MD_BMS_MERCHANT_TYPE");
        tableDefine.addBooleanField("IS_CREATE_SKU","是否生成SKU",null);
        tableDefine.addBooleanField("IS_YXS","是否意向书",null);
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

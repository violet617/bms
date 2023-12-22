package com.jys.bms.finance.storage;

import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.MappingType;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelType;
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
 * @Date 2023/12/14 9:44
 * @Description 基础数据定义--租赁资产（店铺）
 */
//@Component
public class TB_MD_BMS_STORE {
    private static final String Base_Data_NAME = "MD_BMS_STORE";

    private static final String Base_Data_TITLE = "租赁资产";

    private static final String GROUP_NAME = "system";

        /**
     * @description TODO: 基础数据定义
     * @param tenantName
     * @return void
     * @Created by violet at 2023/12/14 12:26
     */
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
        baseDataDefineDTO.setGroupfieldname("QY");

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

        baseDataDefineDTO.setSharefieldname("UNITCODE"); // 隔离维度 组织机构？
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
            R upate = client.upate(baseDataDefineDTO);
            System.out.println(upate);
            System.out.println("修改基础数据");
        }
        initBaseDataColumn();



    }

    private static void initBaseDataColumn(){
        TableDefine tableDefine=TableDefine.newBaseDataTable(Base_Data_NAME,Base_Data_TITLE);
        
        tableDefine.addNvarcharField("MC","资产名称",200,null);
        tableDefine.addNvarcharBaseDataField("LX","资产类型","MD_BMS_ASSET_TYPE");
        tableDefine.addNvarcharBaseDataField("QY","物业区域","MD_BMS_ASSET_REGION");
        tableDefine.addNumericField2("JZMJ","建筑面积",18,2,null);
        tableDefine.addNumericField2("KZMJ","可租面积",18,2,null);
        tableDefine.addNvarcharBaseDataField("STXM","项目","MD_BMS_STXM");
        tableDefine.addDateField("SDATE","生效日期");
        tableDefine.addDateField("EDATE","失效日期");
        tableDefine.addNvarcharBaseDataField("WYXM","物业项目","MD_BMS_WYXM");
        tableDefine.addNvarcharBaseDataField("YYXM","运营项目","MD_BMS_YYXM");
        tableDefine.addNumericField("BH","版本号",null);
        tableDefine.addNumericField("XH","修订号",null);
        tableDefine.addNvarcharField("CUR_BILLCODE","当前版本单据主键",100,null);
        tableDefine.addNvarcharField("CUR_DEFINE","当前版本单据定义",100,null);
        tableDefine.addBooleanField("JSCZL","计算出租率",null);
        tableDefine.addBooleanField("JWYZ","计为已租",null);
        tableDefine.addNvarcharBaseDataField("YT","资产用途","MD_BMS_ASSET_AUTHORITY");
        tableDefine.addNvarcharBaseDataField("FQ","所属分区","MD_BMS_ASSET_ZONE");
        tableDefine.addNvarcharBaseDataField("LD","所属楼栋","MD_BMS_ASSET_BUILDING");
        tableDefine.addNvarcharBaseDataField("DY","所属单元","MD_BMS_ASSET_FLAT");
        tableDefine.addNvarcharBaseDataField("LC","所属楼层","MD_BMS_ASSET_FLOOR");
        tableDefine.addBooleanField("ZZZC","整租资产",null);
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

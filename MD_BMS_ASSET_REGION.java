package com.jys.bms.leaseoperation.storage.baseData;

import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.OrgMngType;
import com.jiuqi.va.domain.common.*;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.extend.DataModelTemplateConsts;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author xuyaping
 * 物业区域
 */
@Component
public class MD_BMS_ASSET_REGION implements StorageSyncTask {
    @Override
    public void execute() {
        System.out.println("MD_BMS_ASSET_REGION 创建===========");
        String tenantName = ShiroUtil.getTenantName();
        //定义表（创建表）定义基础表，设置隔离、共享、列表等系统字段
        createBaseDataDefine(tenantName);
        createBaseDataTable(tenantName); //创建表，新旧表合并
    }

    /**
     * 定义基础表，设置隔离、共享、列表等系统字段
     * @param tenantName
     */
    private static void createBaseDataDefine(String tenantName) {
        BaseDataDefineClient client = ApplicationContextRegister.getBean(BaseDataDefineClient.class);
        BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();

        baseDataDefineDTO.setTenantName(tenantName);
        baseDataDefineDTO.setName("MD_BMS_ASSET_REGION"); //物业区域
        PageVO<BaseDataDefineDO> result = client.list(baseDataDefineDTO);
        baseDataDefineDTO.setStructtype(3);// 级次树222
        baseDataDefineDTO.setLevelcode("222");

        baseDataDefineDTO.setTitle("物业区域");
        baseDataDefineDTO.setGroupname(BaseDataConsts.DEFINE_GROUP_PUBLIC);//公共分组

        baseDataDefineDTO.setSharetype(OrgMngType.ISOLATED.getCode());// 隔离
        baseDataDefineDTO.setSharefieldname("UNITCODE"); //不知道是啥，加上可以让隔离生效

        baseDataDefineDTO.setSolidifyflag(0);//非固化
        baseDataDefineDTO.setReadonly(0);//只读标识 可用
        baseDataDefineDTO.setVersionflag(1); //版本启用 1
        if (result.getTotal() == 0) {
            baseDataDefineDTO.setId(UUID.randomUUID());
            client.add(baseDataDefineDTO);
        } else {//更新：必要时调用，如无更改，则不需更新。
            baseDataDefineDTO.setModifytime(new Date());
            client.upate(baseDataDefineDTO);
        }
    }

    /**
     * 创建基础数据表，新旧表格合并 createBaseDataTable
     */
    private void createBaseDataTable(String tenantName) {
        DataModelClient client2 = ApplicationContextRegister.getBean(DataModelClient.class);

        DataModelDO origalDataModel = getCreateDataMode(tenantName);//模板默认字段


        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName("MD_BMS_ASSET_REGION");
        dataModelDTO.setTenantName(tenantName);
        DataModelDO dataModelDO = client2.get(dataModelDTO);//现用字段
        origalDataModel.setColumns(StorageUtil.mergeDataModel(origalDataModel, dataModelDO));//字段合并
        client2.push(origalDataModel);//创建表
    }

    /**
     * 获得初始化数据 基础数据 模型定义，创建表，往表里加字段 getCreateDataMode
     * @return
     */
    public static DataModelDO getCreateDataMode(String tenantName) {
        DataModelDO dataModelDO  = new DataModelDO();
        dataModelDO.setGroupcode(DataModelGroupConsts.DATAMODEL_GROUP_PUBLIC);
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setName("MD_BMS_ASSET_REGION");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("物业区域");

        //单据主表（基础数据）公共字段
        List<DataModelColumn> columns = StorageUtil.getTemplateFields(DataModelTemplateConsts.DMT_BASEDATA);
        dataModelDO.setColumns(columns);

        // 添加字段
        dataModelDO.addColumn("STXM").columnTitle("所属项目").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_STXM.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("STORE_TYPE").columnTitle("资产类型").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_TYPE.OBJECTCODE").lengths(330).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("YYXM").columnTitle("运营项目").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_YYXM.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("REGION_TYPE").columnTitle("区域类型").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_REGION_TYPE.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SSFQ").columnTitle("所属分区").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_ZONE.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("FLAT").columnTitle("所属单元").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_FLAT.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BUILDING").columnTitle("所属楼栋").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_BUILDING.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("FLOOR").columnTitle("所属楼层").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_FLOOR.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);

        return dataModelDO;
    }
    @Override
    public String getVersion() {
        return "202312071608";
    }

    @Override
    public boolean needCompareVersion(){
        return false;//开发初期暂时不要判断版本号
    }


}

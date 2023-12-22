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
 * 租赁资产
 */
@Component
public class MD_BMS_ASSET implements StorageSyncTask {
    @Override
    public void execute() {
        System.out.println("MD_BMS_ASSET init创建===========");
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
        baseDataDefineDTO.setName("MD_BMS_ASSET");

        baseDataDefineDTO.setStructtype(1);
        baseDataDefineDTO.setTitle("租赁资产");
        baseDataDefineDTO.setGroupname(BaseDataConsts.DEFINE_GROUP_PUBLIC);//公共分组
        baseDataDefineDTO.setSharetype(OrgMngType.ISOLATED.getCode());//隔离
        baseDataDefineDTO.setSharefieldname("UNITCODE"); //隔离组织
        baseDataDefineDTO.setGroupfieldname("QY");


        baseDataDefineDTO.setSolidifyflag(0);//非固化
        baseDataDefineDTO.setReadonly(0);//只读标识 可用
        baseDataDefineDTO.setVersionflag(1); //版本启用 1


        PageVO<BaseDataDefineDO> result = client.list(baseDataDefineDTO);
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
        dataModelDTO.setName("MD_BMS_ASSET");
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
        dataModelDO.setName("MD_BMS_ASSET");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("租赁资产");

        //基础数据 公共字段
        List<DataModelColumn> columns = StorageUtil.getTemplateFields(DataModelTemplateConsts.DMT_BASEDATA);
        dataModelDO.setColumns(columns);

        // 添加字段
        dataModelDO.addColumn("MC").columnTitle("资产名称").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LX").columnTitle("资产类型").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_TYPE.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("QY").columnTitle("物业区域").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_REGION.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);

        dataModelDO.addColumn("JZMJ").columnTitle("建筑面积").columnType(DataModelType.ColumnType.NUMERIC).lengths(18,2).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("KZMJ").columnTitle("可租面积").columnType(DataModelType.ColumnType.NUMERIC).lengths(18,2).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("STXM").columnTitle("项目").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_STXM.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("SDATE").columnTitle("生效日期").columnType(DataModelType.ColumnType.DATE).lengths(60).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("EDATE").columnTitle("失效日期").columnType(DataModelType.ColumnType.DATE).lengths(60).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("WYXM").columnTitle("物业项目").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_WYXM.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("YYXM").columnTitle("运营项目").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_YYXM.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("BH").columnTitle("版本号").columnType(DataModelType.ColumnType.INTEGER).lengths(10).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("XH").columnTitle("修订号").columnType(DataModelType.ColumnType.INTEGER).lengths(10).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CUR_BILLCODE").columnTitle("当前版本单据主键").columnType(DataModelType.ColumnType.NVARCHAR).lengths(100).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CUR_DEFINE").columnTitle("当前版本单据定义").columnType(DataModelType.ColumnType.NVARCHAR).lengths(100).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("JSCZL").columnTitle("计算出租率").columnType(DataModelType.modelTypeToColumnType(8)).lengths(20).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("JWYZ").columnTitle("计为已租").columnType(DataModelType.modelTypeToColumnType(8)).lengths(20).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("YT").columnTitle("资产用途").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_USE.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("FQ").columnTitle("所属分区").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_ZONE.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LD").columnTitle("所属楼栋").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_BUILDING.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("DY").columnTitle("所属单元").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_FLAT.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("LC").columnTitle("所属楼层").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_FLOOR.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ZZZC").columnTitle("整租资产").columnType(DataModelType.modelTypeToColumnType(8)).lengths(20).columnAttr(DataModelType.ColumnAttr.FIXED).defaultVal("false");
        //新增的
        dataModelDO.addColumn("CQDW").columnTitle("产权单位").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_CUSTOMER.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("CQZC").columnTitle("产权资产").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_CQZC.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("ASSET_BH").columnTitle("资产编号").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED).pkey(true);
        dataModelDO.addColumn("TNMJ").columnTitle("套内面积").columnType(DataModelType.ColumnType.NUMERIC).lengths(18,2).columnAttr(DataModelType.ColumnAttr.FIXED);
        dataModelDO.addColumn("JYYT").columnTitle("经营业态").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_ASSET_JYYT.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);


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

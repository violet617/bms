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

import java.util.List;
import java.util.UUID;

/**
 * @author xuyaping
 * 楼层
 */
@Component
public class MD_BMS_ASSET_FLOOR implements StorageSyncTask {
    @Override
    public void execute() {
        System.out.println("MD_BMS_ASSET_FLOOR 创建===========");
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
        baseDataDefineDTO.setName("MD_BMS_ASSET_FLOOR"); //楼层
        PageVO<BaseDataDefineDO> result = client.list(baseDataDefineDTO);
        baseDataDefineDTO.setStructtype(1);// 分组列表型
        baseDataDefineDTO.setGroupfieldname("STXM");
        baseDataDefineDTO.setTitle("楼层");
        baseDataDefineDTO.setGroupname(BaseDataConsts.DEFINE_GROUP_PUBLIC);//公共分组
        baseDataDefineDTO.setSharetype(OrgMngType.SHARE.getCode());// 共享

        baseDataDefineDTO.setSolidifyflag(0);//非固化
        baseDataDefineDTO.setReadonly(0);//只读标识 可用
        baseDataDefineDTO.setVersionflag(1); //版本启用 1
        if (result.getTotal() == 0) {
            baseDataDefineDTO.setId(UUID.randomUUID());
            client.add(baseDataDefineDTO);
        } else {//更新：必要时调用，如无更改，则不需更新。
//            baseDataDefineDTO.setModifytime(new Date());
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
        dataModelDTO.setName("MD_BMS_ASSET_FLOOR");
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
        dataModelDO.setName("MD_BMS_ASSET_FLOOR");
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle("楼层");

        //单据主表（基础数据）公共字段
        List<DataModelColumn> columns = StorageUtil.getTemplateFields(DataModelTemplateConsts.DMT_BASEDATA);
        dataModelDO.setColumns(columns);

        // 添加字段
        dataModelDO.addColumn("STXM").columnTitle("实体项目").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_STXM.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.FIXED);

        return dataModelDO;
    }
    @Override
    public String getVersion() {
        return "20231206-1608";
    }

    @Override
    public boolean needCompareVersion(){
        return false;//开发初期暂时不要判断版本号
    }




}



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
 */
@Component
public class MD_BMS_CUSTOMER implements StorageSyncTask {

    private static final String TABLE_NAME = "MD_BMS_CUSTOMER";

    private static final String TABLE_TITLE = "客户单位";

    private static final String GROUP_NAME = "public";

    @Override
    public void execute() {
        System.out.println("MD_BMS_CUSTOMER init创建===========");
        String tenantName = ShiroUtil.getTenantName();
        System.out.println("====客户单位=====");
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
        baseDataDefineDTO.setName(TABLE_NAME);
        PageVO<BaseDataDefineDO> result = client.list(baseDataDefineDTO);
        baseDataDefineDTO.setStructtype(0);// 列表型
        baseDataDefineDTO.setTitle(TABLE_TITLE);
        baseDataDefineDTO.setGroupname(BaseDataConsts.DEFINE_GROUP_PUBLIC);//公共分组
        baseDataDefineDTO.setSharetype(OrgMngType.SHARE.getCode());// 共享
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
    private static void createBaseDataTable(String tenantName) {
        DataModelClient client2 = ApplicationContextRegister.getBean(DataModelClient.class);

        DataModelDO origalDataModel = getCreateDataMode(tenantName);//模板默认字段

        DataModelDTO dataModelDTO = new DataModelDTO();
        dataModelDTO.setName(TABLE_NAME);
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
        System.out.println("===getCreateDataMode====");
        DataModelDO dataModelDO  = new DataModelDO();
        dataModelDO.setGroupcode(DataModelGroupConsts.DATAMODEL_GROUP_PUBLIC);
        dataModelDO.setBiztype(DataModelType.BizType.BASEDATA);
        dataModelDO.setName(TABLE_NAME);
        dataModelDO.setTenantName(tenantName);
        dataModelDO.setTitle(TABLE_TITLE);

        //单据主表（基础数据）公共字段
        List<DataModelColumn> columns = StorageUtil.getTemplateFields(DataModelTemplateConsts.DMT_BASEDATA);
        dataModelDO.setColumns(columns);

        dataModelDO.addColumn("KS_LX").columnTitle("客商类型").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_CUSTOMER_TYPE.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("SSGS").columnTitle("所属公司").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_CUSTOMER.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("KS_MC").columnTitle("客商名称").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ENGLISHNAME").columnTitle("英文名称").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("TYXYDM").columnTitle("统一信用代码").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("FRXM").columnTitle("法人姓名").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("JYZT").columnTitle("经营状态").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ADDRESS").columnTitle("注册地址").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("ZCZJ").columnTitle("注册资金").columnType(DataModelType.ColumnType.NUMERIC).lengths(18,2).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("CLRQ").columnTitle("成立日期").columnType(DataModelType.ColumnType.DATE).lengths(60).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("QYDH").columnTitle("企业电话").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("EMAIL").columnTitle("电子邮箱").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("GSWZ").columnTitle("公司网址").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("SSHY").columnTitle("所属行业").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_BMS_INDUSTRY.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("JYFW").columnTitle("经营范围").columnType(DataModelType.ColumnType.CLOB).lengths(500).columnAttr(DataModelType.ColumnAttr.EXTEND);

        dataModelDO.addColumn("NBDW").columnTitle("内部单位").columnType(DataModelType.modelTypeToColumnType(8)).lengths(10).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("QD").columnTitle("渠道").columnType(DataModelType.modelTypeToColumnType(8)).lengths(10).columnAttr(DataModelType.ColumnAttr.EXTEND);

        dataModelDO.addColumn("SSBM").columnTitle("所属部门").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_DEPARTMENT.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("CJRY").columnTitle("创建人员").columnType(DataModelType.ColumnType.NVARCHAR).mappingType(MappingType.BASEDATA).mapping("MD_STAFF.OBJECTCODE").lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);

        dataModelDO.addColumn("IDCARD").columnTitle("身份证号").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);
        dataModelDO.addColumn("PHONE").columnTitle("手机号码").columnType(DataModelType.ColumnType.NVARCHAR).lengths(200).columnAttr(DataModelType.ColumnAttr.EXTEND);

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
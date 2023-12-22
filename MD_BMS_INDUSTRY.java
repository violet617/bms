package com.jys.bms.leaseoperation.storage.baseData;

import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.OrgMngType;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jys.bms.common.info.TableDefine;
import com.jys.bms.common.util.CreateDataModelUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * @author xuyaping
 * @Description : 所属行业
 * @Date: 2023/12/20  10:42
 */
@Component
public class MD_BMS_INDUSTRY implements StorageSyncTask {
    private static final String TABLE_NAME = "MD_BMS_INDUSTRY";

    private static final String TABLE_TITLE = "所属行业";

    private static final String GROUP_NAME = "public";
    @Override
    public void execute() {
        System.out.println("MD_BMS_INDUSTRY init创建===========");
        String tenantName = ShiroUtil.getTenantName();

        //定义表（创建表）定义基础表，设置隔离、共享、列表等系统字段
        createBaseDataDefine(tenantName);

        TableDefine define =TableDefine.newBaseDataTable(TABLE_NAME,TABLE_TITLE);
        CreateDataModelUtil.pushBillMasterTable(define);
    }

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
    @Override
    public String getVersion() {
        return "20231206-1608";
    }

    @Override
    public boolean needCompareVersion(){
        return false;//开发初期暂时不要判断版本号
    }



}

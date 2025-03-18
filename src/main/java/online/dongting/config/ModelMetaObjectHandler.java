package online.dongting.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import online.dongting.utils.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;
import java.util.Objects;

public class ModelMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间
     **/
    private static final String CREATE_TIME = "createTime";
    /**
     * 创建者
     **/
    private static final String CREATE_BY = "createBy";
    /**
     * 修改时间
     **/
    private static final String UPDATE_TIME = "updateTime";
    /**
     * 修改者
     **/
    private static final String UPDATE_BY = "updateBy";

    @Override
    public void insertFill(MetaObject metaObject) {
        // 获取到需要被填充的字段值
        if (Objects.isNull(getFieldValByName(CREATE_TIME, metaObject))) {
            setFieldValByName(CREATE_TIME, new Date(), metaObject);
        }
        if (Objects.isNull(getFieldValByName(CREATE_BY, metaObject))) {
            setFieldValByName(CREATE_BY, SecurityUtil.getCurrentUserId(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 获取到需要被填充的字段值
        if (Objects.isNull(getFieldValByName(UPDATE_TIME, metaObject))) {
            setFieldValByName(UPDATE_TIME, new Date(), metaObject);
        }
        if (Objects.isNull(getFieldValByName(UPDATE_BY, metaObject))) {
            setFieldValByName(UPDATE_BY, SecurityUtil.getCurrentUserId(), metaObject);
        }
    }

}

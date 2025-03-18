package online.dongting.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class Entity<T, U> implements Serializable {
    /**
     * 主键
     **/
    @TableId(type = IdType.ASSIGN_ID)
    private T id;
    /**
     * 创建者
     **/
    @TableField(fill = FieldFill.INSERT)
    private U createBy;
    /**
     * 创建时间
     **/
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改者
     **/
    @TableField(fill = FieldFill.UPDATE)
    private U updateBy;
    /**
     * 修改时间
     **/
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;


}

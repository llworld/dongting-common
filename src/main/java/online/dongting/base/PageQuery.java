package online.dongting.base;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.dongting.constant.SystemConst;

import javax.validation.constraints.Max;
import java.io.Serializable;

/**
 * @author: ll
 * @since: 2023/10/20 HOUR:14 MINUTE:37
 * @description: T:数据库表模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class PageQuery<T> implements Serializable {

    @ApiModelProperty(value = "当前页(默认1)")
    @Builder.Default
    private Long page = SystemConst.PAGE_DEFAULT;

    @ApiModelProperty(value = "页大小(默认10)")
    @Max(value = 100, message = "最大页大小：100")
    @Builder.Default
    private Long size = SystemConst.SIZE_DEFAULT;

    public Page<T> getPage() {
        return new Page<>(page, size);
    }

}

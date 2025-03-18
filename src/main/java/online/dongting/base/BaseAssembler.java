package online.dongting.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Component;

/**
 * @author ll
 */
@Component
public class BaseAssembler {


    /**
     *
     * @param resPage  结果集
     * @param sourcePage 数据源
     */
    public void convertPage(IPage<?> resPage, IPage<?> sourcePage){
        resPage.setTotal(sourcePage.getTotal());
        resPage.setPages(sourcePage.getPages());
        resPage.setSize(sourcePage.getSize());
        resPage.setCurrent(sourcePage.getCurrent());
    }

}

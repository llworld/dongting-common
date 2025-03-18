package online.dongting.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import online.dongting.constant.SystemConst;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public abstract class BaseController {
    public <T> ResponseEntity<List<T>> ok(IPage<T> page){
        return ResponseEntity.status(HttpStatus.OK)
                .header(SystemConst.PAGINATION_COUNT_HEADER,String.valueOf(page.getTotal()))
                .header(SystemConst.PAGINATION_PAGES_HEADER,String.valueOf(page.getPages()))
                .header(SystemConst.PAGINATION_SIZE_HEADER,String.valueOf(page.getSize()))
                .header(SystemConst.PAGINATION_NUMBER_HEADER,String.valueOf(page.getCurrent()))
                .body(page.getRecords());
    }
}

package cn.tedu.tmall.admin.content.startup;

import cn.tedu.tmall.admin.content.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DistrictRunner implements ApplicationRunner {

    @Autowired
    private ICategoryService categoryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
       categoryService.rebuildCache();
    }

}

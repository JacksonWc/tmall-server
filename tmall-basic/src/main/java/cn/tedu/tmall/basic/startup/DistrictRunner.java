package cn.tedu.tmall.basic.startup;

import cn.tedu.tmall.basic.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DistrictRunner implements ApplicationRunner {

    @Autowired
    private IDistrictService districtService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        districtService.rebuildCache();
    }

}

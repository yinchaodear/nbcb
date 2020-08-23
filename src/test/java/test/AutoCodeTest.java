package test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yuqiaotech.YuqiaoApplication;
import com.yuqiaotech.common.tools.autocode.AutoCodeUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YuqiaoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutoCodeTest
{
    /**
     * 生成代码入口方法
     */
    @Test
    @Transactional(readOnly = false)
    public void gencode()
    {
        AutoCodeUtil acu = new AutoCodeUtil();
        acu.srcPath = "D:/yuqiaoworkspace/yuqiao-admin-springboot-hibernate/src/main/java";
        acu.pojoName = "Demo";
        acu.packageName = "demo";
        acu.saveToRootx = "D:/yuqiaoworkspace/yuqiao-admin-springboot-hibernate/tmp";
        acu.autoCodeOneModel();
    }
}

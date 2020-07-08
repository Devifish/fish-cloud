package cn.devifish.cloud.file.server;

import cn.devifish.cloud.common.core.BaseApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * FileApplication
 * 文件服务
 *
 * @author Devifish
 * @date 2020/7/8 18:04
 */
@SpringCloudApplication
public class FileApplication extends BaseApplication {

    public static void main(String[] args) {
        run(FileApplication.class, args);
    }

}

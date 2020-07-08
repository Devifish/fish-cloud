package cn.devifish.cloud.search.server;

import cn.devifish.cloud.common.core.BaseApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * SearchApplication
 * 搜索服务
 *
 * @author Devifish
 * @date 2020/7/8 18:09
 */
@SpringCloudApplication
public class SearchApplication extends BaseApplication {

    public static void main(String[] args) {
        run(SearchApplication.class, args);
    }
}

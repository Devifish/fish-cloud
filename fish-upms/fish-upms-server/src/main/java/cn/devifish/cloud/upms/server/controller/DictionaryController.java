package cn.devifish.cloud.upms.server.controller;

import cn.devifish.cloud.upms.server.cache.DictionaryCache;
import cn.devifish.cloud.upms.server.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DictionaryController
 * 字典接口
 *
 * @author Devifish
 * @date 2020/8/1 16:39
 */
@RestController
@RequestMapping("/dictionary")
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;
    private final DictionaryCache dictionaryCache;

}

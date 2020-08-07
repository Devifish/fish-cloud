package cn.devifish.cloud.upms.server.service;

import cn.devifish.cloud.common.core.exception.BizException;
import cn.devifish.cloud.upms.common.entity.Dept;
import cn.devifish.cloud.upms.server.cache.DeptCache;
import cn.devifish.cloud.upms.server.mapper.DeptMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static cn.devifish.cloud.common.core.MessageCode.PreconditionFailed;

/**
 * DeptService
 * 部门服务
 *
 * @author Devifish
 * @date 2020/8/1 16:36
 */
@Service
@RequiredArgsConstructor
public class DeptService {

    private final DeptMapper deptMapper;
    private final DeptCache deptCache;

    /**
     * 根据部门ID查询部门数据
     *
     * @param deptId 部门ID
     * @return 部门
     */
    public Dept selectById(Long deptId) {
        if (deptId == null)
            throw new BizException(PreconditionFailed, "部门ID不能为空");

        return deptCache.getIfAbsent(deptId, deptMapper::selectById);
    }

}

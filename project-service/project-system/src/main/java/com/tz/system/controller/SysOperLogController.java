package com.tz.system.controller;


import com.tz.common.log.annotation.OperateLog;
import com.tz.common.log.enums.BusinessType;
import com.tz.common.model.ApiResponse;
import com.tz.system.model.SysOperLog;
import com.tz.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 操作日志记录 提供者
 *
 * @author KyrieCao
 * @date 2020/3/17 14:53
 */
@RestController
@RequestMapping("operLog")
public class SysOperLogController {

    @Autowired
    private ISysOperLogService sysOperLogService;

    /**
     * 查询操作日志记录
     */
    @GetMapping("get/{operId}")
    public SysOperLog get(@PathVariable("operId") Long operId) {
        return sysOperLogService.selectOperLogById(operId);
    }

    /**
     * 查询操作日志记录列表
     */
    @RequestMapping("list")
    public ApiResponse<List<SysOperLog>> list(SysOperLog sysOperLog) {
        return new ApiResponse<List<SysOperLog>>().success(sysOperLogService.selectOperLogList(sysOperLog));
    }

    /**
     * 新增保存操作日志记录
     */
    @PostMapping("save")
    public void addSave(@RequestBody SysOperLog sysOperLog) {
        sysOperLogService.insertOperlog(sysOperLog);
    }

    /**
     * 删除操作日志记录
     */
    @PostMapping("remove")
    public ApiResponse<?> remove(String ids) {
        return new ApiResponse<>().success(sysOperLogService.deleteOperLogByIds(ids));
    }

    @OperateLog(title = "操作日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    public ApiResponse<?> clean() {
        sysOperLogService.cleanOperLog();
        return new ApiResponse<>().success();
    }
}

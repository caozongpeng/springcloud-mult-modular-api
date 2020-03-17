package com.tz.common.log.event;

import com.tz.system.model.SysOperLog;
import org.springframework.context.ApplicationEvent;


/**
 * 系统操作日志事件
 *
 * @author KyrieCao
 * @date 2020/3/17 14:30
 */
public class SysOperLogEvent extends ApplicationEvent {
    private static final long serialVersionUID = 8905017895058642111L;

    public SysOperLogEvent(SysOperLog source) {
        super(source);
    }
}
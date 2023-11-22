package com.nasida.core.manager;

import com.nasida.core.pool.ThreadPoolHelper;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * @author yanasida
 * @date 2023/11/22 16:15
 * <p>
 * 具有定时校验的管理器
 */
public abstract class TimeCheckedManager<K> implements ConnectorManager<K> {

    /**
     * 时间单位
     */
    protected TimeUnit durationUnit;

    /**
     * 校验周期
     */
    protected long checkDuration;

    /**
     * 延迟启动时间
     */
    protected long delay;

    private Runnable task;

    public TimeCheckedManager() {
        this.durationUnit = TimeUnit.SECONDS;
        this.checkDuration = 4L;
        this.delay = 2L;
    }

    public TimeCheckedManager(@NotNull TimeUnit durationUnit, long checkDuration, long delay) {
        if (durationUnit == null) throw new NullPointerException();
        if (checkDuration < 1L || delay < 0L) throw new IllegalArgumentException();
        this.durationUnit = durationUnit;
        this.checkDuration = checkDuration;
        this.delay = delay;
    }

    /**
     * 校验
     */
    abstract void check();

    private void startCheck() {
        this.task = this::check;
        //定时
        ThreadPoolHelper.scheduled(task, delay, checkDuration, durationUnit);
    }

    private boolean closeCheck() {
        if (task == null) {
            return true;
        }
        return ThreadPoolHelper.remove(task);
    }
}

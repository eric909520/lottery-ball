package com.backend.framework.config;

import com.backend.common.utils.Threads;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池配置
 *
 * @author
 **/
@Configuration
public class ThreadPoolConfig {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    // 核心线程池大小
    private int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    // 最大可创建的线程数
    private int maxPoolSize = CPU_COUNT * 2 + 1;

    // 队列最大长度
    private int queueCapacity = 1500;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 30;

    @Bean(name = "threadPoolExecutor")
    public ThreadPoolExecutor threadPoolExecutor() {

        //线程的创建工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "schedule-pool-%d" + mCount.getAndIncrement());
            }
        };
        //线程池任务满载后采取的任务拒绝策略
        RejectedExecutionHandler rejectHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor mExecute = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024),
                threadFactory,
                rejectHandler
        );
        return mExecute;

        /*ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());*/
//        return executor;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }
}

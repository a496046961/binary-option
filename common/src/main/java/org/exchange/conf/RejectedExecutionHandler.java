package org.exchange.conf;




import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class RejectedExecutionHandler implements java.util.concurrent.RejectedExecutionHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RejectedExecutionHandler.class);

    /**
     * 线程池关闭时，为避免任务丢失，留下处理方法
     * 如果需要由调用方来运行，可以{@code new WalletRejectedExecutionHandler(Runnable::run)}
     */
    private final Consumer<Runnable> handlerwhenshutdown;

    /**
     * 构造
     *
     * @param handlerwhenshutdown 线程池关闭后的执行策略
     */
    public RejectedExecutionHandler(final Consumer<Runnable> handlerwhenshutdown) {
        this.handlerwhenshutdown = handlerwhenshutdown;
    }

    /**
     * 构造
     */
    public RejectedExecutionHandler() {
        this(null);
    }


    /**
     *  拒绝策略，如果未关闭，当触发拒绝策略时，在尝试一分钟的时间重新将任务塞进任务队列，当一分钟超时还没成功时，就抛出异常并直接执行
     * @param r the runnable task requested to be executed
     * @param executor the executor attempting to execute this task
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        try {
            // 线程池未关闭时，阻塞等待
            if (false == executor.isShutdown()) {
                executor.getQueue().offer(r, 60, TimeUnit.SECONDS);
            }else if (null != handlerwhenshutdown) {
                // 当设置了关闭时候的处理
                handlerwhenshutdown.accept(r);
            }
        } catch (InterruptedException e) { // 直接执行
            final Thread t = new Thread(r, "Temporary task executor");
            t.start();

            log.error("拒绝策略异常: ", e);
        }
    }
}

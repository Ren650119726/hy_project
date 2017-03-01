import com.mockuai.dts.core.service.ExportServiceManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by luliang on 15/7/27.
 */
public class TestExcutor {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个固定大小的线程池
        final int index = 0;
        ExecutorService service = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 10; i++) {
            System.out.println("创建线程" + i);
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    System.out.println("启动线程");
                }
            };
            // 在未来某个时间执行给定的命令
            service.execute(run);
        }
        // 关闭启动线程
        service.shutdown();
//        // 等待子线程结束，再继续执行下面的代码
        service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("all thread complete");
    }
}

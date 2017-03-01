package com.mockuai.dts.core.service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 执行导出任务;
 * Created by luliang on 15/7/1.
 */
public class ExportServiceManager {

    private static int index = 0;

    private final ExecutorService executorService = Executors.newFixedThreadPool(16, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            index++;
            return new Thread(r, "export-item-worker-thread-" + index);
        }
    });

    public void submitExportTask(ExportTask exportTask) throws ExecutionException, InterruptedException {

        executorService.execute(exportTask);
    }
    
    public void submitOrderExportTask(OrderExportTask task)throws ExecutionException, InterruptedException {
    	executorService.execute(task);
    }
    
    public void submitSellerUserExportTask(SellerUserExportTask task)throws ExecutionException, InterruptedException {
    	executorService.execute(task);
    }
    
    public void submitTranslogExportTask(TrsnslogExportTask task)throws ExecutionException, InterruptedException {
    	executorService.execute(task);
    }

//    public void submitLabelExportTask(LabelExportTask task) throws ExecutionException, InterruptedException {
//        executorService.execute(task);
//    }

//    public void submitMemberExportTask(MemberExportTask task) throws ExecutionException, InterruptedException {
//        executorService.execute(task);
//    }

//    public void submitStatisticsExportTask(DistributionStatisticsExportTask task) throws ExecutionException, InterruptedException {
//        executorService.execute(task);
//    }

//    public void submitMemberDataMigrateTask(MemberDataMigrateTask task) throws ExecutionException, InterruptedException {
//        executorService.execute(task);
//    }

    public void submitTask(Runnable task) throws ExecutionException, InterruptedException {
        executorService.execute(task);
    }
    
}

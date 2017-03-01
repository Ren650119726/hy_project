package proxy.test;

/**
 * Created by zengzhangqiang on 9/21/15.
 */
public class CatDogImpl implements Cat, Dog{
    @Override
    public String call() {
        System.out.println("cat call!!!");
        return "旺，旺，旺";
    }

    @Override
    public String run() {
        System.out.println("dog run!!!");
        return "RUN, RUN, RUN";
    }
}

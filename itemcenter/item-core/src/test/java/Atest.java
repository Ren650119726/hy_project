import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class Atest {

	/*// @Resource
	// UserClient userClient;

	// @Resource
	// ItemClient itemClient;

//	@Resource
//	ItemSkuClient itemSkuClient;

    private String appKey = "1b0044c3653b89673bc5beff190b68a1";
    @Autowired
    private ItemPropertyManager itemPropertyManager;

    @Autowired
    private DistributorManager distributorManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private ItemLabelManager itemLabelManager;
    @Autowired
    private SpecManager specManager;
        @Test
    public void addProperty() throws ItemException {
            SpecDTO specDTO = new SpecDTO();
            specDTO.setSpecName("生产日期");
            specDTO.setSortOrder(1);
            specManager.addSpec(specDTO);

    }


	@Test
	public void test() throws ItemException {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(2706L);
        itemLabelManager.queryItemLabelsByItem(itemDTO);
	}
    @Test
    public void  test1() throws ItemException {
      //  userManager.isHiKe(1841254L,appKey);
        //userManager.isHiKe(1841255L,appKey);

        ItemPropertyDTO itemPropertyDTO = new ItemPropertyDTO();
        itemPropertyDTO.setSellerId(11222L);
        itemPropertyDTO.setNameId(2L);
        itemPropertyDTO.setName("caca");
        itemPropertyDTO.setCode("spec");
        itemPropertyDTO.setBizCode("hanshu");
        itemPropertyDTO.setItemId(11L);
        itemPropertyDTO.setNameId(5L);
        itemPropertyDTO.setName("生产日期");
        itemPropertyDTO.setValue("2015-01-01");
        itemPropertyDTO.setPropertyValueId(2L);
        itemPropertyManager.addItemProperty(Lists.newArrayList(itemPropertyDTO));
    }*/

    @Test
    public void testSemaphore(){
        // 允许2个线程同时访问
        final Semaphore semaphore = new Semaphore(5);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println("线程:" + Thread.currentThread().getName() +"START");

                        semaphore.acquire();
                        // 这里可能是业务代码
                        System.out.println("线程:" + Thread.currentThread().getName() + "获得许可:" + index);
                        TimeUnit.SECONDS.sleep(1);
                        semaphore.release();
                        System.out.println("允许TASK个数：" + semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }
}


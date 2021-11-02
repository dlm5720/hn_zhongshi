package com.hnzs.tasks;

import com.hnzs.util.HttpRequest;
import com.hnzs.util.JSON;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@PropertySource(value = {"classpath:recruit.properties"})
public class TasksServer{

    @Value("${RecruitStartUrl}")
    private String RecruitStartUrl;

    @Value("${RecruitStopUrl}")
    private String RecruitStopUrl;

    public static void main(String args[]){
        /**
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now=new Date();
        try {
            Date start = sdf.parse("2021-10-27 23:50:00");
            Date end = sdf.parse("2021-10-28 01:50:00");
            System.out.println("1 "+(start.getTime()-now.getTime()));
            if(start.getTime()-now.getTime()<0){
               System.out.println("1 "+(start.getTime()-now.getTime()));
            }
            System.out.println("3 "+(end.getTime()-start.getTime()));
            if((end.getTime()-start.getTime())<0){
                System.out.println("3 "+(end.getTime()-start.getTime()));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
         */

        /**
        String a="[1,2,3,4,5,6,7]";
        int x=4;
        x= x-1==0?x=7:x-1;
        System.out.println(a.charAt(x));
         */

        /**
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()).substring(0,10));

        String temp="abcdefghijklmnopqrs2021-10-28start";
        System.out.println(temp.substring(0,temp.indexOf("-")-4));
         */

        /**
         * taskData taskType(1,2,3)  //1：临时任务  2：周期任务 3：7*24小时任务
         * taskData taskID 10000000
         * taskData taskName 测试测试
         * taskData taskBelogFlow xxxxxxx
         * taskData taskStartTime 2021-10-24 09:07:00
         * taskData taskEndTime 2021-10-24 09:07:00
         * taskData taskCrossDay 0 1
         * taskData taskCycleTime [1,2,3,4,5,6,7]
         * taskData taskCycleEndTime 2021-10-24 09:07:00
         * taskData taskIsTranscode 0 1
         * taskData taskStorageLocation
         * */

        /**
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            HashMap taskData = new HashMap();
            taskData.put("taskType", "1");
            taskData.put("taskID", "100000001");
            taskData.put("taskName", "测试任务");
            taskData.put("taskBelogFlow", "htt://localost/9210/test");
            taskData.put("taskStartTime", "2021-10-29 12:01:00");
            taskData.put("taskEndTime", "2021-10-29 11:56:00");
            //taskData.put("taskCrossDay","0");
            //taskData.put("taskCycleTime","[1,2,3,4,5,6,7]");
            //taskData.put("taskCycleEndTime","2021-10-24 09:07:00");
            taskData.put("taskIsTranscode", "1");
            taskData.put("taskStorageLocation", "1");
            System.out.println(JSON.Encode(taskData));

            Date now;
            Date start;
            Date end;
            Date taskCycleEndTime;
            now = new Date();
            start = sdf.parse(taskData.get("taskStartTime").toString());
            end = sdf.parse(taskData.get("taskEndTime").toString());
            System.out.println((start.getTime()-now.getTime()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
         */


        //定时调度测试
        /**
        pool=Executors.newScheduledThreadPool(1000);
        System.out.println();

        Future future = pool.schedule(
                new Runnable() {
                    public void run() {
                         System.out.println(" 运行时间: " + sdf.format(new Date()));
                    }
                },
                5,
                TimeUnit.SECONDS
        );

        Future future2 = pool.schedule(
                new Runnable() {
                    public void run() {
                        System.out.println(" 运行时间: " + sdf.format(new Date()));
                    }
                },
                7,
                TimeUnit.SECONDS
        );
         */

        /*****/
        ///unit test

        TasksServer ts=new TasksServer();
        ts.start();

        HashMap taskData=new HashMap();
        taskData.put("taskType","1");
        taskData.put("taskID","100000000");
        taskData.put("taskName","测试任务");
        taskData.put("taskBelogFlow","htt://localost/9210/test");
        taskData.put("taskStartTime","2021-10-29 23:17:00");
        taskData.put("taskEndTime","2021-10-29 23:18:00");
        //taskData.put("taskCrossDay","0");
        //taskData.put("taskCycleTime","[1,2,3,4,5,6,7]");
        //taskData.put("taskCycleEndTime","2021-10-24 09:07:00");
        taskData.put("taskIsTranscode","1");
        taskData.put("taskStorageLocation","1");
        taskData.put("taskIsTranscode","1");
        taskData.put("taskStorageLocation","1");
        System.out.println(JSON.Encode(taskData));
        ts.addTask(JSON.Encode(taskData));


        ts.planJobTask(taskData);
        ts.planJobTask(taskData);
        ts.planJobTask(taskData);

        taskData=new HashMap();
        taskData.put("taskType","1");
        taskData.put("taskID","100000001");
        taskData.put("taskName","测试任务");
        taskData.put("taskBelogFlow","htt://localost/9210/test");
        taskData.put("taskStartTime","2021-10-29 23:10:00");
        taskData.put("taskEndTime","2021-10-29 23:45:00");
        //taskData.put("taskCrossDay","0");
        //taskData.put("taskCycleTime","[1,2,3,4,5,6,7]");
        //taskData.put("taskCycleEndTime","2021-10-24 09:07:00");
        taskData.put("taskIsTranscode","1");
        taskData.put("taskStorageLocation","1");
        System.out.println(JSON.Encode(taskData));
        ts.addTask(JSON.Encode(taskData));

        taskData=new HashMap();
        taskData.put("taskType","1");
        taskData.put("taskID","100000002");
        taskData.put("taskName","测试任务");
        taskData.put("taskBelogFlow","htt://localost/9210/test");
        taskData.put("taskStartTime","2021-10-29 23:06:00");
        taskData.put("taskEndTime","2021-10-29 23:10:00");
        //taskData.put("taskCrossDay","0");
        //taskData.put("taskCycleTime","[1,2,3,4,5,6,7]");
        //taskData.put("taskCycleEndTime","2021-10-24 09:07:00");
        taskData.put("taskIsTranscode","1");
        taskData.put("taskStorageLocation","1");
        System.out.println(JSON.Encode(taskData));
        ts.addTask(JSON.Encode(taskData));


        taskData=new HashMap();
        taskData.put("taskType","2");
        taskData.put("taskID","200000001");
        taskData.put("taskName","测试任务");
        taskData.put("taskBelogFlow","htt://localost/9210/test");
        taskData.put("taskStartTime","2021-10-29 23:25:00");
        taskData.put("taskEndTime","2021-10-29 23:33:00");
        //taskData.put("taskCrossDay","0");
        taskData.put("taskCycleTime","[1,2,3,4,5,6,7]");
        taskData.put("taskCycleEndTime","2021-10-30 09:07:00");
        taskData.put("taskIsTranscode","1");
        taskData.put("taskStorageLocation","1");
        System.out.println(JSON.Encode(taskData));
        ts.addTask(JSON.Encode(taskData));

        taskData=new HashMap();
        taskData.put("taskType","2");
        taskData.put("taskID","200000002");
        taskData.put("taskName","测试任务");
        taskData.put("taskBelogFlow","htt://localost/9210/test");
        taskData.put("taskStartTime","2021-10-29 23:25:00");
        taskData.put("taskEndTime","2021-10-30 00:28:00");
        //taskData.put("taskCrossDay","0");
        taskData.put("taskCycleTime","[1,2,6,7]");
        taskData.put("taskCycleEndTime","2021-10-31 23:59:59");
        taskData.put("taskIsTranscode","1");
        taskData.put("taskStorageLocation","1");
        System.out.println(JSON.Encode(taskData));
        ts.addTask(JSON.Encode(taskData));

        taskData=new HashMap();
        taskData.put("taskType","3");
        taskData.put("taskID","300000001");
        taskData.put("taskName","测试任务");
        taskData.put("taskBelogFlow","htt://localost/9210/test");
        taskData.put("taskStartTime","2021-10-29 23:25:00");
        taskData.put("taskEndTime","2021-11-29 23:31:00");
        //taskData.put("taskCrossDay","0");
        //taskData.put("taskCycleTime","[1,2,6,7]");
        //taskData.put("taskCycleEndTime","2021-10-29 23:59:59");
        taskData.put("taskIsTranscode","1");
        taskData.put("taskStorageLocation","1");
        System.out.println(JSON.Encode(taskData));
        ts.addTask(JSON.Encode(taskData));

        /**/
    }

    /**
     * 一：程序在项目部署后完成首次加载，共一下几个部分
     *      1：资源部分
     *          任务计划注册表 ConcurrentHashMap
     *          运行任务池   ScheduledExecutorService
     *      2：任务计划注册表按计划调度线程。
     *
     *      3：注册表和数据库记录的同步线程。(本次程序不实现，重启后有外部程序辅助载入任务)
     *
     *      4：任务运行线程的健康自检线程。
     *
     * 二：可访问操作接口部分
     *      1：添加任务
     *
     *      2：删除任务
     *
     *      3：注册任务实例列表
     *
     *      4：任务运行情况及结果查看
     *
     * 三：系统关闭资源释放
     *
     * 四：命名定义
     *
     * 实例任务线程的名称变量 taskData.get("taskID").toString() + sdf.format(now).substring(0,10)+ "_start"
     * 数据集线程任务结果变量 taskData.get("taskID").toString() + sdf.format(now).substring(0,10)
     * */

    //启动程序
    public String start(){
        if(pool!=null||taskRegedit!=null||taskInstanceMap!=null){
            //资源没有完全清空 不能启动
            return "{\"code\":\"10000\",\"msg\":\"调度所需要的资源不满足启动条件，无法启动！\"}";
        }

        poolSize=1000;

        //资源初始化
        pool=Executors.newScheduledThreadPool(poolSize);
        taskRegedit=new ConcurrentHashMap();
        taskInstanceMap=new ConcurrentHashMap();

        //任务调度线程
        Future planFuture = pool.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try{
                            /**/
                            System.out.println("----------------------------------------------------------------");
                            System.out.println(sdf.format(new Date())+"任务调度线程运行 当前任务实例是："+ taskInstanceMap.keySet().size());
                            Iterator iterator=taskRegedit.keySet().iterator();
                            while(iterator.hasNext()){
                                String key=(String)iterator.next();
                                //System.out.println(key);
                                HashMap taskData=(HashMap) taskRegedit.get(key);
                                //System.out.println(key);
                                planJobTask(taskData);
                            }
                            System.out.println(sdf.format(new Date())+"任务调度线程运行 当前任务实例是："+ taskInstanceMap.keySet().size());
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                10, //延迟多久执行
                5, //执行间隔
                TimeUnit.SECONDS
        );

        //任务线程检查检查线程
        Future  jobFuture = pool.scheduleAtFixedRate(
                new Runnable() {
                    public void run() {
                        try{
                            System.out.println("健康检查任务线程运行");
                            Iterator iterator=taskInstanceMap.keySet().iterator();
                            while(iterator.hasNext()){
                                String temp=(String)iterator.next();
                                Future task=(Future) taskInstanceMap.get(temp.substring(0,temp.indexOf("-")-4));

                                //当注册表中没有该任务时，直接结束该任务
                                if(taskRegedit.get(temp)==null){
                                    if(task!=null) {
                                        task.cancel(true);
                                    }
                                }
                            }
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                60*10, //延迟多久执行
                60*10, //执行间隔
                TimeUnit.SECONDS
        );

        return "{\"code\":\"0\",\"msg\":\"任务调度主程序启动完成！\"}";
    }

    //结束程序
    public static String shutdown(){
        try {
            pool.shutdownNow();
            taskRegedit.clear();
            taskRegedit.clear();
        }
        catch(Exception e){
            e.printStackTrace();
            return "{\"code\":\"10000\",\"msg\":\"主调度程序结束异常！\"}";
        }
        return "{\"code\":\"0\",\"msg\":\"主调度程序结束！\"}";
    }

    public static String addTask(String jsonTaskData){
        HashMap taskData = (HashMap) JSON.Decode(jsonTaskData);
        /**
         * taskData taskType(1,2,3)  //1：临时任务  2：周期任务 3：7*24小时任务
         * taskData taskID 10000000
         * taskData taskName 测试测试
         * taskData taskBelogFlow xxxxxxx
         * taskData taskStartTime 2021-10-24 09:07:00
         * taskData taskEndTime 2021-10-24 09:07:00
         * taskData taskCrossDay 0 1
         * taskData taskCycleTime [1,2,3,4,5,6,7]
         * taskData taskCycleEndTime 2021-10-24 09:07:00
         * taskData taskIsTranscode 0 1
         * taskData taskStorageLocation
         *
         * taskData requestURL  string
         * taskData requestParam xml
         * */

        System.out.println(taskData.get("taskID"));

        //1:第一步 验证注册表中是否存在该任务
        String taskID=(String)taskRegedit.get(taskData.get("taskID"));
        if(taskID!=null && taskID.trim().length()>0){
            return "{\"code\":\"10000\",\"msg\":\"该任务已经存在！\"}";
        }

        //2:第二步 生成任务唯一性编号，生成注册表信息
        /**
         * 将任务数据写入注册表
         * 除7*24小时任务 每种任务都是两个作业，一个启动收录  一个结束收录
         * */
        taskRegedit.put(taskData.get("taskID"),taskData);

        //计算任务开始的时间和结束的时间 转换成秒 用于启动任务
        //2.0 时间转换
        Date now;
        Date start;
        Date end;
        try {
            now=new Date();
            System.out.println("dd:"+taskData.get("taskStartTime").toString());
            Date str=sdf.parse("2021-10-30 12:00:30");
            System.out.println("strr："+str);
            start= sdf.parse(taskData.get("taskStartTime").toString());
            end= sdf.parse(taskData.get("taskEndTime").toString());
            if(start.getTime()-now.getTime()<0){
                return "{\"code\":\"10000\",\"msg\":\"开始时间小于当前！\"}";
            }
            if((end.getTime()-start.getTime())<0){
                return "{\"code\":\"10000\",\"msg\":\"任务结束时间早于任务开始时间！\"}";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "{\"code\":\"10000\",\"msg\":\"时间格式化错误,时间不是正确的格式 yyyy-MM-dd HH:mm:ss！\"}";
        }

       return "{\"code\":\"0\",\"msg\":\"任务已经加入运行计划！\"}";
    }

    public static String delTask(String jsonTaskData){
        try {
            HashMap taskData = (HashMap) JSON.Decode(jsonTaskData);
            taskRegedit.remove(taskData.get("taskID"));
            String taskID=(String)taskRegedit.get(taskData.get("taskID"));
            if(taskID!=null && taskID.trim().length()>0){
                return "{\"code\":\"10000\",\"msg\":\"任务删除失败！\"}";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "{\"code\":\"10000\",\"msg\":\"任务删除遇到异常！\"}";
        }
        return "{\"code\":\"0\",\"msg\":\"任务删除成功！\"}";
    }

    private String planJobTask(HashMap taskData){
        System.out.println(taskData);
        try {
            //检查任务运行实例是否存在
            if(taskInstanceMap.get(taskData.get("taskID").toString() + sdf.format(new Date()).substring(0,10)+"start")!=null){
                //任务已经存在 正在等待或者正在运行 无需执行
                return "{\"code\":\"0\",\"msg\":\"任务已经存在 正在等待或者正在运行 无需执行！\"}";
            }
            if(taskInstanceMap.get(taskData.get("taskID").toString() + sdf.format(new Date()).substring(0,10)+"end")!=null){
                //任务已经存在 正在等待或者正在运行 无需执行
                return "{\"code\":\"0\",\"msg\":\"任务已经存在 正在等待或者正在运行 无需执行！\"}";
            }
            //检查运行结果是否存在
            String runingDate = (String)taskData.get(taskData.get("taskID").toString() + sdf.format(new Date()).substring(0,10));
            if(runingDate!=null&&runingDate.trim().length()>0){
                return "{\"code\":\"0\",\"msg\":\"任务已经执行完成，无需执行！\"}";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return "{\"code\":\"10000\",\"msg\":\"任务实例检查异常！\"}";
        }

        //计算任务开始的时间和结束的时间 转换成秒 用于启动任务
        //2.0 时间转换
        Date now;
        Date start;
        Date end;
        Date taskCycleEndTime;
        try {
            now=new Date();
            start= sdf.parse(taskData.get("taskStartTime").toString());
            end= sdf.parse(taskData.get("taskEndTime").toString());
            taskCycleEndTime= sdf.parse(taskData.get("taskEndTime").toString());
            if (taskCycleEndTime.before(now)){
                return "{\"code\":\"0\",\"msg\":\"已经超过任务执行周期，无需执行！\"}";
            }
            if(start.getTime()-now.getTime()<0){
                return "{\"code\":\"10000\",\"msg\":\"开始时间小于当前！\"}";
            }
            if((end.getTime()-start.getTime())<0){
                return "{\"code\":\"10000\",\"msg\":\"任务结束时间早于任务开始时间！\"}";
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return "{\"code\":\"10000\",\"msg\":\"时间格式化错误！\"}";
        }

        try {
            //2.1 一次性任务
            if(taskData.get("taskType").equals("1")) {
                //收录启动任务线程
                createTaskJob("start",taskData.get("taskID").toString() + sdf.format(now).substring(0,10)+ "_start", taskData, start.getTime() - now.getTime());
                //收录结束任务线程
                createTaskJob("stop",taskData.get("taskID").toString() + sdf.format(now).substring(0,10) + "_end", taskData, end.getTime() - now.getTime());
            }
            //2.2 周期性任务
            else if(taskData.get("taskType").equals("2")) {
                String taskCycleTime=(String)taskData.get("taskCycleTime");
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);
                day= day-1==0?day=7:day-1;
                if(taskCycleTime.charAt(day)>0) {
                    //收录启动任务线程
                    createTaskJob("start",taskData.get("taskID").toString() + sdf.format(now).substring(0,10) + "_start", taskData, start.getTime() - now.getTime());
                    //收录结束任务线程
                    createTaskJob("stop",taskData.get("taskID").toString() + sdf.format(now).substring(0,10) + "_end", taskData, end.getTime() - now.getTime());
                }
            }
            //2.3 7*24小时任务
            else if(taskData.get("taskType").equals("3")) {
                //收录启动任务线程
                createTaskJob("Start",taskData.get("taskID").toString() + sdf.format(now).substring(0,10) + "_start", taskData, start.getTime() - now.getTime());
            }
            else{
                return "{\"code\":\"10000\",\"msg\":\"不支持的任务类型！\"}";
             }
            taskData.put(taskData.get("taskID").toString() + sdf.format(now).substring(0,10) ,"{\"state\":\"0\",\"remark\":\"等待运行\"}");
            taskRegedit.put(taskData.get("taskID").toString(),taskData);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"10000\",\"msg\":\"任务创建错误！\"}";
        }
        return "{\"code\":\"0\",\"msg\":\"任务创建完成！\"}";
    }

    private String createTaskJob(String flag,String jobKey,HashMap taskData,long delaySecond){
        Future future = pool.schedule(
                new Runnable() {
                    public void run() {
                        try {
                            /**
                             * 线程调用的录制或者结束录制接口部分
                             * 现在是什么都没做
                             * */
                            String startUrl=taskData.get("recruitStartUrl")+"";
                            String stopUrl=taskData.get("recruitStopUrl")+"";
                            String startXml=taskData.get("startXml")+"";
                            String stopXml=taskData.get("stopXml")+"";
                            String url="";
                            String xml="";
                            if("start".equals(flag)){
                                url=startUrl;
                                xml=startXml;
                            }
                            if("stop".equals(flag)){
                                url=stopUrl;
                                xml=stopXml;
                            }
                            HashMap map=HttpRequest.sendPostXml(url,xml);
                            System.out.println("result:"+map);
                            taskData.put(
                                    taskData.get("taskID").toString() + sdf.format(new Date()).substring(0, 10),
                                    "{\"state\":\"1\",\"remark\":\"结束运行\"}"
                            );
                            taskRegedit.put(taskData.get("taskID").toString() ,taskData);
                            System.out.println(taskData.get("taskID").toString() + " 运行时间: " + sdf.format(new Date()));
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                        ///执行完成后从执行实例池移除
                        try{
                            taskInstanceMap.remove(jobKey);
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                delaySecond/1000,
                TimeUnit.SECONDS
        );
        taskInstanceMap.put(jobKey, future);
        return "{\"code\":\"0\",\"runingTime\":\""+taskData.get("taskID").toString() + sdf.format(new Date())+"\",\"msg\":\"等待运行！\"}";
    }

    //配置信息
    private int poolSize;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int httpConnectionPoolSize;
    private int httpDefaultMaxPerRoute;
    private int httpSocketTimeout;
    private int httpConnectTimeout;
    private int httpConnectionRequestTimeout;

    /***
     * 执行任务所需要的资源
     * */
    //工作线程池
    private static ScheduledExecutorService pool;
    //注册信息表 考虑到扩展性，最好使用redis Zset
    private static ConcurrentHashMap taskRegedit;
    //运行任务线程的实例
    private static ConcurrentHashMap taskInstanceMap;
    //任务中访问网址需要的HTTP资源池
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        //设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(httpConnectionPoolSize);
        //路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(httpDefaultMaxPerRoute);
        RequestConfig requestConfig = RequestConfig.custom()
                //服务器返回数据(response)的时间，超过该时间抛出read timeout
                .setSocketTimeout(httpSocketTimeout)
                //连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                .setConnectTimeout(httpConnectTimeout)
                //从连接池中获取连接的超时时间，超过该时间未拿到可用连接，
                // 会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .setConnectionRequestTimeout(httpConnectionRequestTimeout)
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }
}

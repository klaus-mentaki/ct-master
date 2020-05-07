package producer;

import java.io.*;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ProductLog {
    //设置电话时间呼叫范围
    private String startTime = "2018-01-01";
    private String endTime = "2018-12-01";

    //生产数据
    //用于存放随机的电话号码
    private Map<String, String> phoneNameMap = new HashMap<>();
    private List<String> phoneList = new ArrayList<>();

    public void initPhone() {
        phoneList.add("15369468720");
        phoneList.add("19920860202");
        phoneList.add("18411925860");
        phoneList.add("14473548449");
        phoneList.add("18749966182");
        phoneList.add("19379884788");
        phoneList.add("19335715448");
        phoneList.add("18503558939");
        phoneList.add("13407209608");
        phoneList.add("15596505995");
        phoneList.add("17519874292");
        phoneList.add("15178485516");
        phoneList.add("19877232369");
        phoneList.add("18706287692");
        phoneList.add("18944239644");
        phoneList.add("17325302007");
        phoneList.add("18839074540");
        phoneList.add("19879419704");
        phoneList.add("16480981069");
        phoneList.add("18674257265");
        phoneList.add("18302820904");
        phoneList.add("15133295266");
        phoneList.add("17868457605");
        phoneList.add("15490732767");
        phoneList.add("15064972307");

        phoneNameMap.put("15369468720", "李雁");
        phoneNameMap.put("19920860202", "卫艺");
        phoneNameMap.put("18411925860", "仰莉");
        phoneNameMap.put("14473548449", "陶欣悦");
        phoneNameMap.put("18749966182", "施梅梅");
        phoneNameMap.put("19379884788", "金虹霖");
        phoneNameMap.put("19335715448", "魏明艳");
        phoneNameMap.put("18503558939", "华贞");
        phoneNameMap.put("13407209608", "华啟倩");
        phoneNameMap.put("15596505995", "仲采绿");
        phoneNameMap.put("17519874292", "卫丹");
        phoneNameMap.put("15178485516", "戚丽红");
        phoneNameMap.put("19877232369", "何翠柔");
        phoneNameMap.put("18706287692", "钱溶艳");
        phoneNameMap.put("18944239644", "钱琳");
        phoneNameMap.put("17325302007", "缪静欣");
        phoneNameMap.put("18839074540", "焦秋菊");
        phoneNameMap.put("19879419704", "吕访琴");
        phoneNameMap.put("16480981069", "沈丹");
        phoneNameMap.put("18674257265", "褚美丽");
        phoneNameMap.put("18302820904", "孙怡");
        phoneNameMap.put("15133295266", "许婵");
        phoneNameMap.put("17868457605", "曹红恋");
        phoneNameMap.put("15490732767", "吕柔");
        phoneNameMap.put("15064972307", "冯怜云");
    }

    /**
     * 生产数据
     * 形式：15064972307，17868457605，2019-09-28 13:28:29 0223
     */
    public String product() {
        String callerNum = null;
        String calleeNum = null;

        String callerName = null;
        String calleeName = null;

        //取主叫电话号码
        int callerIndex = (int) (Math.random() * phoneList.size());
        callerNum = phoneList.get(callerIndex);
        callerName = phoneNameMap.get(callerNum);

        //取被叫电话号码
        while (true) {
            int calleeIndex = (int) (Math.random() * phoneList.size());
            calleeNum = phoneList.get(calleeIndex);
            calleeName = phoneNameMap.get(calleeNum);
            if (!callerNum.equals(calleeNum)) break;
        }
        //通过随机时间，获取通话记录
        String buildTime = randomBuildTime(startTime, endTime);
        //0000
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        String duration = decimalFormat.format((int) (30 * 60 * Math.random()));

        //System.out.println(callerNum + "," + callerName + "," + calleeNum + "," + calleeName + "," + buildTime + "," + duration);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(callerNum + ",").append(callerName + ",").append(buildTime + ",").append(duration);

        return stringBuilder.toString();
    }

    /**
     * 根据传入时间区间，随机建立通话时间, 时间格式：yyyy-MM-dd HH:MM:SS
     * startTimeTS + （endTimeTS - startTimeTs) * Math.random();
     */
    public String randomBuildTime(String startTime, String endTime) {
        try {
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = simpleDateFormat1.parse(startTime);
            Date endDate = simpleDateFormat1.parse(endTime);

            //容错处理 结束时间要大于开始时间
            if (endDate.getTime() < startDate.getTime()) return null;

            //随机时间
            long randomTS = startDate.getTime() +(long) ((endDate.getTime() - startDate.getTime()) * Math.random());
            Date resultDate = new Date(randomTS);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String resultDateString = simpleDateFormat2.format(resultDate);
            //返回通话建立时间
            return resultDateString;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将数据写入到文件流
     * 输出的时候直接编码，所以用字符流
     */
    public void writeLog(String filePath) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8");

            while(true){
                Thread.sleep(300);
                String log = product();
                System.out.println(log);
                outputStreamWriter.write(log+"\n");
                //手动flush，确保每条数据都写入文件
                outputStreamWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e2){
            e2.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args==null||args.length<=0){
            System.out.println("没有参数");
            return;
        }
        ProductLog productLog = new ProductLog();
        productLog.initPhone();
        /*while (true) {
            Thread.sleep(300);
            productLog.product();
        }*/
        /*String logPath = "C:\\work\\calllog.csv";
        productLog.writeLog(logPath);*/
        productLog.writeLog(args[0]);


    }
}

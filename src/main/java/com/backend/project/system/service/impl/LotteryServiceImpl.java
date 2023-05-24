package com.backend.project.system.service.impl;

import com.backend.common.utils.DateUtils;
import com.backend.project.system.domain.LotteryData;
import com.backend.project.system.domain.NumberAnalyze;
import com.backend.project.system.mapper.LotteryDataMapper;
import com.backend.project.system.schedule.LotterySchedule;
import com.backend.project.system.service.ILotteryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 *
 *
 * @author
 */
@Service
public class LotteryServiceImpl implements ILotteryService {

    @Resource
    private LotteryDataMapper lotteryDataMapper;


    @Override
    public List<Map.Entry<String, Integer>> getNumbers(int hour) throws Exception {
        String startTime = DateUtils.getOnePointTime(-hour);
        String endTime = DateUtils.getDate1();
        List<LotteryData> lotteryDatas = lotteryDataMapper.selectLotteryDataListByTime(startTime, endTime);
        NumberAnalyze numberAnalyze = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
        for(LotteryData lotteryData:lotteryDatas) {
            String preDrawCode = lotteryData.getPreDrawCode();
            char[] chars = preDrawCode.toCharArray();
            for (int i = 0; i < LotterySchedule.lengthControl; i++) {
                char var = chars[i];
                int number = Integer.parseInt(String.valueOf(var));
                switch (number) {
                    case 0:
                        numberAnalyze.setZero(numberAnalyze.getZero() + 1);
                        continue;
                    case 1:
                        numberAnalyze.setOne(numberAnalyze.getOne() + 1);
                        continue;
                    case 2:
                        numberAnalyze.setTwo(numberAnalyze.getTwo() + 1);
                        continue;
                    case 3:
                        numberAnalyze.setThree(numberAnalyze.getThree() + 1);
                        continue;
                    case 4:
                        numberAnalyze.setFour(numberAnalyze.getFour() + 1);
                        continue;
                    case 5:
                        numberAnalyze.setFive(numberAnalyze.getFive() + 1);
                        continue;
                    case 6:
                        numberAnalyze.setSix(numberAnalyze.getSix() + 1);
                        continue;
                    case 7:
                        numberAnalyze.setSeven(numberAnalyze.getSeven() + 1);
                        continue;
                    case 8:
                        numberAnalyze.setEight(numberAnalyze.getEight() + 1);
                        continue;
                    case 9:
                        numberAnalyze.setNine(numberAnalyze.getNine() + 1);
                        continue;
                    default:
                        continue;
                }
            }
        }

        List<Map.Entry<String,Integer>> list = sortData(numberAnalyze);

        return list;
    }

    /**
     * 近期x注数据预测 - 横向
     * @param limit
     * @return
     * @throws Exception
     */
    @Override
    public List<Map.Entry<String, Integer>> getNumbers1(int limit) throws Exception {
        List<LotteryData> lotteryDatas = lotteryDataMapper.selectListByPreDrawIssue(limit);
        NumberAnalyze numberAnalyze = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
        for(LotteryData lotteryData : lotteryDatas) {
            String preDrawCode = lotteryData.getPreDrawCode();
            char[] chars = preDrawCode.toCharArray();
            for (int i = 0; i < LotterySchedule.lengthControl; i++) {
                char var = chars[i];
                int number = Integer.parseInt(String.valueOf(var));
                extracted(numberAnalyze, number);
            }
        }

        List<Map.Entry<String,Integer>> list = sortData(numberAnalyze);
        return list;
    }

    private List<Map.Entry<String,Integer>> sortData(NumberAnalyze numberAnalyze) {
        Map<String, Integer> map = new HashMap<>();
        map.put("0", numberAnalyze.getZero());
        map.put("1", numberAnalyze.getOne());
        map.put("2", numberAnalyze.getTwo());
        map.put("3", numberAnalyze.getThree());
        map.put("4", numberAnalyze.getFour());
        map.put("5", numberAnalyze.getFive());
        map.put("6", numberAnalyze.getSix());
        map.put("7", numberAnalyze.getSeven());
        map.put("8", numberAnalyze.getEight());
        map.put("9", numberAnalyze.getNine());

        //创建list保存map中的key-value对，通过map0.entrySet()获取map中的key-value对
        List<Map.Entry<String,Integer>> list = new LinkedList<>(map.entrySet());

        //利用Collections接口的sort函数对list进行降序排序，需要实现Comparator接口的compare方法
        Collections.sort(list, new Comparator(){
            public int compare(Object o1, Object o2){
                //Comparable接口中的compareTo方法
                //A.compareTo(B): A > B 返回正整数；A < B 返回负整数；相等，返回 0

                //Comparator接口中的compare方法
                //返回正整数: 升序；返回负整数: 降序
                return -((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
            }
        });
        return list;
    }

    /**
     * 近期x注数据预测 - 竖向
     * @param limit
     * @return
     * @throws Exception
     */
    public List<String> getNumbers2(int limit) throws Exception {
        List<LotteryData> lotteryDatas = lotteryDataMapper.selectListByPreDrawIssue(limit);
        NumberAnalyze numberAnalyze0 = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
        NumberAnalyze numberAnalyze1 = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
        NumberAnalyze numberAnalyze2 = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
        NumberAnalyze numberAnalyze3 = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
        NumberAnalyze numberAnalyze4 = new NumberAnalyze(0,0,0,0,0,0,0,0,0,0);
        for(LotteryData lotteryData : lotteryDatas) {
            String preDrawCode = lotteryData.getPreDrawCode();
            char[] chars = preDrawCode.toCharArray();
            for (int i = 0; i < LotterySchedule.lengthControlSingle; i++) {
                char var = chars[i];
                int number = Integer.parseInt(String.valueOf(var));
                if (i == 0) {
                    extracted(numberAnalyze0, number);
                } else if (i == 1) {
                    extracted(numberAnalyze1, number);
                } else if (i == 2) {
                    extracted(numberAnalyze2, number);
                } else if (i == 3) {
                    extracted(numberAnalyze3, number);
                } else if (i == 4) {
                    extracted(numberAnalyze4, number);
                }
            }
        }

        List<Map.Entry<String,Integer>> list0 = sortData(numberAnalyze0);
        List<Map.Entry<String,Integer>> list1 = sortData(numberAnalyze1);
        List<Map.Entry<String,Integer>> list2 = sortData(numberAnalyze2);
        List<Map.Entry<String,Integer>> list3 = sortData(numberAnalyze3);
        List<Map.Entry<String,Integer>> list4 = sortData(numberAnalyze4);

        List<String> numbers = new ArrayList<>();
        String number1 = list0.get(0).getKey() + list0.get(1).getKey() + list0.get(2).getKey() + list0.get(3).getKey();
        String number2 = list1.get(0).getKey() + list1.get(1).getKey() + list1.get(2).getKey() + list1.get(3).getKey();
        String number3 = list2.get(0).getKey() + list2.get(1).getKey() + list2.get(2).getKey() + list2.get(3).getKey();
        String number4 = list3.get(0).getKey() + list3.get(1).getKey() + list3.get(2).getKey() + list3.get(3).getKey();
        String number5 = list4.get(0).getKey() + list4.get(1).getKey() + list4.get(2).getKey() + list4.get(3).getKey();
        numbers.add(number1);
        numbers.add(number2);
        numbers.add(number3);
        numbers.add(number4);
        numbers.add(number5);

        return numbers;
    }

    /**
     * 数据计入对象值
     * @param numberAnalyze
     * @param number
     */
    private void extracted(NumberAnalyze numberAnalyze, int number) {
        switch (number) {
            case 0:
                numberAnalyze.setZero(numberAnalyze.getZero() + 1);
                return;
            case 1:
                numberAnalyze.setOne(numberAnalyze.getOne() + 1);
                return;
            case 2:
                numberAnalyze.setTwo(numberAnalyze.getTwo() + 1);
                return;
            case 3:
                numberAnalyze.setThree(numberAnalyze.getThree() + 1);
                return;
            case 4:
                numberAnalyze.setFour(numberAnalyze.getFour() + 1);
                return;
            case 5:
                numberAnalyze.setFive(numberAnalyze.getFive() + 1);
                return;
            case 6:
                numberAnalyze.setSix(numberAnalyze.getSix() + 1);
                return;
            case 7:
                numberAnalyze.setSeven(numberAnalyze.getSeven() + 1);
                return;
            case 8:
                numberAnalyze.setEight(numberAnalyze.getEight() + 1);
                return;
            case 9:
                numberAnalyze.setNine(numberAnalyze.getNine() + 1);
                return;
            default:
                return;
        }
    }

}

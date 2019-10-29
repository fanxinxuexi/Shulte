package project.myshulteproject.com.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import project.myshulteproject.com.adapter.MyAdapter;
import project.myshulteproject.com.R;
import project.myshulteproject.com.util.TableBorderLayout;

public class ClickInterfaceActivity extends AppCompatActivity {
    private int type;
    private TableBorderLayout tableBorderLayout;
    private List<Integer> clickNumberList = new ArrayList<>();//需要点击的顺序
    private List<Integer> numberList = new ArrayList<>();//显示的数据，需要打乱的
    private int maxNumber = 0;//为最大值
    private int starCountAdd = 1;
    private int[] prime_number = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
    private MyAdapter myAdapter;
    private int mlCount;
    private int sec;
    private String time;
    private Timer timer = null;
    private TimerTask task = null;
    private Message msg = null;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefData_time;
    private String Sudoku_mode;
    private int clickNumber = 0;
    private boolean whetherColor = false;
    private List<Integer> isR = new ArrayList<>();
    private List<Integer> isG = new ArrayList<>();
    private List<Integer> isB = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_interface);
        //type 主要作用每行的个数， 以及方格的总个数
        type = getIntent().getIntExtra("tv_mode", -1);
        Sudoku_mode = getIntent().getStringExtra("Sudoku_mode");
        whetherColor = getIntent().getBooleanExtra("whetherColor", false);

        //实例化和获取 本地的 data-time time表示最好的成绩
        editor = getSharedPreferences("history_data", MODE_PRIVATE).edit();
        prefData_time = getSharedPreferences("history_data", MODE_PRIVATE);


        //初始化view
        initViews();

        if (whetherColor == true) {
            setColor();
        }

        //设置数据
        setDatas();
        //设置adapter
        LayoutSetAdapter();
        startTime();


    }

    //设置颜色数据
    private void setColor() {
        isR.clear();
        isG.clear();
        isB.clear();
        for (int i = 40; i <= 255; i++) {
            isR.add(i);
            isG.add(i);
            isB.add(i);
        }
        //打乱数字
        Collections.shuffle(isR);
        Collections.shuffle(isG);
        Collections.shuffle(isB);
    }

    //结束时间
    private void stopTime() {
        mlCount = 0;
        sec = 0;
        task.cancel();
        timer.cancel();
        timer.purge();
        handler.removeMessages(msg.what);
    }

    //开始时间
    private void startTime() {
        task = new TimerTask() {
            @Override
            public void run() {
                if (msg == null) {
                    msg = new Message();
                } else {
                    msg = Message.obtain();
                }
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 1);
    }

    //初始化view
    private void initViews() {
        tableBorderLayout = findViewById(R.id.layTable);
    }

    //设置adapter
    private void LayoutSetAdapter() {
        if (whetherColor == true) {
            myAdapter = new MyAdapter(this, numberList, clickItem, type, isR, isG, isB);
            tableBorderLayout.setAdapter(myAdapter, type);
        } else {
            myAdapter = new MyAdapter(this, numberList, clickItem, type);
            tableBorderLayout.setAdapter(myAdapter, type);
        }
    }

    //设置数据
    private void setDatas() {
        numberList.clear();
        clickNumberList.clear();

        if (Sudoku_mode.equals("normalMode")) {//普通模式：只用计算数量
            //初始化格数
            maxNumber = type * type;
            //遍历所有的数字，添加进集合
            for (int i = 1; i <= maxNumber; i++) {
                numberList.add(i);
                clickNumberList.add(i);

            }
        } else if (Sudoku_mode.equals("PrimeNumberMode")) {
            maxNumber = prime_number.length;
            //遍历所有的数字，添加进集合
            for (int i = 0; i < prime_number.length; i++) {
                numberList.add(prime_number[i]);
                clickNumberList.add(prime_number[i]);
            }
        } else if (Sudoku_mode.equals("OddNumberMode")) {
            maxNumber = type * type;
            for (int i = 1; i < 100; i += 2) {
                if (numberList.size() == maxNumber) break;
                numberList.add(i);
                clickNumberList.add(i);
            }
        }

        //打乱数字
        Collections.shuffle(numberList);
    }

    //处理数字的点击事件
    public View.OnClickListener clickItem = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if ((clickNumber + 1) == maxNumber) {
                //1、结束之后需要和本地数据对比，小于本地的就是新纪录
                //2、当点击非正确数值时，要有提醒正确数值
                String dialogMessage = "";//需要显示的提示
                String dataTime = "";//本次点击的时间
                String historyTime = "";//历史点击的时间
                clickNumber = 0;
                /*
                time.compareTo(historyTime)

                1、字符串相等返回0
                2、前字符串 大于后 字符串 返回正值
                3、前字符串 小于 后字符串 返回负值
                4、如果双方字符串都相等，就会开始比较长度
                */
                //把完成的秒速存下来, 与本地以保存的秒速做对比
                //先找到是哪种模式，在找是几成几的
                if (Sudoku_mode.equals("normalMode")) {//普通模式
                    if (type == 3) {
                        dataTime = "normalMode_data-time3";
                    } else if (type == 5) {
                        dataTime = "normalMode_data-time5";
                    } else if (type == 7) {
                        dataTime = "normalMode_data-time7";
                    } else if (type == 9) {
                        dataTime = "normalMode_data-time9";
                    }
                } else if (Sudoku_mode.equals("PrimeNumberMode")) {//素数模式
                    dataTime = "PrimeNumberMode_data-time5";
                } else if (Sudoku_mode.equals("OddNumberMode")) {//奇数模式
                    if (type == 3) {
                        dataTime = "OddNumberMode_data-time3";
                    } else if (type == 5) {
                        dataTime = "OddNumberMode_data-time5";
                    } else if (type == 7) {
                        dataTime = "OddNumberMode_data-time7";
                    }
                }


                historyTime = prefData_time.getString(dataTime, "");
                if (historyTime != null && historyTime.length() > 0) {//如果为空，就是本地没有数据
                    //对比本次时间和保存的时间
                    int contrast = time.compareTo(historyTime);
                    if (contrast < 0) { //小于表示 time 没 historyTime 大
                        editor.putString(dataTime, time);
                        dialogMessage = "恭喜，产生新纪录：" + time + "\n上次记录为：" + historyTime;
                        starCountAdd = 3;
                    } else {
                        dialogMessage = "耗时：" + time + "\n历史最快记录为：" + historyTime;
                        starCountAdd = 1;
                    }
                } else {
                    editor.putString(dataTime, time);
                    dialogMessage = "恭喜，产生新纪录：" + time;
                    starCountAdd = 3;
                }

                int oldcount = prefData_time.getInt("star_count",0);
                int allCount = oldcount + starCountAdd;
                editor.putInt("star_count", allCount);

                editor.apply();

                Toast.makeText(ClickInterfaceActivity.this, "本次游戏积累"+ starCountAdd +"颗星", Toast.LENGTH_SHORT).show();

                view.setBackgroundColor(getResources().getColor(R.color.color_gray_9999));
                view.setOnClickListener(null);
                stopTime();
                new AlertDialog.Builder(ClickInterfaceActivity.this)
                        .setMessage(dialogMessage)
                        .setPositiveButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ClickInterfaceActivity.this, SelectionModeActivity.class);
                                startActivity(intent);
                                ClickInterfaceActivity.this.finish();
                            }
                        })
                        .setNegativeButton("重新开始", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setDatas();
                                ClickInterfaceActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tableBorderLayout.setAdapter(new MyAdapter(ClickInterfaceActivity.this, numberList, clickItem, type), type);
                                        startTime();
                                    }
                                });
                            }
                        })
                        .setCancelable(false) //点击对话框外部是否关闭
                        .show();

            } else if (clickNumberList.get(clickNumber) == view.getId()) {
                view.setBackgroundDrawable(getResources().getDrawable(R.color.color_gray_9999));
                view.setOnClickListener(null);
                clickNumber++;
            }
        }
    };


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mlCount++;
                    int tatalSec = 0;
                    int msec = 0;
                    //精确到0.01秒
                    tatalSec = (int) (mlCount / 1000);
                    msec = (int) (mlCount % 1000);//毫秒
                    sec = (tatalSec % 1000);//秒

                    try {
                        time = String.format("%1$02d:%2$03d", sec, msec);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        stopTime();
        finish();
    }
}

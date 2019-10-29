package project.myshulteproject.com.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import project.myshulteproject.com.R;
import project.myshulteproject.com.adapter.MyAdapter;
import project.myshulteproject.com.util.ExpandLayout;

import static android.content.Context.MODE_PRIVATE;

public class SelectionModeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_h_3X3, tv_h_5X5, tv_h_7X7, tv_h_9X9, tv_prime_5X5, tv_odd_3X3, tv_odd_5X5, tv_odd_7X7;
    private LinearLayout rl_left_layout, ll_my_history;
    private ScrollView sl_introduce;
    private ImageView iv_menu, iv_menu_history;
    private ExpandLayout el_normal, el_prime_number, el_odd_number;
    private TextView tv_mode_3, tv_mode_5, tv_mode_7, tv_mode_9, tv_start, tv_history, tv_introduce;
    private TextView tv_normal, tv_prime_number, tv_odd_number, tv_delete_All_data;
    private TextView tv_prime_number_5, tv_odd_number_3, tv_odd_number_5, tv_odd_number_7;
    private TextView tv_star;
    private int type = 5;
    private int countdown = 4;
    private int starCount = 0;
    private DrawerLayout drawerLayout;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefData_type;
    private SharedPreferences preferences;
    private TextView tv_set_color_mode;

    private static boolean isExit = false;
    private static final String Normal_Mode = "normalMode";
    private static final String Prime_Number_Mode = "PrimeNumberMode";
    private static final String Odd_Number_Mode = "OddNumberMode";
    private static final String Color_Mode = "ColorMode";
    private String Sudoku_mode = "";
    private boolean whetherColor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_mode);

        //历史的存储
        preferences = getSharedPreferences("history_data", MODE_PRIVATE);

        //积累星数
        starCount = preferences.getInt("star_count",0);


        //实例化和获取 本地的 data-type type表示上一次选择的模式
        editor = getSharedPreferences("data-type", MODE_PRIVATE).edit();
        prefData_type = getSharedPreferences("data-type", MODE_PRIVATE);//类型和个数
        whetherColor = prefData_type.getBoolean("whether_Color", false);
        type = prefData_type.getInt("type", 0);
        Sudoku_mode = prefData_type.getString("mode_type", "");

        if (type == 0 && Sudoku_mode.isEmpty()) {
            type = 5;
            Sudoku_mode = Normal_Mode;
        }

        initView();
        setView();

    }

    private void initView() {
        tv_set_color_mode = findViewById(R.id.tv_set_color_mode);
        tv_odd_number_7 = findViewById(R.id.tv_odd_number_7);
        tv_odd_number_5 = findViewById(R.id.tv_odd_number_5);
        tv_odd_number_3 = findViewById(R.id.tv_odd_number_3);
        el_odd_number = findViewById(R.id.el_odd_number);
        tv_odd_number = findViewById(R.id.tv_odd_number);
        tv_delete_All_data = findViewById(R.id.tv_delete_All_data);
        tv_prime_number_5 = findViewById(R.id.tv_prime_number_5);

        el_prime_number = findViewById(R.id.el_prime_number);
        tv_prime_number = findViewById(R.id.tv_prime_number);
        tv_normal = findViewById(R.id.tv_normal);
        el_normal = findViewById(R.id.el_normal);
        rl_left_layout = findViewById(R.id.rl_left_layout);
        ll_my_history = findViewById(R.id.ll_my_history);
        sl_introduce = findViewById(R.id.sl_introduce);
        iv_menu = findViewById(R.id.iv_menu);
        iv_menu_history = findViewById(R.id.iv_menu_history);
        tv_introduce = findViewById(R.id.tv_introduce);
        tv_history = findViewById(R.id.tv_history);
        drawerLayout = findViewById(R.id.dl_deaLayout);
        tv_mode_3 = findViewById(R.id.tv_mode_3);
        tv_mode_5 = findViewById(R.id.tv_mode_5);
        tv_mode_7 = findViewById(R.id.tv_mode_7);
        tv_mode_9 = findViewById(R.id.tv_mode_9);
        tv_start = findViewById(R.id.tv_start);

        tv_h_3X3 = findViewById(R.id.tv_h_3X3);
        tv_h_5X5 = findViewById(R.id.tv_h_5X5);
        tv_h_7X7 = findViewById(R.id.tv_h_7X7);
        tv_h_9X9 = findViewById(R.id.tv_h_9X9);
        tv_prime_5X5 = findViewById(R.id.tv_prime_5X5);
        tv_odd_3X3 = findViewById(R.id.tv_odd_3X3);
        tv_odd_5X5 = findViewById(R.id.tv_odd_5X5);
        tv_odd_7X7 = findViewById(R.id.tv_odd_7X7);

        tv_star = findViewById(R.id.tv_star);
        tv_star.setText("目前积累" + starCount +"颗星");
    }

    private void setView() {
        tv_set_color_mode.setOnClickListener(this);
        tv_delete_All_data.setOnClickListener(this);
        tv_odd_number_7.setOnClickListener(this);
        tv_odd_number_5.setOnClickListener(this);
        tv_odd_number_3.setOnClickListener(this);
        tv_odd_number.setOnClickListener(this);
        tv_history.setOnClickListener(this);
        tv_introduce.setOnClickListener(this);
        tv_mode_3.setOnClickListener(this);
        tv_mode_5.setOnClickListener(this);
        tv_mode_7.setOnClickListener(this);
        tv_mode_9.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        iv_menu.setOnClickListener(this);
        iv_menu_history.setOnClickListener(this);
        tv_normal.setOnClickListener(this);
        tv_prime_number.setOnClickListener(this);
        el_prime_number.setOnClickListener(this);
        tv_prime_number_5.setOnClickListener(this);

        el_normal.initExpand(false);
        el_prime_number.initExpand(false);
        el_odd_number.initExpand(false);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mode_3:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED); //在倒数的时候，禁止侧边滑动
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 3;
                Sudoku_mode = Normal_Mode;
                break;
            case R.id.tv_mode_5:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 5;
                Sudoku_mode = Normal_Mode;
                break;
            case R.id.tv_mode_7:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 7;
                Sudoku_mode = Normal_Mode;
                break;
            case R.id.tv_mode_9:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 9;
                Sudoku_mode = Normal_Mode;
                break;
            case R.id.tv_prime_number_5:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 5;
                Sudoku_mode = Prime_Number_Mode;
                break;
            case R.id.tv_odd_number_3:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 3;
                Sudoku_mode = Odd_Number_Mode;
                break;
            case R.id.tv_odd_number_5:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 5;
                Sudoku_mode = Odd_Number_Mode;
                break;
            case R.id.tv_odd_number_7:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                type = 7;
                Sudoku_mode = Odd_Number_Mode;
                break;
            case R.id.tv_start:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                handler.sendMessageDelayed(handler.obtainMessage(1), 0);
                break;
            case R.id.tv_history:

                ll_my_history.setVisibility(View.VISIBLE);
                drawerLayout.closeDrawers();//关闭侧滑
                sl_introduce.setVisibility(View.GONE);
                setHistoryData();
                break;
            case R.id.tv_introduce:
                sl_introduce.setVisibility(View.VISIBLE);
                drawerLayout.closeDrawers();//关闭侧滑
                ll_my_history.setVisibility(View.GONE);
                break;
            case R.id.iv_menu:
            case R.id.iv_menu_history:
                drawerLayout.openDrawer(rl_left_layout);//打开侧滑
                break;
            case R.id.tv_normal:
                el_normal.toggleExpand();
                break;
            case R.id.tv_prime_number:
                el_prime_number.toggleExpand();
                break;
            case R.id.tv_odd_number:
                el_odd_number.toggleExpand();
                break;
            case R.id.tv_set_color_mode:
                String stringMessage = null;
                String positiveButton = null;
                if (!whetherColor){
                    stringMessage = "是否开启色彩，方格底色将会带有颜色";
                    positiveButton = "开启";
                }else {
                    stringMessage = "是否关闭色彩，方格底色将会恢复白色";
                    positiveButton = "关闭";
                }
                new AlertDialog.Builder(SelectionModeActivity.this)
                        .setMessage("提示：\n" + stringMessage)
                        .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (whetherColor){
                                    whetherColor = false;
                                }else {
                                    whetherColor = true;
                                }

                                editor.putBoolean("whether_Color",whetherColor);
                                editor.apply();
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();

                break;
            case R.id.tv_delete_All_data:
                new AlertDialog.Builder(SelectionModeActivity.this)
                        .setMessage("提示：\n" + "将会删除存储在本地的记录，所有模式记录都将清零。（需要刷新记录界面）")
                        .setPositiveButton("删除缓存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SelectionModeActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        editor.clear().commit();

                                        preferences.edit().clear().commit();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
                break;
        }
        editor.putInt("type", type);
        editor.putString("mode_type", Sudoku_mode);
        editor.apply();
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    sl_introduce.setVisibility(View.GONE);
                    ll_my_history.setVisibility(View.GONE);
                    tv_start.setOnClickListener(null);
                    iv_menu.setOnClickListener(null);
                    countdown--;
                    if (countdown > 0) {
                        tv_start.setText("" + countdown);
                        handler.sendMessageDelayed(handler.obtainMessage(1), 800);
                    } else {
                        Intent intentStart = new Intent(SelectionModeActivity.this, ClickInterfaceActivity.class);
                        intentStart.putExtra("tv_mode", type);
                        intentStart.putExtra("Sudoku_mode", Sudoku_mode);
                        intentStart.putExtra("whetherColor", whetherColor);
                        startActivity(intentStart);
                        countdown = 4;
                        tv_start.setOnClickListener(SelectionModeActivity.this);
                        iv_menu.setOnClickListener(SelectionModeActivity.this);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //允许侧边滑动
        if (drawerLayout != null) {
            tv_start.setText("开始");
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    //键盘返回键监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //处理键盘事件
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(SelectionModeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };


    //读取缓存中的历史记录
    private void setHistoryData() {
        String data_time3 = preferences.getString("normalMode_data-time3", "");
        if (data_time3.length() > 0) {
            tv_h_3X3.setText("" + data_time3);
        } else {
            tv_h_3X3.setText("暂无记录");
        }

        String data_time5 = preferences.getString("normalMode_data-time5", "");
        if (data_time5.length() > 0) {
            tv_h_5X5.setText("" + data_time5);
        } else {
            tv_h_5X5.setText("暂无记录");
        }

        String data_time7 = preferences.getString("normalMode_data-time7", "");
        if (data_time7.length() > 0) {
            tv_h_7X7.setText("" + data_time7);
        } else {
            tv_h_7X7.setText("暂无记录");
        }

        String data_time9 = preferences.getString("normalMode_data-time9", "");
        if (data_time9.length() > 0) {
            tv_h_9X9.setText("" + data_time9);
        } else {
            tv_h_9X9.setText("暂无记录");
        }

        String data_Prime5 = preferences.getString("PrimeNumberMode_data-time5", "");
        if (data_Prime5.length() > 0) {
            tv_prime_5X5.setText("" + data_Prime5);
        } else {
            tv_prime_5X5.setText("暂无记录");
        }

        String tv_odd3 = preferences.getString("OddNumberMode_data-time3", "");
        if (tv_odd3.length() > 0) {
            tv_odd_3X3.setText("" + tv_odd3);
        } else {
            tv_odd_3X3.setText("暂无记录");
        }

        String tv_odd5 = preferences.getString("OddNumberMode_data-time5", "");
        if (tv_odd5.length() > 0) {
            tv_odd_5X5.setText("" + tv_odd5);
        } else {
            tv_odd_5X5.setText("暂无记录");
        }

        String tv_odd7 = preferences.getString("OddNumberMode_data-time7", "");
        if (tv_odd7.length() > 0) {
            tv_odd_7X7.setText("" + tv_odd7);
        } else {
            tv_odd_7X7.setText("暂无记录");
        }
    }
}

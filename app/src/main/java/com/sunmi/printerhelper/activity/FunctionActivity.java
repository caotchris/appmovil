package com.sunmi.printerhelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucot.R;
import com.sunmi.printerhelper.threadhelp.ThreadPoolManager;
import com.sunmi.printerhelper.utils.AidlUtil;
import com.sunmi.printerhelper.utils.BytesUtil;

import sunmi.sunmiui.dialog.HintOneBtnDialog;

public class FunctionActivity extends AppCompatActivity {
    HintOneBtnDialog mHintOneBtnDialog;
    boolean run;
    /**
     * 在这里增加打印示例
     */
    private final DemoDetails[] demos = {
            new DemoDetails(R.string.function_text, R.drawable.function_text,
                    TextActivity.class),
    };

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        final GridLayoutManager layoutManage = new GridLayoutManager(this, 2);
        mRecyclerView = (RecyclerView)findViewById(R.id.worklist);
        mRecyclerView.setLayoutManager(layoutManage);
        mRecyclerView.setAdapter(new WorkTogetherAdapter());
    }

    class WorkTogetherAdapter extends RecyclerView.Adapter<WorkTogetherAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.demoDetails = demos[position];
            holder.tv.setText(demos[position].titleId);
            holder.tv.setCompoundDrawablesWithIntrinsicBounds(null, getDrawable(demos[position].iconResID), null, null);
        }

        @Override
        public int getItemCount() {
            return demos.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;
            DemoDetails demoDetails;

            public MyViewHolder(final View v) {

                super(v);
                tv =(TextView) v.findViewById(R.id.worktext);
                int TIEMPO = 1; //como ejemplo 10 segundos (10,000 milisegundos)

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.performClick();

                        if(demoDetails != null && demoDetails.activityClass != null)
                        {
                            if(demoDetails.titleId == R.string.function_buffer &&
                                    !(Build.MODEL.contains("P1") || Build.MODEL.contains("p1") || Build.MODEL.contains("V1s") || Build.MODEL.contains("v1s") || Build.MODEL.contains("P2") || Build.MODEL.contains("p2"))){
                                Toast.makeText(FunctionActivity.this, "¡Esta función solo se puede aplicar al modelo especificado V1S", Toast.LENGTH_LONG).show();
                            }else{
                                startActivity(new Intent(FunctionActivity.this, demoDetails.activityClass));
                            }
                            return;
                        }

                        if(demoDetails.titleId == R.string.function_multi){
                            mHintOneBtnDialog.show();
                            run = true;
                            ThreadPoolManager.getInstance().executeTask(new Runnable() {
                                @Override
                                public void run() {
                                    while(run){
                                        AidlUtil.getInstance().sendRawData(BytesUtil.getBaiduTestBytes());
                                        try {
                                            Thread.sleep(4000);
                                        } catch (InterruptedException e) {
                                            break;
                                        }
                                    }


                                }
                            });
                            ThreadPoolManager.getInstance().executeTask(new Runnable() {
                                @Override
                                public void run() {
                                    while(run){
                                        AidlUtil.getInstance().sendRawData(BytesUtil.getKoubeiData());
                                        try {
                                            Thread.sleep(4000);
                                        } catch (InterruptedException e) {
                                            break;
                                        }
                                    }

                                }
                            });
                            ThreadPoolManager.getInstance().executeTask(new Runnable() {
                                @Override
                                public void run() {
                                    while(run){
                                        AidlUtil.getInstance().sendRawData(BytesUtil.getErlmoData());
                                        try {
                                            Thread.sleep(4000);
                                        } catch (InterruptedException e) {
                                            break;
                                        }
                                    }

                                }
                            });
                            ThreadPoolManager.getInstance().executeTask(new Runnable() {
                                @Override
                                public void run() {
                                    while(run){
                                        AidlUtil.getInstance().sendRawData(BytesUtil.getMeituanBill());
                                        try {
                                            Thread.sleep(5000);
                                        } catch (InterruptedException e) {
                                            break;
                                        }
                                    }

                                }
                            });
                        }

                    }
                }, TIEMPO);
            }
        }
    }


    private class DemoDetails {
        @StringRes
        private final int titleId;
        @DrawableRes
        private final int iconResID;
        private final Class<? extends Activity> activityClass;

        public DemoDetails(@StringRes int titleId, @DrawableRes int descriptionId,
                           Class<TextActivity> activityClass) {
            super();
            this.titleId = titleId;
            this.iconResID = descriptionId;
            this.activityClass = activityClass;
        }
    }
}

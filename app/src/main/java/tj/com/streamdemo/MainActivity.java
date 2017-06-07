package tj.com.streamdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import tj.com.streamdemo.view.FlowLayout;

public class MainActivity extends AppCompatActivity {
    private String[] mValuse=new String[]{"androdi","hdfhd","dfhdfdfgj","fdjbvhdjfdfhj","dhfdf","hdhf",
    "dfhf","hhh","fhfdfkdghdhf","df"};
    private FlowLayout mFlowLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlowLayout= (FlowLayout) findViewById(R.id.flow_layout);
        initData();
    }

    private void initData() {
//        for (int i = 0; i <mValuse.length ; i++) {
//            Button btn=new Button(this);
//            ViewGroup.MarginLayoutParams lp=new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
//                    ViewGroup.MarginLayoutParams.WRAP_CONTENT);
//            btn.setText(mValuse[i]);
//            mFlowLayout.addView(btn,lp);
//        }
        LayoutInflater mInflater=LayoutInflater.from(this);

        for (int i = 0; i <mValuse.length ; i++) {
            TextView tv= (TextView) mInflater.inflate(R.layout.tv,mFlowLayout,false);
            tv.setText(mValuse[i]);
            mFlowLayout.addView(tv);
        }
    }
}

package Client.ui.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Client.ui.main.R;

public class CommomDialog extends Dialog implements View.OnClickListener{
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;
    private ImageView ivCancel;


    private TextView tvUserName;
    private TextView tvUserPhone;
    private TextView tvUserDepart;


    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

    private String name;
    private String tel;
    private String depart;


    public CommomDialog(Context context) {
        super(context);
        this.mContext = context;
    }


    public CommomDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }


    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }


    protected CommomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    public CommomDialog setTitle(String title){
        this.title = title;
        return this;
    }


    public CommomDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }


    public CommomDialog setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_layout);
        setCanceledOnTouchOutside(false);
        initView();
    }


    private void initView(){
        contentTxt = (TextView)findViewById(R.id.content);
        titleTxt = (TextView)findViewById(R.id.title);
        submitTxt = (TextView)findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = (TextView)findViewById(R.id.cancel);
        ivCancel = (ImageView)findViewById(R.id.user_cancel);

        tvUserName = (TextView)findViewById(R.id.user_name);
        tvUserPhone = (TextView)findViewById(R.id.user_telphone);
        tvUserDepart = (TextView) findViewById(R.id.user_depart);

        //tvUserPhone.setText("1274636525");


        cancelTxt.setOnClickListener(this);
        ivCancel.setOnClickListener(this);



        contentTxt.setText(content);
        if(!TextUtils.isEmpty(positiveName)){
            submitTxt.setText(positiveName);
        }


        if(!TextUtils.isEmpty(negativeName)){
            cancelTxt.setText(negativeName);
        }


        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);

            tvUserName.setText(name);
            tvUserPhone.setText(tel);
            tvUserDepart.setText(depart);
        }


    }

//确认和取消监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_cancel:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }


    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }


    //设置文字
    public CommomDialog setName(String name){
        this.name = name;
        return this;
    }

    public CommomDialog setTel(String tel){
        this.tel = tel;
        return this;
    }

    public CommomDialog setDpart(String depart){
        this.depart = depart;
        return this;

    }



}

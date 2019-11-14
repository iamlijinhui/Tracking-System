package extrace.ui.main;

import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import extrace.loader.UploadLocation;
import extrace.misc.model.ExpressSheet;
import extrace.ui.domain.ExpressEditActivity;

public class PackageEditAdapter extends BaseAdapter {
    Context context;
    List<ExpressSheet> itemList;
    UploadLocation uploadLocation;
    String pkgID;
    int SCAN_STATUS;

    public PackageEditAdapter(Context context, List<ExpressSheet> itemList, String pkgID, int SCAN_STATUS) {
        this.context = context;
        this.itemList = itemList;
        this.pkgID = pkgID;
        this.SCAN_STATUS = SCAN_STATUS;
    }

    @Override//总共有多少行
    public int getCount() {
        if(itemList == null){
            return 0;
        }
        return itemList.size();
    }

    @Override//每行显示什么
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TextView textView = new TextView(IconChooseActivity.this);
        // textView.setText("第"+position+"行");
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater infater = LayoutInflater.from(context);
            convertView = infater.inflate(R.layout.item_swipe,parent,false);

           // viewHolder.tvID = (TextView) convertView.findViewById(R.id.tv_name);

            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.telCode = (TextView)convertView.findViewById(R.id.tel);
            viewHolder.address = (TextView)convertView.findViewById(R.id.addr);
            viewHolder.time = (TextView)convertView.findViewById(R.id.time);
            viewHolder.status = (TextView)convertView.findViewById(R.id.st);
            viewHolder.toTop = (ViewGroup)convertView.findViewById(R.id.toTop);
            viewHolder.error = (ViewGroup)convertView.findViewById(R.id.error);
            viewHolder.detail = (ViewGroup)convertView.findViewById(R.id.detail);
            //ViewGroup viewGroup = (ViewGroup)convertView.findViewById(R.id.es_item);
            //viewGroup.getParent().requestDisallowInterceptTouchEvent(true);
//            viewGroup.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    //Log.i("touch" , "--tv_touch--");
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
//                       // Log.i("touch" , "--ACTION_UP-");
//                    }
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                       // Log.i("touch" , "--ACTION_DOWN-");
//                    }
//                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                       // Log.i("touch" , "--ACTION_MOVE-");
//                    }
//                    return true;
//                }
//            });


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


        if(itemList.get(position).getRecever() != null){
            viewHolder.name.setText(itemList.get(position).getRecever().getName());			//接收者姓名
            viewHolder.telCode.setText(itemList.get(position).getRecever().getTelCode());	//接收者电话
            viewHolder.address.setText(itemList.get(position).getRecever().getAddress());	//接收者
        }
        if(itemList.get(position).getAccepterTime() != null){
            SimpleDateFormat myFmt=new SimpleDateFormat("MM月dd日 hh:mm");
            viewHolder.time.setText(myFmt.format(itemList.get(position).getAccepterTime()));
        }


        String stText = "";
        switch(itemList.get(position).getStatus()){
            case ExpressSheet.STATUS.STATUS_CREATED:
                stText = "创建";
                break;
            case ExpressSheet.STATUS.STATUS_RECEIVED:
                stText = "收件";
                break;
            case ExpressSheet.STATUS.STATUS_DELIVERIED:
                stText = "交付";
                break;
        }
        viewHolder.status.setText(stText);

     //   viewHolder.tvID.setText(itemList.get(position).getID());

        /* 以下黑体为事件监听响应部分，即点击删除图标和头像会分别显示提醒信息 */
        viewHolder.toTop.setOnClickListener(new View.OnClickListener(){     //置顶
            public void onClick(View v){
                //Toast.makeText(context, "you clicked delete button", Toast.LENGTH_SHORT).show();
                //position在remove后会变，所以先把内容取出来
                ExpressSheet expressSheet = itemList.get(position);
                itemList.remove(position);
                itemList.add(0,expressSheet);
                notifyDataSetInvalidated();

            }
        });

        if (SCAN_STATUS == 2){      //区分打包拆包报错
            viewHolder.error.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    uploadLocation = new UploadLocation();
                    uploadLocation.expressSheetMiss(pkgID,itemList.get(position).getID());
                    Toast.makeText(context, "该包裹已成功报错!", Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            viewHolder.error.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
//                    uploadLocation = new UploadLocation();
//                    uploadLocation.expressSheetMiss(pkgID,itemList.get(position).getID());
                    Toast.makeText(context, "报错成功!", Toast.LENGTH_SHORT).show();
                }
            });

        }


        viewHolder.detail.setOnClickListener(new View.OnClickListener(){    //详情
            public void onClick(View v){
                //Toast.makeText(context, "you clicked hhh", Toast.LENGTH_SHORT).show();
                ExpressSheet es = (ExpressSheet) getItem(position);
                Intent intent = new Intent();
                intent.putExtra("Action","Edit");
                intent.putExtra("ExpressSheet",es);
                intent.setClass(context, ExpressEditActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    @Override
    public Object getItem(int position) {
    	if (itemList != null)
			return itemList.get(position);
		return null;
    }

    @Override
    public long getItemId(int position) {
    	if (itemList != null)
			return itemList.get(position).hashCode();
		return 0;
    }

    class ViewHolder{
        //TextView tvID;
        TextView name;
        TextView telCode;
        TextView address;
        TextView time;
        TextView status;
        ViewGroup toTop;
        ViewGroup error;
        ViewGroup detail;
    }
}

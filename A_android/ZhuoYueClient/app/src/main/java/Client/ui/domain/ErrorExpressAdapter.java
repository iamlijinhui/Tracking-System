package Client.ui.domain;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import Client.misc.model.ExpressSheet;
import Client.misc.model.MissingExpressSheet;
import Client.ui.main.R;
import Client.ui.util.CommomDialog;

public class ErrorExpressAdapter extends BaseAdapter {
    Context context;
    List<MissingExpressSheet> itemList;

    public ErrorExpressAdapter(Context context, List<MissingExpressSheet> itemList) {
        this.context = context;
        this.itemList = itemList;
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

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater infater = LayoutInflater.from(context);
            convertView = infater.inflate(R.layout.item_express_swipe,parent,false);

            viewHolder.pkgId = (TextView)convertView.findViewById(R.id.pkg_id);
            viewHolder.status = (TextView)convertView.findViewById(R.id.pkg_status);
            viewHolder.toTop = (ViewGroup)convertView.findViewById(R.id.toTop);
            viewHolder.error = (ViewGroup)convertView.findViewById(R.id.error);
            viewHolder.detail = (ViewGroup)convertView.findViewById(R.id.detail);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


        if(itemList.get(position) != null){
            viewHolder.pkgId.setText("快 递  单 号：    "+itemList.get(position).getExpressSheet().getID()+"");
        }


        String stText = "最近处理人：    "+itemList.get(position).getUserInfo().getName();
        viewHolder.status.setText(stText);

        //   viewHolder.tvID.setText(itemList.get(position).getID());

        /* 以下黑体为事件监听响应部分，即点击删除图标和头像会分别显示提醒信息 */
        viewHolder.toTop.setOnClickListener(new View.OnClickListener(){     //置顶
            public void onClick(View v){
                //Toast.makeText(context, "you clicked delete button", Toast.LENGTH_SHORT).show();
                //position在remove后会变，所以先把内容取出来
                MissingExpressSheet missingExpressSheet = itemList.get(position);;
                itemList.remove(position);
                itemList.add(0,missingExpressSheet);
                notifyDataSetInvalidated();         //刷新

            }
        });
        viewHolder.error.setOnClickListener(new View.OnClickListener(){ //签收
            public void onClick(View v){

 //               Toast.makeText(context, "已解决!", Toast.LENGTH_SHORT).show();
//                itemList.remove(position);//选择行的位置
//                notifyDataSetInvalidated();         //刷新

                MissingExpressSheet missingExpressSheet = itemList.get(position);;
                CommomDialog commomDialog = new CommomDialog(context, R.style.dialog, "111", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        dialog.dismiss();
                    }
                });

                commomDialog.setName(missingExpressSheet.getUserInfo().getName()+"");
                commomDialog.setDpart(missingExpressSheet.getUserInfo().getDptID()+"");
                commomDialog.setTel(missingExpressSheet.getUserInfo().getTelCode()+"");
                //Toast.makeText(context, "已解决!", Toast.LENGTH_SHORT).show();
                commomDialog.setTitle("最近处理该包裹人员信息").show();


            }
        });

        viewHolder.detail.setOnClickListener(new View.OnClickListener(){    //详情
            public void onClick(View v){
                // Toast.makeText(context, "you clicked hhh", Toast.LENGTH_SHORT).show();
                MissingExpressSheet missingExpressSheet = (MissingExpressSheet) getItem(position);
                ExpressSheet es = missingExpressSheet.getExpressSheet();
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
        TextView pkgId;
        TextView status;
        ViewGroup toTop;
        ViewGroup error;
        ViewGroup detail;
    }
}

package extrace.ui.domain;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;

import extrace.misc.model.TransPackage;
import extrace.misc.model.UsersPackage;
import extrace.ui.main.R;

public class MyPackageAdapter extends BaseAdapter {
    Context context;
    List<UsersPackage> itemList;

    public MyPackageAdapter(Context context, List<UsersPackage> itemList) {
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
            convertView = infater.inflate(R.layout.item_package_swipe,parent,false);

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
            viewHolder.pkgId.setText("包裹号：      "+itemList.get(position).getPkg().getID()+"");
        }


        String stText = "";
        switch(itemList.get(position).getPkg().getStatus()){
            case 0:
                stText = "状    态：      新建";
                break;
            case 1:
                stText = "状    态：      揽收";
                break;
            case 2:
                stText = "状    态：      转运";
                break;
            case 3:
                stText = "状    态：      派送";
                break;
            case 4:
                stText = "状    态：      完成";
                break;
            case 5:
                stText = "状    态：      打包中";
                break;
        }
        viewHolder.status.setText(stText);

        //   viewHolder.tvID.setText(itemList.get(position).getID());

        /* 以下黑体为事件监听响应部分，即点击删除图标和头像会分别显示提醒信息 */
        viewHolder.toTop.setOnClickListener(new View.OnClickListener(){     //置顶
            public void onClick(View v){
                //Toast.makeText(context, "you clicked delete button", Toast.LENGTH_SHORT).show();
                //position在remove后会变，所以先把内容取出来
                UsersPackage usersPackage = itemList.get(position);;
                itemList.remove(position);
                itemList.add(0,usersPackage);
                notifyDataSetInvalidated();         //刷新

            }
        });
        viewHolder.error.setOnClickListener(new View.OnClickListener(){ //签收
            public void onClick(View v){

                Toast.makeText(context, "已解决!", Toast.LENGTH_SHORT).show();
                itemList.remove(position);//选择行的位置
                notifyDataSetInvalidated();         //刷新
            }
        });

        viewHolder.detail.setOnClickListener(new View.OnClickListener(){    //详情
            public void onClick(View v){
                // Toast.makeText(context, "you clicked hhh", Toast.LENGTH_SHORT).show();
                UsersPackage usersPackage = (UsersPackage) getItem(position);
                Intent intent = new Intent();
                intent.putExtra("packageId",usersPackage.getPkg().getID());
                intent.setClass(context, PackageInfoActivity.class);
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

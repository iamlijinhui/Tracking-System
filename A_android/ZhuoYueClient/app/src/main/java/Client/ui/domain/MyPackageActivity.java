package Client.ui.domain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import Client.loader.UsersPackagesLoader;
import Client.misc.model.UsersPackage;
import Client.net.IDataAdapter;
import Client.ui.main.R;

public class MyPackageActivity extends AppCompatActivity implements IDataAdapter<List<UsersPackage>> {

    ListView listView;
    UsersPackagesLoader usersPackagesLoader;
    List<UsersPackage> usersPackageList;
    MyPackageAdapter myPackageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_package);

        listView = (ListView) findViewById(R.id.my_package_lvpkg);
        usersPackagesLoader = new UsersPackagesLoader(MyPackageActivity.this,MyPackageActivity.this);
        usersPackagesLoader.getUserPackages(19+"");
    }

    @Override
    public List<UsersPackage> getData() {
        return usersPackageList;
    }

    @Override
    public void setData(List<UsersPackage> data) {
        usersPackageList = data;
    }

    @Override
    public void notifyDataSetChanged() {
        listView.setAdapter(new MyPackageAdapter( this,usersPackageList)); //icon spair数组
    }
}

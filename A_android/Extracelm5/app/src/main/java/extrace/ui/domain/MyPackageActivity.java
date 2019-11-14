package extrace.ui.domain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import extrace.loader.UsersPackagesLoader;
import extrace.misc.model.UsersPackage;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;

public class MyPackageActivity extends AppCompatActivity implements IDataAdapter<List<UsersPackage>> {

    ListView listView;
    UsersPackagesLoader usersPackagesLoader;
    List<UsersPackage> usersPackageList;
    MyPackageAdapter myPackageAdapter;
    private ExTraceApplication app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_package);

        listView = (ListView) findViewById(R.id.my_package_lvpkg);
        usersPackagesLoader = new UsersPackagesLoader(MyPackageActivity.this,MyPackageActivity.this);
        app = (ExTraceApplication) getApplication();
        usersPackagesLoader.getUserPackages(app.getLoginUser().getUID()+"");
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

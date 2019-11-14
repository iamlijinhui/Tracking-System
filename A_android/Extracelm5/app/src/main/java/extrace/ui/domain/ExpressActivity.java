package extrace.ui.domain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import extrace.loader.ExpressLoader;
import extrace.misc.model.ExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.R;
import zxing.util.CaptureActivity;

/**
 * **************************************************************废弃
 */
public class ExpressActivity extends AppCompatActivity implements IDataAdapter<ExpressSheet> {
    public static final int REQUEST_HISTORY = 105;

    private ExTraceApplication app;
    ExpressLoader mLoader;
    ExpressSheet expressSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);
        app = (ExTraceApplication) getApplication();

        Intent mIntent = getIntent();
        if (mIntent.hasExtra("Action")) {
            if(mIntent.getStringExtra("Action").equals("esHistory")){	//// 运输历史记录查询

                esHistory();

            }
            else{
                this.setResult(RESULT_CANCELED, mIntent);
                this.finish();
            }
        }
        else{
            this.setResult(RESULT_CANCELED, mIntent);
            this.finish();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode){
                    /*
                     * 查包
                     */
                    case REQUEST_HISTORY:

                        if (data.hasExtra("BarCode")) {			//打包
                            String id = data.getStringExtra("BarCode");

                            Toast.makeText(ExpressActivity.this,id,Toast.LENGTH_SHORT).show();
                            try {
                                 mLoader = new ExpressLoader(this, this);
                                 mLoader.Load(id);
                             } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;


                }
                break;
            default:
                break;
        }
    }

//     case REQUEST_HISTORY:		//运输历史
//            if (data.hasExtra("BarCode")) {
//        String id = data.getStringExtra("BarCode");

//				break;

    /**
     * 新增运输历史
     */

    private void esHistory(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_HISTORY);
    }

    @Override
    public ExpressSheet getData() {
        return expressSheet;
    }

    @Override
    public void setData(ExpressSheet data) {
        expressSheet = data;
    }

    @Override
    public void notifyDataSetChanged() {
        //Toast.makeText(ExpressActivity.this,expressSheet+"",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ExpressActivity.this, PackageDeliverActivity.class);
        intent.putExtra("expressSheet",expressSheet);
        startActivity(intent);
    }
}


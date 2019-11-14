package Client.ui.EsClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import Client.ui.domain.PackageDeliverActivity;
import Client.ui.main.R;
import zxing.util.CaptureActivity;


public class ClientSearchActivity extends AppCompatActivity {
    public static final int REQUEST_SearchESid = 666;

    ImageView ivUser;
    EditText etEsId;
    ImageView ivScan;
    Button btOK;
    String esId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search);
        ivUser = (ImageView)findViewById(R.id.user);
        etEsId = (EditText)findViewById(R.id.es_et_id);
        ivScan = (ImageView)findViewById(R.id.es_iv_scan);
        btOK = (Button)findViewById(R.id.es_bt_ok);
        setListener();
    }

    private void setListener() {

        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        etEsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esId = etEsId.getText().toString();
                Intent intent = new Intent(ClientSearchActivity.this,PackageDeliverActivity.class);
                intent.putExtra("Action",esId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode){
                    /*
                     * 查包
                     */
                    case REQUEST_SearchESid:

                        if (data.hasExtra("BarCode")) {			//打包
                            String id = data.getStringExtra("BarCode");
                            esId = id;
                            etEsId.setText(id);
//
                        }
                        break;


                }
                break;
            default:
                break;
        }
    }

    private void Search(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_SearchESid);
    }
}

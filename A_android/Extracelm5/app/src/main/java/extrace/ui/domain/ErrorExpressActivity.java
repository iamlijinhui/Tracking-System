package extrace.ui.domain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import extrace.loader.MissExpressSheetLoader;
import extrace.misc.model.MissingExpressSheet;
import extrace.net.IDataAdapter;
import extrace.ui.main.R;

public class ErrorExpressActivity extends AppCompatActivity implements IDataAdapter<List<MissingExpressSheet>> {
    List<MissingExpressSheet> missingExpressSheets;
    MissExpressSheetLoader missExpressSheetLoader;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_express);
        listView = (ListView)findViewById(R.id.error_express_lves);

        missExpressSheetLoader = new MissExpressSheetLoader(ErrorExpressActivity.this,ErrorExpressActivity.this);
        missExpressSheetLoader.getMissExpressSheet();
    }

    @Override
    public List<MissingExpressSheet> getData() {
        return missingExpressSheets;
    }

    @Override
    public void setData(List<MissingExpressSheet> data) {
        missingExpressSheets = data;
    }

    @Override
    public void notifyDataSetChanged() {
        listView.setAdapter(new ErrorExpressAdapter( this,missingExpressSheets)); //icon spair数组

    }
}

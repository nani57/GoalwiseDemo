package com.app.goalwise.ui;

import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.app.goalwise.R;
import com.app.goalwise.adapters.FundsListAdapter;
import com.app.goalwise.api.ApiClient;
import com.app.goalwise.api.ApiInterface;
import com.app.goalwise.models.FundsList;
import android.support.v7.app.ActionBar;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FundsListAdapter.ShowDialog {
    ApiInterface apiInterface;
    Call<List<FundsList>> fundsListCall;
    RecyclerView recyclerView;
    FundsListAdapter fundsListAdapter;
    List<FundsList> fundsList;
    private LinearLayoutManager linearLayoutManager;
    EditText searchEditText;
    LinearLayout linearLayout;
    ImageView searchImageview;
    View alertDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        init();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (searchEditText.getText().toString().length() >= 3) {
                    if(fundsList!=null){
                        fundsList.clear();
                    }
                    callApi(searchEditText.getText().toString());
                }
                else {
                    fundsList.clear();
                    if(fundsListAdapter!=null){
                        fundsListAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(fundsListAdapter);
                    }
                }

               Log.v("Length",searchEditText.getText().toString().length()+ "Length");
            }
        });
        searchImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
    public void showAlertDialogPopup(String fundname) {


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        alertDialogView = inflater.inflate(R.layout.successfully_dialog_layout, null);
        TextView fundName = alertDialogView.findViewById(R.id.messageTextView);
        TextView gotIt = alertDialogView.findViewById(R.id.got_it);
        fundName.setText(fundname + " " + getResources().getString(R.string.fund_added_successfully));
        alertDialog.setView(alertDialogView);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();
        gotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    public void init(){
        searchEditText = (EditText)findViewById(R.id.search_editText);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fundsList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        linearLayout = (LinearLayout)findViewById(R.id.enter_sip_layout);
        searchImageview = (ImageView) findViewById(R.id.search_iv);

    }
    public void callApi(String keyword){
        fundsListCall = apiInterface.fundsList(keyword,"GQCqJDU1hZ3e4aXOhLG905HbKoiBV1ZU6ipCB9Oc");
        fundsListCall.enqueue(new Callback<List<FundsList>>() {
            @Override
            public void onResponse(Call<List<FundsList>> call, Response<List<FundsList>> response) {
                Log.v("Sogn", "Login response " + response.body().get(0).getFundname());
                fundsList = response.body();
                fundsListAdapter = new FundsListAdapter(getApplicationContext(),fundsList,MainActivity.this);
                recyclerView.setAdapter(fundsListAdapter);
                fundsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<FundsList>> call, Throwable t) {
                Log.d("TAG", "UserPointFail");

            }
        });
    }

    @Override
    public void addFundClicked(String fund) {
        showAlertDialogPopup(fund);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

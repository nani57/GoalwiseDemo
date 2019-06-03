package com.app.goalwise.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.goalwise.R;
import com.app.goalwise.models.FundsList;

import java.util.ArrayList;
import java.util.List;

public class FundsListAdapter extends RecyclerView.Adapter<FundsListAdapter.FundsViewHolder> {
    private List<FundsList> fundsList;
    Context context;
    int sipAmount;
    private ShowDialog showDialog;


    public class FundsViewHolder extends RecyclerView.ViewHolder {
        public TextView fundName, minSipAmount, minSipMultiple, sipDates, addTextView;
        LinearLayout linearLayout;
        Button addFundButton;
        EditText sipAmountEditText;
        List<Integer> sipdatesList;

        public FundsViewHolder(final View view) {
            super(view);
            fundName = (TextView) view.findViewById(R.id.tv_fund_name);
            minSipAmount = (TextView) view.findViewById(R.id.min_sip_amount);
            minSipMultiple = (TextView) view.findViewById(R.id.min_sip_multiple);
            sipDates = (TextView) view.findViewById(R.id.sip_dates_values);
            addTextView = (TextView) view.findViewById(R.id.add);
            addFundButton = (Button) view.findViewById(R.id.add_fund_button);
            sipAmountEditText = (EditText) view.findViewById(R.id.et_sip_amount);
            linearLayout = (LinearLayout) view.findViewById(R.id.enter_sip_layout);
            sipdatesList = new ArrayList<>();
            linearLayout.setVisibility(View.GONE);


        }
    }


    public FundsListAdapter(Context context, List<FundsList> fundsList, ShowDialog showDialog) {
        this.fundsList = fundsList;
        this.context = context;
        this.showDialog = showDialog;
    }

    @Override
    public FundsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.funds_list_item, parent, false);

        return new FundsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FundsViewHolder holder, final int position) {
        final FundsList fund = fundsList.get(position);
        holder.linearLayout.setVisibility(View.GONE);
        holder.addTextView.setVisibility(View.VISIBLE);

        holder.fundName.setText(fund.getFundname());
        holder.minSipAmount.setText(String.valueOf(fund.getMinsipamount()));
        holder.minSipMultiple.setText(String.valueOf(fund.getMinsipmultiple()));

        int dateListSize = fund.getSipdates().size();

        for (int i = 0; i < dateListSize; i++) {
            if (i != dateListSize - 1) {
                holder.sipDates.setText(fund.getSipdates().toString().replaceAll("\\[|\\]", ""));
            }
        }

        holder.addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fund.setClicked(true);

                if (fund.isClicked()) {
                    holder.linearLayout.setVisibility(View.VISIBLE);
                    holder.addTextView.setVisibility(View.GONE);

                }

            }
        });
        if (fund.isClicked()) {
            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.addTextView.setVisibility(View.GONE);

        }
        holder.sipAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(holder.sipAmountEditText.getText().toString())) {
                    sipAmount = Integer.parseInt(holder.sipAmountEditText.getText().toString());
                }
            }
        });
        holder.addFundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sipAmount >= fund.getMinsipamount() && sipAmount % fund.getMinsipmultiple() == 0) {
                    showDialog.addFundClicked(fund.getFundname());
                    fund.setClicked(false);
                    holder.linearLayout.setVisibility(View.GONE);
                    holder.addTextView.setVisibility(View.VISIBLE);
                    holder.sipAmountEditText.getText().clear();

                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return fundsList.size();
    }

    public interface ShowDialog {
        void addFundClicked(String fund);
    }

}

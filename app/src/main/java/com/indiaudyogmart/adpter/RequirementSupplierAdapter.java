package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.model.RequirementSupplierItem;

import java.util.List;

public class RequirementSupplierAdapter extends RecyclerView.Adapter<RequirementSupplierAdapter.MyViewHolder> {
    private Context mContext;
    private List<RequirementSupplierItem> mData;

    public RequirementSupplierAdapter(Context mContext, List<RequirementSupplierItem> mData) {
        this.mContext = mContext;
        this.mData=mData;
    }

    public RequirementSupplierAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.supplier_list_item,parent,false);
        return new RequirementSupplierAdapter.MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RequirementSupplierAdapter.MyViewHolder holder, final int position) {



        try {
            holder.companyname.setText(mData.get(position).getCompanynName());
            holder.address.setText(mData.get(position).getAddress());
            holder.email.setText(mData.get(position).getEmail());
            holder.website.setText(mData.get(position).getWebsite());
            holder.name.setText(mData.get(position).getName());
            holder.department.setText(mData.get(position).getDepartment());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView companyname;
        TextView address;
        TextView email;
        TextView website;
        TextView name;
        TextView department;
        RelativeLayout relativeLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyname = (TextView) itemView.findViewById(R.id.tv_company_name);
            address = (TextView) itemView.findViewById(R.id.tv_address);
           email = (TextView) itemView.findViewById(R.id.tv_email);
           website = (TextView) itemView.findViewById(R.id.tv_website);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            department = (TextView) itemView.findViewById(R.id.tv_department);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rl_supplier);
        }
    }
}

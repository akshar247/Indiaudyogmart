package com.indiaudyogmart.adpter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.indiaudyogmart.fragment.AdharFragment;
import com.indiaudyogmart.fragment.BankFragment;
import com.indiaudyogmart.fragment.HomeFragment;
import com.indiaudyogmart.fragment.PanCardFragment;


public class KycAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public KycAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BankFragment bank = new BankFragment();
                return bank;
            case 1:
                AdharFragment adhar = new AdharFragment();
                return adhar;
            case 2:
               PanCardFragment pancard = new PanCardFragment();
                return pancard;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}

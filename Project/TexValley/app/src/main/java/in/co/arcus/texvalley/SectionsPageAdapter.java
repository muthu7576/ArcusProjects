package in.co.arcus.texvalley;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;

import java.util.ArrayList;
import java.util.List;

public  class SectionsPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mfragmentList = new ArrayList<>();
    private final List<String> mfragmentTitlelist = new ArrayList<>();


   public SectionsPageAdapter(FragmentManager fm){
       super(fm);
   }


    @Override
    public CharSequence getPageTitle(int position) {
        return mfragmentTitlelist.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentTitlelist.size();
    }
    public void addFragment(Fragment fragment,String title){
        mfragmentList.add(fragment);
        mfragmentTitlelist.add(title);
    }
}

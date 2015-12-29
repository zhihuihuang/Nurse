package com.guc.bureau2.fragment;

import java.util.ArrayList;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.MainAdapter;
import com.guc.bureau2.application.GucApplication;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.HomeItemDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.widget.LineGridView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainFragment extends BaseFragment {
    private ArrayList<HomeItemDTO> data = new ArrayList<>();
    private LineGridView mGridView;
    private boolean islod = false;
    // private ViewPager viewPager;
    // private LinearLayout ll_indicator;
    //private View view_indicator1;
    // private View view_indicator2;
    //private int cur_item = 50;
    // private ScheduledExecutorService scheduledExecutorService;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            viewPager.setCurrentItem(++cur_item);
//        }
//    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_main);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {
        controlBar(R.string.home_page, R.string.home_page, false, false);
        if (!islod) {
            String[] titles = new String[]{getResources().getString(R.string.archive_statistics), getResources().getString(R.string.health_check), getResources().getString(R.string.business_journal), getResources().getString(R.string.business_statistics), getResources().getString(R.string.drug_rank), getResources().getString(R.string.disease_statistice)};
            int[] images = new int[]{R.mipmap.ic_home_x1, R.mipmap.ic_home_x2, R.mipmap.ic_home_x3, R.mipmap.ic_home_x4, R.mipmap.ic_home_x5, R.mipmap.ic_home_x6};
            for (int i = 0; i < 6; i++) {
                HomeItemDTO dto = new HomeItemDTO();
                dto.setLable(titles[i]);
                dto.setSourceId(images[i]);
                data.add(dto);
            }
            islod = true;
        }
        MainAdapter adapter = new MainAdapter(data, R.layout.layout_item_main);
        mGridView.setAdapter(adapter);
        mActivity.setStatus(true);
        loginLog();
        GucApplication.status=2;
        // initHeadView();
        // initIndicator();
        // viewPager.setCurrentItem(cur_item);
        //startAutoPage();
    }

//    private void initIndicator() {
//        mActivity.getLayoutInflater().inflate(R.layout.layout_indicator_select, ll_indicator, true);
//        mActivity.getLayoutInflater().inflate(R.layout.layout_indicator_select, ll_indicator, true);
//        view_indicator1 = ll_indicator.getChildAt(0);
//        view_indicator2 = ll_indicator.getChildAt(1);
//        view_indicator1.setBackgroundResource(R.drawable.shape_indicator_select);
//        view_indicator2.setBackgroundResource(R.drawable.shape_indicator_normal);
//    }

//    private void initHeadView() {
//        int[] head_img = {R.mipmap.ic_home_title1, R.mipmap.ic_home_title2, R.mipmap.ic_home_title1,};
//        ArrayList<ImageView> viwes = new ArrayList<>();
//        for (int i = 0; i < head_img.length + 2; i++) {
//            ImageView imageView = new ImageView(mActivity);
//            ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            imageView.setLayoutParams(viewPagerImageViewParams);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            viwes.add(imageView);
//        }
//        HeadPagerAdapter adapter1 = new HeadPagerAdapter(viwes, head_img);
//        viewPager.setAdapter(adapter1);
//    }

//    private void startAutoPage() {
//        ViewPagerScroller scroller = new ViewPagerScroller(mActivity);
//        scroller.setScrollDuration(2000);
//        scroller.initViewPagerScroll(viewPager);
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(1);
//            }
//        };
//        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(timerTask, 3, 3, TimeUnit.SECONDS);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //scheduledExecutorService.shutdownNow();
        //handler.removeCallbacksAndMessages(null);
    }

    private void loginLog() {
        GucNetEnginClient.loginLog(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    protected void setListeners() {
        mGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mActivity.replace("archivesregionfragment", ArchivesRegionFragment.newInstance(), true);
                        break;
                    case 1:
                        mActivity.replace("CheckRegionFragment", CheckRegionFragment.newInstance(), true);
                        break;
                    case 2:
                        mActivity.replace("businessregionfragment", BusinessRegionFragment.newInstance(), true);
                        break;
                    case 3:
                        mActivity.replace("BusinessStatisticsFragment", BusinessStatisticsFragment.newInstance(), true);
                        break;
                    case 4:
                        mActivity.replace("DrugRankRegionFragment", DrugRankRegionFragment.newInstance(), true);
                        break;
                    case 5:
                        mActivity.replace("diseasestatisticsfragment", DiseaseRankRegionFragment.newInstance(), true);
                        break;
                    default:
                        //mActivity.replace("hospitallistfragment", HospitalListFragment.newInstance(position), true);
                        break;
                }
            }
        });
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                cur_item = position;
//                if (position % 2 == 0) {
//                    view_indicator1.setBackgroundResource(R.drawable.shape_indicator_select);
//                    view_indicator2.setBackgroundResource(R.drawable.shape_indicator_normal);
//                } else {
//                    view_indicator1.setBackgroundResource(R.drawable.shape_indicator_normal);
//                    view_indicator2.setBackgroundResource(R.drawable.shape_indicator_select);
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    @Override
    protected void initWidget(View view) {
        mGridView = (LineGridView) view.findViewById(R.id.gridview);
    }

    public static Fragment newInstance() {
        return new MainFragment();
    }
//    class ViewPagerScroller extends Scroller {
//        private int mScrollDuration = 2000;             // 滑动速度
//        /**
//         * 设置速度速度
//         * @param duration
//         */
//        public void setScrollDuration(int duration){
//            this.mScrollDuration = duration;
//        }
//
//        public ViewPagerScroller(Context context) {
//            super(context);
//        }
//
//        public ViewPagerScroller(Context context, Interpolator interpolator) {
//            super(context, interpolator);
//        }
//
//        public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
//            super(context, interpolator, flywheel);
//        }
//
//        @Override
//        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
//            super.startScroll(startX, startY, dx, dy, mScrollDuration);
//        }
//
//        @Override
//        public void startScroll(int startX, int startY, int dx, int dy) {
//            super.startScroll(startX, startY, dx, dy, mScrollDuration);
//        }
//
//
//
//        public void initViewPagerScroll(ViewPager viewPager) {
//            try {
//                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
//                mScroller.setAccessible(true);
//                mScroller.set(viewPager, this);
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

}

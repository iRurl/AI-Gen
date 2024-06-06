package com.ai.ai_gen.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ai.ai_gen.R;
import com.ai.ai_gen.activity.BannerView;
import com.ai.ai_gen.activity.Item_Detail_type1Activity;
import com.ai.ai_gen.activity.NewsSearchActivity;
import com.ai.ai_gen.activity.ServiceAllActivity;
import com.ai.ai_gen.activity.ServiceViewActivity;
import com.ai.ai_gen.adapter.GgTitleAdapter;
import com.ai.ai_gen.adapter.HomeViewPagerAdapter;
import com.ai.ai_gen.adapter.RecycleServiceAdapter;
import com.ai.ai_gen.bean.BannerBean;
import com.ai.ai_gen.bean.GgviewBean;
import com.ai.ai_gen.bean.ServiceBean;
import com.ai.ai_gen.fragment.guanggao.GgContentFragment;
import com.ai.ai_gen.fragment.guanggao.InfiniteLinearSnapHelper;
import com.ai.ai_gen.fragment.guanggao.ScaleCenterItemLayoutManager;
import com.ai.ai_gen.fragment.recommend.guanggaoFragment;
import com.ai.ai_gen.fragment.recommend.haowenFragment;
import com.ai.ai_gen.fragment.recommend.jieriFragment;
import com.ai.ai_gen.fragment.recommend.meishiFragment;
import com.ai.ai_gen.fragment.recommend.musicFragment;
import com.ai.ai_gen.fragment.recommend.pinpaiFragment;
import com.ai.ai_gen.fragment.recommend.wenanFragment;
import com.ai.ai_gen.utils.APIConfig;
import com.ai.ai_gen.utils.CustomViewpager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private static final String TAG = HomeFragment.class.getSimpleName();
    private final OkHttpClient client = new OkHttpClient();
    private final int mTitleMargin = 2;
    private final String[] tj_List = new String[]{"文案合辑", "美食推广", "音乐鉴赏", "节日感文案", "今日好文", "广告模板", "品牌案例"};
    private final int[] tj_idList = new int[]{0, 1, 2, 3, 4, 5, 6};
    RecyclerView gg_TitleRecyclerView;
    GgviewBean ggList;
    private Banner banner;
    private Button search_btn;
    private RecycleServiceAdapter recycleServiceAdapter;
    private List<ServiceBean.RowsBean> rowsBeanList;
    private RecyclerView home_recyclerview;
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                ServiceBean serviceBean = (ServiceBean) msg.obj;
                rowsBeanList = serviceBean.getRows();
                recycleServiceAdapter = new RecycleServiceAdapter(getActivity(), rowsBeanList);
                home_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 5));
                home_recyclerview.setAdapter(recycleServiceAdapter);
                recycleServiceAdapter.setItemClickListener(new RecycleServiceAdapter.MyItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String url = APIConfig.BASE_URL + "/" + rowsBeanList.get(position).getLink();
                        Intent intent = null;
                        if (position != 9) {
                            intent = new Intent(getActivity(), ServiceViewActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", rowsBeanList.get(position).getServiceName());
                            bundle.putString("url", url);
                            intent.putExtras(bundle);
                            getActivity().startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "由于未接入后端，因此当前仅有这么多服务~", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        }
    };
    //推荐
    private ArrayList<Integer> moveToList;
    private LinearLayout tj_titleLayout;
    private ArrayList<String> tj_titleList;            //标题文字集合
    private HomeViewPagerAdapter tj_Adapter;
    private ArrayList<Fragment> tj_fragmentsList;      //viewPager加载类
    private CustomViewpager tj_viewPager;
    private HorizontalScrollView tj_scrollView;
    private ArrayList<TextView> tj_textViewList;       //标题控件集合
    private int tj_currentPos;                         //现在显示的是第几页
    //广告
    private ArrayList<String> gg_titleList;            //内容文字集合
    private HomeViewPagerAdapter gg_content_Adapter;
    private ArrayList<Fragment> content_fragmentsList;
    private CustomViewpager gg_ContentviewPager;

    private LinearLayout  yx_1;
    private LinearLayout  yx_2;
    private LinearLayout  yx_3;
    private LinearLayout  yx_4;
    private LinearLayout  yx_5;
    private LinearLayout  yx_6;

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public View initView() {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = View.inflate(getActivity(), R.layout.fragment_home, null);
        banner = view.findViewById(R.id.banner);
        search_btn = view.findViewById(R.id.search_btn);
        home_recyclerview = view.findViewById(R.id.home_recyclerview);
        tj_scrollView = (HorizontalScrollView) view.findViewById(R.id.tj_scrollView);
        tj_titleLayout = (LinearLayout) view.findViewById(R.id.tj_titleLayout);
        tj_viewPager = (CustomViewpager) view.findViewById(R.id.tj_viewPager);
        //广告
        gg_TitleRecyclerView = view.findViewById(R.id.gg_TitleRecyclerView);
        gg_ContentviewPager = (CustomViewpager) view.findViewById(R.id.gg_Contentview);

        //营销
        yx_1=view.findViewById(R.id.yx_1);
        yx_2=view.findViewById(R.id.yx_2);
        yx_3=view.findViewById(R.id.yx_3);
        yx_4=view.findViewById(R.id.yx_4);
        yx_5=view.findViewById(R.id.yx_5);
        yx_6=view.findViewById(R.id.yx_6);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
         initSearch();  //初始化搜索框
        initBanner();      // 初始化轮播图
        getServiceData();
        init_tj_page();
        get_gg_HttpData();
        initclick();
    }

    private void initclick() {
        yx_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Item_Detail_type1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageurl",null);
                bundle.putString("content", "他的爱就像他发的信息，只增不减" );
                bundle.putString("desc",  "搜狗输入法");
                bundle.putString("zannum",  "1.2w");
                bundle.putInt("resid",  R.mipmap.fq1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        yx_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Item_Detail_type1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageurl",null);
                bundle.putString("content", "我随理想去了远方父亲默默走进了旧时光" );
                bundle.putString("desc",  "民生银行");
                bundle.putString("zannum",  "1.2w");
                bundle.putInt("resid",  R.mipmap.fq2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        yx_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Item_Detail_type1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageurl",null);
                bundle.putString("content", "从细节，显露个性；由搭配，释放气场。" );
                bundle.putString("desc",  "服饰饰品");
                bundle.putString("zannum",  "1.1w");
                bundle.putInt("resid",  R.mipmap.fs1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        yx_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Item_Detail_type1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageurl",null);
                bundle.putString("content", "女人如果只能拥有一件珠宝，那必定是珍珠" );
                bundle.putString("desc",  "戴安娜王妃");
                bundle.putString("zannum",  "1.5w");
                bundle.putInt("resid",  R.mipmap.fs2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        yx_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Item_Detail_type1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageurl",null);
                bundle.putString("content", "如果每个人都是一颗小星星，那旅行时就是脱离轨道，和别的星星相遇。" );
                bundle.putString("desc",  "旅行");
                bundle.putString("zannum",  "1.4w");
                bundle.putInt("resid",  R.mipmap.ly1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        yx_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Item_Detail_type1Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("imageurl",null);
                bundle.putString("content", "山不理你，树不想你，草不疼你，海不爱你，沙漠嘲笑你，小溪讽刺你，城市容不下你，村庄留不住你，你知不知道，你不去看世界，世界也懒得看你。" );
                bundle.putString("desc",  "高德地图");
                bundle.putString("zannum",  "1.8w");
                bundle.putInt("resid",  R.mipmap.ly2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化推荐页面
     */
    private void init_tj_page() {
        tj_fragmentsList = new ArrayList<>();
        tj_titleList = new ArrayList<>();
        tj_textViewList = new ArrayList<>();
        moveToList = new ArrayList<>();
        tj_Adapter = new HomeViewPagerAdapter(getChildFragmentManager());

        tj_titleList.add(tj_List[0]);
        wenanFragment fragment = new wenanFragment(getActivity(), tj_idList[0]);
        tj_fragmentsList.add(fragment);
        add_tj_TitleLayout(tj_titleList.get(0), tj_idList[0]);

        tj_titleList.add(tj_List[1]);
        meishiFragment meishifragment = new meishiFragment(getContext(), tj_idList[1]);
        tj_fragmentsList.add(meishifragment);
        add_tj_TitleLayout(tj_titleList.get(1), tj_idList[1]);

        tj_titleList.add(tj_List[2]);
        musicFragment musicfragment = new musicFragment(getContext(), tj_idList[2]);
        tj_fragmentsList.add(musicfragment);
        add_tj_TitleLayout(tj_titleList.get(2), tj_idList[2]);

        tj_titleList.add(tj_List[3]);
        jieriFragment jierifragment = new jieriFragment(getContext(), tj_idList[3]);
        tj_fragmentsList.add(jierifragment);
        add_tj_TitleLayout(tj_titleList.get(3), tj_idList[3]);

        tj_titleList.add(tj_List[4]);
        haowenFragment haowenfragment = new haowenFragment(getContext(), tj_idList[4]);
        tj_fragmentsList.add(haowenfragment);
        add_tj_TitleLayout(tj_titleList.get(4), tj_idList[4]);

        tj_titleList.add(tj_List[5]);
        guanggaoFragment guanggaofragment = new guanggaoFragment(getContext(), tj_idList[5]);
        tj_fragmentsList.add(guanggaofragment);
        add_tj_TitleLayout(tj_titleList.get(5), tj_idList[5]);

        tj_titleList.add(tj_List[6]);
        pinpaiFragment pinpaifragment = new pinpaiFragment(getContext(), tj_idList[6]);
        tj_fragmentsList.add(pinpaifragment);
        add_tj_TitleLayout(tj_titleList.get(6), tj_idList[6]);

        // 如果不设置，可能第三个页面以后就显示不出来了，因为offset就是默认值1了
        tj_Adapter.setData(tj_fragmentsList);
        tj_viewPager.setAdapter(tj_Adapter);
        tj_viewPager.setOffscreenPageLimit(9);
        tj_textViewList.get(0).setTextColor(Color.rgb(255, 255, 255));
        tj_textViewList.get(0).setBackgroundResource(R.drawable.tj_text_back);
        tj_currentPos = 0;
        tj_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(final int position) {
                // 切换到当前页面，重置高度
                tj_viewPager.requestLayout();
                tj_textViewList.get(tj_currentPos).setTextColor(Color.rgb(94, 94, 94));
                tj_textViewList.get(tj_currentPos).setBackgroundResource(0);
                tj_textViewList.get(position).setTextColor(Color.rgb(255, 255, 255));
                tj_textViewList.get(position).setBackgroundResource(R.drawable.tj_text_back);
                tj_currentPos = position;
                tj_scrollView.scrollTo((int) moveToList.get(position), 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }
    private void add_tj_TitleLayout(String title, int position) {
        final TextView textView = (TextView) getLayoutInflater().inflate(R.layout.tj_horizontal_title, null);
        textView.setText(title);
        textView.setTag(position);
        textView.setOnClickListener(new posOnClickListener());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 25;
        params.rightMargin = dip2px(getActivity(), mTitleMargin) * 4;
        tj_titleLayout.addView(textView, params);
        tj_textViewList.add(textView);
        int width;
        if (position == 0) {
            width = 0;
            moveToList.add(width);
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            tj_textViewList.get(position - 1).measure(w, h);
            width = tj_textViewList.get(position - 1).getMeasuredWidth() + mTitleMargin * 8;
            moveToList.add(width + moveToList.get(moveToList.size() - 1));
        }
    }
    class posOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if ((int) view.getTag() == tj_currentPos) {
                return;
            }
            tj_textViewList.get(tj_currentPos).setTextColor(Color.rgb(94, 94, 94));
            tj_textViewList.get(tj_currentPos).setBackgroundResource(0);
            tj_currentPos = (int) view.getTag();
            tj_viewPager.setCurrentItem(tj_currentPos);
            tj_viewPager.requestLayout();
        }
    }

    /**
     * 初始化广告页面
     */
    public void get_gg_HttpData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(APIConfig.BASE_URL + "/home/guanggao/list")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("onFailure", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Gson gson = new Gson();
                    ggList = gson.fromJson(result, GgviewBean.class);

                    // Ensure UI operations are run on the main thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            init_gg_page();
                            init_content_page();
                            setupViewPagerSync();
                        }
                    });
                }
            }
        });
    }

    private void init_gg_page() {
        gg_titleList = new ArrayList<>();
        for (int i = 0; i < ggList.getTotal(); i++) {
            gg_titleList.add(ggList.getRows().get(i).getUrl());
        }

        ScaleCenterItemLayoutManager layoutManager = new ScaleCenterItemLayoutManager(getContext());
        gg_TitleRecyclerView.setLayoutManager(layoutManager);

        GgTitleAdapter adapter = new GgTitleAdapter(getContext(), gg_titleList);
        gg_TitleRecyclerView.setAdapter(adapter);

        InfiniteLinearSnapHelper snapHelper = new InfiniteLinearSnapHelper();
        snapHelper.attachToRecyclerView(gg_TitleRecyclerView);

        int midPosition = Integer.MAX_VALUE / 2;
        int actualPosition = midPosition - midPosition % gg_titleList.size();
        gg_TitleRecyclerView.scrollToPosition(actualPosition);

        gg_TitleRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    View centerView = snapHelper.findSnapView(layoutManager);
                    if (centerView != null) {
                        int pos = layoutManager.getPosition(centerView);
                        int originalPosition = pos % ggList.getTotal();
                        gg_ContentviewPager.setCurrentItem(originalPosition, true);
                    }
                }
            }
        });
    }

    private void init_content_page() {
        content_fragmentsList = new ArrayList<>();
        gg_content_Adapter = new HomeViewPagerAdapter(getChildFragmentManager());
        for (int i = 0; i < ggList.getTotal(); i++) {
            GgContentFragment fragment = new GgContentFragment(getActivity(), ggList.getRows().get(i));
            content_fragmentsList.add(fragment);
        }

        gg_content_Adapter.setData(content_fragmentsList);
        gg_ContentviewPager.setAdapter(gg_content_Adapter);
        gg_ContentviewPager.setOffscreenPageLimit(10);
    }

    private void setupViewPagerSync() {
        gg_ContentviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int targetPosition = (Integer.MAX_VALUE / 2) + position;
                gg_TitleRecyclerView.scrollToPosition(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void initBanner() {
        //网络加载图片
        List<BannerBean.RowsDTO> list = new ArrayList<>();
        list.add(new BannerBean.RowsDTO("https://cdn.pixabay.com/photo/2017/06/20/08/52/japanese-2422324_640.jpg", null));
        list.add(new BannerBean.RowsDTO("https://cdn.pixabay.com/photo/2015/05/12/06/12/maple-763557_1280.jpg", null));
        list.add(new BannerBean.RowsDTO("https://alifei05.cfp.cn/creative/vcg/nowater800/new/VCG211289906085.png?x-oss-process=image/format,webp", null));
        list.add(new BannerBean.RowsDTO("https://alifei05.cfp.cn/creative/vcg/nowater800/new/VCG211320015022.jpg?x-oss-process=image/format,webp", null));
        banner.setAdapter(new BannerImageAdapter<BannerBean.RowsDTO>(list) {
                    @Override
                    public void onBindView(BannerImageHolder holder, BannerBean.RowsDTO data, int position, int size) {
                        //BannerImageHolder 图片加载自己实现
                        Glide.with(getActivity())
                                .load(data.getImgUrl())
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                                .into(holder.imageView);
                    }
                }).addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()))
                .setBannerRound(20)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(Object o, int position) {
                        //  getIntent(position);
                        Intent intent = new Intent(getActivity(), BannerView.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", list.get(position).getImgUrl());
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
    }

    public void initSearch() {
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "搜索失败，未接入后端，因此无法搜索", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getServiceData() {
        Request request = new Request.Builder()
                .url(APIConfig.BASE_URL + "/service/service/list")
                .build();
        try {
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("onFailure", e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String result = response.body().string();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();
                                ServiceBean serviceBean = gson.fromJson(result, ServiceBean.class);
                                Message msg = new Message();
                                msg.what = 0;
                                msg.obj = serviceBean;
                                handler.sendMessage(msg);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }




}

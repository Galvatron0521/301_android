package com.rilintech.fragment_301_huxike_android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.activity.BaseWebActivity;
import com.rilintech.fragment_301_huxike_android.activity.ClockIndexActivity;
import com.rilintech.fragment_301_huxike_android.activity.QuestionnaireActivity;
import com.rilintech.fragment_301_huxike_android.activity.WeatherActivity;
import com.rilintech.fragment_301_huxike_android.activity.XiaoChuanActivity;
import com.rilintech.fragment_301_huxike_android.activity.XiaoChuanRiJiEditActivity;
import com.rilintech.fragment_301_huxike_android.adapter.DragAdapter;
import com.rilintech.fragment_301_huxike_android.app.AppApplication;
import com.rilintech.fragment_301_huxike_android.bean.ChannelItem;
import com.rilintech.fragment_301_huxike_android.bean.ChannelManage;
import com.rilintech.fragment_301_huxike_android.tool.SdcardTools;
import com.rilintech.fragment_301_huxike_android.view.DragGrid;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rilintech on 15/7/28.
 */
public class FragmentHome extends Fragment {

    public static String TAG = "ChannelActivity";
    //用户栏目的GRIDVIEW
    private DragGrid userGridView;
    private RelativeLayout title_bar;
    private ImageView user_info;
    //用户栏目对应的适配器，可以拖动
    DragAdapter userAdapter;
    //用户栏目列表
    ArrayList<ChannelItem> userChannelList = new ArrayList<>();
    //是否在移动，由于这边是动画结束后才进行的数据更替，设置这个限制为了避免操作太频繁造成的数据错乱。
    boolean isMove = false;
    //广告栏
    private ViewPager viewPager;

    private LinearLayout pointGroup;

    //中间可拖动控件
    private DraggableGridViewPager mDraggableGridViewPager;
    private ArrayAdapter<String> mAdapter;

    //网址的集合
    private ArrayList<String> urlLists = new ArrayList<String>();
    String wujiandaoUri = "http://www.rilintech.com";
    String dakaUri = "http://www.rilintech.com";
    String dianyingjieUri = "http://www.rilintech.com";
    String dongliUri = "http://www.rilintech.com";
    String leshiUri = "http://www.rilintech.com";
    // 图片资源集合
    ArrayList<Bitmap> assetsUrlBitmapLists;
    //图片url集合
    List<String> assetsUrlLists;
    // 图片标题集合
    private ArrayList<ImageView> imageList;
    //上一个页面的位置
    protected int lastPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        initData();
        setImageData();

        return view;
    }

    /**
     * 初始化滚动栏
     */
    private void setImageData() {
        imageList = new ArrayList<>();
        for (int i = 0; i < assetsUrlBitmapLists.size(); i++) {

            // 初始化图片资源
            ImageView image = new ImageView(getActivity());
            //image.setImageBitmap(assetsUrlBitmapLists.get(i));
            image.setBackgroundDrawable(new BitmapDrawable(assetsUrlBitmapLists.get(i)));
            imageList.add(image);

            // 添加指示点
            ImageView point = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

		/*
         * 自动循环： 1、定时器：Timer 2、开子线程 while true 循环 3、ColckManager 4、 用handler
		 * 发送延时信息，实现循环
		 */
        isRunning = true;
        // 设置图片的自动滑动
        handler.sendEmptyMessageDelayed(0, 3000);


        mDraggableGridViewPager.setAdapter(mAdapter);

        //  defaultUserChannels.add(new ChannelItem(1, "用药提醒", R.drawable.clock,1, 1));
        // defaultUserChannels.add(new ChannelItem(2, "问卷", R.drawable.questionnaire, 2, 1));
        // defaultUserChannels.add(new ChannelItem(3, "天气", R.drawable.weather, 3, 1));
        //  defaultUserChannels.add(new ChannelItem(4, "哮喘知识", R.drawable.asthma_knowledge,4, 1));
        //  defaultUserChannels.add(new ChannelItem(5, "哮喘日记", R.drawable.asthma_knowledge, 5, 1));
        //  defaultUserChannels.add(new ChannelItem(6, "更多", R.drawable.home_add, 6, 1));
        mAdapter.add("Grid" + 1);
        mAdapter.add("Grid" + 2);
        mAdapter.add("Grid" + 3);
        mAdapter.add("Grid" + 4);
        mAdapter.add("Grid" + 5);
        mAdapter.add("Grid" + 6);

        mDraggableGridViewPager.setOnPageChangeListener(new DraggableGridViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.v(TAG, "onPageScrolled position=" + position + ", positionOffset=" + positionOffset
//                    + ", positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                // Log.i(TAG, "onPageSelected position=" + position);
                Log.i("oo", "4");

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d(TAG, "onPageScrollStateChanged state=" + state);
                Log.i("oo", "3");

            }
        });
        mDraggableGridViewPager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString());

                if (Integer.parseInt(((TextView) view).getTag() + "") == 3) {
                    startActivity(new Intent(getActivity(), WeatherActivity.class));
                }
                if (Integer.parseInt(((TextView) view).getTag() + "") == 1) {
                    startActivity(new Intent(getActivity(), ClockIndexActivity.class));
                }
                if (Integer.parseInt(((TextView) view).getTag() + "") == 2) {
                    startActivity(new Intent(getActivity(), QuestionnaireActivity.class));
                }
                if (Integer.parseInt(((TextView) view).getTag() + "") == 4) {
                    startActivity(new Intent(getActivity(), XiaoChuanActivity.class).putExtra("info", "xiaochuanzhishi"));

                }

                if (Integer.parseInt(((TextView) view).getTag() + "") == 5) {
                    startActivity(new Intent(getActivity(), XiaoChuanRiJiEditActivity.class).putExtra("info", "xiaochuanzhishi"));

                }
                if (Integer.parseInt(((TextView) view).getTag() + "") == 6) {
                    Toast.makeText(getActivity(), "添加更多，敬请期待", Toast.LENGTH_SHORT).show();
                }
                Log.i("oo", "2");

            }
        });
        mDraggableGridViewPager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //showToast(((TextView) view).getText().toString() + " long clicked!!!");

                Log.i("oo", "1");
                return true;
            }
        });
        mDraggableGridViewPager.setOnRearrangeListener(new DraggableGridViewPager.OnRearrangeListener() {
            @Override
            public void onRearrange(int oldIndex, int newIndex) {
                // Log.i(TAG, "OnRearrangeListener.onRearrange from=" + oldIndex + ", to=" + newIndex);
                String item = mAdapter.getItem(oldIndex);
                mAdapter.setNotifyOnChange(false);
                mAdapter.remove(item);
                mAdapter.insert(item, newIndex);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        /**
         * 页面切换后调用
         * position  新的页面位置
         */
        public void onPageSelected(int position) {

            position = getPosition(position);
            // 设置文字描述内容
            //iamgeDesc.setText(imageDescriptions[position]);
            // 改变指示点的状态
            // 把当前点enbale 为true
            pointGroup.getChildAt(position).setEnabled(true);
            // 把上一个点设为false
            pointGroup.getChildAt(lastPosition).setEnabled(false);
            lastPosition = position;
        }

        //页面正在滑动的时候，回调
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        //当页面状态发生变化的时候，回调
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 获取真实的位置
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        position = position % imageList.size();
        return position;
    }

    /**
     * 判断是否自动滚动
     */
    private boolean isRunning = false;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 让viewPager 滑动到下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            if (isRunning) {
                handler.sendEmptyMessageDelayed(0, 3000);
            }
        }

        ;
    };


    private class MyPagerAdapter extends PagerAdapter {
        /**
         * 获得页面的总数
         */
        @Override
        public int getCount() {
            return Integer.MAX_VALUE; // 使得图片可以循环
        }

        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            // 给 container 添加一个view
            View view = imageList.get(getPosition(position));
            container.addView(view);
            if (position == 0) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String url = "https://mp.weixin.qq.com/s?__biz=MzAxMjMxMzAzOQ==&mid=203135820&idx=1&sn=6a4baedd7b1c345060337aacf14ce024&scene=1&srcid=12218ohGI6PKIYvVSoAeCcrg&key=710a5d99946419d9035331685f71cf09335e57ea9af1bb723e426e36a4388b9e354ba4362aa0117357aa1f1b6bcb49b7&ascene=0&uin=MTI5NTY5NjgxNQ%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.11.1+build(15B42)&version=11020201&pass_ticket=tICNpVzk0wxPZxBukQTs81kDBq5sDBIdFIqTdwDVA7E6NrjCfGH4LlxUUnoZEKwH";
                        Bundle bundle = new Bundle();
                        bundle.putString("url", url);
                        Intent intent = new Intent(getActivity(), BaseWebActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);

//                        Intent intent = new Intent(getActivity(), TestVedioActivity.class);
//
//                        getActivity().startActivity(intent);
                    }
                });
            } else if (position == 1) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), XiaoChuanActivity.class);
                        intent.putExtra("info", "xiaochuanyaowu");
                        getActivity().startActivity(intent);
                    }
                });
            } else if (position == 2) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), XiaoChuanActivity.class);
                        intent.putExtra("info", "xiaochuanjibingguanli");
                        getActivity().startActivity(intent);

                    }
                });

            } else if (position == 3) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String url = "https://mp.weixin.qq.com/s?__biz=MzA5MjUxOTgyMA==&mid=203270042&idx=1&sn=74dbe163876f747a034d30766ba528ff&scene=1&srcid=04065Lj0XcydkIsIMMwhSNLZ&key=710a5d99946419d94047138e5fd5c4ece3202817b40bcfb5d793fdfd49264d368869dd8e108eb2afeea67abc34157b67&ascene=0&uin=MTI5NTY5NjgxNQ%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.11.1+build(15B42)&version=11020201&pass_ticket=tICNpVzk0wxPZxBukQTs81kDBq5sDBIdFIqTdwDVA7E6NrjCfGH4LlxUUnoZEKwH";
                        Bundle bundle = new Bundle();
                        bundle.putString("url", url);
                        Intent intent = new Intent(getActivity(), BaseWebActivity.class);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });


            }
            // 返回一个和该view相对的object
            return imageList.get(getPosition(position));
        }

        /**
         * 判断 view和object的对应关系
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            if (view == object) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 销毁对应位置上的object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // System.out.println("destroyItem  ::" + position);
            container.removeView((View) object);

        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //网址
        urlLists.add(wujiandaoUri);
        urlLists.add(dakaUri);
        urlLists.add(dianyingjieUri);
        urlLists.add(dongliUri);
        // urlLists.add(leshiUri);

        userChannelList = ((ArrayList<ChannelItem>) ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).getUserChannel());
        userAdapter = new DragAdapter(getActivity(), userChannelList);
        //userGridView.setAdapter(userAdapter);
        //设置GRIDVIEW的ITEM的点击监听
        // userGridView.setOnItemClickListener(new MyOnItemClickListener());
        assetsUrlLists = new ArrayList<>();
        final SdcardTools sdcardTools = new SdcardTools(getActivity());
        boolean state = sdcardTools.getSdcardState();
        //if (state) {
            /*if (sdcardTools.isDirFileExist("307Image/")) {

                String imagePath_1 = "/mnt/sdcard/307Image/a.jpg";
                String imagePath_2 = "/mnt/sdcard/307Image/b.jpg";
                String imagePath_3 = "/mnt/sdcard/307Image/c.jpg";
                String imagePath_4 = "/mnt/sdcard/307Image/d.jpg";
                //String imagePath_5 = "/mnt/sdcard/307Image/e.jpg";
                String url_1 = ImageDownloader.Scheme.FILE.wrap(imagePath_1);
                String url_2 = ImageDownloader.Scheme.FILE.wrap(imagePath_2);
                String url_3 = ImageDownloader.Scheme.FILE.wrap(imagePath_3);
                String url_4 = ImageDownloader.Scheme.FILE.wrap(imagePath_4);
                //String url_5 = ImageDownloader.Scheme.FILE.wrap(imagePath_5);
                assetsUrlLists.clear();
                assetsUrlLists.add(url_1);
                assetsUrlLists.add(url_2);
                assetsUrlLists.add(url_3);
                assetsUrlLists.add(url_4);
                //assetsUrlLists.add(url_5);

                loadImage();

            } else {*/

                final String assetsUrl_1 = ImageDownloader.Scheme.ASSETS.wrap("a.jpg");
                final String assetsUrl_2 = ImageDownloader.Scheme.ASSETS.wrap("b.jpg");
                final String assetsUrl_3 = ImageDownloader.Scheme.ASSETS.wrap("c.jpg");
                final String assetsUrl_4 = ImageDownloader.Scheme.ASSETS.wrap("d.jpg");
                //final String assetsUrl_5 = ImageDownloader.Scheme.ASSETS.wrap("e.jpg");

                assetsUrlLists.add(assetsUrl_1);
                assetsUrlLists.add(assetsUrl_2);
                assetsUrlLists.add(assetsUrl_3);
                assetsUrlLists.add(assetsUrl_4);
                //assetsUrlLists.add(assetsUrl_5);

                loadImage();

//                new Thread(new Runnable() {
//                    InputStream is_1, is_2, is_3, is_4;
//                    //InputStream is_5;
//
//                    @Override
//                    public void run() {
//                        try {
//                            File dirFile = sdcardTools.createDirOnSDCard("307Image/");
//                            is_1 = getActivity().getAssets().open("a.jpg");
//                            is_2 = getActivity().getAssets().open("b.jpg");
//                            is_3 = getActivity().getAssets().open("c.jpg");
//                            is_4 = getActivity().getAssets().open("d.jpg");
//                            //is_5 = getActivity().getAssets().open("e.jpg");
//
//                            sdcardTools.writeData2SDCard("myImage/", "a.jpg", is_1);
//                            sdcardTools.writeData2SDCard("myImage/", "b.jpg", is_2);
//                            sdcardTools.writeData2SDCard("myImage/", "c.jpg", is_3);
//                            sdcardTools.writeData2SDCard("myImage/", "d.jpg", is_4);
//                            //sdcardTools.writeData2SDCard("myImage/", "e.jpg", is_5);
//
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        } finally {
//                            if (is_1 != null) {
//                                try {
//                                    is_1.close();
//                                    is_1 = null;
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                            if (is_2 != null) {
//                                try {
//                                    is_2.close();
//                                    is_2 = null;
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                            if (is_3 != null) {
//                                try {
//                                    is_3.close();
//                                    is_3 = null;
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                            if (is_4 != null) {
//                                try {
//                                    is_4.close();
//                                    is_4 = null;
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                            /*if (is_5 != null) {
//                                try {
//                                    is_5.close();
//                                    is_5 = null;
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }*/
//                        }
//                    }
//                }).start();
            //}
//        } else {
//            Intent intent = new Intent(getActivity(), MyAlertDialog.class);
//            intent.putExtra("title", "注意");
//            intent.putExtra("msg", "SD卡不存在");
//            startActivity(intent);
//        }
    }

    /**
     * 加载url地址的图片
     */
    private void loadImage() {
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        assetsUrlBitmapLists = new ArrayList<>();
        assetsUrlBitmapLists.clear();
        ImageSize mImageSize = new ImageSize(658, 411);
        for (int i = 0; i < assetsUrlLists.size(); i++) {
            Bitmap bitmap = imageLoader.loadImageSync(assetsUrlLists.get(i), mImageSize, options);
            assetsUrlBitmapLists.add(bitmap);
        }
    }

    /**
     * 初始化布局
     */
    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        pointGroup = (LinearLayout) view.findViewById(R.id.point_group);

        // userGridView = (DragGrid) view.findViewById(R.id.userGridView);
        title_bar = (RelativeLayout) view.findViewById(R.id.title_bar);
        mDraggableGridViewPager = (DraggableGridViewPager) view.findViewById(R.id.draggable_grid_view_pager);

        mAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), 0) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final String text = getItem(position);
                if (convertView == null) {

                    //  defaultUserChannels.add(new ChannelItem(1, "用药提醒", R.drawable.clock,1, 1));
                    // defaultUserChannels.add(new ChannelItem(2, "问卷", R.drawable.questionnaire, 2, 1));
                    // defaultUserChannels.add(new ChannelItem(3, "天气", R.drawable.weather, 3, 1));
                    //  defaultUserChannels.add(new ChannelItem(4, "哮喘知识", R.drawable.asthma_knowledge,4, 1));
                    //  defaultUserChannels.add(new ChannelItem(5, "哮喘日记", R.drawable.asthma_knowledge, 5, 1));
                    //  defaultUserChannels.add(new ChannelItem(6, "更多", R.drawable.home_add, 6, 1));
                    convertView = (TextView) getActivity().getLayoutInflater().inflate(R.layout.draggable_grid_item, null);
                    if (text.equals("Grid2")) {
                        convertView.setBackgroundResource(R.drawable.questionnaire);
                        convertView.setTag(2);
                    } else if (text.equals("Grid3")) {
                        convertView.setBackgroundResource(R.drawable.weather);
                        convertView.setTag(3);
                    } else if (text.equals("Grid4")) {
                        convertView.setBackgroundResource(R.drawable.asthma_knowledge);
                        convertView.setTag(4);
                    } else if (text.equals("Grid5")) {
                        convertView.setBackgroundResource(R.drawable.xiaochuanriji);
                        convertView.setTag(5);
                    } else if (text.equals("Grid6")) {
                        convertView.setBackgroundResource(R.drawable.home_add);
                        convertView.setTag(6);
                    }
                }
                //  ((TextView) convertView).setText(text);
                return convertView;
            }

        };
    }

    /**
     * 退出时候保存选择后数据库的设置
     */
    private void saveChannel() {
        ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).deleteAllChannel();
        ChannelManage.getManage(AppApplication.getApp().getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ChannelItem item = userChannelList.get(position);
            String name = item.getName();
            if ("天气".equals(name)) {
                startActivity(new Intent(getActivity(), WeatherActivity.class));
            }
            if ("用药提醒".equals(name)) {
                startActivity(new Intent(getActivity(), ClockIndexActivity.class));
            }
            if ("问卷".equals(name)) {
                startActivity(new Intent(getActivity(), QuestionnaireActivity.class));
            }
            if ("哮喘知识".equals(name)) {
                Intent intent = new Intent(getActivity(), XiaoChuanActivity.class);
                intent.putExtra("info", "xiaochuanzhishi");
                startActivity(intent);
            }
            if ("更多".equals(name)) {
                Toast.makeText(getActivity(), "添加更多，敬请期待", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroyView() {
        saveChannel();
        isRunning = false;
        super.onDestroyView();
    }


}

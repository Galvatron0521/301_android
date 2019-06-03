package com.rilintech.fragment_301_huxike_android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.L;
import com.rilintech.fragment_301_huxike_android.R;
import com.rilintech.fragment_301_huxike_android.activity.PersonActivity;
import com.rilintech.fragment_301_huxike_android.http.Url;
import com.rilintech.fragment_301_huxike_android.http.UserModel;
import com.rilintech.fragment_301_huxike_android.utils.ActivityManager;
import com.rilintech.fragment_301_huxike_android.utils.SharedPrefsUtil;
import com.rilintech.fragment_301_huxike_android.view.CircleImageView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by rilintech on 16/4/10.
 */
public class PersonCenterFragment extends Fragment{

    private View contentView;
    private LinearLayout userLinearLayout, fanKuiLinearLayout, banBenLinearLayout;
    public CircleImageView headImageView;
    private Button exitButton;
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.fragment_personcenter, null);

        userLinearLayout = (LinearLayout)contentView.findViewById(R.id.person);
        fanKuiLinearLayout = (LinearLayout)contentView.findViewById(R.id.fankui);

        linearLayout = (LinearLayout)contentView.findViewById(R.id.linears);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d("dddddd");
            }
        });

        userLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });

        fanKuiLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果发生错误，请查看logcat日志
                Map<String, String> mUICustomInfo = new HashMap();
                mUICustomInfo.put("themeColor", "#00a0e9");
                FeedbackAPI.setUICustomInfo(mUICustomInfo);
                FeedbackAPI.openFeedbackActivity(getActivity());

            }
        });

        headImageView = (CircleImageView)contentView.findViewById(R.id.head);
        headImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                intent.setShowGif(true);
                getActivity().startActivityForResult(intent, 200);
            }
        });

        banBenLinearLayout = (LinearLayout)contentView.findViewById(R.id.banben);
        banBenLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //版本
            }
        });


        String imageUri = Url.HEADURL+ SharedPrefsUtil.getValue(getActivity(), "data", "userId", null);
        //String imageUrl = "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg";
        UserModel userModel = new UserModel();
        userModel.getImageUrl(getActivity(), imageUri, new UserModel.ResultEventCallBack() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, final String responseString) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject dataJson = new JSONObject(responseString);
                            String url = dataJson.getString("url");

                            ImageSize mImageSize = new ImageSize(100, 100);
                            //显示图片的配置
                            DisplayImageOptions options = new DisplayImageOptions.Builder()
                                    .cacheInMemory(true)
                                    //.cacheOnDisc(true)
                                    .cacheOnDisc(true)
                                    .bitmapConfig(Bitmap.Config.RGB_565)
                                    .build();

                            ImageLoader.getInstance().loadImage(Url.IP+url, mImageSize, options, new SimpleImageLoadingListener() {

                                @Override
                                public void onLoadingComplete(String imageUri, View view,
                                                              Bitmap loadedImage) {
                                    super.onLoadingComplete(imageUri, view, loadedImage);
                                    headImageView.setImageBitmap(loadedImage);
                                }

                            });
                        }catch (Exception e){

                        }
                    }
                });
            }

            @Override
            public void onError(int statusCode, Header[] headers, String responseString) {

            }
        });

        exitButton = (Button)contentView.findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityManager.getInstance().finshAllActivities();
            }
        });

        return contentView;
    }

}

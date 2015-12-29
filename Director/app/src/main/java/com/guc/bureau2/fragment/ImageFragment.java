package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ImageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView imageView=new ImageView(getActivity());
        ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(viewPagerImageViewParams);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(getArguments().getInt("resId"));
        return imageView;
    }

    public static ImageFragment newInstance(int resId) {
        Bundle args = new Bundle();
        args.putInt("resId",resId);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

}

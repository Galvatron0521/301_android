package com.rilintech.fragment_301_huxike_android.utils;

import com.rilintech.fragment_301_huxike_android.bean.XiaoChuanRiJi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rilintech on 16/4/25.
 */
public class SingleRiJi {
          private static SingleRiJi instance;
          private static  List<XiaoChuanRiJi> viewList;
          private SingleRiJi(){

          }
          public static SingleRiJi getInstance() {
              if (instance == null) {
                      instance = new SingleRiJi();
              }
              return instance;
          }

    public static List<XiaoChuanRiJi> getViewList() {
        return viewList;
    }

    public static void setViewList(List<XiaoChuanRiJi> viewList) {
        SingleRiJi.viewList = viewList;
    }
}

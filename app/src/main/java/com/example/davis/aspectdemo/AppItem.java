package com.example.davis.aspectdemo;

import java.io.Serializable;

/**
 * Created by davis on 2019/3/21.
 */

public class AppItem implements Serializable {

    public String packageName;
    public String  downloadUrl;
    public int number;

    @Override
    public String toString() {
        return packageName  + "    "+downloadUrl+"    " +number;
    }
}

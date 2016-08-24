package com.iammsun.sample.navigator;

import com.iammsun.navigator.annotation.Nav;

@Nav(value = "app://activities/a", stringParams = {"source"}, intParams = {"index"}, dataParams =
        "data")
public class A extends BaseActivity {
}

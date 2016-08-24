package com.iammsun.sample.navigator;

import com.iammsun.navigator.annotation.Nav;

@Nav(value = {"app://activities/c", "app://activities/c2"}, stringParams = {"source"})
public class C extends BaseActivity {
}

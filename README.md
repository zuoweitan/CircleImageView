# CircleImageView
A circular ImageView simply used as normal ImageView.

![CircleImageView](https://github.com/zuoweitan/CircleImageView/raw/master/screen_shot.png)

it's easy to use just like ImageView,and you never need to **do this**:

* create a new bitmap.
* fixed scale type.(CENTER_CROP)

you can simply use the library like Picasso or Glide with this view.

Gradle
------
```
dependencies {
    ...
    compile 'com.vivifarm.view:circleimageview:1.2.0'
}
```

Usage
-----
```xml
<com.zuowei.circleimageview.CircleImageView
  android:src="@mipmap/lufei"
  android:scaleType="centerCrop"
  style="@style/circle_style" />
  
<com.zuowei.circleimageview.CircleImageView
  android:src="@mipmap/lufei"
  android:scaleType="centerInside"
  style="@style/circle_style" />

<com.zuowei.circleimageview.CircleImageView
  android:src="@mipmap/lufei"
  android:scaleType="fitXY"
  style="@style/circle_style" />

<com.zuowei.circleimageview.CircleImageView
  android:src="@mipmap/lufei"
  android:scaleType="fitCenter"
  style="@style/circle_style" />
  
<com.zuowei.circleimageview.CircleImageView
  android:src="@mipmap/lufei"
  android:scaleType="matrix"
  style="@style/circle_style" />

```

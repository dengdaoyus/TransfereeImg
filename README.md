# Transferee
transferee 可以帮助你完成从缩略图到原图的无缝过渡转变，功能体系仿照并涵盖 ios 版本的 QQ、微信朋友圈、新浪微博的图片浏览功能。

transferee 支持两种模式：

1. 只有原图，就是说九宫格列表中的图片和全屏显示的大图其实来源于一张图片。详见 [UniversalNoThumActivity](https://github.com/Hitomis/transferee/blob/trans/simple/app/src/main/java/com/hitomi/transferimage/activity/universal/UniversalNoThumActivity.java)
2. 既有原图，又有缩略图，例如我司使用了阿里云的图片裁剪功能提供了缩略图来源，在列表页使用阿里云裁剪后的缩略图，优化列表数据流量和流畅度，同时又能在详情页或者图片查看器中显示大图。这种情况下也是 transferee 最适合的模式。详见[UniversalNormalActivity](https://github.com/Hitomis/transferee/blob/master/app/src/main/java/com/hitomi/transferimage/activity/universal/UniversalNormalActivity.java)

如有任何问题可以提 Issues

# Preview
<img src="preview/transferee1.gif" />
<img src="preview/transferee2.gif" />


# Usage

step 1: 一个页面只创建一个 transferee 示例 (建议写在 onCreate 方法中)
```
transferee = Transferee.getDefault(context);
```

setp 2: 为 transferee 创建参数配置器，一般配置固定不变的参数
```
TransferConfig config = TransferConfig.build()
       .setSourceImageList(sourceImageList)
       .setThumbnailImageList(thumbnailImageList)
       .setMissPlaceHolder(R.mipmap.ic_empty_photo)
       .setErrorPlaceHolder(R.mipmap.ic_empty_photo)
       .setProgressIndicator(new ProgressPieIndicator())
       .setIndexIndicator(new NumberIndexIndicator())
       .setJustLoadHitImage(true)
       .setListView(gvImages)
       .setImageId(R.id.image_view)
       .setOnLongClcikListener(new Transferee.OnTransfereeLongClickListener() {
           @Override
           public void onLongClick(ImageView imageView, int pos) {
               saveImage(imageView);
           }
       })
       .create();
```

setp 3: 显示 transferee
```
config.setNowThumbnailIndex(position);
transferee.apply(config).show();
```

# Config
| 属性 | 说明 |
| :--: | :--: |
| nowThumbnailIndex | 缩略图在图组中的索引 |
| offscreenPageLimit | 显示 transferee 时初始化加载的图片数量, 默认为1, 表示第一次加载3张(nowThumbnailIndex, nowThumbnailIndex + 1, nowThumbnailIndex - 1); 值为 2, 表示加载5张。依次类推 |
| missPlaceHolder | 缺省的占位图，资源 id 格式。图片未加载完成时默认显示的图片 |
| missDrawable | 缺省的占位图，Drawable 格式。图片未加载完成时默认显示的图片 |
| errorPlaceHolder | 加载错误的占位图，资源 id 格式。原图加载错误时显示的图片 |
| errorDrawable | 加载错误的占位图，Drawable 格式。原图加载错误时显示的图片 |
| backgroundColor | transferee 显示时，图片后的背景色 |
| duration | transferee 播放过渡动画的动画时长 |
| justLoadHitImage | 是否只加载当前显示在屏幕中的的原图。如果设置为 true，那么只有当 transferee 切换到当前页面时，才会触发当前页面原图的加载动作，否则按 offscreenPageLimit 所设置的数值去做预加载和当前页面的加载动作 |
| originImageList | 缩略图 ImageView 集合 |
| thumbnailImageList | 缩略图路径集合 |
| sourceImageList | 原图路径集合 |
| progressIndicat | 图片加载进度指示器 (默认内置 ProgressPieIndicator 和 ProgressBarIndicator)。可实现 IProgressIndicator 接口定义自己的图片加载进度指示器 |
| indexIndicator | 图片索引指示器 (默认内置 CircleIndexIndicator 和 NumberIndexIndicator)。可实现 IIndexIndicator 接口定义自己的图片索引指示器 |
| imageLoader | 图片加载器 (默认 UniversalImageLoader )。可实现 ImageLoader 接口定义自己的图片加载器 |
| imageId | RecyclerView 或者 ListView 的 ItemView 中的 ImageView id|
| listView | 如果你是使用的 ListView 或者 GridView 来排列显示图片，那么需要将你的 ListView 或者 GridView 绑定到 listView 参数 |
| recyclerView | 如果你使用的 RecyclerView 来排列显示图片，需要将 RecyclerView 绑定到 recyclerView 参数 |

# Method
| 方法 | 说明 |
| :--: | :--: |
| getDefault(context) | 获取 transferee 实例 |
| apply(config) | 将配置参数对象应用到 transferee 实例中 |
| show() | 打开/显示 transferee |
| show(listener) | 打开/显示 transferee，并监听显示/关闭状态 |
| isShown() | transferee 是否显示 |
| dismiss() | 关闭 transferee |
| clear(imageLoader) | 清除 ImageLoader 中加载的缓存 |
| setOnTransfereeStateChangeListener(listener) | 设置 transferee 显示/关闭状态改变的监听器 |

# Update log
+ v0.5
  - 优化打开或者关闭 transferee 时背景色显示效果，从直接显示背景色优化为渐变显示。
  - 基于 [#26](https://github.com/Hitomis/transferee/issues/26) 添加 transferee 使用本地图片场景下的 Demo
  - 为 transferee 组件添加背景色自定义功能
  - 为 transferee 组件添加长按监听器，并添加了长按保存图片到相册的示例代码
  - 更新了部分示例代码中失效的图片地址

# Todo
+ [x] 支持高清大图和长图浏览
+ [x] 支持扩展图片保存、收藏等相关操作
+ [x] 优化 TransferConfig 的参数配置。将固定不变的配置项从每次的参数对象创建中分离开来
+ [ ] 优化单个图片使用 transferee 的场景


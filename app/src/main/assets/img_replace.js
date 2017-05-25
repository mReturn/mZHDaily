
//正在加载
var DEFAULT_LOADING_IMG_URI = "file:///android_asset/default_pic_content_image_loading_light.png";
var DEFAULT_LOADING_IMG_URI_DARK = "file:///android_asset/default_pic_content_image_loading_dark.png";
//点击图片下载
var DEFAULT_IMG_URI = "file:///android_asset/default_pic_content_image_download_light.png";
var DEFAULT_IMG_URI_DARK = "file:///android_asset/default_pic_content_image_download_dark.png";

var default_attr = "reimg-src"

/**
 *  替换图片 默认显示正在加载
 *  wifi状态下替换为正在加载 移动网络时替换为点击加载
 *  source 网络地址  replaceSource 替换地址（DEFAULT_IMG...）
 */
function img_replace(source,replaceSource){
    $('img[reimg-src*="'+source+'"]').each(function(){
        $(this).attr('src',replaceSource)
    })
}

 // 加载图片，调用loadImage方法，wifi下自动调用
 function onLoaded(){
    var allImg = document.querySelectorAll("img");
    allImg = Array.prototype.slice.call(allImg,0);
    allImg.forEach(function(image){
        if(image.src == DEFAULT_IMG_URI){
            ZhihuDaily.loadImage(image.getAttribute(default_attr));
        }
    });
 }

 //图片点击事件
 function onImageClick(mImage){
    if(mImage.src == DEFAULT_LOADING_IMG_URI) {
        //点击加载
        mImage.src = DEFAULT_IMG_URI;
        ZhihuDaily.clickToLoadImage(mImage.getAttribute(default_attr));
    }else if(mImage.src == DEFAULT_LOADING_IMG_URI_DARK) {
        //点击加载
        mImage.src = DEFAULT_IMG_URI;
        ZhihuDaily.clickToLoadImage(mImage.getAttribute(default_attr));
    }else {
        //点击放大
        ZhihuDaily.openImage(mImage.getAttribute(default_attr));
    }
 }

 /**
 * 图片加载完调用此方法
 * netUrl 网络地址  loaclUrI 本地地址
 */
 function onImageLoadingComplete(netUrl, localUrI){
    var allImg = document.querySelectorAll("img")；
    allImg = Array.prototype.slice.call(allImg,0);
    allImg.forEach(function(image){
        if(image.getAttribute(default_attr) == netUrl ||
         image.getAttribute(default_attr) == decodeURIComponent(loaclUrI)){
            image.src = loaclUrI;
         }
    });
 }

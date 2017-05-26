
var DEFAULT_IMAGE_URI = "file:///android_asset/default_pic_content_image_loading_light.png";
var DEFAULT_LOADING_IMAGE_URI = "file:///android_asset/default_pic_content_image_download_light.png";

var DEFAULT_IMAGE_URI_DARK = "file:///android_asset/default_pic_content_image_loading_dark.png";
var DEFAULT_LOADING_IMAGE_URI_DARK = "file:///android_asset/default_pic_content_image_download_dark.png";

var default_attr = "zhimg-src"


/**
 *  wifi状态下替换为正在加载。。。DEFAULT_IMAGE_URI
 *  移动网络状态下替换为点击图片加载。。。DEFAULT_LOADING_IMAGE_URI
 *  source:网络地址     replaceSource:DEFAULT_IMAGE_URI/DEFAULT_LOADING_IMAGE_URI
 *
 */
function img_replace(source, replaceSource) {// 图片替换,默认显示正在加载。。。
    $('img[zhimg-src*="'+source+'"]').each(function () {
        $(this).attr('src', replaceSource);
    });
}

/**
 *  wifi状态下自动调用
 */
function onLoaded() { // 加载图片，调用BihuDaily的loadImage方法
	var allImage = document.querySelectorAll("img");
	allImage = Array.prototype.slice.call(allImage, 0);
	allImage.forEach(function(image) {
		if (image.src == DEFAULT_IMAGE_URI || image.src == DEFAULT_IMAGE_URI_DARK) {
			ZhihuDaily.loadImage(image.getAttribute(default_attr));
		}
	});
}

function onImageClick(pImage) { // 图片点击事件
	if (pImage.src == DEFAULT_LOADING_IMAGE_URI) { // 点击图片开始加载
	    pImage.src=DEFAULT_IMAGE_URI;// 图片加载中
		ZhihuDaily.clickToLoadImage(pImage.getAttribute(default_attr));
	}else if(pImage.src == DEFAULT_LOADING_IMAGE_URI_DARK){
	    pImage.src=DEFAULT_IMAGE_URI_DARK;// 图片加载中
    	ZhihuDaily.clickToLoadImage(pImage.getAttribute(default_attr));
	}else { // 打开查看图片界面
		ZhihuDaily.openImage(pImage.getAttribute(default_attr));
	}
};

/**
 *  每张图片加载完成时调用此方法
 *  pOldUrl:网络地址        pNewUrl:图片下载完成保存在本地地址
 *
 */
function onImageLoadingComplete(pOldUrl, pNewUrl) {
	var allImage = document.querySelectorAll("img");
	allImage = Array.prototype.slice.call(allImage, 0);
	allImage.forEach(function(image) {
		if (image.getAttribute(default_attr) == pOldUrl || image.getAttribute(default_attr) == decodeURIComponent(pOldUrl)) {
			image.src = pNewUrl;
		}
	});
}
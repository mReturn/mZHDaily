package com.mreturn.zhihudaily.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/23.
 */

public class StoriesBean extends BaseStoryBean implements Parcelable {
    /**
     * title : 走，我们去老铁山看「超凶」的鸟
     * ga_prefix : 051913
     * images : ["https://pic1.zhimg.com/v2-42a6ccb94d689d2cdbd5c10c367242dc.jpg"]
     * multipic : true
     * type : 0
     * id : 9427065
     */

    private String title;
    private String ga_prefix;
    private boolean multipic;
    private int type;
    private int id;
    private List<String> images;

    private String date;
    private boolean isRead;
    private boolean haveImg;

    public static final int TYPE_STORY = 0;
    public static final int TYPE_NO_IMG_STORY = 4;

    public StoriesBean() {
    }

    public StoriesBean(int showType) {
        this.showType = showType;
    }

    public StoriesBean(String date, int showType) {
        this.date = date;
        this.showType = showType;
    }

    public StoriesBean(int id, String title, String image, String date) {
        this.id = id;
        this.title = title;
        if (image == null || image.length() == 0 || image.equals("")) {
            setShowType(TYPE_NO_IMG_STORY);
            this.haveImg = false;
        } else {
            this.haveImg = true;
            images = new ArrayList<>();
            images.add(image);
        }
        this.date = date;
    }

    public StoriesBean(int id, String title, String image, String date, boolean multipic, boolean isRead) {
        this(id, title, image, date);
        this.multipic = multipic;
        this.isRead = isRead;
    }

    protected StoriesBean(Parcel in) {
        title = in.readString();
        ga_prefix = in.readString();
        multipic = in.readByte() != 0;
        type = in.readInt();
        id = in.readInt();
        images = in.createStringArrayList();
        date = in.readString();
        isRead = in.readByte() != 0;
        haveImg = in.readByte() != 0;
    }

    public static final Creator<StoriesBean> CREATOR = new Creator<StoriesBean>() {
        @Override
        public StoriesBean createFromParcel(Parcel in) {
            return new StoriesBean(in);
        }

        @Override
        public StoriesBean[] newArray(int size) {
            return new StoriesBean[size];
        }
    };

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isHaveImg() {
        return haveImg;
    }

    public void setHaveImg(boolean haveImg) {
        this.haveImg = haveImg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public boolean isMultipic() {
        return multipic;
    }

    public void setMultipic(boolean multipic) {
        this.multipic = multipic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(ga_prefix);
        parcel.writeByte((byte) (multipic ? 1 : 0));
        parcel.writeInt(type);
        parcel.writeInt(id);
        parcel.writeStringList(images);
        parcel.writeString(date);
        parcel.writeByte((byte) (isRead ? 1 : 0));
        parcel.writeByte((byte) (haveImg ? 1 : 0));
    }
}

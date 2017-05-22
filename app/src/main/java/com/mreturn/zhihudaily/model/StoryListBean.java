package com.mreturn.zhihudaily.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mreturn.zhihudaily.adapter.BaseAdapterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/19.
 */

public class StoryListBean implements Parcelable{


    /**
     * date : 20170519
     * stories : [{"title":"走，我们去老铁山看「超凶」的鸟","ga_prefix":"051913","images":["https://pic1.zhimg.com/v2-42a6ccb94d689d2cdbd5c10c367242dc.jpg"],"multipic":true,"type":0,"id":9427065},{"images":["https://pic1.zhimg.com/v2-5ce92900ee72c1cbe11bd2701f800fd0.jpg"],"type":0,"id":9429042,"ga_prefix":"051912","title":"两场西部决赛，两次争议垫脚，NBA 应该是这样的吗？"},{"images":["https://pic2.zhimg.com/v2-4dbaa473fa861761bfdc95e2815b5739.jpg"],"type":0,"id":9428130,"ga_prefix":"051912","title":"大误 · 午睡前的爽朗小故事"},{"images":["https://pic1.zhimg.com/v2-f40a853210f253e72ab66a6dc6e03fa8.jpg"],"type":0,"id":9428781,"ga_prefix":"051910","title":"「输液」是怎么被发明出来的？"},{"images":["https://pic3.zhimg.com/v2-3b0c73c026afbebb5ddd8f481d356f52.jpg"],"type":0,"id":9427497,"ga_prefix":"051909","title":"从现在起，Google 的每一个比特，都是人工智能"},{"images":["https://pic2.zhimg.com/v2-ec70dc71e9fd101aea8104de7d132ffd.jpg"],"type":0,"id":9428540,"ga_prefix":"051908","title":"手动挡和自动挡，你喜欢开哪种？"},{"images":["https://pic4.zhimg.com/v2-fa320acfb8124615b9c0937a6d709537.jpg"],"type":0,"id":9423171,"ga_prefix":"051907","title":"- 有哪些简短而激励人心的话？\r\n- 今天周五"},{"images":["https://pic2.zhimg.com/v2-3bc1c33017d3df8bc45dde83d8822d51.jpg"],"type":0,"id":9428478,"ga_prefix":"051907","title":"臭氧污染要来了，常规手段防不住，但也不用太担心"},{"images":["https://pic1.zhimg.com/v2-ea1eb90aaef1cf4312ed26c048a991e4.jpg"],"type":0,"id":9428510,"ga_prefix":"051907","title":"不要把公积金当负担，这是福利啊"},{"images":["https://pic2.zhimg.com/v2-252c1aad88e01d6640ab9f520959bb79.jpg"],"type":0,"id":9427620,"ga_prefix":"051906","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic2.zhimg.com/v2-c5c2c9a63f384cfc9a7ecff7c669b59d.jpg","type":0,"id":9429042,"ga_prefix":"051912","title":"两场西部决赛，两次争议垫脚，NBA 应该是这样的吗？"},{"image":"https://pic1.zhimg.com/v2-773fd5cd8bb84657148ede88c04bbd38.jpg","type":0,"id":9427497,"ga_prefix":"051909","title":"从现在起，Google 的每一个比特，都是人工智能"},{"image":"https://pic4.zhimg.com/v2-318bb3b3320942f3075d3b61e6d39caf.jpg","type":0,"id":9428510,"ga_prefix":"051907","title":"不要把公积金当负担，这是福利啊"},{"image":"https://pic1.zhimg.com/v2-0439996d6da41b66ea544db59d5b36ac.jpg","type":0,"id":9428478,"ga_prefix":"051907","title":"臭氧污染要来了，常规手段防不住，但也不用太担心"},{"image":"https://pic4.zhimg.com/v2-9cefd8baab0e69e346ee3362a67ff9f3.jpg","type":0,"id":9427389,"ga_prefix":"051814","title":"还记得初中课本说的可燃冰吗？它终于被中国首次成功开采"}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    protected StoryListBean(Parcel in) {
        date = in.readString();
        stories = in.createTypedArrayList(StoriesBean.CREATOR);
        top_stories = in.createTypedArrayList(TopStoriesBean.CREATOR);
    }

    public static final Creator<StoryListBean> CREATOR = new Creator<StoryListBean>() {
        @Override
        public StoryListBean createFromParcel(Parcel in) {
            return new StoryListBean(in);
        }

        @Override
        public StoryListBean[] newArray(int size) {
            return new StoryListBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(date);
        parcel.writeTypedList(stories);
        parcel.writeTypedList(top_stories);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean extends BaseAdapterBean implements Parcelable{
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


        public StoriesBean(String date, int showType) {
            this.date = date;
            this.showType = showType;
        }

        public StoriesBean(int id, String title, String image, String date) {
            this.id = id;
            this.title = title;
            if (image == null || image.length() == 0 || image.equals("")) {
                setShowType(StoriesBean.TYPE_NO_IMG_STORY);
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

    public static class TopStoriesBean implements Parcelable{
        /**
         * image : https://pic2.zhimg.com/v2-c5c2c9a63f384cfc9a7ecff7c669b59d.jpg
         * type : 0
         * id : 9429042
         * ga_prefix : 051912
         * title : 两场西部决赛，两次争议垫脚，NBA 应该是这样的吗？
         */

        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public TopStoriesBean(int id, String title, String image) {
            this.id = id;
            this.title = title;
            this.image = image;
        }

        protected TopStoriesBean(Parcel in) {
            image = in.readString();
            type = in.readInt();
            id = in.readInt();
            ga_prefix = in.readString();
            title = in.readString();
        }

        public static final Creator<TopStoriesBean> CREATOR = new Creator<TopStoriesBean>() {
            @Override
            public TopStoriesBean createFromParcel(Parcel in) {
                return new TopStoriesBean(in);
            }

            @Override
            public TopStoriesBean[] newArray(int size) {
                return new TopStoriesBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(image);
            parcel.writeInt(type);
            parcel.writeInt(id);
            parcel.writeString(ga_prefix);
            parcel.writeString(title);
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

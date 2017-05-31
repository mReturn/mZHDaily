package com.mreturn.zhihudaily.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.mreturn.zhihudaily.utils.CommonUtils;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/31.
 */

public class CommentBean {


    private List<Comments> comments;

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public static class Comments extends BaseStoryBean implements Parcelable{
        /**
         * author : 利奥塔的元叙事
         * content : 就是说父母没有从孩子的全面身心健康和发展出发（至少表面上来看），而是仍然只看重某一点尤其是学习成绩，甚至不惜以牺牲其他方面为代价。同时给孩子灌输的理念是：没有学习成绩，就不值得被父母关爱，不值得有朋友；而学习成绩好，就可以不做家务，就可以受宠。其实我现在真的看清，学习成绩只是生活的一部分，从长远发展来看，全面的身心健康才是最重要的。身边有不少这样的例子，上了好大学又怎样呢？有心理阴影、身心扭曲的，活得根本不好，甚至抑郁、自杀，都有可能。参考前一阵很火的文章《北大四成新生曾有自杀念头》。
         * avatar : http://pic1.zhimg.com/b3ed9ae2532f2085dd76248cdf6d02c8_im.jpg
         * time : 1496195564
         * reply_to : {"content":"很多回复都跟你说了同样的话:所谓成绩不好连父母都不会接纳。不过我不太明白，你们所说的接纳是什么意思，是说父母需要无条件接受你做的一切，不能批评指责，否则就是不爱孩子，就是有回报地对待孩子？可实际上成绩好了受益人是孩子自己啊\u2026\u2026","status":0,"id":29156416,"author":"kurama0919"}
         * id : 29156507
         * likes : 0
         */

        private String author;
        private String content;
        private String avatar;
        private int time;
        private ReplyToBean reply_to;
        private int id;
        private int likes;

        public Comments(int typeHeader) {
            showType = typeHeader;
        }

        public Comments(String content,int typeTitle) {
            this.content = content;
            showType =  typeTitle;
        }

        protected Comments(Parcel in) {
            author = in.readString();
            content = in.readString();
            avatar = in.readString();
            time = in.readInt();
            id = in.readInt();
            likes = in.readInt();
            showType = in.readInt();
        }

        public static final Creator<Comments> CREATOR = new Creator<Comments>() {
            @Override
            public Comments createFromParcel(Parcel in) {
                return new Comments(in);
            }

            @Override
            public Comments[] newArray(int size) {
                return new Comments[size];
            }
        };

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTime() {
            return CommonUtils.timestamp2Date(time);
        }

        public void setTime(int time) {
            this.time = time;
        }

        public ReplyToBean getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyToBean reply_to) {
            this.reply_to = reply_to;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(author);
            parcel.writeString(content);
            parcel.writeString(avatar);
            parcel.writeInt(time);
            parcel.writeInt(id);
            parcel.writeInt(likes);
            parcel.writeInt(showType);
        }

        public static class ReplyToBean {
            /**
             * content : 很多回复都跟你说了同样的话:所谓成绩不好连父母都不会接纳。不过我不太明白，你们所说的接纳是什么意思，是说父母需要无条件接受你做的一切，不能批评指责，否则就是不爱孩子，就是有回报地对待孩子？可实际上成绩好了受益人是孩子自己啊……
             * status : 0
             * id : 29156416
             * author : kurama0919
             */

            private String content;
            private int status;
            private int id;
            private String author;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }
        }
    }
}

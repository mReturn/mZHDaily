package com.mreturn.zhihudaily.model;

import java.util.List;

/**
 * Created by mReturn
 * on 2017/5/24.
 */

public class StoryDetailBean {

    /**
     * body : <div class="main-wrap content-wrap">
     <div class="headline">

     <div class="img-place-holder"></div>



     </div>

     <div class="content-inner">




     <div class="question">
     <h2 class="question-title">如何理解 Deepmind 称最新版 AlphaGo 能让李世乭版本的 AlphaGo  三子？</h2>

     <div class="answer">

     <div class="meta">
     <img class="avatar" src="http://pic3.zhimg.com/b689199923bc4c4f8df7842f4c3fe6ea_is.jpg">
     <span class="author">不会功夫的潘达，</span><span class="bio">围棋文化</span>
     </div>

     <div class="content">
     <p>今天，在浙江举办的未来围棋峰会会场上，DeepMind 团队首席科学家 David Sliver 介绍了 AlphaGo 的最新进展。</p>
     <p>其中的这张图，吸引了吃瓜群众的眼球。</p>
     <p><img class="content-image" src="http://pic1.zhimg.com/70/v2-e86cad6c4e7395e897a10f2c0e5101a4_b.jpg" alt="" /></p>
     <p>简单说，就是最新版的 AlphaGo<strong>（以下简称 Master）</strong>，能够让对战李世乭版本的 AlphaGo<strong>（以下简称 AlphaGo-Lee）</strong>三子。让三子是什么概念？有请柯洁为大家解说。</p>
     <p><img class="content-image" src="http://pic3.zhimg.com/70/v2-ca48754428e9ccec1a65d21f46b093fa_b.jpg" alt="" /></p>
     <p>这个问题，我们还可以再深入一点讨论。</p>
     <p>前几年的《围棋天地》杂志，每期有个固定栏目，《36 问》，就是问一位职业棋手 36 个问题。其中一问就是，你认为你和围棋之神的差距多大。我的印象中，除了个别奇葩答案以外，认为棋神让自己三个，双方旗鼓相当的职业棋手是最多的。与之对应，AlphaGo 的机械臂黄士杰博士，去年也曾在推特上说，他也认为围棋之神不能让顶尖职业棋手四个子。</p>
     <p>等等，现在 Master 能让 AlphaGo-Lee 三子，而 AlphaGo-Lee 战胜了李世乭，那么 Master 岂不是至少能让李世乭三子？也就是说，最新版本的 AlphaGo，已经和围棋之神能够平起平坐了？</p>
     <p>非也。</p>
     <p>去年 5 月，就在 AlphaGo- 李世乭五番棋之后，David Silver 在伦敦大学学院做报告，其中也有一张类似的图。</p>
     <p><img class="content-image" src="http://pic1.zhimg.com/70/v2-fa46f8ad9b9f86ecb8769eed4480ba2c_b.jpg" alt="" /></p>
     <p>（这图拍的，应该召唤饶罗翔老师）</p>
     <p>图中说，AlphaGo-Lee 能够让 Nature 杂志上那一版本的 AlphaGo（以下简称 AlphaGo-Nature）三到四子。这里的让子是带贴目的，所以要打一个小小的折扣。打完折扣以后取平均值，可以认为 AlphaGo-Lee 能够让 AlphaGo-Nature 三子。</p>
     <p>注意，在这张图里，AlphaGo-Lee 的等级分是 4500 分！而今天峰会上的图，AlphaGo-Lee 只有 3600+ 分。这也容易解释。在上图的左下角，有一行小字：&ldquo;<strong>注意：等级分评估基于自对弈结果</strong>&rdquo;。也就是说，4500 这个分数是 AlphaGo 的不同版本之间互相战斗之后，根据比分评估的。而 3600+ 这个分数，是根据 AlphaGo-Lee 与人类的对局结果评估的。AlphaGo-Lee 和人类一共就下了五盘棋，也就是对李世乭的 4 胜 1 负。李世乭当时的等级分在 3550 分左右。根据贝叶斯 ELO 算法，将 AlphaGo-Lee 的分数定为 3600+，恰如其分。</p>
     <p>那么问题来了，按照 AlphaGo 自评估的 4500 分，她理应碾压李世乭，为什么最后还输了一局呢？</p>
     <p>这里涉及到一个概念，&ldquo;<strong>过拟合</strong>&rdquo;。</p>
     <p>过拟合，在机器学习领域指，在训练 AI 的过程中，因为样本太少或训练时间太长等原因，训练结果只适应（特殊的）训练样本，而不适应一般情形。用一张图说明。</p>
     <p><img class="content-image" src="http://pic2.zhimg.com/70/v2-e4a8b389c755a70bcd080a36672e2115_b.jpg" alt="" /></p>
     <p>绿线代表过拟合模型，黑线代表正则化模型。虽然绿线完美的匹配训练数据，但太过依赖，并且与黑线相比，对于新的测试数据上具有更高的错误率。 （来自维基百科）</p>
     <p>过拟合这个概念，具体表现到 AlphaGo 身上，就是新版本的 AlphaGo 更善于对付旧版本的 AlphaGo，而相对不善于对付一般性的对手，比如李世乭。</p>
     <p>讲回 DeepMind 团队今天的报告。</p>
     <p><img class="content-image" src="http://pic1.zhimg.com/70/v2-e86cad6c4e7395e897a10f2c0e5101a4_b.jpg" alt="" /></p>
     <p>现在再看这张图，应该就豁然开朗了。Master 的 4700 分，同样是基于自对弈的评分。Master 能让 AlphaGo-Lee 三子，可能含有过拟合的水分。<strong>因此，Master 能否让柯洁三子，以现有数据无法判断。</strong></p>
     <p>也许你想问，Master 到底能让柯洁几个呢？</p>
     <p><strong>我的看法是，棋手的水平越高，让子的难度就越大</strong>。刚学围棋的人，可能被一个连业余段位都没有的棋手让九个以上。水平相当的业余棋手之间，有&ldquo;互让三子好胜负&rdquo;的说法。而据 @傅奇轩 说，李世乭九段经常在北京，找业余 6 段棋手下带彩的让三子棋。一盘一万美金，李世乭大致胜负各半。顶尖职业棋手和高水平的业余棋手相比，可以算是两个世界的人，但最多也就让三个子。想让柯洁三个子，难度非常大，也许棋神能做到。</p>
     <p>另外，AlphaGo 也有技术问题要解决。对李世乭版本的 AlphaGo，逆风局稳定性远不如顺风局。而让子棋从一开始就是大逆风局。AlphaGo 在逆风局会如何表现，是未来的一大看点。</p>
     <p>个人观点。如果现版本的 AlphaGo 对战柯洁，我觉得让两个子会有看头，让三个子我 all in 柯洁。</p>
     <p>柯洁应该对自己有信心。今天他发的微博，大概是这个小心机婊在给自己减压吧。无论如何，明天，持白棋的柯洁，请加油！</p>
     </div>
     </div>


     <div class="view-more"><a href="http://www.zhihu.com/question/60237643">查看知乎讨论<span class="js-question-holder"></span></a></div>

     </div>





     <div class="question">
     <h2 class="question-title"></h2>

     <div class="answer">

     <div class="content">
     <p>更多讨论，查看 知乎圆桌 &middot;&nbsp;<a href="https://www.zhihu.com/roundtable/kejie-vs-alphago?utm_campaign=official_account&amp;utm_source=zhihudaily&amp;utm_medium=link&amp;utm_content=roundtable">人机对弈终章</a></p>
     </div>
     </div>


     </div>


     </div>
     </div>
     * image_source : 《黑客帝国 2：重装上阵》
     * title : AlphaGo：「你先连下三子，摆好了叫我一声就行」
     * image : https://pic1.zhimg.com/v2-987e095aa8447186ace6926a9a3a33d4.jpg
     * share_url : http://daily.zhihu.com/story/9438686
     * js : []
     * ga_prefix : 052414
     * images : ["https://pic3.zhimg.com/v2-44a2bcd519506b6fa92e2697eba4fbd6.jpg"]
     * type : 0
     * id : 9438686
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private String ga_prefix;
    private int type;
    private int id;
    private List<?> js;
    private List<String> images;
    private List<String> css;


    public StoryDetailBean(String body, String image_source, String title, String image) {
        this.body = body;
        this.image_source = image_source;
        this.title = title;
        this.image = image;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
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

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }
}

package com.gaofh.lovehym;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class NewsTitleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView newTitle_listView;
    private NewsAdapter newsAdapter;
    private List<News> newsList;
    private boolean isTwoPaper;
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        newsList=getNews();
        newsAdapter=new NewsAdapter(activity,R.layout.news_item,newsList);

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=(View)inflater.inflate(R.layout.fragment_news_item,container,false);
       newTitle_listView=(ListView) view.findViewById(R.id.fragment_news_title_listView);
       newTitle_listView.setAdapter(newsAdapter);
       newTitle_listView.setOnItemClickListener(this);

        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
//        if(getActivity().findViewById(R.id.news_content_fragment)!=null){
//             isTwoPaper=true;
//        }else {
//             isTwoPaper=false;
//        }
    }
    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
       News news=newsList.get(position);
       NewsContentFragment newsContentFragment=(NewsContentFragment) getFragmentManager().findFragmentById(R.id.fragment_main_large_content);
       newsContentFragment.refresh(news.getTitle(),news.getContent());
    }

    public List<News> getNews(){
        List<News> newList=new ArrayList<News>();
        News news1=new News();
        news1.setTitle("联播+｜总书记指引网络媒体将正能量变成“大流量”");
        news1.setContent("2024年是我国全功能接入国际互联网30周年。\n" +
                "\n" +
                "30年来，互联网技术发展日新月异，引领了社会生产新变革，创造了人类生活新空间，拓展了国家治理新领域。与此同时，互联网已经成为舆论斗争的主战场。\n" +
                "\n" +
                "让党的声音成为网络空间最强音，需要清醒认知、前瞻布局，也需要有效的治理。党的十八大以来，以习近平同志为核心的党中央高度重视全媒体传播体系建设，提出一系列重大论断，作出一系列重要部署。\n" +
                "\n" +
                "2020年6月30日，习近平总书记主持召开中央全面深化改革委员会第十四次会议。会议强调，建立以内容建设为根本、先进技术为支撑、创新管理为保障的全媒体传播体系，牢牢占据舆论引导、思想引领、文化传承、服务人民的传播制高点。\n" +
                "\n" +
                "无论媒体形态如何演变，优质内容永远是舆论场上的“硬通货”。新闻媒体的影响力，始终是以内容为根基。");
        newList.add(news1);
        News news2=new News();
        news2.setTitle("足金“不足”、证书不“真”——网购黄金市场乱象调查");
        news2.setContent("中消协发布的2023年全国消协组织受理投诉情况表明，去年黄金珠宝首饰投诉量大幅增加。记者在黑猫投诉平台上以“黄金”为关键词检索，发现共有3万余条投诉信息，其中不少是关于“网购黄金被骗”。记者调查发现，网购黄金存在多重乱象。\n" +
                "\n" +
                "　　“999足金”，实则“掺钨、掺铜”。天津的张女士告诉记者，1月中旬她通过某直播间购买了8颗1克重的“金豆”。“第一次‘攒金豆’，谨慎起见拿去送检，结果发现并不是商家说的‘999足金’，里面掺了钨。”张女士说。在黑猫投诉平台上，也有多名消费者表示，自己网购的“999足金”中被检测出掺铜。\n" +
                "\n" +
                "　　“一口价”黄金，计价模式“雾里看花”，购买页面难觅“重要信息”。消费者李欣（化名）说，自己在某平台店铺购买黄金吊坠，页面没有标注“一口价”黄金，也找不到克重信息，买回家发现吊坠工艺普通且仅重0.7克，售价却近900元，比购买时黄金价格翻了1倍多。李欣认为商家故意隐瞒克重等重要信息，客服却表示，的确没有标明克重和“一口价”，但写了3D硬金工艺就是证明。某网络电商平台商家王祎（化名）告诉记者，不少店铺刻意不标“一口价”等字眼，甚至将“克重”改为“长、宽”等数据，就是为了吸引消费者购买，“不知克重，看着不小，买回家却发现是中空的。”\n" +
                "\n" +
                "　　“大品牌”金饰，钢印“消失”、二维码错误。黄金首饰上的印记是打印或刻印的永久性标识，一般标注着厂家代号、贵金属材料及其纯度。湖南长沙的消费者徐阳（化名）投诉，自己网购某“知名品牌”金锁，收到后就发现上面完全没有品牌标识和足金字样，商家却回复：“金锁这么小，钢印糊了很正常”。随后，他扫描检测证签上的二维码，却屡屡显示“运行时错误”。\n" +
                "\n" +
                "　　记者通过检索还发现，一些商家被投诉的原因集中在“混淆概念”等问题上。如将“真金”解释为“真的金属制品”，与消费者玩起了文字游戏。");
      newList.add(news2);
      News news3=new News();
      news3.setTitle("雷军回复张颂文：已为您准备了一辆车");
      news3.setContent("近日，小米旗下全新电动汽车小米SU7正式发布，引发了广大消费者的热议和期待。3月30日，@雷军 在社交媒体回复 @张颂文 ：“我们已为您准备了一辆，4/3开始交车，欢迎你来。你喜欢哪种颜色？”随后，该话题迅速冲上热搜第一。\n" +
              "\n" +
              "此前，在张颂文发布的微博评论区里，有网友发问“小米汽车马上发售了，雷总没有给老师准备一辆？”张颂文有趣回复道，“暂时没人提这事啊，我该如何暗示一下对方而又不失分寸？”没想到，今天雷军就转发回复了。");
      newList.add(news3);
      return newList;
    }
}

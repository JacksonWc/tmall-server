package cn.tedu.tmall.admin.content.dao.persist.mapper;

import cn.tedu.tmall.admin.content.pojo.entity.Article;
import cn.tedu.tmall.admin.content.pojo.vo.ArticleListItemVO;
import cn.tedu.tmall.common.vo.PageData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    //通过文章分类id去查询文章分类名称，联合article表和category表
    //select distinct a.category_name from content_article a join
    //    content_category c on a.category_id=c.id where a.id=1;
    String getCategoryName(Long categoryId);


    //文章vo的list的展示
    List<ArticleListItemVO> listAllArticles();
}

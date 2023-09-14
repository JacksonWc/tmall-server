package cn.tedu.tmall.admin.content.dao.persist.repository;

import cn.tedu.tmall.admin.content.pojo.entity.Article;
import cn.tedu.tmall.admin.content.pojo.vo.ArticleListItemVO;
import cn.tedu.tmall.common.vo.PageData;

public interface IArticleRepository {

    int insertNewArticle(Article article);

    PageData<ArticleListItemVO> listAllArticles(Integer pageNum, Integer defaultQueryPageSize);
}

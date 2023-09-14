package cn.tedu.tmall.admin.content.service;

import cn.tedu.tmall.admin.content.pojo.param.ArticleAddNewParam;
import cn.tedu.tmall.admin.content.pojo.vo.ArticleListItemVO;
import cn.tedu.tmall.common.vo.PageData;

public interface IArticleService {
    int insertNewArticle(ArticleAddNewParam articleAddNewParam);

    PageData<ArticleListItemVO> listAllArticles(Integer pageNum);
}

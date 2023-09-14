package cn.tedu.tmall.admin.content.dao.persist.repository.impl;

import cn.tedu.tmall.admin.content.dao.persist.mapper.ArticleMapper;
import cn.tedu.tmall.admin.content.dao.persist.repository.IArticleRepository;
import cn.tedu.tmall.admin.content.pojo.entity.Article;
import cn.tedu.tmall.admin.content.pojo.vo.ArticleListItemVO;
import cn.tedu.tmall.common.util.PageInfoToPageDataConverter;
import cn.tedu.tmall.common.vo.PageData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArticleRepository implements IArticleRepository {
    @Autowired
    ArticleMapper articleMapper;


    @Override
    public int insertNewArticle(Article article) {
        return articleMapper.insert(article);
    }

    @Override
    public PageData<ArticleListItemVO> listAllArticles(Integer pageNum, Integer defaultQueryPageSize) {
        PageHelper.startPage(pageNum,defaultQueryPageSize);
        List<ArticleListItemVO> list=articleMapper.listAllArticles();
        PageInfo<ArticleListItemVO> pageInfo = new PageInfo<>(list);

        return PageInfoToPageDataConverter.convert(pageInfo);
    }
}

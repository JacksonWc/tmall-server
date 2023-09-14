package cn.tedu.tmall.admin.content.service.impl;

import cn.tedu.tmall.admin.content.dao.persist.mapper.ArticleMapper;
import cn.tedu.tmall.admin.content.dao.persist.repository.IArticleRepository;
import cn.tedu.tmall.admin.content.pojo.entity.Article;
import cn.tedu.tmall.admin.content.pojo.param.ArticleAddNewParam;
import cn.tedu.tmall.admin.content.pojo.vo.ArticleListItemVO;
import cn.tedu.tmall.admin.content.service.IArticleService;
import cn.tedu.tmall.common.vo.PageData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Value("${tmall.dao.default-query-page-size}")
    private Integer defaultQueryPageSize;

    @Autowired
    IArticleRepository articleRepository;

    @Override
    public int insertNewArticle(ArticleAddNewParam articleAddNewParam) {
        Article article=new Article();
        //TODO 对前端传过来的数据进行验证

        BeanUtils.copyProperties(articleAddNewParam,article);

        //需要服务器端设置的字段，作者id，作者名字，分类名称，ip，
        //都有默认值-顶数量，踩数量，浏览量，评论量，审核状态，显示状态，是否推荐，2个时间

        //设置分类名称
        //通过文章分类id去查询文章分类名称，联合article表和category表
        String categoryName=articleMapper.getCategoryName(articleAddNewParam.getCategoryId());
        article.setCategoryName(categoryName);




        return articleMapper.insert(article);
    }

    @Override
    public PageData<ArticleListItemVO> listAllArticles(Integer pageNum) {
        return articleRepository.listAllArticles(pageNum,defaultQueryPageSize);
    }
}

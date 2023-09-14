package cn.tedu.tmall.admin.account.service;

import cn.tedu.tmall.admin.account.pojo.vo.UserListItemVO;
import cn.tedu.tmall.common.vo.PageData;
import cn.tedu.tmall.common.web.JsonResult;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IUserService {
    public PageData<UserListItemVO> listManager(Integer pageNum,Integer pageSize) ;

    PageData<UserListItemVO> listManager(Integer pageNum);

    int deleteManager(Long id);

    int enableOrClose(Integer status,Long id);
}

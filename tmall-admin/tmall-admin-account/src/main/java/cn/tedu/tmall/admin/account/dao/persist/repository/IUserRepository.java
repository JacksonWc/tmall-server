package cn.tedu.tmall.admin.account.dao.persist.repository;

import cn.tedu.tmall.admin.account.pojo.entity.User;
import cn.tedu.tmall.admin.account.pojo.vo.UserListItemVO;
import cn.tedu.tmall.common.vo.PageData;

public interface IUserRepository {
    PageData<UserListItemVO> listManager(Integer pageNum, Integer pageSize);

    int deleteManagerById(Long id);

    int setStatus(User user);

    User getUserById(Long id);
}

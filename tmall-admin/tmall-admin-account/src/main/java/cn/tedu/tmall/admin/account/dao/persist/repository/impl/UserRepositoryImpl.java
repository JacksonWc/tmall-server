package cn.tedu.tmall.admin.account.dao.persist.repository.impl;

import cn.tedu.tmall.admin.account.dao.persist.mapper.UserMapper;
import cn.tedu.tmall.admin.account.dao.persist.repository.IUserRepository;
import cn.tedu.tmall.admin.account.pojo.entity.User;
import cn.tedu.tmall.admin.account.pojo.vo.UserListItemVO;
import cn.tedu.tmall.common.util.PageInfoToPageDataConverter;
import cn.tedu.tmall.common.vo.PageData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    @Autowired
    UserMapper userMapper;

    @Override
    public PageData<UserListItemVO> listManager(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserListItemVO> userListItemVOS = userMapper.ListManager();
        PageInfo<UserListItemVO> pageInfo = new PageInfo(userListItemVOS);
        return PageInfoToPageDataConverter.convert(pageInfo);
    }

    @Override
    public int deleteManagerById(Long id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int setStatus(User user) {
        return userMapper.updateById(user);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
}

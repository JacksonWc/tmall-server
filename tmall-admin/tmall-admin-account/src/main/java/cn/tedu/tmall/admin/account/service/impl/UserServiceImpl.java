package cn.tedu.tmall.admin.account.service.impl;

import cn.tedu.tmall.admin.account.dao.persist.repository.IUserRepository;
import cn.tedu.tmall.admin.account.pojo.entity.User;
import cn.tedu.tmall.admin.account.pojo.vo.UserListItemVO;
import cn.tedu.tmall.admin.account.service.IUserService;
import cn.tedu.tmall.common.vo.PageData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Value("${tmall.dao.default-query-page-size}")
    private Integer defaultQueryPageSize;


    @Override
    public PageData<UserListItemVO> listManager(Integer pageNum,Integer defaultQueryPageSize) {

        return  userRepository.listManager(pageNum,defaultQueryPageSize);
    }

    //为了每页展示的数量不由用户控制，所以写了这个方法
    @Override
    public PageData<UserListItemVO> listManager(Integer pageNum) {
        return listManager(pageNum,defaultQueryPageSize);
    }

    //删除管理员的功能
    @Override
    public int deleteManager(Long id) {
        //根据这个id去查用户的角色，如果不是管理员则抛出异常--所选择的用户不是管理员


        return userRepository.deleteManagerById(id);
    }

    @Override
    public int enableOrClose(Integer status,Long id) {
        //从数据库查出来现在的状态，如果和传过来的状态一致，则抛异常--用户已处于此状态
        User user=userRepository.getUserById(id);

//        User updateUser=new User();
//        //把查出来的属性赋值给updateUser,这一步是没有必要的
//        BeanUtils.copyProperties(user,updateUser);
       user.setEnable(status);

        return userRepository.setStatus(user);
    }
}

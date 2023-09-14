package cn.tedu.tmall.admin.account.dao.persist.mapper;

import cn.tedu.tmall.admin.account.pojo.entity.User;
import cn.tedu.tmall.admin.account.pojo.vo.UserListItemVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

        //通过角色名称获取管理员列表
    //select u.id,u.username,u.avatar,u.phone,u.email,u.description,u.enable,u.last_login_ip,u.login_count,u.gmt_last_login
        //from account_user u join account_user_role mi on u.id=mi.user_id
        //join account_role r on r.id=mi.role_id where r.name like '%管理员'
        List<UserListItemVO> ListManager();

}

package cn.tedu.tmall.passport.dao.persist.mapper;

import cn.tedu.tmall.passport.pojo.entity.User;
import cn.tedu.tmall.passport.pojo.vo.UserLoginInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    UserLoginInfoVO getLoginInfoByUsername(String username);

}

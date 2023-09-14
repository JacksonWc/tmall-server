package cn.tedu.tmall.admin.mall.service.impl;

import cn.tedu.tmall.admin.mall.dao.persist.repository.ICategoryRepository;
import cn.tedu.tmall.admin.mall.pojo.entity.Category;
import cn.tedu.tmall.admin.mall.pojo.param.CategoryAddNewParam;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryListItemVO;
import cn.tedu.tmall.admin.mall.pojo.vo.CategoryStandardVO;
import cn.tedu.tmall.admin.mall.service.ICategoryService;
import cn.tedu.tmall.common.enumerator.ServiceCode;
import cn.tedu.tmall.common.ex.ServiceException;
import cn.tedu.tmall.common.vo.PageData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

    //不仅仅从配置文件读，而且从配置文件读的优先级是最低的
    @Value("${tmall.dao.default-query-page-size}")
    private Integer defaultQueryPageSize;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public void addNew(CategoryAddNewParam categoryAddNewParam) {
//        数据有效性判断，用户名要唯一
        String name = categoryAddNewParam.getName();
        int count = categoryRepository.countByName(name);
        if (count > 0) {
            String message = "添加类别失败，类别名称已经被占用！";
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //服务器得到depth的值
        Integer depth = 1;
        Long parentId = categoryAddNewParam.getParentId();
        CategoryStandardVO parentCategory = null;
        if (parentId != 0) {
            parentCategory = categoryRepository.getStandardById(parentId);
            if (parentCategory == null) {
                String message = "添加类别失败，父级类别不存在！";
                throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
            }
            depth = parentCategory.getDepth() + 1;
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryAddNewParam, category);
//        这两个属性是服务器端添加的而不是客户端传过来的
        category.setDepth(depth);
        //新增的类别默认不是父级类别
        category.setIsParent(0);
//        属性添加完之后插入实体类
        int rows = categoryRepository.insert(category);
        if (rows != 1) {
            String message = "添加类别失败，服务器忙，请稍后再试！";
            throw new ServiceException(ServiceCode.ERROR_INSERT, message);
        }

        //更新父类属性isParent的值
        if (parentCategory != null && parentCategory.getIsParent() == 0) {
            Category updateCategory = new Category();
            updateCategory.setId(parentId);
            updateCategory.setIsParent(1);
            rows = categoryRepository.updateById(updateCategory);
            if (rows != 1) {
                String message = "添加类别失败，服务器忙，请稍后再试！";
                throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
            }
        }
    }

    @Override
    public PageData<CategoryListItemVO> listByParent(Long parentId, Integer pageNum) {
        return categoryRepository.listByParent(parentId, pageNum, defaultQueryPageSize);
    }

    @Override
    public PageData<CategoryListItemVO> listByParent(Long parentId, Integer pageNum, Integer pageSize) {
        return categoryRepository.listByParent(parentId, pageNum, pageSize);
    }


    @Override
    public void delete(Long id) {
        log.debug("开始处理【根据ID删除类别】的业务，参数：{}", id);
//        通过id查询数据库中的Category
        CategoryStandardVO currentCategory = categoryRepository.getStandardById(id);

        if (currentCategory == null) {
            String message = "删除类别失败，尝试删除的类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

//        当前分类是父级类别，不能删
        if (currentCategory.getIsParent() == 1) {
            String message = "删除类别失败，该类别仍包含子级类别！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }


        int rows = categoryRepository.deleteById(id);
        //通过影响的行数来判断是否删除成功
        if (rows != 1) {
            String message = "删除类别失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_DELETE, message);
        }


        Long parentId = currentCategory.getParentId();
        int count = categoryRepository.countByParent(parentId);
        //删了之后，原父类的商品类别没有下层的商品类别了（删的就是这个下层的）
        if (count == 0) {
            Category parentCategory = new Category();
//            这个其实mybatis plus更新字段值的语法要求写的
            parentCategory.setId(parentId);
            parentCategory.setIsParent(0);
            rows = categoryRepository.updateById(parentCategory);
            if (rows != 1) {
                //这是原子操作，如果父类类别改为非父类类别失败了，整个删除类别名称的行为都不被允许完成
                String message = "删除类别失败，服务器忙，请稍后再尝试！";
                log.warn(message);
                throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
            }
        }
    }

    @Override
    public void setEnable(Long id) {
        log.debug("开始处理【启用类别】的业务，参数：{}", id);
        updateEnableById(id, 1);
    }

    @Override
    public void setDisable(Long id) {
        log.debug("开始处理【禁用类别】的业务，参数：{}", id);
        updateEnableById(id, 0);
    }

    private void updateEnableById(Long id, Integer enable) {
        CategoryStandardVO currentCategory = categoryRepository.getStandardById(id);
        if (currentCategory == null) {
            String message = "类别失败，类别数据不存在！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_NOT_FOUND, message);
        }

        //因为不需要额外的查询，所以就加个这个不重要的判断
        if (currentCategory.getEnable().equals(enable)) {
            String message = "类别失败，此类别已经处于此状态！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_CONFLICT, message);
        }

        //更新启用的状态
        Category updateCategory = new Category();
        updateCategory.setId(id);
        updateCategory.setEnable(enable);
        int rows = categoryRepository.updateById(updateCategory);
        if (rows != 1) {
            String message = "类别失败，服务器忙，请稍后再尝试！";
            log.warn(message);
            throw new ServiceException(ServiceCode.ERROR_UPDATE, message);
        }
    }
}
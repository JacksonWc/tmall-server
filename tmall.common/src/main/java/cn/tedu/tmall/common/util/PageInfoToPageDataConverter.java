package cn.tedu.tmall.common.util;



import cn.tedu.tmall.common.vo.PageData;
import com.github.pagehelper.PageInfo;

/**
 * 将PageInfo转换成PageData的转换器工具类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
public class PageInfoToPageDataConverter {

    /**
     * 将PageHelper框架中的PageInfo类型对象转换成自定义的PageData类型对象
     *
     * @param pageInfo PageInfo对象
     * @param <T>      PageInfo对象中的列表数据中的元素数据的类型
     * @return 自定义的PageData类型的对象
     */
    //静态方法的泛型需要单独声明
    public synchronized static <T> PageData<T> convert(PageInfo<T> pageInfo) {
        PageData<T> pageData = new PageData<>();
        pageData.setPageSize(pageInfo.getPageSize())
                .setTotal(pageInfo.getTotal())
                .setCurrentPage(pageInfo.getPageNum())
                .setMaxPage(pageInfo.getPages())
                .setList(pageInfo.getList());
        return pageData;
    }

}

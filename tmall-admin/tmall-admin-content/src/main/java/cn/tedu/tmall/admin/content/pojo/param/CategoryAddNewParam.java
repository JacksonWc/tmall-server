package cn.tedu.tmall.admin.content.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 新增类别的参数类
 *
 * @author java@tedu.cn
 * @version 2.0
 */
@Data
public class CategoryAddNewParam implements Serializable {

    /**
     * 类别名称
     */
    @ApiModelProperty(value = "文章类别名称",required = true)
    @NotNull(message = "新增文章类别名称失败，文章类别名称不能为空")
    @Pattern(regexp = "^(?:[\\u4e00-\\u9fa5]{1,5}|[\\u4e00-\\u9fa5a-zA-Z\\s]{2,12})$",
            message = "新增类别失败，类别名称只能是1~5长度的汉字，或者2~12个长度的由英文、汉字、空格组成的名称！")
    private String name;

    /**
     * 关键词列表，各关键词使用英文的逗号分隔
     */
    @ApiModelProperty(value = "关键词列表",required = true)
    private String keywords;

    /**
     * 排序序号
     */
    @NotNull(message = "新增类别失败，请提交排序序号！")
    @Range(max = 255,message =  "新增类别失败，排序序号必须是0~255之间的数字！")
    @ApiModelProperty(value = "排序序号，必须是0~255之间的数字",required = true)
    private Integer sort;

    /**
     * 是否启用，1=启用，0=未启用
     */
    @ApiModelProperty(value = "是否启用，1=启用，0=未启用",required = true)
    @NotNull(message = "新增类别失败，请选择当前类别是否启用！")
    @Range(max = 1,message = "新增类别失败，请选择当前类别是否启用！")
    private Integer enable;

    /**
     * 是否显示在导航栏中，1=启用，0=未启用
     */
    @ApiModelProperty(value = "是否显示在导航栏中，1=启用，0=未启用",required = true)
    @NotNull(message = "新增类别失败，请选择当前类别是否显示在导航栏中！")
    @Range(max = 1,message = "新增类别失败，请选择当前类别是否显示在导航栏中！")
    private Integer isDisplay;

}

package cn.tedu.tmall.admin.mall.redis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@EqualsAndHashCode
class Point implements Serializable {
    private static final long serialVersionUID = 2737478896760951013L;
    private int x;
    private int y;
}


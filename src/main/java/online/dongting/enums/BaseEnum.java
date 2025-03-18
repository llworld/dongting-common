package online.dongting.enums;

public interface BaseEnum<E extends Enum<?>, T> {

    /**
     * 获取值
     */
    T getValue();

    /**
     * 描述
     */
    default String getDesc() {
        return null;
    }

}

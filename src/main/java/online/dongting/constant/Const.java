package online.dongting.constant;

/**
 * @author: ll
 * @since: 2023/10/8 HOUR:17 MINUTE:43
 * @description:
 */

public class Const {

    public static final String PAGINATION_COUNT_HEADER = "X-Pagination-Count";
    public static final String PAGINATION_PAGES_HEADER = "X-Pagination-Pages";
    public static final String PAGINATION_SIZE_HEADER = "X-Pagination-Size";
    public static final String PAGINATION_NUMBER_HEADER = "X-Pagination-Number";

    public static final String CURRENT_USER_ID_HEADER = "x-currUserId";

    public static final String CURRENT_USER_HEADER = "x-currUser";

    public static final String CURRENT_USER_ROLES_HEADER = "x-currUserId-roles";

    public static final String DO_MAIN_REGEX = "dapengjiaoyu.com";

    public static final String DO_MAIN_REPLACEMENT = "dapengjiaoyu.cn";

    /**
     * 权限的数据来源
     */
    public static final String REMARK_TYPE = "PC";

    /**
     * 默认当前页
     */
    public static final int PAGE_DEFAULT = 1;
    /**
     * 最小当前页
     */
    public static final int PAGE_MIN = 1;


    /**
     * 最大页大小
     */
    public static final int SIZE_MAX = 20;
    /**
     * 最小页大小
     */
    public static final int SIZE_MIN = 1;
    /**
     * 默认页大小
     */
    public static final int SIZE_DEFAULT = 10;

    /**
     * 参数错误提示
     */
    public static final String PAGE_MIN_TIPS = "当前页不能小于" + PAGE_MIN;
    public static final String SIZE_MAX_TIPS = "页大小不能大于" + SIZE_MAX;
    public static final String SIZE_MIN_TIPS = "页大小不能小于" + SIZE_MIN;
    public static final String COMMENT_SIZE_MAX_TIPS = "返回评论列表数不能大于" + SIZE_MAX;
    public static final String PRAISE_SIZE_MAX_TIPS = "返回点赞列表数不能大于" + SIZE_MAX;
    public static final String REPLIE_SIZE_MAX_TIPS = "返回回复列表数不能大于" + SIZE_MAX;

    /**
     * 用户角色，Y：是员工角色
     */
    public static final String POSITION_EMPLOYEE = "Y";


    public static final String CURRENT_CODE_HEADER = "x-code";
}

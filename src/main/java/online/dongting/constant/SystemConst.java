package online.dongting.constant;

/**
 * @author ll
 */
public class SystemConst {

    /**
     * 总条数
     */
    public static final String PAGINATION_COUNT_HEADER = "X-Pagination-Count";
    /**
     * 总页数
     */
    public static final String PAGINATION_PAGES_HEADER = "X-Pagination-Pages";
    /**
     * 每页大小
     */
    public static final String PAGINATION_SIZE_HEADER = "X-Pagination-Size";
    /**
     * 当前页
     */
    public static final String PAGINATION_NUMBER_HEADER = "X-Pagination-Number";

    /**
     * 用户Id
     */
    public static final String CURRENT_USER_ID_HEADER = "x-currUserId";


    public static final Long SIZE_MAX = 100L;
    public static final Long SIZE_DEFAULT = 10L;
    public static final Long PAGE_DEFAULT = 1L;

    /**
     * redis点赞key前缀
     * key = DIARY_LIKE_KEY  :   diaryId     ::  userId
     */
    public static final String DIARY_LIKE_KEY = "DIARY_LIKE_KEY";

    /**
     * redis点赞数前缀
     */
    public static final String DIARY_LIKE_COUNT = "DIARY_LIKE_COUNT_";
}

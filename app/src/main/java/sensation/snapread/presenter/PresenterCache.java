package sensation.snapread.presenter;

import java.util.List;

/**
 * 存储临时的userID等内容
 * Created by Alan on 2016/9/28.
 */
public class PresenterCache {
    private static PresenterCache presenterCache;

    String userID;
    List<String> postTitleList;
    List<String> typeList;

    private PresenterCache() {
    }

    public static PresenterCache getInstance() {
        if (presenterCache == null) {
            presenterCache = new PresenterCache();
        }
        return presenterCache;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<String> getPostTitleList() {
        return postTitleList;
    }

    public void setPostTitleList(List<String> postTitleList) {
        this.postTitleList = postTitleList;
    }
}

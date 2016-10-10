package sensation.snapread.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sensation.snapread.model.MyApplication;

/**
 * 存储临时的userID等内容
 * Created by Alan on 2016/9/28.
 */
public class PresenterCache {
    private static final String USER_PREF = "user", SEARCH_PREF = "search", HISTORY = "history",
            USERID = "userID", USERID_PREFIX = "local";
    private static PresenterCache presenterCache;

    String userID;
    List<String> postTitleList;
    List<String> typeList;
    SharedPreferences searchPref, userPref;

    private PresenterCache() {
    }

    public static PresenterCache getInstance() {
        if (presenterCache == null) {
            presenterCache = new PresenterCache();
        }
        return presenterCache;
    }

    public String[] getSearchHistory() {
        if (searchPref == null) {
            searchPref = MyApplication.getContext().getSharedPreferences(SEARCH_PREF, Context.MODE_PRIVATE);
        }
        Set<String> searchHistoryList = searchPref.getStringSet(HISTORY, new HashSet<String>());
        if (searchHistoryList.size() == 0) {
            return null;
        } else {
            String[] historyArray = new String[searchHistoryList.size()];
            return searchHistoryList.toArray(historyArray);
        }
    }

    public void addSearchHistory(String keyword) {
        if (searchPref == null) {
            searchPref = MyApplication.getContext().getSharedPreferences(SEARCH_PREF, Context.MODE_PRIVATE);
        }
        Set<String> searchHistoryList = searchPref.getStringSet(HISTORY, new HashSet<String>());
        searchHistoryList.add(keyword);
        searchPref
                .edit()
                .putStringSet(HISTORY, searchHistoryList)
                .apply();
    }

    public void deleteSearchHistory() {
        searchPref
                .edit()
                .remove(HISTORY)
                .apply();
    }

    public String getUserID() {
        if (userID != null) {
            return userID;
        } else {
            if (userPref == null) {
                userPref = MyApplication.getContext().getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
            }
            String userID = userPref.getString(USERID, "");
            if (userID.equals("")) {
                return createUserID(userPref);
            } else {
                return userID;
            }
        }
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    private String createUserID(SharedPreferences userPref) {
        Calendar calendar = Calendar.getInstance();
        setUserID(USERID_PREFIX + calendar.getTimeInMillis());
        SharedPreferences.Editor editor = userPref.edit();
        editor.putString(USERID, userID);
        editor.apply();
        return userID;
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

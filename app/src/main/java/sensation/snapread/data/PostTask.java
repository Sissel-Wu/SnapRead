package sensation.snapread.data;

/**
 * 查看收藏文章细节的任务
 * Created by Alan on 2016/9/8.
 */
public class PostTask {
    String postID;

    public PostTask(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}

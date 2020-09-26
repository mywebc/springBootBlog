package hello.entity;

public abstract class Result<T> {
    String status;
    String msg;
    T data;
//
//    public static Result failure(String msg) {
//        return new Result("fail", msg, false);
//    }

    protected Result(String status, String msg, boolean isLogin) {
        this(status, msg, null);
    }

    protected Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

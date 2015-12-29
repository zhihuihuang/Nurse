package com.guc.bureau2.listener;


public interface ChangeTitle {
    public void controlBar(int resId, int backId, boolean isBack, boolean isRight);

    public void controlBar(String resId, int backId, boolean isLeft, boolean isRight);

    public void controlBar(String resId, String backId, boolean isLeft, boolean isRight);

    public void controlBar(int resId, String backId, boolean isLeft, boolean isRight);
}

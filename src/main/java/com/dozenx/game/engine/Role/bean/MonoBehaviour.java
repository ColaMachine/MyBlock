package com.dozenx.game.engine.Role.bean;

/**
 * Created by colamachine on 19-9-15.
 */
public abstract class MonoBehaviour {
//     MonoBehaviour(){}
    private  boolean useGUILayout;
    public   boolean runInEditMode;

    public boolean isUseGUILayout() {
        return useGUILayout;
    }

    public void setUseGUILayout(boolean useGUILayout) {
        this.useGUILayout = useGUILayout;
    }

    public boolean isRunInEditMode() {
        return runInEditMode;
    }

    public void setRunInEditMode(boolean runInEditMode) {
        this.runInEditMode = runInEditMode;
    }
    //    public  void print();
//    public void CcelInvoke();
//    public void Invoke()String methodName,float time;

}

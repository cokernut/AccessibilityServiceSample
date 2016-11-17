package top.cokernut.sample;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Admin on 2016/11/17.
 */

public class MyService extends AccessibilityService {
    private int code = INSTALL;
    private static final int INSTALL = 0;
    private static final int NEXT = 1;
    private static final int FINISH = 2;
    /**
     * 页面变化回调事件
     * @param event event.getEventType() 当前事件的类型;
     *              event.getClassName() 当前类的名称;
     *              event.getSource() 当前页面中的节点信息；
     *              event.getPackageName() 事件源所在的包名
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // 事件页面节点信息不为空
        if (event.getSource() != null) {
            // 判断事件页面所在的包名，这里是自己
            if (event.getPackageName().equals(getApplicationContext().getPackageName())) {
                switch (code) {
                    case INSTALL:
                        click(event, "安装", TextView.class.getName());
                        Log.d("test=======", "安装");
                        try {
                            Thread.sleep(Toast.LENGTH_SHORT);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        code = NEXT;
                        break;
                    case NEXT:
                        click(event, "下一步", Button.class.getName());
                        Log.d("test=======", "下一步");
                        try {
                            Thread.sleep(Toast.LENGTH_SHORT);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        code = FINISH;
                        break;
                    case FINISH:
                        click(event, "完成", TextView.class.getName());
                        Log.d("test=======", "完成");
                        try {
                            Thread.sleep(Toast.LENGTH_SHORT);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        code = INSTALL;
                        break;
                    default:
                        break;
                }
            }
        } else {
            Log.d("test=====", "the source = null");
        }
    }

    /**
     * 模拟点击
     * @param event 事件
     * @param text 按钮文字
     * @param widgetType 按钮类型，如android.widget.Button，android.widget.TextView
     */
    private void click(AccessibilityEvent event, String text, String widgetType) {
        // 事件页面节点信息不为空
        if (event.getSource() != null) {
            // 根据Text搜索所有符合条件的节点, 模糊搜索方式; 还可以通过ID来精确搜索findAccessibilityNodeInfosByViewId
            List<AccessibilityNodeInfo> stop_nodes = event.getSource().findAccessibilityNodeInfosByText(text);
            // 遍历节点
            if (stop_nodes != null && !stop_nodes.isEmpty()) {
                AccessibilityNodeInfo node;
                for (int i = 0; i < stop_nodes.size(); i++) {
                    node = stop_nodes.get(i);
                    // 判断按钮类型
                    if (node.getClassName().equals(widgetType)) {
                        // 可用则模拟点击
                        if (node.isEnabled()) {
                            node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }
                }
            }
        }
    }

    /**
     * 中断AccessibilityService的反馈时调用
     */
    @Override
    public void onInterrupt() {
        Log.d("test=====", "Interrupt");
    }
}

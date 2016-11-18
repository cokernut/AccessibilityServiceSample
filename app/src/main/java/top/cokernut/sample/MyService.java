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
     *              类型：
     *                   #TYPES_ALL_MASK：所有类型
     *                   #TYPE_VIEW_CLICKED ：单击
                         #TYPE_VIEW_LONG_CLICKED ：长按
                         #TYPE_VIEW_SELECTED ：选中
                         #TYPE_VIEW_FOCUSED ：获取焦点
                         #TYPE_VIEW_TEXT_CHANGED ：文字改变
                         #TYPE_WINDOW_STATE_CHANGED ：窗口状态改变
                         #TYPE_NOTIFICATION_STATE_CHANGED ：通知状态改变
                         #TYPE_VIEW_HOVER_ENTER
                         #TYPE_VIEW_HOVER_EXIT
                         #TYPE_TOUCH_EXPLORATION_GESTURE_START
                         #TYPE_TOUCH_EXPLORATION_GESTURE_END
                         #TYPE_WINDOW_CONTENT_CHANGED
                         #TYPE_VIEW_SCROLLED
                         #TYPE_VIEW_TEXT_SELECTION_CHANGED
                         #TYPE_ANNOUNCEMENT
                         #TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY
                         #TYPE_GESTURE_DETECTION_START
                         #TYPE_GESTURE_DETECTION_END
                         #TYPE_TOUCH_INTERACTION_START
                         #TYPE_TOUCH_INTERACTION_END
                         #TYPE_WINDOWS_CHANGED
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
                        code = NEXT;
                        break;
                    case NEXT:
                        click(event, "下一步", Button.class.getName());
                        Log.d("test=======", "下一步");
                        code = FINISH;
                        break;
                    case FINISH:
                        click(event, "完成", TextView.class.getName());
                        Log.d("test=======", "完成");
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

   /* @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        //   Type类型：
        //                #TYPES_ALL_MASK：所有类型
        //                #TYPE_VIEW_CLICKED ：单击
        //                #TYPE_VIEW_LONG_CLICKED ：长按
        //                #TYPE_VIEW_SELECTED ：选中
        //                #TYPE_VIEW_FOCUSED ：获取焦点
        //                #TYPE_VIEW_TEXT_CHANGED ：文字改变
        //                #TYPE_WINDOW_STATE_CHANGED ：窗口状态改变
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.packageNames = PACKAGE_NAMES;
        ...配置
        setServiceInfo(info);
    }
*/
    /**
     * 中断AccessibilityService的反馈时调用
     */
    @Override
    public void onInterrupt() {
        Log.d("test=====", "Interrupt");
    }
}

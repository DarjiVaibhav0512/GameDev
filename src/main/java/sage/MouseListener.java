package sage;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener
{
    private static MouseListener instance;
    private double xscroll, yscroll;
    private double xPos, yPos, xLast, yLast;
    //mButtonPressed = mouseButtonPressed
    private boolean mButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener()
    {
        this.xscroll = 0.0;
        this.yscroll = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.xLast = 0.0;
        this.yLast = 0.0;
    }

    public static MouseListener get()
    {
        if (MouseListener.instance == null)
        {
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    public static void mPosCallback(long window, double xpos, double ypos)
    {
        get().xLast = get().xPos;
        get().yLast = get().yPos;
        get().xPos = xpos;
        get().yPos = ypos;
        get().isDragging = get().mButtonPressed[0] || get().mButtonPressed[1] || get().mButtonPressed[2];
    }

    public static void mButtonCallback(long window, int button, int action, int mods)
    {
        if(action == GLFW_PRESS)
        {
            if(button < get().mButtonPressed.length)
            {
                get().mButtonPressed[button] = true;
            }
        }
        else if(action == GLFW_RELEASE)
        {
            if(button < get().mButtonPressed.length)
            {
                get().mButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mScrollCallback(long window, double xOffset, double yOffset)
    {
        get().xscroll = xOffset;
        get().yscroll = yOffset;
    }

    public static void endFrame()
    {
        get().xscroll = 0.0;
        get().yscroll = 0.0;
        get().xLast = get().xPos;
        get().yLast = get().yPos;
    }

    public static float getX()
    {
        return (float)get().xPos;
    }

    public static float getY()
    {
        return (float)get().yPos;
    }

    public static float getDx()
    {
        return (float)(get().xLast - get().xPos);
    }

    public static float getDy()
    {
        return (float)(get().yLast - get().yPos);
    }

    public static float getScrollX()
    {
        return (float)get().xscroll;
    }

    public static float getScrollY()
    {
        return (float)get().yscroll;
    }

    public static boolean isDragging()
    {
        return get().isDragging;
    }

    public static boolean mButtonDown(int button)
    {
        if(button < get().mButtonPressed.length)
        {
            return get().mButtonPressed[button];
        }
        else
        {
            return false;
        }
    }
}

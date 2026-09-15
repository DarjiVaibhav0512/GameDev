package sage;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGUILayer imGuiLayer;

    public float r, g, b, a;
    private boolean fadestoBlack = false;
//    private boolean fadestoWhite = false;

    private static Window window = null;

    private static Scene currentScene;

    private Window()
    {
        this.width = 1080;
        this.height = 720;
        this.title = "Game";
        r = 1;
        g = 1;
        b = 1;
        a = 1;
    }

    public static void changeScene(int newScene)
    {
        switch(newScene)
        {
            case 0:
                currentScene = new LvlEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LvlScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false : "Unknow scene ' " + newScene + "'";
                break;
        }
    }

    public static Window get()
    {
        if(Window.window == null)
        {
            Window.window = new Window();
        }
        return Window.window;
    }

    public static Scene getScene()
    {
        return get().currentScene;
    }

    public void run()
    {
        System.out.println("Alola LWJGL" + Version.getVersion() + "!");

        init();
        loop();

        //Free the Memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init()
    {
        //Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if(!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        //Create the Window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow == NULL)
        {
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mPosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::kCallback);

        //Make OpenGL context
        glfwMakeContextCurrent(glfwWindow);
        //Enable v-sync
        glfwSwapInterval(1);

        //Make window visible
        glfwShowWindow(glfwWindow);

        /*
        This line is critical for LWJGL's interoperation with GLFW's
		OpenGL context, or any context that is managed externally.
		LWJGL detects the context that is current in the current thread,
		creates the GLCapabilities instance and makes the OpenGL
		bindings available for use.
         */
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        this.imGuiLayer = new ImGUILayer();
        this.imGuiLayer.init(glfwWindow);

        Window.changeScene(0);
    }

    public void loop()
    {
        float startTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;

        currentScene.load();

        while(!glfwWindowShouldClose(glfwWindow))
        {
            //Poll Events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0)
            {
                currentScene.update(dt);
            }

            this.imGuiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow);

            endTime = (float)glfwGetTime();
            dt = endTime - startTime;
            startTime = endTime;
        }
        currentScene.saveExit();
    }

}

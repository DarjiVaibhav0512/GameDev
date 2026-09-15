package sage;

import imgui.ImFont;
import imgui.ImGui;
import imgui.glfw.ImGuiImplGlfw;
import imgui.gl3.ImGuiImplGl3;
import imgui.ImGuiIO;
import imgui.ImFontAtlas;

public class ImGUILayer {

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    public void init(long window) {
        ImGui.createContext();

        ImGuiIO io = ImGui.getIO();

        // Save/load ImGui window settings
        io.setIniFilename("imgui.ini");

        // Font configuration
        ImFontAtlas fontAtlas = io.getFonts();

        ImFont myFont = fontAtlas.addFontFromFileTTF(
                "assets/fonts/PressStart2P-Regular.ttf",
                20
        );

        io.setFontDefault(myFont);

        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 330");
    }

    public void update(float dt, Scene currentScene) {
        startFrame();
        currentScene.sceneImgui();
        ImGui.showDemoWindow();

        endFrame();
    }

    public void startFrame() {
        imGuiGlfw.newFrame();
        imGuiGl3.newFrame();

        ImGui.newFrame();
    }

    public void endFrame() {
        ImGui.render();

        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    public void shutdown() {
        imGuiGl3.shutdown();
        imGuiGlfw.shutdown();
        ImGui.destroyContext();
    }
}
package sage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import util.AssetPool;

public class LvlEditorScene extends Scene
{
    private GameObject obj1;
    private Spritesheet sprites;

    public LvlEditorScene()
    {

    }

    @Override
    public void init()
    {
        loadResources();

        this.camera = new Camera(new Vector2f(-350, -170));
        if(levelLoaded)
        {
            return;
        }

        sprites = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        obj1 = new GameObject("Object 1", new Transform(
                new Vector2f(200, 100), new Vector2f(256, 256)), 2);
        SpriteRenderer obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(0, 0, 1, 1));
        obj1.addComponent(obj1Sprite);
        this.addGameObjToScene(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2", new Transform(
                new Vector2f(400, 100), new Vector2f(256, 256)), 3);
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
        obj2SpriteRenderer.setSprite(obj2Sprite);

        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjToScene(obj2);
    }

    private void loadResources()
    {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                        16, 16, 26, 0));
//        AssetPool.addSpritesheet("assets/tinyswords/Units/Blue Units/Warrior/Warrior_Attack1.png",
//                new Spritesheet(AssetPool.getTexture("assets/tinyswords/Units/Blue Units/Warrior/Warrior_Attack1.png"),
//                         192, 192, 4, 0));
    }

    @Override
    public void update(float dt)
    {
//        System.out.println("" + (1.0f / dt) + " FPS");

        for(GameObject go : this.gameObjects)
        {
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui()
    {
        ImGui.begin("LvlEditorScene");
        ImGui.text("LvlEditorScene2");
        ImGui.end();
    }
}

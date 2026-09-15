package components;

import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import sage.Component;
import sage.Transform;

public class SpriteRenderer extends Component
{
    //(0, 1) Bottom Right
    //(0, 0) Bottom Left
    //(1, 1) Top Right
    //(1, 0) Top Left
    private Vector4f color = new Vector4f(1, 1, 1, 1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = false;

//    public SpriteRenderer(Vector4f color)
//    {
//        this.color = color;
//        this.sprite = new Sprite(null);
//        this.isDirty = true;
//    }
//
//    public SpriteRenderer(Sprite sprite)
//    {
//        this.sprite = sprite;
//        this.color = new Vector4f(1, 1, 1, 1);
//        this.isDirty = true;
//    }

    @Override
    public void start()
    {
        this.lastTransform = gameObj.transform.copy();
    }

    @Override
    public void update(float dt)
    {
        if(!this.lastTransform.equals(this.gameObj.transform))
        {
            this.gameObj.transform.copy(this.lastTransform);
            isDirty = true;
        }
    }

    @Override
    public void imgui()
    {
        float[] imColor = {color.x, color.y, color.z, color.w};
        if(ImGui.colorPicker4("Color Picker: ", imColor))
        {
            this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
            this.isDirty =  true;
        }
    }

    public Vector4f getColor()
    {
        return this.color;
    }

    public Texture  getTexture()
    {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords()
    {
       return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
        this.isDirty =  true;
    }

    public void setColor(Vector4f color)
    {
        if(!this.color.equals(color))
        {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public boolean isDirty()
    {
        return this.isDirty;
    }

    public void setClean()
    {
        this.isDirty = false;
    }
}

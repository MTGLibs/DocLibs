package com.wxiwei.office.common.shape;

import java.util.ArrayList;
import java.util.List;

public class GroupShape extends AbstractShape
{
    /**
     * 
     */
    public GroupShape()
    {
        shapes = new ArrayList<IShape>();
    }
    

    public short getType()
    {
        return SHAPE_GROUP;
    }
    
    public int getOffX()
    {
        return offX;
    }

    public void setOffX(int offX)
    {
        this.offX = offX;
    }

    public int getOffY()
    {
        return offY;
    }

    public void setOffY(int offY)
    {
        this.offY = offY;
    }

    public void setOffPostion(int offX, int offY)
    {
        this.offX = offX;
        this.offY = offY;
    }
    
    /**
     * append shape of this slide
     */
    public void appendShapes(IShape shape)
    {
        this.shapes.add(shape);
    }
    
    /**
     * get all shapes of this slide
     */
    public IShape[] getShapes()
    {
        return shapes.toArray(new IShape[shapes.size()]);
    }
    
    private int offX, offY;
    // shapes of this slide
    private List<IShape> shapes;
}

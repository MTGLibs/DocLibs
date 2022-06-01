package com.wxiwei.office.common.shape;

import com.wxiwei.office.common.bg.BackgroundAndFill;
import com.wxiwei.office.common.borders.Line;
import com.wxiwei.office.java.awt.Rectangle;
import com.wxiwei.office.pg.animate.IAnimation;

public class AbstractShape implements IShape
{
    // picture
    public static final short SHAPE_PICTURE = 0;
    // text box
    public static final short SHAPE_TEXT = SHAPE_PICTURE + 1; // 1
    // auto shape
    public static final short SHAPE_AUTOGRAPH = SHAPE_TEXT + 1 ;// 2
    //
    public static final short SHAPE_BG_FILL = SHAPE_AUTOGRAPH + 1; // 3
    //
    public static final short SHAPE_LINE = SHAPE_BG_FILL + 1; // 4 
    // chart
    public static final short SHAPE_CHART = SHAPE_LINE + 1; // 5
    // table
    public static final short SHAPE_TABLE = SHAPE_CHART + 1; // 6
    //group shape
    public static final short SHAPE_GROUP = SHAPE_TABLE + 1; // 7
    //smart art
    public static final short SHAPE_SMART_ART = SHAPE_GROUP + 1; // 8
    

    public short getType()
    {
        return -1;
    }


    public IShape getParent()
    {
        return parent;
    }
    

    public void setParent(IShape shape)
    {
        this.parent = shape;
    }
    

    public void setGroupShapeID(int id)
    {
        grpSpID = id;
    }
    

    public int getGroupShapeID()
    {
        return grpSpID;
    }
    
    public void setShapeID(int id)
    {
        this.id = id;
    }
    
    public int getShapeID()
    {
        return id;
    }
    

    public Rectangle getBounds()
    {
        return rect;
    }


    public void setBounds(Rectangle rect)
    {
        this.rect = rect;
    }


    public Object getData()
    {
        return null;
    }


    public void setData(Object data)
    {
        
    }
    

    public BackgroundAndFill getBackgroundAndFill()
    {
        return bgFill;
    }


    public void setBackgroundAndFill(BackgroundAndFill bgFill)
    {
        this.bgFill = bgFill;
    }
    

    public boolean getFlipHorizontal()
    {
        return flipH;
    }
    

    public void setFlipHorizontal(boolean flipH)
    {
        this.flipH = flipH;
    }
    

    public boolean getFlipVertical()
    {
        return flipV;
    }
    

    public void setFlipVertical(boolean flipV)
    {
        this.flipV = flipV;
    }
    

    public float getRotation()
    {
        return angle;
    }
    

    public void setRotation(float angle)
    {
        this.angle = angle;
    }
    
    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }
    
    public boolean isHidden()
    {
        return hidden;
    }
    

    public void setAnimation(IAnimation animation)
    {
        this.animation = animation;
    }
    
    public IAnimation getAnimation()
    {
        return animation;
    }
    
    public boolean hasLine()
    {
        return line != null;
    }
    
    public void setLine(boolean hasLine)
    {
    	this.hasLine = hasLine;
    	if(hasLine && line == null)
    	{
    		line = new Line();
    	}
    }
    public void setLine(Line line)
    {
    	this.line = line;
    	if(line !=  null)
    	{
    		this.hasLine = true;
    	}
    }
    
    public Line createLine()
    {
    	line = new Line();
    	return line;
    }
    

    public Line getLine()
    {
        return line;
    }
    

    public int getPlaceHolderID()
    {
        return placeHolderID;
    }

    public void setPlaceHolderID(int placeHolderID)
    {
        this.placeHolderID = placeHolderID;
    }
    

    public void dispose()
    {
        if (parent != null)
        {
            parent.dispose();
            parent = null;
        }
        rect = null;
        if(animation != null)
        {
            animation.dispose();
            animation = null;
        }
        if (bgFill != null)
        {
            bgFill.dispose();
            bgFill = null;
        }
        
        if(line != null)
        {
        	line.dispose();
        	line = null;
        }
    }
    
    private IShape parent;
    private int grpSpID = -1;
    private int id;
    
    private BackgroundAndFill bgFill;
    protected Rectangle rect;
    private boolean flipH;
    private boolean flipV;
    private float angle;
    
    private boolean hidden;
    private IAnimation animation;
    
    private boolean hasLine;
    private Line line;
    private int placeHolderID;
}

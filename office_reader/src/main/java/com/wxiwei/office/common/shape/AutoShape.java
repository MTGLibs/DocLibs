package com.wxiwei.office.common.shape;


public class AutoShape extends AbstractShape
{

    public AutoShape()
    {
        
    }
    

    public AutoShape(int type)
    {
        this.type = type;
    }
    

    public short getType()
    {
        return SHAPE_AUTOGRAPH;
    }
    
    /**
     * get autoShape type
     * @return
     */
    public int getShapeType()
    {
        return type;
    }
    
    /**
     * set autoShape type
     * @return
     */
    public void setShapeType(int type)
    {
        this.type = type;
    }
    
    
    
    /**
     * set values of adjust points
     * @param values
     */
    public void setAdjustData(Float[] values)
    {
        this.values = values;
    }
    
    /**
     * get values of adjust points
     * @return
     */
    public Float[] getAdjustData()
    {
        return values;
    }
    
    /**
     * 
     * @param shape07
     */
    public void setAuotShape07(boolean shape07)
    {
        this.shape07 = shape07;
    }
    
    /**
     * 
     * @return
     */
    public boolean isAutoShape07()
    {
        return shape07;
    }
    
    /**
     * dispose
     */
    public void dispose()
    {
        super.dispose();        
    }
    
    //
    private int type;
    
    // adjust values by clockwise(percent)
    private Float[] values;    
    
    // autoShape is 07 or 03
    private boolean shape07 = true;
}

package com.wxiwei.office.common.shape;

import java.util.ArrayList;
import java.util.List;

import com.wxiwei.office.common.autoshape.ExtendPath;

public class ArbitraryPolygonShape extends LineShape
{   
    public ArbitraryPolygonShape()
    {
        paths = new ArrayList<>();
    }
    
    public void appendPath(ExtendPath path)
    {
        this.paths.add(path);
    }
    
    public List<ExtendPath> getPaths()
    {
        return paths;
    }
    
    private List<ExtendPath> paths;
}

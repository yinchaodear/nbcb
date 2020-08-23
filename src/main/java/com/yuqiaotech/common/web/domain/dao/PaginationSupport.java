package com.yuqiaotech.common.web.domain.dao;

import java.util.List;

public class PaginationSupport
{
    public final static int PAGESIZE = 20;
    
    private int pageSize = PAGESIZE;
    
    private List items;
    
    private int totalCount;
    
    private int[] indice = new int[0];
    
    private int startIndex = 0;
    
    public PaginationSupport(int totalCount)
    {
        setPageSize(PAGESIZE);
        setTotalCount(totalCount);
        setStartIndex(0);
    }
    
    public PaginationSupport(List items, int totalCount)
    {
        setPageSize(PAGESIZE);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(0);
    }
    
    public PaginationSupport(List items, int totalCount, int startIndex)
    {
        setPageSize(PAGESIZE);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }
    
    public PaginationSupport(List items, int totalCount, int startIndex, int pageSize)
    {
        setPageSize(pageSize);
        setTotalCount(totalCount);
        setItems(items);
        setStartIndex(startIndex);
    }
    
    /**
     * @return the items
     */
    public List getItems()
    {
        return items;
    }
    
    /**
     * @param items
     *            the items to set
     */
    public void setItems(List items)
    {
        this.items = items;
    }
    
    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }
    
    /**
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    /**
     * @return the totalCount
     */
    public int getTotalCount()
    {
        return totalCount;
    }
    
    /**
     * @param totalCount
     *            the totalCount to set
     */
    public void setTotalCount(int totalCount)
    {
        if (totalCount > 0)
        {
            this.totalCount = totalCount;
            int count = totalCount / pageSize;
            if (totalCount % pageSize > 0)
                count++;
            indice = new int[count];
            for (int i = 0; i < count; i++)
            {
                indice[i] = pageSize * i;
            }
        }
        else
        {
            this.totalCount = 0;
        }
    }
    
    /**
     * @return the indice
     */
    public int[] getIndice()
    {
        return indice;
    }
    
    /**
     * @param indice
     *            the indice to set
     */
    public void setIndice(int[] indice)
    {
        this.indice = indice;
    }
    
    public int getStartIndex()
    {
        return startIndex;
    }
    
    public void setStartIndex(int startIndex)
    {
        if (totalCount <= 0)
        {
            this.startIndex = 0;
        }
        else if (startIndex >= totalCount)
        {
            this.startIndex = indice[indice.length - 1];
        }
        else if (startIndex < 0)
        {
            this.startIndex = 0;
        }
        else
        {
            this.startIndex = indice[startIndex / pageSize];
        }
    }
    
    public int getNextIndex()
    {
        int nextIndex = getStartIndex() + pageSize;
        if (nextIndex >= totalCount)
        {
            return getStartIndex();
        }
        else
        {
            return nextIndex;
        }
    }
    
    public int getLastIndex()
    {
        if (indice.length == 0)
            return 0;
        return indice[indice.length - 1];
    }
    
    public int getPreviousIndex()
    {
        int previousIndex = getStartIndex() - pageSize;
        if (previousIndex < 0)
            return 0;
        else
            return previousIndex;
    }
    
    public int getPageCount()
    {
        return indice.length;
    }
    
    public int getCurrentPage()
    {
        return startIndex / pageSize + 1;
    }
    
    /**
     * 
     * 
     * @return
     */
    public int getPageNo()
    {
        return getCurrentPage();
    }
    
    public void setPageNo(int pageNo)
    {
        startIndex = pageSize * (pageNo - 1);
    }
}

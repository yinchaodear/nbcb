
package com.yuqiaotech.zsnews.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.tools.common.FileUtils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.bean.MaterialBean;
import com.yuqiaotech.zsnews.model.Channel;
import com.yuqiaotech.zsnews.model.Column;
import com.yuqiaotech.zsnews.model.Material;
import com.yuqiaotech.zsnews.model.PicMapping;

/**
 * 开屏素材
 */
@RestController
@RequestMapping("zsnews/materialwindow")
public class MaterialWindowController extends BaseController
{
    private static String MODULE_PATH = "zsnews/materialwindow/";
    
    @Autowired
    private BaseRepository<Material, Long> materialRepository;
    
    @Autowired
    private BaseRepository<Channel, Long> channelRepository;
    
    @Autowired
    private BaseRepository<Column, Long> columnRepository;
    
    @Autowired
    private BaseRepository<PicMapping, Long> picMappingRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(Material material, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(material);
        PaginationSupport ps = materialRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(Material material)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Material.class);
        if (material != null)
        {
            if (StringUtils.isNotEmpty(material.getTitle()))
            {
                dc.add(Restrictions.or(Restrictions.ilike("title", material.getTitle(), MatchMode.ANYWHERE),
                    Restrictions.ilike("remark", material.getTitle(), MatchMode.ANYWHERE)));
            }
            if (material.getStatus() != null)
            {
                dc.add(Restrictions.eq("status", material.getStatus()));
            }
        }
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        dc.add(Restrictions.or(Restrictions.eq("type", NewsDicConstants.IMaterial.Type.WINDOW),
            Restrictions.eq("type", NewsDicConstants.IMaterial.Type.APP)));
        return dc;
    }
    
    @GetMapping("status/{mid}")
    public Result status(@PathVariable Long mid)
    {
        Material material = materialRepository.findUniqueBy("id", mid, Material.class);
        
        if (material != null)
        {
            if (material.getStatus() == null || material.getStatus() == NewsDicConstants.ICommon.STATUS_DOWN)
            {
                //如果是从下架变上架，要看同一个资源位有无已经上架的素材
                String checkOne = "from Material where column.id=" + material.getColumn().getId() + " and (type="
                    + NewsDicConstants.IMaterial.Type.WINDOW + " or type=" + NewsDicConstants.IMaterial.Type.APP
                    + ") and status=" + NewsDicConstants.ICommon.STATUS_UP + " and deltag="
                    + NewsDicConstants.ICommon.DELETE_NO + " and id != " + mid;
                List<Material> dblist = materialRepository.findByHql(checkOne);
                if (CollectionUtils.isNotEmpty(dblist))
                {
                    return failure("当前资源位已经存在上架资源，不可重复上架");
                }
            }
            if (material.getStatus() == null || material.getStatus() == NewsDicConstants.ICommon.STATUS_DOWN)
            {
                material.setStatus(NewsDicConstants.ICommon.STATUS_UP);
            }
            else
            {
                material.setStatus(NewsDicConstants.ICommon.STATUS_DOWN);
            }
            
            materialRepository.update(material);
        }
        return success();
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
        modelAndView.addObject("material_tid", System.currentTimeMillis());
        modelAndView.setViewName(MODULE_PATH + "add");
        return modelAndView;
    }
    
    @PostMapping("save")
    public Result save(@RequestBody MaterialBean materialBean)
    {
        //这里增加一个逻辑：同一个资源位，只能有一个上架的素材存在
        String checkOne;
        if (materialBean.getColumnId() != null)
        {
            checkOne = "from Material where column.id=" + materialBean.getColumnId() + " and type="
                + NewsDicConstants.IMaterial.Type.WINDOW + " and status=" + NewsDicConstants.ICommon.STATUS_UP
                + " and deltag=" + NewsDicConstants.ICommon.DELETE_NO;
        }
        else
        {
            //找开机
            checkOne = "from Material where  type=" + NewsDicConstants.IMaterial.Type.APP + " and status="
                + NewsDicConstants.ICommon.STATUS_UP + " and deltag=" + NewsDicConstants.ICommon.DELETE_NO;
        }
        List<Material> dblist = materialRepository.findByHql(checkOne);
        if (CollectionUtils.isNotEmpty(dblist))
        {
            return failure("当前资源位已经存在上架资源，不可重复添加");
        }
        Material material = new Material();
        BeanUtils.copyProperties(materialBean, material);
        
        if (materialBean.getColumnId() != null)
        {
            Column column = columnRepository.findUniqueBy("id", materialBean.getColumnId(), Column.class);
            material.setColumn(column);
            material.setType(NewsDicConstants.IMaterial.Type.WINDOW);
        }
        else
        {
            material.setType(NewsDicConstants.IMaterial.Type.APP);
        }
        
        material.setStatus(NewsDicConstants.ICommon.STATUS_UP);//默认上架
        material.setDeltag(NewsDicConstants.ICommon.DELETE_NO);//默认未删除
        material.setUser(getCurrentUser());
        material = materialRepository.save(material);
        
        String objectId = materialBean.getObjectId();
        String objectType = materialBean.getObjectType();
        List<PicMapping> picmappinglist = new ArrayList<>();
        if (StringUtils.isNotEmpty(materialBean.getPicname1()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(material);
            pm.setDisplayOrder(1);
            pm.setPicpath(materialBean.getPicname1());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname2()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(material);
            pm.setDisplayOrder(2);
            pm.setPicpath(materialBean.getPicname2());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname3()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(material);
            pm.setDisplayOrder(3);
            pm.setPicpath(materialBean.getPicname3());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname4()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(material);
            pm.setDisplayOrder(4);
            pm.setPicpath(materialBean.getPicname4());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname5()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(material);
            pm.setDisplayOrder(5);
            pm.setPicpath(materialBean.getPicname5());
            picmappinglist.add(pm);
        }
        
        if (CollectionUtils.isNotEmpty(picmappinglist))
        {
            for (PicMapping pm : picmappinglist)
            {
                picMappingRepository.save(pm);
            }
            
            //把图片移动到material对应的ID下
            File srcFile = new File(attachmentRoot + "/" + objectType + "/" + objectId + "/");
            File dstFile = new File(attachmentRoot + "/" + objectType + "/" + material.getId() + "/");
            if (srcFile.exists())
            {
                try
                {
                    org.apache.commons.io.FileUtils.copyDirectory(srcFile, dstFile);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                FileUtils.deleteDir(srcFile);
            }
        }
        
        return decide(true);
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        Material materialdb = materialRepository.get(id, Material.class);
        MaterialBean materialBean = new MaterialBean();
        BeanUtils.copyProperties(materialdb, materialBean);
        List<PicMapping> picMappingList = picMappingRepository
            .findByHql("from PicMapping where material.id=" + materialdb.getId() + " order by displayOrder asc");
        int idx = 1;
        for (PicMapping picMapping : picMappingList)
        {
            switch (idx)
            {
                case 1:
                    materialBean.setPicname1(picMapping.getPicpath());
                    break;
                case 2:
                    materialBean.setPicname2(picMapping.getPicpath());
                    break;
                case 3:
                    materialBean.setPicname3(picMapping.getPicpath());
                    break;
                case 4:
                    materialBean.setPicname4(picMapping.getPicpath());
                    break;
                case 5:
                    materialBean.setPicname5(picMapping.getPicpath());
                    break;
                default:
                    break;
            }
            idx++;
        }
        
        modelAndView.addObject("material", materialBean);
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody MaterialBean materialBean)
    {
        Material materialdb = materialRepository.findUniqueBy("id", materialBean.getId(), Material.class);
        Column column = null;
        if (materialBean.getColumnId() != null)
        {
            Column columndb = materialdb.getColumn();
            if (columndb == null || !columndb.getId().equals(materialBean.getColumnId()))
            {
                //如果原来没有。或者新的修改了
                column = columnRepository.findUniqueBy("id", materialBean.getColumnId(), Column.class);
            }
        }
        BeanUtils.copyProperties(materialBean, materialdb, getNullPropertyNames(materialBean));
        if (column != null)
        {
            materialdb.setColumn(column);
        }
        
        if (materialdb.getDeltag() == null)
        {
            materialdb.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        }
        if (materialdb.getStatus() == null)
        {
            materialdb.setStatus(NewsDicConstants.ICommon.STATUS_UP);
        }
        
        if (materialBean.getColumnId() != null)
        {
            materialdb.setType(NewsDicConstants.IMaterial.Type.WINDOW);
        }
        else
        {
            materialdb.setType(NewsDicConstants.IMaterial.Type.APP);
        }
        materialdb.setUser(getCurrentUser());
        
        materialRepository.update(materialdb);
        
        String objectId = materialBean.getObjectId();
        String objectType = materialBean.getObjectType();
        List<PicMapping> picmappinglist = new ArrayList<>();
        if (StringUtils.isNotEmpty(materialBean.getPicname1()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(materialdb);
            pm.setDisplayOrder(1);
            pm.setPicpath(materialBean.getPicname1());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname2()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(materialdb);
            pm.setDisplayOrder(2);
            pm.setPicpath(materialBean.getPicname2());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname3()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(materialdb);
            pm.setDisplayOrder(3);
            pm.setPicpath(materialBean.getPicname3());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname4()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(materialdb);
            pm.setDisplayOrder(4);
            pm.setPicpath(materialBean.getPicname4());
            picmappinglist.add(pm);
        }
        if (StringUtils.isNotEmpty(materialBean.getPicname5()))
        {
            PicMapping pm = new PicMapping();
            pm.setMaterial(materialdb);
            pm.setDisplayOrder(5);
            pm.setPicpath(materialBean.getPicname5());
            picmappinglist.add(pm);
        }
        picMappingRepository.executeUpdate("delete from PicMapping where material.id=" + materialdb.getId(),
            new HashMap<>());
        if (CollectionUtils.isNotEmpty(picmappinglist))
        {
            for (PicMapping pm : picmappinglist)
            {
                picMappingRepository.save(pm);
            }
        }
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除素材")
    public Result remove(@PathVariable Long id)
    {
        Material materialdb = materialRepository.findUniqueBy("id", id, Material.class);
        if (materialdb.getStatus() == NewsDicConstants.ICommon.STATUS_UP)
        {
            return decide(false, null, "上架状态，不可删除");
        }
        materialdb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        materialRepository.update(materialdb);
        return decide(true);
    }
    
    @DeleteMapping("batchRemove/{ids}")
    @Logging(title = "批量删除角色")
    public Result batchRemove(@PathVariable String ids)
    {
        if (StringUtils.isNotEmpty(ids))
        {
            List<Long> cdids =
                Arrays.asList(ids.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            DetachedCriteria dc = DetachedCriteria.forClass(Material.class);
            dc.add(Restrictions.in("id", cdids));
            List<Material> materialList = materialRepository.findByCriteria(dc);
            for (Material material : materialList)
            {
                material.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                materialRepository.update(material);
            }
            return decide(true);
        }
        return decide(false);
    }
}

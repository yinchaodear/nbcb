
package com.yuqiaotech.zsnews.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.logging.annotation.Logging;
import com.yuqiaotech.common.tools.common.ImgBase64Utils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.bean.ColumnBean;
import com.yuqiaotech.zsnews.model.Column;
import com.yuqiaotech.zsnews.model.Company;
import com.yuqiaotech.zsnews.model.CompanyDetail;
import com.yuqiaotech.zsnews.service.UploadService;

@RestController
@RequestMapping("zsnews/companydetail")
public class CompanyDetailController extends BaseController
{
    private static String MODULE_PATH = "zsnews/companydetail/";
    
    @Autowired
    private BaseRepository<Column, Long> columnRepository;
    
    @Autowired
    private BaseRepository<Company, Long> companyRepository;
    @Autowired
    private BaseRepository<CompanyDetail, Long> companydetailRepository;
    @Autowired
    private UploadService uploadService;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data( PageDomain pageDomain,@RequestParam(value="name",required=false) String name,
    		@RequestParam(value="status",required=false) String status )
    {
    	System.out.println("CompanyController.data()");
    	System.err.println(name+status);
        DetachedCriteria dc = composeDetachedCriteria();
        //dc.addOrder(Order.asc("displayOrder"));
        if(name!=null&&!"".equals(name)){
        	dc.add(Restrictions.like("name", "%"+name+"%"));
        }
        if(status!=null&&!"".equals(status)){
        	dc.add(Restrictions.eq("status", status));
        }
        PaginationSupport ps = companyRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    @GetMapping("status/{cid}")
    public Result status(@PathVariable Long cid)
    {
    	Company company = companyRepository.findUniqueBy("id", cid, Company.class);
        //Column column = columnRepository.findUniqueBy("id", cid, Column.class);
        if (company != null)
        {
            if (company.getStatus()!=null&&"停用".equals(company.getStatus()))
            {
                company.setStatus("启用");
            }else{
            	company.setStatus("停用");
            }
            
            companyRepository.update(company);
        }
        return success();
    }
    
    public DetachedCriteria composeDetachedCriteria()
    {
        DetachedCriteria dc = DetachedCriteria.forClass(Company.class);
//        if (column != null)
//        {
//            if (StringUtils.isNotEmpty(column.getTitle()))
//            {
//                dc.add(Restrictions.or(Restrictions.ilike("title", column.getTitle(), MatchMode.ANYWHERE),
//                    Restrictions.ilike("remark", column.getTitle(), MatchMode.ANYWHERE)));
//            }
//            if (column.getStatus() != null)
//            {
//                dc.add(Restrictions.eq("status", column.getStatus()));
//            }
//        }
        //dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        
        return dc;
    }
    
    @GetMapping("add")
    public ModelAndView add(ModelAndView modelAndView)
    {
    	System.out.println("CompanyController.add()");
        modelAndView.addObject("displayOrder", getNextDisplayOrder());
        modelAndView.addObject("objectId", System.currentTimeMillis());
        modelAndView.setViewName(MODULE_PATH + "add");
        return modelAndView;
    }
    
	@GetMapping("upload")
	public ModelAndView upload(ModelAndView modelAndView) {
		System.out.println("CompanyController.upload()");

		
	modelAndView.setViewName(MODULE_PATH+"upload");
	return modelAndView;

	}
    
	@RequestMapping("uploadfile")
	public ModelAndView uploadfile(ModelAndView modelAndView,HttpServletRequest request) throws Exception{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		MultipartFile file = multipartRequest.getFile("excelFile");
		if (file.isEmpty()) {
			
		}
		InputStream inputStream = file.getInputStream();
		List<List<Object>> list = uploadService.getBankListByExcel(inputStream, file.getOriginalFilename());
		inputStream.close();
        int number =0;
        int addnumber =0;
		for (int i = 0; i < list.size(); i++) {
			Map lo = (Map) list.get(i);
			// TODO 随意发挥
			System.out.println(lo);
		
			if("汇总".equals(lo.get("门店名称"))){
				continue;
			}
			CompanyDetail detail= new CompanyDetail();
			detail.setName(uploadService.toStr(uploadService.columnsForBlank(lo.get("商户名称"))));
			detail.setCode(uploadService.toStr(lo.get("商户编号")));
			detail.setBankcode(uploadService.toStr(lo.get("收款账户")));
			detail.setOrganization(uploadService.toStr(lo.get("所属网点")));
			detail.setModel(uploadService.toStr(lo.get("商户模式")));
			detail.setStatus(uploadService.toStr(lo.get("商户状态")));
            number++;
		}
		String msg ="共处理数据"+number+"条"+",新增数据"+addnumber+"条";
		System.out.println("CompanyDetailController.upload()");
		modelAndView.addObject("msg",msg);
		modelAndView.setViewName(MODULE_PATH+"upload");
		return modelAndView;
	}
    
    
    @PostMapping("save")
    public Result save(@RequestBody ColumnBean columnBean)
    {
        Column column = new Column();
        BeanUtils.copyProperties(columnBean, column);
        
        column.setStatus(NewsDicConstants.ICommon.STATUS_UP);//默认上架
        column.setDeltag(NewsDicConstants.ICommon.DELETE_NO);//默认未删除
        if (column.getDisplayOrder() == null)
        {
            column.setDisplayOrder(getNextDisplayOrder());
        }
        
        if (StringUtils.isNotEmpty(columnBean.getPicname1()))
        {
            File srcFile = new File(attachmentRoot + "/" + columnBean.getObjectType() + "/" + columnBean.getObjectId()
                + "/" + columnBean.getPicname1() + ".small");
            if (srcFile != null && srcFile.exists() && srcFile.isFile())
            {
                //保存logo
                column.setLogo(ImgBase64Utils.getImgStr(srcFile));
            }
        }
        columnRepository.save(column);
        return decide(true);
    }
    
    private Integer getNextDisplayOrder()
    { //查当前最大的顺序号
        String sql = "select ifnull(max(f_display_order),0) maxorder from t_column where f_display_order is not null";
        List<Map<String, Object>> rst = columnRepository.findMapByNativeSql(sql);
        Integer displayOrder = 1;
        if (CollectionUtils.isNotEmpty(rst) && rst.get(0) != null && !rst.get(0).isEmpty())
        {
            displayOrder = com.yuqiaotech.common.tools.common.StringUtils
                .parseInt(com.yuqiaotech.common.tools.common.StringUtils.parseNull(rst.get(0).get("maxorder"))) + 1;
        }
        return displayOrder;
    }
    
    @GetMapping("edit")
    public ModelAndView edit(ModelAndView modelAndView, Long id)
    {
        modelAndView.addObject("column", columnRepository.get(id, Column.class));
        modelAndView.setViewName(MODULE_PATH + "edit");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody ColumnBean columnBean)
    {
        
        Column columndb = columnRepository.findUniqueBy("id", columnBean.getId(), Column.class);
        BeanUtils.copyProperties(columnBean, columndb, getNullPropertyNames(columnBean));
        if (columndb.getDisplayOrder() == null)
        {
            columndb.setDisplayOrder(getNextDisplayOrder());
        }
        if (columndb.getDeltag() == null)
        {
            columndb.setDeltag(NewsDicConstants.ICommon.DELETE_NO);
        }
        if (columndb.getStatus() == null)
        {
            columndb.setStatus(NewsDicConstants.ICommon.STATUS_UP);
        }
        
        if (StringUtils.isNotEmpty(columnBean.getPicname1()))
        {
            File srcFile = new File(attachmentRoot + "/" + columnBean.getObjectType() + "/" + columnBean.getObjectId()
                + "/" + columnBean.getPicname1() + ".small");
            if (srcFile != null && srcFile.exists() && srcFile.isFile())
            {
                //保存logo
                columndb.setLogo(ImgBase64Utils.getImgStr(srcFile));
            }
        }
        else
        {
            columndb.setLogo("");
        }
        columnRepository.update(columndb);
        return decide(true);
    }
    
    @DeleteMapping("remove/{id}")
    @Logging(title = "删除角色")
    public Result remove(@PathVariable Long id)
    {
        
        //TODO:这里要看，有没有关联，如果有的话，不能删除
        //FIXME:栏目删除为逻辑删除
        Column columndb = columnRepository.findUniqueBy("id", id, Column.class);
        columndb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
        columnRepository.update(columndb);
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
            DetachedCriteria dc = DetachedCriteria.forClass(Column.class);
            dc.add(Restrictions.in("id", cdids));
            List<Column> columnList = columnRepository.findByCriteria(dc);
            for (Column columndb : columnList)
            {
                columndb.setDeltag(NewsDicConstants.ICommon.DELETE_YES);
                columnRepository.update(columndb);
            }
            return decide(true);
        }
        return decide(false);
    }
    
    @GetMapping("listcolumn")
    public Result AppColum(ModelAndView modelAndView)
    {
        String sql = "SELECT f_title as title,f_h5href as path,f_id FROM t_column where f_status =0 and f_deltag =0 order by f_display_order asc";
        List column = columnRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("column", column);
        return success(result);
    }
    
}

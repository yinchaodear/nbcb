package com.yuqiaotech.zsnews.webservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.zsnews.model.HistorySearchRecord;
import com.yuqiaotech.zsnews.model.Material;

@RestController
@RequestMapping("zsnews/material")
public class MaterialService extends BaseController
{
	@Autowired
    private BaseRepository<Material, Long> materialRepository;
	
	/*
	 * 只针对首页的
	 */	
    @GetMapping("materialswiper")
    public Result MaterialSwiper(@RequestParam Long columid,@RequestParam Long channelid)
    {
        System.out.println("MaterialService.MaterialSwiper()"+columid+channelid);
        String sql ="SELECT  t.f_id  ,f_picpath FROM t_pic_mapping  m inner join t_material t on  m.f_material_id = t.f_id  "
        		+ "inner join t_channel c on t.f_channel_id = c.f_id where t.f_type =1 and t.f_channel_id = "+channelid+" and  t.f_status=0 and t.f_column_id  = "+columid;
        List pic = materialRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("pic",pic);
        return success(result);
    }
    
   
    
    
	/*
	 * 只针对那些固定格式的
	 */	
    @GetMapping("materialswipermerchant")
    public Result MaterialSwiperMerchant(@RequestParam Long columid,@RequestParam String type)
    {
        System.out.println("MaterialService.MaterialSwiperMerchant()");
        String sql ="SELECT  t.f_id  ,f_picpath FROM t_pic_mapping m inner join t_material t on "
        		+ "m.f_material_id = t.f_id  where  t.f_type =1 and  t.f_column_id  = "+columid+" and t.f_title = '"+type+"'  and t.f_status=0";
        List pic = materialRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("pic",pic);
        return success(result);
    }
    
    
	/*
	 * 只针对每个栏目的闪屏的
	 */	
    @GetMapping("materialswipercolumn")
    public Result MaterialSwipercolumn(@RequestParam Long columid)
    {
        System.out.println("MaterialService.MaterialSwiper()"+columid);
        String sql ="SELECT  t.f_id  ,f_picpath FROM t_pic_mapping  m inner join t_material t on  m.f_material_id = t.f_id  "
        		+ " where t.f_type =2  and t.f_column_id  = "+columid +" and t.f_status=0";
        List pic = materialRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("pic",pic);
        return success(result);
    }
    /*
     * 开机闪屏图片
     */
    @GetMapping("openImg")
    public Result openImg()
    {
        String sql ="SELECT  t.f_id  ,f_picpath FROM t_pic_mapping  m inner join t_material t on  m.f_material_id = t.f_id  "
                + " where t.f_type =3  and t.f_status=0 ";
        List<Map<String, Object>> list = materialRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("list",list);
        return success(result);
    }
	
}

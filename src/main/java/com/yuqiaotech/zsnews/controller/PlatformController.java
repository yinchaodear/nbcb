package com.yuqiaotech.zsnews.controller;

import java.io.File;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.tools.common.ImgBase64Utils;
import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.zsnews.bean.PlatformBean;
import com.yuqiaotech.zsnews.model.Platform;

/**
 * 平台配置
 */
@RestController
@RequestMapping(value = {"zsnews/platform"})
@CrossOrigin(origins = "*", maxAge = 3600)
public class PlatformController extends BaseController
{
    private static String MODULE_PATH = "zsnews/platform/";
    
    @Autowired
    private BaseRepository<Platform, Long> platformRepository;
    
    @GetMapping("main")
    public ModelAndView main(ModelAndView modelAndView)
    {
        Platform platform = null;
        List<Platform> platformList = platformRepository.findByHql("from Platform");
        if (CollectionUtils.isNotEmpty(platformList))
        {
            platform = platformList.get(0);
        }
        else
        {
            platform = new Platform();
            platform.setTitle("世界浙商网");
            platform = platformRepository.save(platform);
        }
        modelAndView.addObject("platform", platform);
        modelAndView.setViewName(MODULE_PATH + "main");
        return modelAndView;
    }
    
    @PutMapping("update")
    public Result update(@RequestBody PlatformBean platformBean)
    {
        Platform platformdb = platformRepository.findUniqueBy("id", platformBean.getId(), Platform.class);
        BeanUtils.copyProperties(platformBean, platformdb, getNullPropertyNames(platformBean));
        
        if (StringUtils.isNotEmpty(platformBean.getPicname1()))
        {
            File srcFile = new File(
                attachmentRoot + "/platform/" + platformBean.getId() + "/" + platformBean.getPicname1() + ".small");
            if (srcFile != null && srcFile.exists() && srcFile.isFile())
            {
                //保存logo
                platformdb.setLogo(ImgBase64Utils.getImgStr(srcFile));
            }
        }
        else
        {
            platformdb.setLogo("");
        }
        
        platformRepository.update(platformdb);
        return decide(true);
    }
    
    @GetMapping("platform")
    public Result platform()
    {
        Platform platform = null;
        List<Platform> platformList = platformRepository.findByHql("from Platform");
        if (CollectionUtils.isNotEmpty(platformList))
        {
            platform = platformList.get(0);
        }
        return success(platform);
    }
}

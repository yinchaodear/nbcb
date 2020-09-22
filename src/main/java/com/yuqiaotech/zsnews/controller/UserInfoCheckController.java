package com.yuqiaotech.zsnews.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.dao.PaginationSupport;
import com.yuqiaotech.common.web.domain.request.PageDomain;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.common.web.domain.response.ResultTable;
import com.yuqiaotech.zsnews.NewsDicConstants;
import com.yuqiaotech.zsnews.model.UserInfo;
import com.yuqiaotech.zsnews.model.UserInfoCheck;

/**
 * 用户审核控制
 */
@RestController
@RequestMapping("zsnews/userinfocheck")
public class UserInfoCheckController extends BaseController
{
    private static String MODULE_PATH = "zsnews/userinfocheck/";
    
    @Autowired
    private BaseRepository<UserInfo, Long> userInfoRepository;
    
    @Autowired
    private BaseRepository<UserInfoCheck, Long> userInfoCheckRepository;
    
    @GetMapping("main")
    public ModelAndView main()
    {
        return JumpPage(MODULE_PATH + "main");
    }
    
    @GetMapping("data")
    public ResultTable data(UserInfo userInfo, PageDomain pageDomain)
    {
        DetachedCriteria dc = composeDetachedCriteria(userInfo);
        dc.addOrder(Order.desc("created"));
        
        PaginationSupport ps = userInfoRepository.paginateByCriteria(dc, pageDomain.getPage(), pageDomain.getLimit());
        return pageTable(ps.getItems(), ps.getTotalCount());
    }
    
    public DetachedCriteria composeDetachedCriteria(UserInfo userInfo)
    {
        DetachedCriteria dc = DetachedCriteria.forClass(UserInfo.class);
        if (StringUtils.isNotEmpty(userInfo.getUsername()))
        {
            dc.add(Restrictions.or(Restrictions.ilike("username", userInfo.getUsername(), MatchMode.ANYWHERE),
                Restrictions.ilike("newUsername", userInfo.getUsername(), MatchMode.ANYWHERE),
                Restrictions.ilike("remark", userInfo.getUsername(), MatchMode.ANYWHERE),
                Restrictions.ilike("newRemark", userInfo.getUsername(), MatchMode.ANYWHERE)));
        }
        if (userInfo.getStatus() != null)
        {
            if (userInfo.getStatus() == 0)
            {
                dc.add(Restrictions.or(Restrictions.eq("status", NewsDicConstants.IUserInfo.Status.DEFAULT),
                    Restrictions.eq("status", NewsDicConstants.IUserInfo.Status.SUCCESS)));
            }
            else
            {
                dc.add(Restrictions.eq("status", userInfo.getStatus()));
            }
        }
        dc.add(Restrictions.eq("deltag", NewsDicConstants.ICommon.DELETE_NO));
        return dc;
    }
    
    @GetMapping("check/{uid}")
    public Result check(@PathVariable Long uid, Integer status, String msg)
    {
        UserInfo userInfo = userInfoRepository.findUniqueBy("id", uid, UserInfo.class);
        UserInfoCheck uic = new UserInfoCheck();
        uic.setUserinfoId(userInfo.getId());
        uic.setUsername(userInfo.getNewUsername());
        uic.setAvatar(userInfo.getNewAvatar());
        uic.setRemark(userInfo.getNewRemark());
        uic.setNickName(userInfo.getNewNickName());
        uic.setChecker(getCurrentUser());
        uic.setCheckResult(status);
        uic.setCheckTime(new Date());
        uic.setCheckMsg(msg);
        userInfoCheckRepository.save(uic);
        
        if (status == NewsDicConstants.IUserInfo.Status.SUCCESS)
        {
            if (StringUtils.isNotEmpty(userInfo.getNewAvatar()))
            {
                userInfo.setAvatar(userInfo.getNewAvatar());
            }
            if (StringUtils.isNotEmpty(userInfo.getNewRemark()))
            {
                userInfo.setRemark(userInfo.getNewRemark());
            }
            if (StringUtils.isNotEmpty(userInfo.getNewUsername()))
            {
                userInfo.setUsername(userInfo.getNewUsername());
            }
            if (StringUtils.isNotEmpty(userInfo.getNewNickName()))
            {
                userInfo.setNickName(userInfo.getNewNickName());
            }
        }
        userInfo.setStatus(status);
        userInfoRepository.update(userInfo);
        
        return success();
    }
}

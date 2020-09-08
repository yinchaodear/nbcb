
package com.yuqiaotech.zsnews.webservice;

import com.yuqiaotech.common.web.base.BaseController;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.common.web.domain.response.Result;
import com.yuqiaotech.zsnews.model.HistorySearchRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping(value = {"zsnews/history", "ws/history"})
public class HistoryService extends BaseController
{
    private static String MODULE_PATH = "zsnews/history/";
    
    @Autowired
    private BaseRepository<HistorySearchRecord, Long> historySearchRecordRepository;

    /*
     * 查询自己的搜索历史
     */
    @GetMapping("historyList")
    public Result AppHistoryData()
    {
        System.out.println("HistoryService.AppHistoryData()"+getCurrentUserId());
        String sql ="SELECT f_content FROM t_history_search_record where f_user_info_id = "+getCurrentUserId();
        List history = historySearchRecordRepository.findMapByNativeSql(sql);
        Map result = new HashMap<>();
        result.put("history",history);
        return success(result);
    }
   
    
    /*
     * 删除历史
     */
    @GetMapping("deleteHistory")
    public Result deleteHistory()
    {
        System.out.println("HistoryService.deleteHistory()");
        String sql ="delete FROM t_history_search_record where f_user_info_id = "+getCurrentUserId();
        historySearchRecordRepository.executeUpdateByNativeSql(sql, null);
        Map result = new HashMap<>();
        result.put("msg", 1);
        return success(result);
    }
   
    
  
}

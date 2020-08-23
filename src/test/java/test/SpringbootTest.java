package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yuqiaotech.YuqiaoApplication;
import com.yuqiaotech.common.web.base.BaseRepository;
import com.yuqiaotech.sysadmin.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = YuqiaoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootTest
{
    @Autowired
    private BaseRepository<User, Long> userRepository;
    
    @Test
    @Transactional(readOnly = false)
    public void testinsert()
    {
        for (int i = 1; i <= 5; i++)
        {
            User user1 = new User();
            user1.setUsername("test" + i);
            user1.setPassword(new BCryptPasswordEncoder().encode("pwd" + i));
            
            user1 = userRepository.save(user1);
            
            System.out.println(user1.getId());
        }
    }
    
    @Test
    @Transactional(readOnly = true)
    public void testfindunique()
    {
        User u = userRepository.findUniqueBy("username", "hxx", User.class);
        System.out.println(u.getPassword());
    }
    
    @Test
    @Transactional(readOnly = true)
    public void testfindall()
    {
        List<User> userlist = userRepository.findByCriteria(DetachedCriteria.forClass(User.class));
        //        List<User> userlist = userRepository.findByHql("from User");
        for (User user : userlist)
        {
            System.out.println(user.getUsername());
        }
    }
    
    @Test
    @Transactional(readOnly = false)
    public void testdelete()
    {
        User u = userRepository.get(1L, User.class);
        if (u != null)
        {
            System.out.println("username:" + u.getUsername() + ",userid:" + u.getId());
            userRepository.delete(u);
        }
        else
        {
            System.out.println("user is null");
        }
    }
    
    @Test
    @Transactional(readOnly = false)
    public void testupdate()
    {
        User u = userRepository.get(2L, User.class);
        u.setAbout("about");
        u.setUsername("hxx2-update");
        userRepository.update(u);
    }
    
    @Test
    @Transactional(readOnly = false)
    public void testUpdateByHql()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("username", "hxx2");
        params.put("id", 2L);
        int rst = userRepository.executeUpdate("update User set username=:username where id=:id", params);
        System.out.println(rst);
    }
    
    @Test
    @Transactional(readOnly = false)
    public void testUpdateBySql()
    {
        Map<String, Object> params = new HashMap<>();
        params.put("f_tel", "");
        params.put("f_id", 2);
        String sql = "update t_app_user set f_tel=:f_tel where f_id=:f_id";
        userRepository.executeUpdateByNativeSql(sql, params);
    }
    
    @Test
    @Transactional(readOnly = false)
    public void testQueryPage()
    {
        List<User> userList = userRepository.findByHql("from User", 50, 5);
        for (User user : userList)
        {
            System.out.println(user.getId() + "," + user.getUsername());
        }
    }
    
}

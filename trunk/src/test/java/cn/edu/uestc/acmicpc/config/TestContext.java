package cn.edu.uestc.acmicpc.config;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.oj.service.impl.UserServiceImpl;
import cn.edu.uestc.acmicpc.service.JudgeService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;

@Configuration
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.db",
    "cn.edu.uestc.acmicpc.util",
    "cn.edu.uestc.acmicpc.service",
    "cn.edu.uestc.acmicpc.oj.service",
    "cn.edu.uestc.acmicpc.training"
})
@PropertySource("classpath:resources.properties")
@EnableTransactionManagement
public class TestContext extends ApplicationContextConfig {

  @Bean
  @Autowired
  public UserService realUserService(@Qualifier("mockUserDAO") IUserDAO userDAO,
      @Qualifier("mockGlobalService") GlobalService globalService) {
    return new UserServiceImpl(userDAO, globalService);
  }

  @Bean
  public UserService mockUserService() {
    return mock(UserService.class);
  }

  @Bean
  public GlobalService mockGlobalService() {
    return mock(GlobalService.class);
  }

  @Bean
  public IUserDAO mockUserDAO() {
    return mock(IUserDAO.class);
  }

  @Bean
  @Override
  public JudgeService judgeService() {
    return null;
  }
}
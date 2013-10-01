package cn.edu.uestc.acmicpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.edu.uestc.acmicpc.judge.JudgeService;

/** Integration test context configurations. */
@Configuration
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.db",
    "cn.edu.uestc.acmicpc.util",
    "cn.edu.uestc.acmicpc.service",
    "cn.edu.uestc.acmicpc.web.oj.service",
    "cn.edu.uestc.acmicpc.web.training"
})
@PropertySource("classpath:resources.properties")
@EnableTransactionManagement
public class IntegrationTestContext extends ApplicationContextConfig {

  @Bean(name = "judgeService")
  @Override
  public JudgeService judgeService() {
    return null;
  }
}

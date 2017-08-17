package com.thinkgem.jeesite.modules.wechat.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

  
/**
 * AbstractTransactionalJUnit4SpringContextTests:
 * 				这个类为我们解决了在web.xml中配置OpenSessionInview
 * 				所解决的session生命周期延长的问题，所以要继承这个类。该类已经在类级别预先配置了好了事物支持，
 * 				因此不必再配置@Transactional和@RunWith
 * @RunWith(SpringJUnit4ClassRunner.class)
 * @Transactional 
 * 				纾解是为了防止对数据进行污染，使用这个注解完成功能测试又不影响原有数据，对于测试库和开发库没有分
 * 				开的情况下是一个很不错的选择
 * @TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
 * 				这里的事务关联到配置文件中的事务控制器（transactionManager = "transactionManager"），
 * 				同时指定自动回滚（defaultRollback = true）。这样做操作的数据才不会污染数据库！
 * @author Administrator
 *
 */
@ContextConfiguration(locations={"classpath*:beans.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)  
public abstract class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests{

}

package com.course.common.core.constant;

/**
 * 配置Security相关常用属性
 * 
 * @author qinlei
 * @date 2021/8/2 下午1:17
 */
public interface SecurityConstants {
	/**
	 * 自定义登录url
	 */
	String LOGIN_PAGE = "/token/login";
	/**
	 * 自定义登录执行url
	 */
	String LOGIN_PROCESS_URL = "/token/doLogin";
	/**
	 * 自定义退出url
	 */
	String LOGOUT_URL = "/token/logout";
	/**
	 * 不需要鉴权的url，认证服务器
	 */
	String[] permitAllUrls = { LOGIN_PAGE, LOGIN_PROCESS_URL, LOGOUT_URL, "/actuator/**" };

	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";

	/***
	 * 资源服务器默认bean名称
	 */
	String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_FIND_STATEMENT = "select "
			+ "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove from tb_oauthclient";
	/**
	 * 默认的查询语句
	 */
	String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	/**
	 * 按条件client_id 查询
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

	// 授权码数据库存储 JdbcAuthorizationCodeServices
	/**
	 * 查询授权码
	 */
	String CODE_STATEMENT_SELECT = "select code, authentication from tb_oauth_code where code = ?";

	/**
	 * 插入授权码
	 */
	String CODE_STATEMENT_INSERT = "insert into tb_oauth_code (code, authentication) values (?, ?)";

	/**
	 * 删除授权码
	 */
	String CODE_STATEMENT_DELETE = "delete from tb_oauth_code where code = ?";

	/**
	 * 用户ID字段
	 */
	String DETAILS_USER_ID = "userId";

	/**
	 * 用户名字段
	 */
	String DETAILS_USERNAME = "userName";

	/**
	 * 用户部门字段
	 */
	String DETAILS_DEPT_ID = "deptId";

	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";
}

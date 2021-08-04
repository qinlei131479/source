package com.course.common.security.constant;

/**
 * 配置Security相关常用属性
 * 
 * @author qinlei
 * @date 2021/8/2 下午1:17
 */
public interface SecurityConstant {

	/**
	 * 默认登录URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";

	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

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
}
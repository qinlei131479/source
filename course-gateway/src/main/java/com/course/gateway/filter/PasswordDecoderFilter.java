package com.course.gateway.filter;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.course.gateway.constant.CommonConstant;
import com.course.gateway.properties.GatewayConfigProperties;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author qinlei
 * @date 2021/7/29 下午4:07
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordDecoderFilter extends AbstractGatewayFilterFactory {

	private final GatewayConfigProperties configProperties;

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();

			// 不是登录请求，直接向下执行
			if (!StrUtil.containsAnyIgnoreCase(request.getURI().getPath(), CommonConstant.OAUTH_TOKEN_URL)) {
				return chain.filter(exchange);
			}

			URI uri = exchange.getRequest().getURI();
			String queryParam = uri.getRawQuery();
			Map<String, String> paramMap = HttpUtil.decodeParamMap(queryParam, CharsetUtil.CHARSET_UTF_8);

			String password = paramMap.get(CommonConstant.PASSWORD);
			if (StrUtil.isNotBlank(password)) {
				try {
					password = decryptAES(password, configProperties.getEncodeKey());
				} catch (Exception e) {
					log.error("密码解密失败:{}", password);
					return Mono.error(e);
				}
				paramMap.put(CommonConstant.PASSWORD, password.trim());
			}
			URI newUri = UriComponentsBuilder.fromUri(uri).replaceQuery(HttpUtil.toParams(paramMap)).build(true)
					.toUri();

			ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(newUri).build();
			return chain.filter(exchange.mutate().request(newRequest).build());
		};
	}

	/**
	 * password解密后传输
	 * 
	 * @param data
	 * @param pass
	 * @return
	 */
	private String decryptAES(String data, String pass) {
		AES aes = new AES(Mode.CBC, Padding.NoPadding, new SecretKeySpec(pass.getBytes(), CommonConstant.KEY_ALGORITHM),
				new IvParameterSpec(pass.getBytes()));
		byte[] result = aes.decrypt(Base64.decode(data.getBytes(StandardCharsets.UTF_8)));
		return new String(result, StandardCharsets.UTF_8);
	}
}

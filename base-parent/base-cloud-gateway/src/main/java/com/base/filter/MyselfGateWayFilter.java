package com.base.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyselfGateWayFilter implements GlobalFilter, Ordered {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

		log.info("我自己的gateway 过滤器");

//		String uname = exchange.getRequest().getQueryParams().getFirst("uname");
//		if (uname == null) {
//			log.info("非法用户");
//			exchange.getResponse().setStatusCode(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
//			return exchange.getResponse().setComplete();
//		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {

		return 0;
	}
	

}

package com.course.biz.sys.entity.config;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.course.common.core.annotation.ValidExpress;
import com.course.common.core.entity.Req;
import com.course.common.core.valid.ReqFieldExpress;
import com.course.biz.sys.enums.ApiRunModeEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author qinlei
 * @date 2021/6/28 下午3:41
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApiConfig extends Req<ApiConfig> {

	@NotBlank(message = "请选择运行模式", groups = { Create.class, Update.class })
	@ReqFieldExpress(label = "运行模式", type = ReqFieldExpress.TYPE_RADIO, enumClazzs = { ApiRunModeEnum.class })
	private String runMode;

	@ReqFieldExpress(label = "本地接口版本")
	@NotNull(message = "不允许为空", groups = { Create.class, Update.class })
	@ValidExpress(enableField = "runMode", enableValues = { "develop", "test" })
	private String versionLocal;

	@ReqFieldExpress(label = "生产接口版本")
	@NotNull(message = "不允许为空", groups = { Create.class, Update.class })
	@ValidExpress(enableField = "runMode", enableValues = { "product" })
	private String versionProduct;

	@NotBlank(message = "不允许为空", groups = { Create.class, Update.class })
	@ValidExpress(enableField = "runMode", enableValues = { "develop", "test" })
	@ReqFieldExpress(label = "生产接口url")
	private String urlProduct;
}

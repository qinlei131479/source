package com.course.code;

import com.course.generator.GeneratorCodeApplication;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import java.io.IOException;

/**
 * @author qinlei
 * @date 2021/6/20 上午10:52
 */
@SpringBootTest(classes = GeneratorCodeApplication.class)
public class Tests {

    @Test
    public void test() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:/ftl/code/");
        factory.setDefaultEncoding("UTF-8");
        Configuration cfg = factory.createConfiguration();
        Template t = cfg.getTemplate("entity.txt", "UTF-8");
        t.getName();
    }
}

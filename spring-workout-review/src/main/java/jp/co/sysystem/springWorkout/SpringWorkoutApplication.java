package jp.co.sysystem.springWorkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class SpringWorkoutApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

  @Autowired
  private MessageSource messageSource;

  /**
   * Cookie名の設定
   */
  private String COOKIE_NAME = "_sp_workout";

  /**
    * SpringBoot主処理
    * @param args
    */
  public static void main(String[] args) {
    SpringApplication.run(SpringWorkoutApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(SpringWorkoutApplication.class);
  }

  /**
   * LocalValidatorFactoryBeanのsetValidationMessageSourceで
   * バリデーションメッセージをValidationMessages.propertiesからSpringの
   * MessageSource(messages.properties)に上書きする
   *
   * @return localValidatorFactoryBean
   */
  @Bean
  public LocalValidatorFactoryBean validator() {
    LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
    localValidatorFactoryBean.setValidationMessageSource(messageSource);
    return localValidatorFactoryBean;
  }

  /* (非 Javadoc)
   * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#getValidator()
   */
  @Override
  public Validator getValidator() {
    return validator();
  }

  /**
   * Cookie名の設定
   * @return
   */
  @Bean
  public ServletContextInitializer servletContextInitializer() {
    return servletContext -> servletContext.getSessionCookieConfig().setName(COOKIE_NAME);
  }
}

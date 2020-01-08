package epsilon.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import epsilon.controller.MonitorController;
import orion.core.Constant;
import orion.navigation.Navigation;

public class ApplicationContextListener implements ServletContextListener {

	Scheduler scheduler;

	public void contextInitialized(ServletContextEvent servletContextEvent) {

		Constant.setContextPath(servletContextEvent.getServletContext().getContextPath());

		Injector injector = Guice.createInjector(new AbstractModule() {
			protected void configure() {
				install(new epsilon.persistence.PersistenceModule());
			}
		});

		common.Core.setInjector(injector);

		Navigation.configuration( //
				MonitorController.class //
		);

		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try {
			if (scheduler != null) {
				scheduler.shutdown();
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}

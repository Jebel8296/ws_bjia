package com.chinamcom.framework.common.container;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

/**
 * 
 * @author fattiger.xiaoyang
 * @date 2016/07/18
 */
public class Application {
	private static Logger log = Logger.getLogger(Application.class);
	
	public static void run(List<Verticle> verticles, VertxOptions options, DeploymentOptions deploymentOptions){
		if (options == null) {
			options = new VertxOptions();
		}
		Consumer<Vertx> runner = vertx -> {
			try {
				for(Verticle verticle: verticles){
					if (deploymentOptions != null) {
						vertx.deployVerticle(verticle, deploymentOptions);
					} else {
						vertx.deployVerticle(verticle);
					}
				}
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
				t.printStackTrace();
			}
		};
		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					Vertx vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
					log.error(res.cause().getMessage(), res.cause());
				}
			});
		} else {
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
	
	public static void run(Verticle verticle, VertxOptions options, DeploymentOptions deploymentOptions){
		if (options == null) {
			options = new VertxOptions();
		}
		Consumer<Vertx> runner = vertx -> {
			try {
				if (deploymentOptions != null) {
					vertx.deployVerticle(verticle, deploymentOptions);
				} else {
					vertx.deployVerticle(verticle);
				}
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
				t.printStackTrace();
			}
		};
		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					Vertx vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
					log.error(res.cause().getMessage(), res.cause());
				}
			});
		} else {
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
	
	public static void run(String verticleID, VertxOptions options, DeploymentOptions deploymentOptions){
		if (options == null) {
			options = new VertxOptions();
		}
		Consumer<Vertx> runner = vertx -> {
			try {
				if (deploymentOptions != null) {
					vertx.deployVerticle(verticleID, deploymentOptions);
				} else {
					vertx.deployVerticle(verticleID);
				}
			} catch (Throwable t) {
				log.error(t.getMessage(), t);
				t.printStackTrace();
			}
		};
		if (options.isClustered()) {
			Vertx.clusteredVertx(options, res -> {
				if (res.succeeded()) {
					Vertx vertx = res.result();
					runner.accept(vertx);
				} else {
					res.cause().printStackTrace();
					log.error(res.cause().getMessage(), res.cause());
				}
			});
		} else {
			Vertx vertx = Vertx.vertx(options);
			runner.accept(vertx);
		}
	}
	
	  public static void run(String exampleDir, String verticleID, VertxOptions options, DeploymentOptions deploymentOptions) {
		    if (options == null) {
		      options = new VertxOptions();
		    }
		    try {
		      File current = new File(".").getCanonicalFile();
		      if (exampleDir.startsWith(current.getName()) && !exampleDir.equals(current.getName())) {
		        exampleDir = exampleDir.substring(current.getName().length() + 1);
		      }
		    } catch (IOException e) {
		    	
		    }
		    System.setProperty("vertx.cwd", exampleDir);
		    Consumer<Vertx> runner = vertx -> {
		      try {
		        if (deploymentOptions != null) {
		          vertx.deployVerticle(verticleID, deploymentOptions);
		        } else {
		          vertx.deployVerticle(verticleID);
		        }
		      } catch (Throwable t) {
		        t.printStackTrace();
		      }
		    };
		    if (options.isClustered()) {
		      Vertx.clusteredVertx(options, res -> {
		        if (res.succeeded()) {
		          Vertx vertx = res.result();
		          runner.accept(vertx);
		        } else {
		          res.cause().printStackTrace();
		        }
		      });
		    } else {
		      Vertx vertx = Vertx.vertx(options);
		      runner.accept(vertx);
		    }
		  }
}

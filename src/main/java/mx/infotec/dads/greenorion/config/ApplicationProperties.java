package mx.infotec.dads.greenorion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private final Deployment deployment = new Deployment();

	public static class Deployment {
		private String projectUrl;

		public String getProjectUrl() {
			return projectUrl;
		}

		public void setProjectUrl(String projectUrl) {
			this.projectUrl = projectUrl;
		}
	}

	public Deployment getDeployment() {
		return deployment;
	}
}

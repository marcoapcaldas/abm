package de.fraunhofer.abm.hermes.impl;

import java.io.BufferedReader;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.fraunhofer.abm.builder.api.BuildUtils;
import de.fraunhofer.abm.builder.docker.base.AbstractHermesStep;

public class DeleteHermesContainer extends AbstractHermesStep<Void> {

	private static final transient Logger logger = LoggerFactory.getLogger(DeleteHermesContainer.class);
	private String containerName;
	private String repoDir;
	BufferedReader r, e;
	String line;

	public DeleteHermesContainer(String repoDir) {
		super(repoDir);
		this.repoDir = repoDir;
		this.name = "Delete Hermes Container";
		// TODO Auto-generated constructor stub
	}

	@Override
	public Void execute() throws InterruptedException {
		setStatus(STATUS.IN_PROGRESS);
		logger.info("Deleting Hermes container {}", containerName);

		try {

			Result result = exec("docker rm " + containerName, new File(repoDir));
			output = result.stdout;
			errorOutput = result.stderr;
			setStatus(result.exitValue == 0 ? STATUS.SUCCESS : STATUS.FAILED);
		} catch (Throwable t) {
			logger.error("Couldn't delete Hermes container: " + containerName, t);
			errorOutput = BuildUtils.createErrorString("Couldn't delete Hermes container:" + containerName, t);
			setThrowable(t);
		}
		return null;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

}
